package nuoyuan.com.mockitodmo.annotation;

import dagger.Component;
import nuoyuan.com.mockitodmo.MainActivity;

/**
 * Created by weichyang on 2017/3/16.
 */

@Component(
        modules = MainModule.class
)
public interface ActivityComponent {

    void inject(MainActivity activity);
}

