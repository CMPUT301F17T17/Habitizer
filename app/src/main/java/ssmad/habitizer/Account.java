<<<<<<< HEAD
/*
 *  Class Name: HabitEvent
=======

/*
 *  Class Name: Account
>>>>>>> master
 *  Version: 0.5
 *  Date: November 13th, 2017
 *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  You may use, distribute, or modify this code under terms and conditions of the
 *  Code of Students Behaviour at University of Alberta
 */
<<<<<<< HEAD
package ssmad.habitizer;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

=======

package ssmad.habitizer;


import android.os.Parcel;
import android.os.Parcelable;
>>>>>>> master
/**
 * Created by Andoryu on 2017-11-10.
 */

<<<<<<< HEAD
public class Account{
=======
/**
 * Represents an Account
 * @author Andrew
 * @version 0.5
 * @see UserProfile
 * @since 0.5
 */

public class Account implements Parcelable{
>>>>>>> master
    private String username;
    private String password;
    private byte[] portrait;
    private String name;
    private String birthday;
    private String gender;
    private String[] followers = {};
    private String[] following = {};
    private String[] requests = {};
    private String[] sent_requests = {};

    /**
     * Constructor with username, password, portrait, name, birthday, and gender
     * @param username
     * @param password
     * @param portrait
     * @param name
     * @param birthday
     * @param gender
     */
    public Account(String username, String password, byte[] portrait, String name, String birthday, String gender){
        this.username = username;
        this.password = password;
        this.portrait = portrait;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
    }

<<<<<<< HEAD
    public Account(String username) {
        this.username = username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public void setUserPassword(String password) {
        this.password = password;
=======
    /**
     * Constructor given parcel
     * @param in
     */
    public Account(Parcel in){
        username = in.readString();
        password = in.readString();
        portrait = new byte[in.readInt()];
        in.readByteArray(portrait);
        name = in.readString();
        birthday = in.readString();
        gender = in.readString();
>>>>>>> master
    }


    @Override
    public int describeContents(){
        return 0;
    }

    /**
     * Writes to parcel
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
        dest.writeByteArray(portrait);
        dest.writeString(name);
        dest.writeString(birthday);
        dest.writeString(gender);
    }

    /**
     * Creates account with parcelable
     */
    public static final Parcelable.Creator<Account> CREATOR = new Parcelable.Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }
        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };


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

<<<<<<< HEAD
    public String getGender(){
        return this.gender;
    }

    public String[] getFollowers() {
        return followers;
    }

    public void setFollowers(String[] followers) {
        this.followers = followers;
    }

    public String[] getFollowing() {
        return following;
    }

    public void setFollowing(String[] following) {
        this.following = following;
    }

    public String[] getRequests() {
        return requests;
    }

    public void setRequests(String[] requests) {
        this.requests = requests;
    }

    public String[] getSent_requests() {
        return sent_requests;
    }

    public void setSent_requests(String[] sent_requests) {
        this.sent_requests = sent_requests;
    }
=======
    /**
     * Sets Portrait for this account
     * @return
     */
    public void setPortrait(byte[] portrait){this.portrait = portrait; }

    /**
     * Sets Name for this account
     * @return
     */
    public void setName(String name) {this.name = name;}

    /**
     * Sets Birthday for this account
     * @return
     */
    public void setBirthday(String birthday){this.birthday = birthday;}

    /**
     * Sets Gender for this account
     * @return
     */
    public void setGender(String gender){this.gender = gender;}

    /**
     * Gets Username for this account
     * @return
     */
    public String getUsername(){return this.username;}

    /**
     * Gets Portrait for this account
     * @return
     */
    public byte[] getPortrait(){return this.portrait;}

    /**
     * Gets Name for this account
     * @return
     */
    public String getName(){return this.name;}

    /**
     * Gets Birthday for this account
     * @return
     */
    public String getBirthday(){return this.birthday;}

    /**
     * Gets Gender for this account
     * @return
     */
    public String getGender(){return this.gender;}
>>>>>>> master
}
