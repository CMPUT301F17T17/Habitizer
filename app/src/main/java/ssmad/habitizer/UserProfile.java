package ssmad.habitizer;

import android.graphics.Bitmap;

import com.google.gson.JsonObject;

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

    public UserProfile(JsonObject userData) {
        //TODO;
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

    public String userId() {
        return "";
    }

    public String getOtherInfo() {
        return "";
    }
}
