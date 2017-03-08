package nuoyuan.com.mockitodmo.moickto;

/**
 * Created by weichyang on 2017/3/8.
 */

class IsValid implements org.mockito.ArgumentMatcher<Integer> {

    @Override
    public boolean matches(Integer o) {
        return o == 1 || o == 2;
    }
}
