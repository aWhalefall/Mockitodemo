package nuoyuan.com.mockitodmo;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.test.espresso.IdlingResource;

import java.util.Random;

import nuoyuan.com.mockitodmo.test.SimpleIdlingResource;


/**
 * Created by weichyang on 2017/3/8.
 * 模拟数据请求
 */

public class MockRemoteResource {

    private static final long SERVICE_LATENCY_IN_MILLIS = 3000;
    private static SimpleIdlingResource mIdlingResource;

    public void request(final RequestCallBack callBack) {

        mIdlingResource.setIdleState(false);
        if (new Random().nextInt(2) > 0) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    callBack.Success("success 16156413131313132222222222222222222222222222222222222222222222222222222");
                    // TODO: 2017/3/8 自动化测试
                    mIdlingResource.setIdleState(true);
                }
            }, SERVICE_LATENCY_IN_MILLIS);

        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    callBack.Erroe("Error 4040444444444444444444444444444444444444444444");
                    // TODO: 2017/3/8 自动化测试
                    mIdlingResource.setIdleState(true);
                }
            }, SERVICE_LATENCY_IN_MILLIS);

        }
    }

    @NonNull
    public static IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

}
