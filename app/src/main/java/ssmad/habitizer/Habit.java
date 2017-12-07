/*
 *  Class Name: Habit
 *  Version: 1.0
 *  Date: November 13th, 2017
 *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  You may use, distribute, or modify this code under terms and conditions of the
 *  Code of Students Behaviour at University of Alberta
 */
package ssmad.habitizer;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Sadman on 2017-11-10.
 */

/**
 * Represents a Habit
 * @author Sadman
 * @version 1.0
 * @since 0.5
 */
public class Habit {
    private String title;
    private Date startDate;
    private String reason;
    private String id;

    public String getId() {
        return id;
    }

    private int[] daysOfWeekComplete = {0,0,0,0,0,0,0};

    private int[] daysOfWeekDue = {0,0,0,0,0,0,0};
    private String username;



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets days of week habit is due
     * @return
     */
    public int[] getDaysOfWeekDue() {
        return daysOfWeekDue;
    }

    /**
     * Sets days of week habit is due
     * @param daysOfWeekDue
     */
    public void setDaysOfWeekDue(int[] daysOfWeekDue) {
        for(int i = 0;i < 7;i++){

            this.daysOfWeekDue[i] = daysOfWeekDue[i];
        }
    }

    private ArrayList<HabitEvent> events;

    /**
     * Returns string of habit with details
     * @return
     */
    @Override
    public String toString() {
        return "Habit{" +
                "title='" + title + '\'' +
                ", startDate=" + startDate +
                '}';
    }


    public Habit() {
    }

    /**
     * Constructor for Habit with title, date, and reason
     * @param title
     * @param startDate
     * @param reason
     */
    public Habit(String title, Date startDate, String reason) {
        this.title = title;
        this.startDate = startDate;
        this.reason = reason;

    }

    /**
     * Gets Title of this habit
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets Title for this habit
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets Start Date of this habit
     * @return
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets Start Date of this habit
     * @param startDate
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets Reason for this habit
     * @return
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets Reason for this habit
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Gets days of week completed for this habit
     * @return
     */
    public int[] getDaysOfWeekComplete() {
        return daysOfWeekComplete;
    }

    /**
     * Sets days of week completed for this habit
     * @param daysOfWeekComplete
     */
    public void setDaysOfWeekComplete(int[] daysOfWeekComplete) {
        this.daysOfWeekComplete = daysOfWeekComplete;
    }

    /**
     * Gets habit events of this habit
     * @return
     */
    public ArrayList<HabitEvent> getEvents() {
        return events;
    }

    /**
     * Sets habit events for this habit
     * @param events
     */
    public void setEvents(ArrayList<HabitEvent> events) {
        this.events = events;
    }

    public String getJsonString() {
        JsonObject j = new JsonObject();
        j.addProperty("username", this.getUsername());
        j.addProperty("title", this.getTitle());
        j.addProperty("reason", this.getReason());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(this.getStartDate());
        j.addProperty("date",dateStr);

        JsonArray daysdue = new JsonArray();
        for(int i = 0; i < this.getDaysOfWeekDue().length; i++){
            daysdue.add(this.getDaysOfWeekDue()[i]);
        }
        j.add("daysDue", daysdue);

        JsonArray daysdone = new JsonArray();
        for(int i = 0; i < this.getDaysOfWeekComplete().length; i++){
            daysdone.add(this.getDaysOfWeekComplete()[i]);
        }
        j.add("daysDone", daysdone);

        Gson g = new Gson();
        String s = g.toJson(j);
        Log.d("Habit.Json", s);
        return s;
    }

    public void fromJsonObject(JsonObject job) throws ParseException {
         Gson g = new Gson();
        String s = g.toJson(job);
        Log.d("HABIT.json", s);
        JsonObject j  = job.get("_source").getAsJsonObject();
        this.setUsername(j.get("username").getAsString());

        this.setReason(j.get("reason").getAsString());
        this.setTitle(j.get("title").getAsString());
        this.setId(job.get("_id").getAsString());
        Log.d("HABIT.json", "before date");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = sdf.parse(j.get("date").getAsString());
        this.setStartDate(d);

        Log.d("HABIT.json", "before days");
        JsonArray jsonDaysDue = j.get("daysDue").getAsJsonArray();
        JsonArray jsonDaysDone = j.get("daysDone").getAsJsonArray();
        for(int i = 0; i < this.getDaysOfWeekDue().length; i++){
            this.daysOfWeekDue[i] = jsonDaysDue.get(i).getAsInt();
            this.daysOfWeekComplete[i] = jsonDaysDone.get(i).getAsInt();

        }
        Log.d("HABIT.json", "after days");

    }

    public void setId(String id) {
        this.id = id;
    }
}
