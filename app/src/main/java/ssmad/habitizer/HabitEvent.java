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
import android.location.Location;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sadman on 2017-11-10.
 */

/*
Refs:
https://alvinalexander.com/source-code/android/android-how-load-image-file-and-set-imageview
 */

public class HabitEvent {
    private String title;
    private Date completionDate;

    private String habit_id;
    private String pic_id;

    public String getPic_id() {
        return pic_id;
    }

    public void setPic_id(String pic_id) {
        this.pic_id = pic_id;
    }

    private String id;
    private byte[] pic;
    private double[] location;

    private String comment;

    private boolean hasPic;
    private boolean hasLoc;
    private String username;

    public HabitEvent() {

    }

    public boolean hasPicture() {
        return hasPic;
    }

    public boolean hasLocation() {
        return hasLoc;
    }

    public HabitEvent(String title, Date completionDate, byte[] pic, double[] location, String
            comment) {
        this.title = title;
        this.completionDate = completionDate;
        if (pic == null) {
            this.hasPic = Boolean.FALSE;
        } else {
            this.hasPic = Boolean.TRUE;
            this.pic = pic;
        }
        if (location == null) {
            this.hasLoc = Boolean.FALSE;
        } else {
            this.hasLoc = Boolean.TRUE;
            this.location = location;
        }
        if (comment == null) {
            this.comment = "";
        } else {
            this.comment = comment;
        }

    }

    public String getJsonString() {
        JsonObject j = new JsonObject();
        j.addProperty("habitid", this.getHabit_id());
        j.addProperty("title", this.getTitle());
        j.addProperty("comment", this.getComment());
        if (this.hasPicture()) {
            //j.addProperty("pic", this.getPicBytes().toString());
            j.addProperty("picId", this.getPic_id());
        }
        if (this.hasLocation()) {
            j.addProperty("lat", this.getLocation()[0]);
            j.addProperty("lng", this.getLocation()[1]);
        }
        j.addProperty("hasPic", this.hasPicture());
        j.addProperty("hasLoc", this.hasLocation());
        j.addProperty("username", this.getUsername());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(this.getCompletionDate());
        j.addProperty("completionDate", dateStr);

        Gson g = new Gson();
        String s = g.toJson(j);
        Log.d("Event.Json", s);
        return s;
    }

    public void fromJsonObject(JsonObject job) throws ParseException {
        Gson g = new Gson();
        String s = g.toJson(job);
        Log.d("HABIT.json", s);
        JsonObject j = job.get("_source").getAsJsonObject();
        this.setHabit_id(j.get("habitid").getAsString());
        this.setComment(j.get("comment").getAsString());
        this.setTitle(j.get("title").getAsString());
        this.setUsername(j.get("username").getAsString());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = sdf.parse(j.get("completionDate").getAsString());
        this.setCompletionDate(d);

        this.setId(job.get("_id").getAsString());
        this.hasPic = j.get("hasPic").getAsBoolean();
        this.hasLoc = j.get("hasLoc").getAsBoolean();
        if (this.hasPicture()) {
            this.setPic_id(j.get("picId").getAsString());
            //this.setPicBytes(j.get("pic").getAsString().getBytes());
        }
        if (this.hasLocation()) {
            double[] loc = {j.get("lat").getAsDouble(), j.get("lng").getAsDouble()};
            this.setLocation(loc);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public byte[] getPicBytes() {
        return pic;
    }

    public void setPicBytes(byte[] pic) {
        if(pic == null){
            this.hasPic = false;
        }else{
this.hasPic = true;
        }
        this.pic = pic;
    }


    public double[] getLocation() {
        return location;
    }

    public String getHabit_id() {
        return habit_id;
    }

    public void setHabit_id(String habit_id) {
        this.habit_id = habit_id;
    }

    public void setLocation(double[] location) {
        if(location == null){
            this.hasLoc = false;
        }else{
            this.hasLoc = true;
        }
        this.location = location;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPictureJsonString() {
        JsonObject j = new JsonObject();
        j.addProperty("pic", this.getPicBytes().toString());


        Gson g = new Gson();
        String s = g.toJson(j);
        return s;
    }
    public void setPicFromJsonObject(JsonObject job){
        JsonObject j = job.get("_source").getAsJsonObject();
        this.setPicBytes(j.get("pic").getAsString().getBytes());
    }
}
