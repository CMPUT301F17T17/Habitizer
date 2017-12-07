/*
 *  Class Name: EditHabitEventActivity
 *  Version: 0.5
 *  Date: November 13th, 2017
 *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  You may use, distribute, or modify this code under terms and conditions of the
 *  Code of Students Behaviour at University of Alberta
 */
package ssmad.habitizer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

import static ssmad.habitizer.AddHabitEventActivity.COMMENT_MAX_SIZE;
import static ssmad.habitizer.AddHabitEventActivity.location;
import static ssmad.habitizer.AddHabitEventActivity.picBytes;
import static ssmad.habitizer.ViewHabitActivity.toEdit;

/**
 * Edits habit events
 * @author Minfeng, Sadman
 * @version 0.5
 * @see HabitEvent
 * @since 0.5
 */
public class EditHabitEventActivity extends AppCompatActivity {

    /**
     * Called when activity starts, takes input, sets values, confirm button
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_habit_event);
        SyncController.sync(this);
        (findViewById(R.id.add)).setVisibility(View.GONE);
        (findViewById(R.id.edit_title)).setVisibility(View.VISIBLE);
        final int position = getIntent().getExtras().getInt(ViewHabitActivity.toEdit);
        HabitEvent habitEvent = DummyMainActivity.myHabitEvents.get(position);
        ((EditText) findViewById(R.id.comment_input)).setText(habitEvent.getComment());
        ((TextView) findViewById(R.id.what_habit)).setText(habitEvent.getTitle());
        CheckBox picCheck = (CheckBox) findViewById(R.id.pic_check);
        AddHabitEventActivity.setUpCheckBoxes(this);
        AddHabitEventActivity.setUpPicButtons(this);
        picBytes = habitEvent.getPicBytes();
        location = habitEvent.getLocation();
        if (habitEvent.hasPicture()) {
            picCheck.toggle();
            AddHabitEventActivity.picIsVisible = Boolean.TRUE;
            Bitmap p = AddHabitEventActivity.getPicFromBytes(habitEvent.getPicBytes());
            AddHabitEventActivity.setPic(this, p);
        }
        CheckBox locCheck = (CheckBox) findViewById(R.id.location_check);
        Button doneButton = (Button) findViewById(R.id.done_button);
        if (habitEvent.hasLocation()) {
            locCheck.toggle();
            LinearLayout mapToggle = (LinearLayout) findViewById(R.id.map_toggle);
            mapToggle.setVisibility(View.VISIBLE);
            Location loc = new Location("lol");
            loc.setLatitude(habitEvent.getLocation()[0]);
            loc.setLongitude(habitEvent.getLocation()[1]);
            MapController.initMap2(this, loc ,AddHabitEventActivity.EVENT_PERMISSION_CHECK);
        }


        final Activity fctx = this;
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doneEvent(position);
            }
        });
        doneButton.setVisibility(View.VISIBLE);
        (findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setResult(123, new Intent().putExtra("event_position", position));
                finish();
            }
        });


    }

    /**
     * Checks constraints and on passing, checks and updates server
     * @param position
     */
    private void doneEvent(int position) {
        HabitEvent habitEvent = DummyMainActivity.myHabitEvents.get(position);
        String comment = ((EditText) findViewById(R.id.comment_input)).getText().toString();
        if (comment.length() > COMMENT_MAX_SIZE) {
            DummyMainActivity.toastMe("Comment must be less than " + COMMENT_MAX_SIZE + " chars",
                    this);
        } else {
            ElasticsearchController.UpdateItemsTask postHabitEvent =
                    new ElasticsearchController.UpdateItemsTask();

            habitEvent.setComment(comment);
            habitEvent.setPicBytes(picBytes);
            habitEvent.setLocation(location);


            postHabitEvent.execute(DummyMainActivity.Event_Index, habitEvent.getId(),habitEvent
                    .getJsonString());

            try {
                Boolean success = postHabitEvent.get();
                if(!success){
                    throw new Exception("lol");
                }
            } catch (Exception e) {
                Log.d("ESC", "Could not update habit event on first try.");
                String[] s = {
                        DummyMainActivity.Event_Index,
                        String.valueOf(SyncController.TASK_UPDATE),
                        habitEvent.getJsonString(),
                        habitEvent.getId()
                };
                SyncController.addToSync(s, habitEvent);
            }
            // Andrew stuff
            //
            // TODO fix this for offline
            //FileController.saveInFile(EditHabitEventActivity.this, DummyMainActivity.HABITEVENTFILENAME,DummyMainActivity.myHabitEvents);
            setResult(123, new Intent().putExtra("event_position", position));
            finish();
        }
    }
}
