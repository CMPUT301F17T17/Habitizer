
/*
 *  Class Name: Account
 *  Version: 0.5
 *  Date: November 13th, 2017
 *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  You may use, distribute, or modify this code under terms and conditions of the
 *  Code of Students Behaviour at University of Alberta
 */

package ssmad.habitizer;

/**
 * Created by Andoryu on 2017-11-10.
 */

/**
 * Represents an Account
 * @author Andrew
 * @version 0.5
 * @see UserProfile
 * @since 0.5
 */

public class Account {
    private String username;
    private String password;

    /**
     * Constructor with username and password
     * @param username
     * @param password
     */
    public Account(String username, String password){
        this.username = username;
        this.password = password;
    }

    /**
     * Sets Username for this account
     * @param username
     */
    public void setUserName(String username) {
        this.username = username;
    }

    /**
     * Sets Password for this account
     * @param password
     */
    public void setUserPassword(String password) {
        this.password = password;
    }

    /**
     * Gets Username for this account
     * @return
     */
    public String getUserName(){
        return this.username;
    }

    /**
     * Gets Password for this account
     * @return
     */
    public String getPassword(){
        return this.password;
    }

}
