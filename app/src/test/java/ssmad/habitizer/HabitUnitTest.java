/*
 *  Class Name: HabitUnitTest
 *  Version: 0.5
 *  Date: November 13th, 2017
 *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  You may use, distribute, or modify this code under terms and conditions of the
 *  Code of Students Behaviour at University of Alberta
 */

package ssmad.habitizer;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Tests Habits
 * @author Simon
 * @version 0.5
 * @see Habit
 * @since 0.5
 */
public class HabitUnitTest {
    /**
     * Tests title getter/setter
     * @throws Exception
     */
    @Test
    public void testTitleGetAndSet() throws Exception {
        Date cal = Calendar.getInstance().getTime();
        Habit habit = new Habit("Push-Ups", cal, "Big Arms" );

        habit.setTitle("Sit-Ups");
        assertEquals("Sit-Ups", habit.getTitle());
    }

    /**
     * Tests start date getter/setter
     * @throws Exception
     */
    @Test
    public void testStartDateGetAndSet() throws Exception{
        Date cal = Calendar.getInstance().getTime();
        Habit habit = new Habit("Push-Ups", cal, "Big Arms" );

        Date cal2 = Calendar.getInstance().getTime();
        habit.setStartDate(cal2);
        assertEquals(cal2, habit.getStartDate());
    }

    /**
     * Tests reason getter/setter
     * @throws Exception
     */
    @Test
    public void testReasonGetAndSet() throws Exception{
        Date cal = Calendar.getInstance().getTime();
        Habit habit = new Habit("Push-Ups", cal, "Big Arms" );
        habit.setReason("Flat Stomach");
        assertEquals("Flat Stomach", habit.getReason());
    }



}

