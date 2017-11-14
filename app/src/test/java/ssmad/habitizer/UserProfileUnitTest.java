/*
 *  Class Name: UserProfileUnitTest
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
 * Created by cryst on 11/12/2017.
 */

/**
 * For testing UserProfile
 * @author Simon
 * @version 0.5
 * @see UserProfile
 * @since 0.5
 */
public class UserProfileUnitTest {
    /**
     * Tests getters
     * @throws Exception
     */
    @Test
    public void testUGetters() throws Exception {
        byte[] testbyte = {15, 15};
        UserProfile testuser = new UserProfile("SSMAD", testbyte, "Sadman", "1996-01-01", "Male");

        assertEquals("SSMAD", testuser.getUsername());
        assertEquals(testbyte, testuser.getPortrait());
        assertEquals("Sadman", testuser.getName());
        assertEquals("1996-01-01", testuser.getBirthday());
        assertEquals("Male", testuser.getGender());
    }

    /**
     * Tests setters
     * @throws Exception
     */
    public void testUSetters() throws Exception {
        byte[] testbyte = {15, 15};
        UserProfile testuser = new UserProfile("SSMAD", testbyte, "Sadman", "1996-01-01", "Male");

        byte[] newbyte = {99, 99};
        testuser.setName("Agirl");
        testuser.setBirthday("2010-05-15");
        testuser.setGender("Female");
        testuser.setPortrait(newbyte);

        assertEquals(newbyte, testuser.getPortrait());
        assertEquals("Agirl", testuser.getName());
        assertEquals("2010-05-15", testuser.getBirthday());
        assertEquals("Female", testuser.getGender());



    }

}
