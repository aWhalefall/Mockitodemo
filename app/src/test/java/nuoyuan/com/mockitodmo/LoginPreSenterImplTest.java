package nuoyuan.com.mockitodmo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * Created by weichyang on 2017/3/8.
 * 模拟mockito进行单元测试
 */
public class LoginPreSenterImplTest {

    @Mock
    LoginView loginView;
    @Mock
    MockRemoteResource mockRemoteResource;
    private LoginPreSenterImpl loginPreSenter;

    @Before
    public void initMock() {
        MockitoAnnotations.initMocks(this);
        loginPreSenter = new LoginPreSenterImpl(loginView, mockRemoteResource);
    }


    @Captor
    public ArgumentCaptor<RequestCallBack> callback;


    @Test
    public void MockLoginSuccess() {

        loginPreSenter.Login();

        verify(loginView).showLoading();
        //捕获回调对象，后面进行操作
        verify(mockRemoteResource).request(callback.capture());

        callback.getValue().Success("Success-------------------------test");

        verify(loginView).hiddenLoading();

        verify(loginView).LoginSuccess(anyString());


    }

    @Test
    public void MockLoginErroe() {

        loginPreSenter.Login();

        verify(loginView).showLoading();
        //捕获回调对象，后面进行操作
        verify(mockRemoteResource).request(callback.capture());

        callback.getValue().Erroe("Error-------------------------test");

        verify(loginView).hiddenLoading();

        verify(loginView).LoginError(anyString());

    }


}