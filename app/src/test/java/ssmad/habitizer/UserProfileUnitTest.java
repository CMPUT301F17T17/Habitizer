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
     * Tests username getter
     * @throws Exception
     */
    @Test
    public void testUserGetter() throws Exception {
        byte[] testbyte = {15, 15};
        UserProfile testuser = new UserProfile("SSMAD", testbyte, "Sadman", "1996-01-01", "Male");

        assertEquals("SSMAD", testuser.getUsername());
    }

    /**
     * Tests Portrait getter/setter
     * @throws Exception
     */
    @Test
    public void testPortraitGetAndSet() throws Exception {
        byte[] testbyte = {15,15};
        UserProfile testuser = new UserProfile("SSMAD", testbyte, "Sadman", "1996-01-01", "Male");
        byte[] newbyte = {20, 20};
        testuser.setPortrait(newbyte);
        assertEquals(newbyte, testuser.getPortrait());
    }

    /**
     * Tests Name getter/setter
     * @throws Exception
     */
    @Test
    public void testNameGetAndSet() throws Exception{
        byte[] testbyte = {15, 15};
        UserProfile testuser = new UserProfile("SSMAD", testbyte, "Sadman", "1996-01-01", "Male");

        testuser.setName("newName");
        assertEquals("newName", testuser.getName());
    }

    /**
     * Tests Birthday getter/setter
     * @throws Exception
     */
    @Test
    public void testBirthdayGetAndSet() throws Exception{
        byte[] testbyte = {15, 15};
        UserProfile testuser = new UserProfile("SSMAD", testbyte, "Sadman", "1996-01-01", "Male");

        testuser.setBirthday("2001-12-12");
        assertEquals("2001-12-12", testuser.getBirthday());
    }

    /**
     * Tests Gender getter/setter
     * @throws Exception
     */
    @Test
    public void testGenderGetAndSet() throws Exception{
        byte[] testbyte = {15, 15};
        UserProfile testuser = new UserProfile("SSMAD", testbyte, "Sadman", "1996-01-01", "Male");

        testuser.setGender("Female");
        assertEquals("Female", testuser.getGender());
    }

}
