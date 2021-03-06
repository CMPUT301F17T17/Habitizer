/*
 *  Class Name: AccountUnitTest
 *  Version: 0.5
 *  Date: November 13th, 2017
 *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  You may use, distribute, or modify this code under terms and conditions of the
 *  Code of Students Behaviour at University of Alberta
 */

package ssmad.habitizer;


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
        byte [] testByte = {10, 10};
        Account account = new Account("testUsername", "testPassword", testByte, "Test User", "1996-01-01", "Male");
        account.setUserName("newUsername");
        assertEquals("newUsername", account.getUserName());

    }

    /**
     * Tests password getter/setter
     * @throws Exception
     */
    @Test
    public void testPasswordGetAndSet() throws Exception{
        byte [] testByte = {10, 10};
        Account account = new Account("testUsername", "testPassword", testByte, "Test User", "1996-01-01", "Male");
        account.setUserPassword("newPassword");
        assertEquals("newPassword", account.getPassword());
    }

}
