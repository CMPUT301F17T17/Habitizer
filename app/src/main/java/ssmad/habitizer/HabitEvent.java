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

import java.util.Date;

/**
 * Created by Sadman on 2017-11-10.
 */

/*
Refs:
https://alvinalexander.com/source-code/android/android-how-load-image-file-and-set-imageview
 */

/**
 * Represents a Habit Event
 * @author Sadman
 * @version 0.5
 * @since 0.5
 */
public class HabitEvent {
    private String title;
    private Date completionDate;
    private byte[] pic;
    private double[] location;
    private String comment;
    private boolean hasPic;
    private boolean hasLoc;

    /**
     * Boolean to check if event has picture
     * @return
     */
    public boolean hasPicture() {
        return hasPic;
    }

    /**
     * Boolean to check if event has location
     * @return
     */
    public boolean hasLocation() {
        return hasLoc;
    }

    /**
     * Constructor for habit event with title, date, picture, location, and comment
     * @param title
     * @param completionDate
     * @param pic
     * @param location
     * @param comment
     */
    public HabitEvent(String title, Date completionDate, byte[] pic, double[] location, String
            comment) {
        this.title = title;
        this.completionDate = completionDate;
        if(pic == null){
            this.hasPic = Boolean.FALSE;
        }else {
            this.hasPic = Boolean.TRUE;
            this.pic = pic;
        }
        if(location == null){
            this.hasLoc = Boolean.FALSE;
        }else {
            this.hasLoc = Boolean.TRUE;
            this.location = location;
        }
        if(comment == null){
            this.comment = "";
        }else {
            this.comment = comment;
        }

    }

    /**
     * Gets Title
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets Title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets completion date
     * @return
     */
    public Date getCompletionDate() {
        return completionDate;
    }

    /**
     * Sets completion date
     * @param completionDate
     */
    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    /**
     * Gets picture (bytes)
     * @return
     */
    public byte[] getPicBytes() {
        return pic;
    }

    /**
     * Sets picture (bytes)
     * @param pic
     */
    public void setPicBytes(byte[] pic) {
        this.pic = pic;
    }

    /**
     * Gets location
     * @return
     */
    public double[] getLocation() {
        return location;
    }

    /**
     * Sets location
     * @param location
     */
    public void setLocation(double[] location) {
        this.location = location;
    }

    /**
     * Gets comment
     * @return
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets comment
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}
