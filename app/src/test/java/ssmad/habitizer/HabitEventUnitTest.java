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
 * For testing Habit Events
 * @author Simon
 * @version 0.5
 * @see HabitEvent
 * @since 0.5
 */
public class HabitEventUnitTest {
    /**
     * Tests title getter/setter
     * @throws Exception
     */
    @Test
    public void testTitleGetAndSet() throws Exception {
        Date cal = Calendar.getInstance().getTime();
        double [] loc = {15.00, 15.00};
        byte [] testByte = {10, 10};
        HabitEvent habitevent = new HabitEvent("Push-Ups", cal, testByte, loc, "I did 7");
        habitevent.setTitle("Sit-ups");
        assertEquals("Sit-ups", habitevent.getTitle());
    }

    /**
     * Tests completion date getter/setter
     * @throws Exception
     */
    @Test
    public void testCompletionDateGetAndSet() throws Exception{
        Date cal = Calendar.getInstance().getTime();
        double [] loc = {15.00, 15.00};
        byte [] testByte = {10, 10};
        HabitEvent habitevent = new HabitEvent("Push-Ups", cal, testByte, loc, "I did 7");

        Date cal2 = Calendar.getInstance().getTime();
        habitevent.setCompletionDate(cal2);
        assertEquals(cal2, habitevent.getCompletionDate());
    }

    /**
     * Tests picture bytes getter/setter
     * @throws Exception
     */
    @Test
    public void testPicBytesGetAndSet() throws Exception{
        Date cal = Calendar.getInstance().getTime();
        double [] loc = {15.00, 15.00};
        byte [] testByte = {10, 10};
        HabitEvent habitevent = new HabitEvent("Push-Ups", cal, testByte, loc, "I did 7");

        byte [] newByte = {20, 20};
        habitevent.setPicBytes(newByte);
        assertEquals(newByte, habitevent.getPicBytes());
    }

    /**
     * Tests location getter/setter
     * @throws Exception
     */
    @Test
    public void testLocationGetAndSet() throws Exception {
        Date cal = Calendar.getInstance().getTime();
        double [] loc = {15.00, 15.00};
        byte [] testByte = {10, 10};
        HabitEvent habitevent = new HabitEvent("Push-Ups", cal, testByte, loc, "I did 7");

        double [] newLoc = {45.00, 45.00};
        habitevent.setLocation(newLoc);
        assertEquals(newLoc, habitevent.getLocation());
    }

    /**
     * Tests comment getter/setter
     * @throws Exception
     */
    @Test
    public void testCommentGetAndSet() throws Exception {
        Date cal = Calendar.getInstance().getTime();
        double [] loc = {15.00, 15.00};
        byte [] testByte = {10, 10};
        HabitEvent habitevent = new HabitEvent("Push-Ups", cal, testByte, loc, "I did 7");

        habitevent.setComment("I did 6...");
        assertEquals("I did 6...", habitevent.getComment());
    }

}
