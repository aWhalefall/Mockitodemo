package nuoyuan.com.mockitodmo.moickto;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import nuoyuan.com.mockitodmo.moickto.arg.Person;
import nuoyuan.com.mockitodmo.moickto.arg.PersonDao;
import nuoyuan.com.mockitodmo.moickto.arg.PersonService;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by weichyang on 2017/3/8.
 * 使用mock 注解
 * 1.设置 runWith（MockitoJUnitRunner.class）
 * 2.@before  { MokeitoAnnotations.initMocks(this)}
 */

@RunWith(MockitoJUnitRunner.class)
public class MockitoTest2 {


    // @before  { MokeitoAnnotations.initMocks(this)}
//    @Before
//    public void initMockTest2(){
//        MockitoAnnotations.initMocks(this);
//    }

    @Mock
    List mockList;
    @Mock
    Comparator mockComparator;

    @Test
    public void shortHand() {
        mockList.add(1);
        verify(mockList).add(1);
    }

    //2.6 参数匹配

    @Test
    public void with_arguments() {
        Comparable comparable = mock(Comparable.class);
        when(comparable.compareTo("Test")).thenReturn(1);
        when(comparable.compareTo("Omg")).thenReturn(2);
        Assert.assertEquals(1, comparable.compareTo("Test"));
        Assert.assertEquals(2, comparable.compareTo("Omg"));
        //对于没有预设的情况会返回默认值
        Assert.assertEquals(0, comparable.compareTo("Not Stub"));
    }

    //除了匹配制定参数外，还可以匹配自己想要的任意参数
    @Test
    public void with_unspecified_arguments() {

        when(mockList.get(anyInt())).thenReturn(1);
        when(mockList.contains(argThat(new IsValid()))).thenReturn(true);
        Assert.assertEquals(1, mockList.get(1));
        Assert.assertEquals(1, mockList.get(999));
        Assert.assertTrue(mockList.contains(1));
        Assert.assertTrue(mockList.contains(3));
    }

    //注意：如果你使用了参数匹配，那么所有的参数都必须通过matchers来匹配，如下代码


    @Test
    public void all_arguments_provided_by_matchers() {

        mockComparator.compare("nihao", "hello");
        verify(mockComparator).compare(anyString(), eq("hello"));
        //下面的为无效的参数匹配使用
        //verify(mockComparator).compare(anyString(),"hello");
    }


//2.7 自定义参数匹配

    @Test
    public void argumentMatchersTest() {

        when(mockList.addAll(argThat(new IsListofTwoElements()))).thenReturn(true);

//        mockList.addAll(Arrays.asList("one","two","thress"));
        mockList.addAll(Arrays.asList("one", "two"));
        verify(mockList).addAll(argThat(new IsListofTwoElements()));
    }

    // 2.8 捕获参数来进一步断言

    /**
     * 这个方法很重要
     */
    @Test
    public void capturing_args() {

        PersonDao personDao = mock(PersonDao.class);

        PersonService personService = new PersonService(personDao);

        ArgumentCaptor<Person> argumentCaptor = ArgumentCaptor.forClass(Person.class);
        personService.update(1, "jack");
        verify(personDao).update(argumentCaptor.capture());
        Assert.assertEquals(1, argumentCaptor.getValue().getId());
        Assert.assertEquals("jack", argumentCaptor.getValue().getName());
    }

    //  2.9 使用方法预期回调接口生成期望值（Answer结构）
    @Test
    public void answerTest() {
        //使用Answer来生成我们我们期望的返回
        when(mockList.get(anyInt())).thenAnswer(new CustomAnswer());
        Assert.assertEquals("hello world:0", mockList.get(0));
        Assert.assertEquals("hello world:999", mockList.get(999));
    }

