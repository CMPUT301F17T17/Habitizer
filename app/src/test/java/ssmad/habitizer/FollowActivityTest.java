package ssmad.habitizer;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by dwai on 2017-10-23.
 */

public class FollowActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;
    
    public FollowActivityTest() {
        super(FollowActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testFollow() {

        solo.assertCurrentActivity("Wrong Activity!", FollowActivity.class);

        // click on button
        solo.clickOnView(solo.getView(R.id.following_button));
        solo.sleep(10000);
        solo.waitForActivity("SocialTabActivity");
        solo.assertCurrentActivity("Wrong Activity!", HabitTabActivity.class);
    }

    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
