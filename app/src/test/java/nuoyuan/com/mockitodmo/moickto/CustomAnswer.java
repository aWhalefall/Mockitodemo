package nuoyuan.com.mockitodmo.moickto;


import org.mockito.invocation.InvocationOnMock;

/**
 * Created by weichyang on 2017/3/8.
 */

class CustomAnswer implements org.mockito.stubbing.Answer<String> {
    @Override
    public String answer(InvocationOnMock invocation) throws Throwable {
        Object[] args = invocation.getArguments();
        return "hello world:" + args[0];
    }
}
