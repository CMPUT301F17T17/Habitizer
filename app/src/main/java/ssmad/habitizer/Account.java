/*
 *
 *  *  Class Name:
 *  *  Version: 1.0
 *  *  Date: December 6th, 2017
 *  *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  *  You may use, distribute, or modify this code under terms and conditions of the
 *  *  Code of Students Behaviour at University of Alberta
 *
 */

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

/**
 * Represents an Account
 * @author Andrew
 * @version 1.0
 * @see UserProfile
 * @since 0.5
 */

public class Account{
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

    /**
     * Constructor with username
     * @param username
     */
    public Account(String username) {
        this.username = username;
    }

    /**
     * For setting username
     * @param username
     */
    public void setUserName(String username) {
        this.username = username;
    }

    /**
     * For setting password
     * @param password
     */
    public void setUserPassword(String password) {
        this.password = password;
    }

    /**
     * For getting username
     * @return
     */
    public String getUserName(){
        return this.username;
    }

    /**
     * For getting password
     * @return
     */
    public String getPassword(){
        return this.password;
    }

    /**
     * For setting portrait
     * @param portrait
     */
    public void setPortrait(byte[] portrait){
        this.portrait = portrait;
    }

    /**
     * For setting name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * For setting birthday
     * @param birthday
     */
    public void setBirthday(String birthday){
        this.birthday = birthday;
    }

    /**
     * For setting Gender
     * @param gender
     */
    public void setGender(String gender){
        this.gender = gender;
    }

    /**
     * For getting username
     * @return
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * For getting portrait
     * @return
     */
    public byte[] getPortrait(){
        return this.portrait;
    }

    /**
     * For getting name
     * @return
     */
    public String getName(){
        return this.name;
    }

    /**
     * For getting birthday
     * @return
     */
    public String getBirthday(){
        return this.birthday;
    }

    /**
     * For getting gender
     * @return
     */
    public String getGender(){
        return this.gender;
    }

    /**
     * For getting followers
     * @return
     */
    public String[] getFollowers() {
        return followers;
    }

    /**
     * For setting followers
     * @param followers
     */
    public void setFollowers(String[] followers) {
        this.followers = followers;
    }

    /**
     * For getting following
     * @return
     */
    public String[] getFollowing() {
        return following;
    }

    /**
     * For setting following
     * @param following
     */
    public void setFollowing(String[] following) {
        this.following = following;
    }

    /**
     * For getting follow requests
     * @return
     */
    public String[] getRequests() {
        return requests;
    }

    /**
     * For setting follow requests
     * @param requests
     */
    public void setRequests(String[] requests) {
        this.requests = requests;
    }

    /**
     * For getting sent follow requests
     * @return
     */
    public String[] getSent_requests() {
        return sent_requests;
    }

    /**
     * For setting sent follow requests
     * @param sent_requests
     */
    public void setSent_requests(String[] sent_requests) {
        this.sent_requests = sent_requests;
    }
}
