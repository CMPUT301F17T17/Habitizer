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

public class HabitEvent {
    private String title;
    private Date completionDate;
    private byte[] pic;
    private double[] location;
    private String comment;
    private boolean hasPic;
    private boolean hasLoc;

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
        this.pic = pic;
    }

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
