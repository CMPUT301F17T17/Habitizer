/*
 *  Class Name: HabitEvent
 *  Version: 0.5
 *  Date: November 13th, 2017
 *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  You may use, distribute, or modify this code under terms and conditions of the
 *  Code of Students Behaviour at University of Alberta
 */
package ssmad.habitizer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by cryst on 10/23/2017.
 */

public class AddHabitActivity extends AppCompatActivity {
    public static final int MAX_REASON_LENGTH = 30;
    public static final int MAX_TITLE_LENGTH = 10;
    public static int[] days = {0, 0, 0, 0, 0, 0, 0};
    public static final int[] daytags = {R.id.m,
            R.id.t, R.id.w, R.id.th,
            R.id.f, R.id.s, R.id.su};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_habit);
        Button addButton = (Button) findViewById(R.id.confirmH_button);
        final TextView habitInput = (TextView) findViewById(R.id.habit_input);
        habitInput.setVisibility(View.VISIBLE);

        final TextView reasonInput = (TextView) findViewById(R.id.reason_input);
        reasonInput.setVisibility(View.VISIBLE);

        (findViewById(R.id.addnew)).setVisibility(View.VISIBLE);
        addButton.setVisibility(View.VISIBLE);
        setUpDays(this);
        makeDaysEditable(this);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (habitChecksOut(AddHabitActivity.this)) {
                    Date start = new Date();
                    ElasticsearchController.AddItemsTask postHabit = new ElasticsearchController.AddItemsTask();

                    Habit habit = new Habit(habitInput.getText().toString(), start, reasonInput
                            .getText().toString());
                    habit.setDaysOfWeekDue(days);
                    habit.setUsername(DummyMainActivity.currentUser);
                    postHabit.execute(DummyMainActivity.Habit_Index, habit.getJsonString());
                    try{
                        String id = postHabit.get();
                        habit.setId(id);
                    }catch (Exception e){
                        Log.d("ESC", "Could not update habit on first try.");
                    }
                    DummyMainActivity.myHabits.add(habit);
                    DummyMainActivity.myHabitDict.put(habit.getTitle(), 0);
                    DummyMainActivity.toastMe("New habit added!", AddHabitActivity.this);
                    resetDays();

                    FileController.saveInFile(AddHabitActivity.this, DummyMainActivity.HABITFILENAME, DummyMainActivity.myHabits);

                    AddHabitActivity.this.finish();
                }
            }
        });
    }

    private boolean habitExists(String name) {
        return DummyMainActivity.myHabitDict.containsKey(name);
    }
    public static void setDays( Habit habit){
        int[] thisHabitDays = habit.getDaysOfWeekDue();

        for(int i = 0; i < 7; i++){
            AddHabitActivity.days[i] = thisHabitDays[i];
        }
    }

    public static void setUpDays(Activity ctx) {
        LinearLayout daysOuter = (LinearLayout) ctx.findViewById(R.id.days_outer);
        View childdays = ctx.getLayoutInflater().inflate(R.layout.days, null);

        // Setting things

        for (int i = 0; i < 7; i++) {
            final TextView dayTextView = (TextView) childdays.findViewById(AddHabitActivity.daytags[i]);
            final int currenti = i;
            if (AddHabitActivity.days[i] == 1) {
                dayTextView.setBackground(ctx.getDrawable(R.drawable.days_border_valid));
            }

        }

        LinearLayout daysInner = (LinearLayout) childdays.findViewById(R.id.days_inner);
        daysInner.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        daysOuter.addView(childdays);

    }

    public static void makeDaysEditable(Activity ctx) {
        View childdays = ctx.findViewById(R.id.days_inner);
        for (int i = 0; i < 7; i++) {
            final TextView dayTextView = (TextView) childdays.findViewById(AddHabitActivity.daytags[i]);
            final int currenti = i;
            final Activity fctx = ctx;
            dayTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int[] days = AddHabitActivity.days;
                    days[currenti] = days[currenti] == 0 ? 1 : 0;
                    if (days[currenti] == 1) {
                        dayTextView.setBackground(fctx.getDrawable(R.drawable.days_border_valid));
                    } else {
                        dayTextView.setBackground(fctx.getDrawable(R.drawable.days_border));
                    }
                }
            });
        }
    }

    public static void resetDays() {
        for (int i = 0; i < 7; i++) {
            AddHabitActivity.days[i] = 0;
        }
    }

    public static boolean habitChecksOut(Activity ctx) {
        String title = ((EditText) ctx.findViewById(R.id.habit_input)).getText().toString();
        if (title.isEmpty()) {
            DummyMainActivity.toastMe("Must enter title", ctx);
        } else if (title.length() > AddHabitActivity.MAX_TITLE_LENGTH) {
            DummyMainActivity.toastMe("Title must be less than " + AddHabitActivity.MAX_TITLE_LENGTH + " chars",
                    ctx);
        } else if (DummyMainActivity.myHabitDict.containsKey(title)) {
            DummyMainActivity.toastMe("Habit already exists", ctx);

        } else {
            String reason = ((EditText) ctx.findViewById(R.id.reason_input)).getText().toString();
            if (reason.length() > AddHabitActivity.MAX_REASON_LENGTH) {
                DummyMainActivity.toastMe("Reason must be less than " + AddHabitActivity.MAX_REASON_LENGTH + " " + "chars", ctx);
            } else {
                return Boolean.TRUE;
            }

        }
        return Boolean.FALSE;
    }
}
