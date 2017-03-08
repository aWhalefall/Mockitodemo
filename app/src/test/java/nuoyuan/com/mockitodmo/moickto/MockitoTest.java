package nuoyuan.com.mockitodmo.moickto;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.RETURNS_SMART_NULLS;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by weichyang on 2017/3/8.
 */


public class MockitoTest {


    @Mock
    List mockList;  //进行mockList

    @Mock
    Iterator iterator;

    @Mock
    OutputStream outputStream;

    @Before
    public void initMockito() {

        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void verify_behaivour() {
        //模拟创建一个List对象 使用@      mock

        //使用mock的对象
        mockList.add(0);
        mockList.clear();
        verify(mockList).add(0);
        verify(mockList).clear();
    }


    //模拟我们所期望的结果

    @Test
    public void when_thenReturn() {

        //预设当iterator调用next()时第一次返回hello，第n次都返回world
        when(iterator.next()).thenReturn("hello").thenReturn("world");
        //使用mock的对象
        String result = iterator.next() + " " + iterator.next();
        //验证结果
        Assert.assertEquals("hello world", result);
    }

    @Test(expected = IOException.class)
    public void when_thenThrow() throws IOException {

        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        //预设当流关闭时抛出异常
        doThrow(new IOException()).when(outputStream).close();
        outputStream.close();

    }

    //2.3  RETURNS_SMART_NULLS和RETURNS_DEEP_STUBS

    @Test
    public void returnsSmartNullsTest() {
        //使用RETURNS_SMART_NULLS参数创建的mock对象，不会抛出NullPointerException异常。另外控制台窗口会提示信息“SmartNull returned by unstubbed get() method on mock”        System.out.println(mock.toArray().length);

        List mock = mock(List.class, RETURNS_SMART_NULLS);
        System.out.println(mock.get(0));
    }

    //RETURNS_DEEP_STUBS也是创建mock对象时的备选参数
    //RETURNS_DEEP_STUBS参数程序会自动进行mock所需的对象，方法deepstubsTest和deepstubsTest2是等价的

    @Test
    public void deepstubsTest() {
        Account account = mock(Account.class, RETURNS_DEEP_STUBS);
        when(account.getRailwayTicket().getDestination()).thenReturn("Beiing");
        account.getRailwayTicket().getDestination();
        verify(account.getRailwayTicket()).getDestination();
        Assert.assertEquals("Beiing", account.getRailwayTicket().getDestination());

    }


    @Test
    public void deepstudbTest2() {
        Account account = mock(Account.class);
        RailwayTicket railwayTicket = mock(RailwayTicket.class);
        when(account.getRailwayTicket()).thenReturn(railwayTicket);
        when(railwayTicket.getDestination()).thenReturn("Beijing");
        account.getRailwayTicket().getDestination();
        verify(account.getRailwayTicket()).getDestination();
        Assert.assertEquals("Beijing", account.getRailwayTicket().getDestination());


    }

    // 2.4 模拟方法体抛出异常

    @Test(expected = RuntimeException.class)
    public void doThrow_when() {
        doThrow(new RuntimeException()).when(mockList).add(1);
        mockList.add(1);

    }


}
