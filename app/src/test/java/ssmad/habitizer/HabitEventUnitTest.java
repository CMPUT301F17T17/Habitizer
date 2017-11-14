/*
 *  Class Name: HabitEventUnitTest
 *  Version: 0.5
 *  Date: November 13th, 2017
 *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  You may use, distribute, or modify this code under terms and conditions of the
 *  Code of Students Behaviour at University of Alberta
 */

package ssmad.habitizer;

import android.location.Location;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by cryst on 11/10/2017.
 */

@SuppressWarnings("DefaultFileTemplate")
/**
 * For testing Habit Events
 * @author Simon
 * @version 0.5
 * @see HabitEvent
 * @since 0.5
 */
public class HabitEventUnitTest {
    /**
     * Tests getters
     * @throws Exception
     */
    @Test
    public void testHEGetters() throws Exception {
        Date cal = Calendar.getInstance().getTime();
        double [] loc = {15.00, 15.00};
        byte [] testByte = {10, 10};
        HabitEvent habitevent = new HabitEvent("Push-Ups", cal, testByte, loc, "I did 7");

        assertEquals("Push-Ups", habitevent.getTitle());
        assertEquals(cal, habitevent.getCompletionDate());
        assertEquals(testByte, habitevent.getPicBytes());
        assertEquals(loc, habitevent.getLocation());
        assertEquals("I did 7", habitevent.getComment());
    }

    /**
     * Tests Setters
     * @throws Exception
     */
    @Test
    public void testHESetters() throws Exception {
        Date cal = Calendar.getInstance().getTime();
        double [] loc = {15.00, 15.00};
        byte [] testByte = {10, 10};
        HabitEvent habitevent = new HabitEvent("Push-Ups", cal, testByte, loc, "I did 7");


        Date cal2 = Calendar.getInstance().getTime();
        double [] newloc = {20.00, 20.00};
        byte [] newtestByte = {50, 50};

        habitevent.setTitle("Sit-Ups");
        habitevent.setCompletionDate(cal2);
        habitevent.setPicBytes(newtestByte);
        habitevent.setLocation(newloc);
        habitevent.setComment("I did 5!");


        assertEquals("Sit-Ups", habitevent.getTitle());
        assertEquals(cal2, habitevent.getCompletionDate());
        assertEquals(newloc, habitevent.getLocation());
        assertEquals(newtestByte, habitevent.getPicBytes());
        assertEquals("I did 5!", habitevent.getComment());

    }

}
