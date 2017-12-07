package ssmad.habitizer;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * Created by cryst on 10/23/2017.
 */

public class HabitTabActivityTest extends ActivityInstrumentationTestCase2<HabitTabActivity>{
    private Solo solo;

    public HabitTabActivityTest() {super(HabitTabActivity.class);}

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }


    public void AddHabitTest(){
        solo.clickOnView(solo.getView(R.id.habit_tab_button));
        solo.assertCurrentActivity("Wrong Activity!", HabitTabActivity.class);
        solo.clickOnView(solo.getView(R.id.add_habit_button));

        solo.waitForActivity("AddHabitActivity");
        solo.assertCurrentActivity("Wrong Activity!", AddHabitActivity.class);
        solo.enterText((EditText) solo.getView(R.id.habit_input), "Push-Ups");
        solo.enterText((EditText) solo.getView(R.id.reason_input), "I want big arms");
        // open up picker and select some date

        //solo.clickOnView(solo.getView(R.id.freq_input));
        // open up picker and select some days
        solo.clickOnView(solo.getView(R.id.confirmH_button));

        solo.waitForActivity("HabitTabActivity");
        assertTrue(solo.waitForText("Push-Ups"));

        solo.clickInList(0);
        solo.waitForActivity("EditHabitActivity");
        assertTrue(solo.waitForText("I want big arms"));

    }


    public void AddHabitEventTest(){
        solo.clickOnView(solo.getView(R.id.habit_tab_button));
        solo.assertCurrentActivity("Wrong Activity!", HabitTabActivity.class);

        assertTrue(solo.waitForText("Push-Ups"));
        assertTrue(solo.waitForText("Due Today"));
        solo.clickInList(0,0);

        solo.waitForActivity("AddHabitEventActivity");
        solo.assertCurrentActivity("Wrong Activity!", AddHabitEventActivity.class);
        solo.enterText((EditText) solo.getView(R.id.comment_input), "I DID ABOUT 7!");
        //solo.clickOnView(solo.getView(R.id.confirmE_button));

        solo.waitForActivity("HabitTabActivity");
        solo.assertCurrentActivity("Wrong Activity!", HabitTabActivity.class);
        assertFalse(solo.waitForText("Due Today"));

    }

    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

}
