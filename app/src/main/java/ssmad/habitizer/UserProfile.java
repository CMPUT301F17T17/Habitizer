/*
 *  Class Name: UserProfile
 *  Version: 0.5
 *  Date: November 13th, 2017
 *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  You may use, distribute, or modify this code under terms and conditions of the
 *  Code of Students Behaviour at University of Alberta
 */

package ssmad.habitizer;

import android.graphics.Bitmap;

/**
 * Created by Andoryu on 2017-11-11.
 */

/**
 * Represents a User
 * @author Andrew
 * @version 0.5
 * @since 0.5
 */
public class UserProfile {
    private String username;
    private byte[] portrait;
    private String name;
    private String birthday;
    private String gender;

    /**
     * Constructor with username, picture, name, birthday, and gender
     * @param username
     * @param portrait
     * @param name
     * @param birthday
     * @param gender
     */
    public UserProfile(String username, byte[] portrait, String name, String birthday, String gender){
        this.username = username;
        this.portrait = portrait;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
    }

    /**
     * Sets picture
     * @param portrait
     */
    public void setPortrait(byte[] portrait){
        this.portrait = portrait;
    }

    /**
     * Sets name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets birthday
     * @param birthday
     */
    public void setBirthday(String birthday){
        this.birthday = birthday;
    }

    /**
     * Sets gender
     * @param gender
     */
    public void setGender(String gender){
        this.gender = gender;
    }

    /**
     * Gets username
     * @return
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * Gets picture
     * @return
     */
    public byte[] getPortrait(){
        return this.portrait;
    }

    /**
     * Gets name
     * @return
     */
    public String getName(){
        return this.name;
    }

    /**
     * Gets birthday
     * @return
     */
    public String getBirthday(){
        return this.birthday;
    }

    /**
     * Gets gender
     * @return
     */
    public String getGender(){
        return this.gender;
    }
}
