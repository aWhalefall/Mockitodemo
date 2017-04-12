package nuoyuan.com.mockitodmo.annotation;


import dagger.Module;
import dagger.Provides;
import nuoyuan.com.mockitodmo.LoginView;

@Module
public class MainModule {

    // mvp view 设置
   public LoginView mView;

    public MainModule(LoginView view) {
        mView = view;
    }

    @Provides
    LoginView provideMainView() {
        return mView;
    }


}