    /**
     * 匿名内部类格式
     */
    @Test
    public void answerTest2() {
        //使用Answer来生成我们我们期望的返回
        when(mockList.get(anyInt())).thenAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                Object[] o = invocation.getArguments();
                return "hello world:" + o[0];
            }
        });
        Assert.assertEquals("hello world:0", mockList.get(0));
        Assert.assertEquals("hello world:999", mockList.get(999));
    }

    //2.10 修改对未预设的调用返回默认期望

    @Test
    public void unstubbed_Invocations() {
        List list = mock(List.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return 999;
            }
        });
        Assert.assertEquals(999, list.get(1));
        Assert.assertEquals(999, list.size());
    }

    //2.11 用spy监控真实对象

    @Test(expected = IndexOutOfBoundsException.class)
    public void spy_on_real_objects() {
        List list = new LinkedList();
        List spy = spy(list);
        //下面预设的spy.get(0)会报错，因为会调用真实对象的ge t(0)，所以会抛出越界异常
        // when(spy.get(0)).thenReturn(3);

        //使用doReturn-when可以避免when-thenReturn调用真实对象api
        doReturn(999).when(spy).get(999);
        //预设size()期望值
        when(spy.size()).thenReturn(100);

        spy.add(1);
        spy.add(2);
        Assert.assertEquals(100, spy.size());
        Assert.assertEquals(1, spy.get(0));
        Assert.assertEquals(2, spy.get(1));

        verify(spy).add(1);
        verify(spy).add(2);
        Assert.assertEquals(999, spy.get(999));

        /**
         * 会报角标越界异常
         */
        int dd = (int) spy.get(2);
        Assert.assertEquals(10, dd);

    }

    @Test
    public void real_partial_mock() {

        List list = spy(ArrayList.class);
        Assert.assertEquals(0, list.size());
        /**
         * 调用真实的对象方法
         */
        A a = mock(A.class);
        Assert.assertEquals(999, a.doSomething(999));
    }

//    2.13 重置mock

    @Test
    public void reset_mock() {
        when(mockList.size()).thenReturn(10);
        mockList.add(1);
        Assert.assertEquals(10, mockList.size());
        //重置mock，清除所有的互动和预设        reset(list);
        reset(mockList);
        Assert.assertEquals(0, mockList.size());
    }

    @Test
    public void verifying_number_of_invocations() {

        List list = mock(List.class);
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);
        list.add(3);
        list.add(3);

        verify(list).add(1);
        //验证是否被调用一次，等效于下面的times(1)
        verify(list, times(1)).add(1);

        verify(list, times(2)).add(2);
        verify(list, times(3)).add(3);
        //验证是否从未被调用过
        verify(list, never()).add(5);
        //验证至少调用一次
        verify(list, atLeastOnce()).add(1);
        //验证至多调用3次
        verify(list, atMost(3)).add(3);
        //验证至少调用2次
        verify(list, atLeast(2)).add(2);

    }

    // 2.15 连续调用

    //@Test(expected = RuntimeException.class)
    @Test
    public void consecutive_calls() {

        //模拟连续调用返回期望值，如果分开，则只有最后一个有效
        when(mockList.get(0)).thenReturn(0);
        when(mockList.get(0)).thenReturn(1);
        when(mockList.get(0)).thenReturn(2);
        Assert.assertEquals(2, mockList.get(0));

        when(mockList.get(0)).thenReturn(0).thenReturn(1).thenReturn(2).thenThrow(new RuntimeException());

        Assert.assertEquals(0, mockList.get(0));
        Assert.assertEquals(1, mockList.get(0));
        Assert.assertEquals(2, mockList.get(0));
        //第三次或更多调用都会抛出异常
        Assert.assertEquals(2, mockList.get(0));

    }

//    2.5.6 验证执行顺序

    @Test
    public void verification_in_order() {

        List mock1 = mock(List.class);
        List mock2 = mock(List.class);

        mock1.add(1);
        mock2.add("hello");
        mock1.add(2);
        mock2.add("world");
        //将需要排序的mock对象放入InOrder
//        InOrder inOrder=mock(mock.class);
        InOrder inOrder = inOrder(mock1, mock2);

        //下面的代码不能颠倒顺序，验证执行顺序
        inOrder.verify(mock1).add(1);
        inOrder.verify(mock2).add("hello");
        inOrder.verify(mock1).add(2);
        inOrder.verify(mock2).add("world");
    }

//    2.17 确保模拟对象上无互动发生

    @Test
    public void verify_interaction() {

        List l1 = mock(List.class);
        List l2 = mock(List.class);
        List l3 = mock(List.class);

        l1.add(1);
        verify(l1).add(1);
        verify(l2, never()).add(1);
        verifyZeroInteractions(l2, l3);
    }

    //    2.18 找出冗余的互动(即未被验证到的)
    @Test
    public void find_redundant_interaction() {

        List l1 = mock(List.class);

        l1.add(1);
        l1.add(2);
        verify(l1, times(2)).add(anyInt());
        List l2 = mock(List.class);

        l2.add(1);
        l2.add(2);  //没有发生交互
        verify(l2).add(1);
        verifyNoMoreInteractions(l2);
    }

}
