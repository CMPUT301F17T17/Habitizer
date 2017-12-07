
/*
 *  Class Name: Habit
 *  Version: 1.0
 *  Date: December 6th, 2017
 *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  You may use, distribute, or modify this code under terms and conditions of the
 *  Code of Students Behaviour at University of Alberta
 *
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

    /**
     * Getter for ID
     * @return
     */
    public String getId() {
        return id;
    }

    private int[] daysOfWeekComplete = {0,0,0,0,0,0,0};

    private int[] daysOfWeekDue = {0,0,0,0,0,0,0};
    private String username;


    /**
     * Getter for username
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for days of week habit is due
     * @return
     */
    public int[] getDaysOfWeekDue() {
        return daysOfWeekDue;
    }

    /**
     * Setter for days of week habit is due
     * @param daysOfWeekDue
     */
    public void setDaysOfWeekDue(int[] daysOfWeekDue) {
        for(int i = 0;i < 7;i++){

            this.daysOfWeekDue[i] = daysOfWeekDue[i];
        }
    }

    private ArrayList<HabitEvent> events;

    /**
     * Puts together habit info to create string
     * @return
     */
    @Override
    public String toString() {
        return "Habit{" +
                "title='" + title + '\'' +
                ", startDate=" + startDate +
                '}';
    }

    /**
     * Empty constructor for Habit
     */
    public Habit() {
    }

    /**
     * Constructor for Habit with title, startDate, and reason
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
     * Getter for title of habit
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for title of habit
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for start date
     * @return
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Setter for start date
     * @param startDate
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Getter for reason
     * @return
     */
    public String getReason() {
        return reason;
    }

    /**
     * Setter for reason
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Getter for days of the week habit was completed
     * @return
     */
    public int[] getDaysOfWeekComplete() {
        return daysOfWeekComplete;
    }

    /**
     * Setter for days of week habit was completed
     * @param daysOfWeekComplete
     */
    public void setDaysOfWeekComplete(int[] daysOfWeekComplete) {
        this.daysOfWeekComplete = daysOfWeekComplete;
    }

    public ArrayList<HabitEvent> getEvents() {
        return events;
    }

    /**
     * Setter for list of habit events
     * @param events
     */
    public void setEvents(ArrayList<HabitEvent> events) {
        this.events = events;
    }

    /**
     * Getter for Json string
     * @return
     */
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

    /**
     * Creates habit from json job
     * @param job
     * @throws ParseException
     */
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

    /**
     * Setter for ID
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }
}
