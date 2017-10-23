package ssmad.habitizer;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by cryst on 10/22/2017.
 */

public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo solo;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }


    public void testSignUp() {

        solo.assertCurrentActivity("Wrong Activity!", LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.username_input));
        solo.enterText((EditText) solo.getView(R.id.username_input), "SSMAD");

        // click on button
        solo.clickOnView(solo.getView(R.id.signup_button));
        solo.sleep(10000);
        solo.waitForActivity("HabitTabActivity");
        solo.assertCurrentActivity("Wrong Activity!", HabitTabActivity.class);
    }

    public void testLogin() {

        solo.assertCurrentActivity("Wrong Activity!", LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.username_input));
        solo.enterText((EditText) solo.getView(R.id.username_input), "SSMAD");

        // click on button
        solo.clickOnView(solo.getView(R.id.login_button));
        solo.sleep(10000);
        solo.waitForActivity("HabitTabActivity");
        solo.assertCurrentActivity("Wrong Activity!", HabitTabActivity.class);
    }

    public void testNoInputLogin(){

        solo.assertCurrentActivity("Wrong Activity!", LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.username_input));
        // click on button
        solo.clickOnView(solo.getView(R.id.login_button));

        assertTrue(solo.waitForText("Please enter a username!"));

        solo.assertCurrentActivity("Wrong Activity!", LoginActivity.class);
    }



    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}
