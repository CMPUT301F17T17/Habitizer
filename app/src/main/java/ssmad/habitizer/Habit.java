package ssmad.habitizer;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Sadman on 2017-11-10.
 */

public class Habit {
    private String title;
    private Date startDate;
    private String reason;
    private int[] daysOfWeekComplete = {0,0,0,0,0,0,0};
    private ArrayList<HabitEvent> events;

    public Habit(String title, Date startDate, String reason) {
        this.title = title;
        this.startDate = startDate;
        this.reason = reason;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int[] getDaysOfWeekComplete() {
        return daysOfWeekComplete;
    }

    public void setDaysOfWeekComplete(int[] daysOfWeekComplete) {
        this.daysOfWeekComplete = daysOfWeekComplete;
    }

    public ArrayList<HabitEvent> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<HabitEvent> events) {
        this.events = events;
    }
}