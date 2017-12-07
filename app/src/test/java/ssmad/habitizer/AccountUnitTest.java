/*
 *  Class Name: AccountUnitTest
 *  Version: 0.5
 *  Date: November 13th, 2017
 *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  You may use, distribute, or modify this code under terms and conditions of the
 *  Code of Students Behaviour at University of Alberta
 */

package ssmad.habitizer;

/**
 * Created by cryst on 11/29/2017.
 */

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * For testing Accounts
 * @author Simon
 * @version 0.5
 * @see Account
 * @since 0.5
 */
public class AccountUnitTest {
    /**
     * Tests username getter/setter
     * @throws Exception
     */
    @Test
    public void testUsernameGetAndSet() throws Exception{
        byte[] testbyte = {15, 15};
        Account account = new Account("testUsername", "testPassword", testbyte, "testname", "2000-01-01", "Male");
        account.setUserName("newUsername");
        assertEquals("newUsername", account.getUserName());

    }

    /**
     * Tests password getter/setter
     * @throws Exception
     */
    @Test
    public void testPasswordGetAndSet() throws Exception{
        byte[] testbyte = {15, 15};
        Account account = new Account("testUsername", "testPassword", testbyte, "testname", "2000-01-01", "Male");
        account.setUserPassword("newPassword");
        assertEquals("newPassword", account.getPassword());
    }

    /**
     * Tests portrait getter/setter
     * @throws Exception
     */
    @Test
    public void testPortraitGetAndSet() throws Exception{
        byte[] testbyte = {15, 15};
        Account account = new Account("testUsername", "testPassword", testbyte, "testname", "2000-01-01", "Male");
        byte[] newbyte = {10, 10};
        account.setPortrait(newbyte);
        assertEquals(newbyte, account.getPortrait());
    }

    /**
     * Tests name getter/setter
     * @throws Exception
     */
    @Test
    public void testNameGetAndSet() throws Exception{
        byte[] testbyte = {15, 15};
        Account account = new Account("testUsername", "testPassword", testbyte, "testname", "2000-01-01", "Male");
        account.setName("newName");
        assertEquals("newName", account.getName());
    }

    /**
     * Tests birthday getter/setter
     * @throws Exception
     */
    @Test
    public void testBirthdayGetAndSet() throws Exception{
        byte[] testbyte = {15, 15};
        Account account = new Account("testUsername", "testPassword", testbyte, "testname", "2000-01-01", "Male");
        account.setBirthday("1990-05-05");
        assertEquals("1990-05-05", account.getBirthday());
    }

    /**
     * Tests gender getter/setter
     * @throws Exception
     */
    @Test
    public void testGenderGetAndSet() throws Exception{
        byte[] testbyte = {15, 15};
        Account account = new Account("testUsername", "testPassword", testbyte, "testname", "2000-01-01", "Male");
        account.setGender("Female");
        assertEquals("Female", account.getGender());
    }

}
