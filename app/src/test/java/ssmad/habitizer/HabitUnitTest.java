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
 * Created by cryst on 11/10/2017.
 */

/**
 * Tests Habits
 * @author Simon
 * @version 0.5
 * @see Habit
 * @since 0.5
 */
public class HabitUnitTest {
    /**
     * Tests getters
     * @throws Exception
     */
    @Test
    public void testHGetters() throws Exception {
        Date cal = Calendar.getInstance().getTime();
        Habit habit = new Habit("Push-Ups", cal, "Big Arms" );

        assertEquals("Push-Ups", habit.getTitle());
        assertEquals("Big Arms", habit.getReason());
        assertEquals(cal, habit.getStartDate());
    }

    /**
     * Tests setters
     * @throws Exception
     */
    @Test
    public void testHSetters() throws Exception {
        Date cal = Calendar.getInstance().getTime();
        Habit habit = new Habit("Push-Ups", cal, "Big Arms");

        habit.setTitle("Sit-Ups");
        habit.setReason("Flat Stomach");
        Date cal2 = Calendar.getInstance().getTime();
        habit.setStartDate(cal2);

        assertEquals("Sit-Ups", habit.getTitle());
        assertEquals("Flat Stomach", habit.getReason());
        assertEquals(cal2, habit.getStartDate());
    }


}

