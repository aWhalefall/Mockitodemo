package nuoyuan.com.mockitodmo;


import android.app.Activity;
import android.widget.Toast;

import javax.inject.Inject;

/**
 * Created by weichyang on 2017/3/8.
 */


public class LoginPreSenterImpl implements LoginPresenter {


    public LoginView loginView;
    MockRemoteResource mockRemoteResource;

    @Inject
    public LoginPreSenterImpl(LoginView loginView) {
        this.loginView = loginView;
        mockRemoteResource = new MockRemoteResource();
    }

    //为了测试方便定义的构造
    public LoginPreSenterImpl(LoginView loginView, MockRemoteResource mockRemoteResource) {
        this.loginView = loginView;
        this.mockRemoteResource = mockRemoteResource;
    }

    @Override
    public void Login() {
//        显示加载框
        loginView.showLoading();

//        模拟网络请求
        mockRemoteResource.request(new RequestCallBack() {
            @Override
            public void Success(String info) {
                loginView.hiddenLoading();

                loginView.LoginSuccess(info);
            }

            @Override
            public void Erroe(String info) {
                loginView.hiddenLoading();

                loginView.LoginError(info);
            }
        });
    }

    public void test(Activity activity) {
        Toast.makeText(activity, "注解成功", 1000).show();
    }

}

