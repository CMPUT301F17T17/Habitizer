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
    private String pathToPicture;
    private Location location;
    private String comment;

    public HabitEvent(String title, Date completionDate) {
        this.title = title;
        this.completionDate = completionDate;
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

    public String getPathToPicture() {
        return pathToPicture;
    }

    public void setPathToPicture(String pathToPicture) {
        this.pathToPicture = pathToPicture;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
