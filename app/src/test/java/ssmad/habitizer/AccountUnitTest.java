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
        Account account = new Account("testUsername", "testPassword");
        account.setUserName("newUsername");
        assertEquals("newUsername", account.getUserName());

    }

    /**
     * Tests password getter/setter
     * @throws Exception
     */
    @Test
    public void testPasswordGetAndSet() throws Exception{
        Account account = new Account("testUsername", "testPassword");
        account.setUserPassword("newPassword");
        assertEquals("newPassword", account.getPassword());
    }

}
