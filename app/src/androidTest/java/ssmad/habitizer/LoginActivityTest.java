package ssmad.habitizer;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

import org.junit.Test;

/**
 * Created by cryst on 10/22/2017.
 */

public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo solo;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void setUp() throws Exception{
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Test
    public void test2SignUp() {

        solo.assertCurrentActivity("Wrong Activity!", LoginActivity.class);

        // click on buttons
        solo.clickOnView(solo.getView(R.id.signup_btn));
        solo.sleep(50);
        //.clearEditText((EditText) solo.getView(R.id.username_input));
        solo.enterText((EditText) solo.getView(R.id.username_input), "testUser");
        //solo.clearEditText((EditText) solo.getView(R.id.password_input));
        solo.enterText((EditText) solo.getView(R.id.password_input), "testPass");
        solo.clickOnView(solo.getView(R.id.signup_btn));
        solo.sleep(10000);

        //solo.waitForActivity("EditProfileActivity");
        solo.enterText((EditText) solo.getView(R.id.nmText), "testName");
        solo.clearEditText((EditText) solo.getView(R.id.btText));
        solo.enterText((EditText) solo.getView(R.id.btText), "1996-01-01");
        //click confirm
        solo.clickOnView(solo.getView(R.id.confirm_btn));

        solo.sleep(10000);
        solo.waitForActivity("LoginActivity");
        solo.assertCurrentActivity("Wrong Activity!", LoginActivity.class);
    }
    @Test
    public void testLogin3() {

        solo.assertCurrentActivity("Wrong Activity!", LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.username_input));
        solo.enterText((EditText) solo.getView(R.id.username_input), "SSMAD");
        solo.clearEditText((EditText) solo.getView(R.id.password_input));
        solo.enterText((EditText) solo.getView(R.id.password_input), "SSMAD");

        // click on button
        solo.clickOnView(solo.getView(R.id.login_btn));
        solo.sleep(10000);
        solo.waitForActivity("DummyMainActivity");
        solo.assertCurrentActivity("Wrong Activity!", DummyMainActivity.class);
    }
    @Test
    public void test1NoInputLogin(){

        solo.assertCurrentActivity("Wrong Activity!", LoginActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.username_input));
        // click on button
        solo.clickOnView(solo.getView(R.id.login_btn));

        assertTrue(solo.waitForText("Login failed: username or password is incorrect!"));

        solo.assertCurrentActivity("Wrong Activity!", LoginActivity.class);
    }



    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}
