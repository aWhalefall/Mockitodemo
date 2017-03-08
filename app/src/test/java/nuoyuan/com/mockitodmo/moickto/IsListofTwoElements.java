package nuoyuan.com.mockitodmo.moickto;

import java.util.List;

/**
 * Created by weichyang on 2017/3/8.
 */

class IsListofTwoElements implements org.mockito.ArgumentMatcher<List> {
    @Override
    public boolean matches(List argument) {
        return argument.size() == 2;
    }
}
