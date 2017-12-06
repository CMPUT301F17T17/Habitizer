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
 * Created by Andoryu on 2017-11-10.
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

    public Account(String username, String password, byte[] portrait, String name, String birthday, String gender){
        this.username = username;
        this.password = password;
        this.portrait = portrait;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
    }

    public Account(String username) {
        this.username = username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public void setUserPassword(String password) {
        this.password = password;
    }

    public String getUserName(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
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
}
