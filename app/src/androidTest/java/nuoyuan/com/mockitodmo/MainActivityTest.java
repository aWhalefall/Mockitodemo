package nuoyuan.com.mockitodmo;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void initEspross() {
        Espresso.registerIdlingResources(MockRemoteResource.getIdlingResource());
    }

    @After
    public void DestroyEspross() {
        Espresso.unregisterIdlingResources(MockRemoteResource.getIdlingResource());
    }


    @Test
    public void mainActivityTest() {
        //模拟点击
        ViewInteraction button = onView(
                allOf(withId(R.id.button), isDisplayed()));
        button.perform(click());

        //再次模拟点击
        button.perform(click());

    }

}
