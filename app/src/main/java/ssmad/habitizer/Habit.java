/*
 *  Class Name: Habit
 *  Version: 0.5
 *  Date: November 13th, 2017
 *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  You may use, distribute, or modify this code under terms and conditions of the
 *  Code of Students Behaviour at University of Alberta
 */

package ssmad.habitizer;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Sadman on 2017-11-10.
 */

/**
 * Represents a Habit
 * @author Sadman
 * @version 0.5
 * @since 0.5
 */
public class Habit {
    private String title;
    private Date startDate;
    private String reason;
    private int[] daysOfWeekComplete = {0,0,0,0,0,0,0};
    private int[] daysOfWeekDue = {0,0,0,0,0,0,0};

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
}
