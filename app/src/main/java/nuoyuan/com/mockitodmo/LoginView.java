package nuoyuan.com.mockitodmo;

/**
 * Created by weichyang on 2017/3/8.
 */

public interface LoginView {

    void showLoading();

    void hiddenLoading();

    void LoginError(String info);

    void LoginSuccess(String info);

}
