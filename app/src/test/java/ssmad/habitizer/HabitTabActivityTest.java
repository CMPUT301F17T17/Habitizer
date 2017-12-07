/*
 *
 * Class Name:
 * Version: 1.0
 * Date: December 6th, 2017
 * Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the
 * Code of Students Behaviour at University of Alberta
 *
 */

package ssmad.habitizer;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

/**
 * UI Testing of Habit Tab Activities
 * @author Derrick
 * @version 1.0
 * @see HabitTabActivity
 * @since 1.0
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

    /**
     * Tests adding a habit
     */
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


    /**
     * Tests adding a habit event
     */
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
