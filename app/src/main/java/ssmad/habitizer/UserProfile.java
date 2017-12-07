/*
 *  Class Name: HabitEvent
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

public class UserProfile {
    private String username;
    private byte[] portrait;
    private String name;
    private String birthday;
    private String gender;

    public UserProfile(String username, byte[] portrait, String name, String birthday, String gender){
        this.username = username;
        this.portrait = portrait;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
    }

    public void setPortrait(byte[] portrait){
        this.portrait = portrait;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(String birthday){
        this.birthday = birthday;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public String getUsername(){
        return this.username;
    }

    public byte[] getPortrait(){
        return this.portrait;
    }

    public String getName(){
        return this.name;
    }

    public String getBirthday(){
        return this.birthday;
    }

    public String getGender(){
        return this.gender;
    }
}
