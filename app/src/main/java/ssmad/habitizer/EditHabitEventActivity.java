/*
<<<<<<< HEAD
 *  Class Name: HabitEvent
=======
 *  Class Name: EditHabitEventActivity
>>>>>>> master
 *  Version: 0.5
 *  Date: November 13th, 2017
 *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  You may use, distribute, or modify this code under terms and conditions of the
 *  Code of Students Behaviour at University of Alberta
 */
<<<<<<< HEAD
=======

>>>>>>> master
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
            Boolean IHaveMapPermission = MapController.checkMapPermission(this);
            if (IHaveMapPermission) {
                mapToggle.setVisibility(View.VISIBLE);
                Location loc = new Location("lol");
                double[] habitEventLoc = habitEvent.getLocation();
                loc.setLatitude(habitEventLoc[0]);
                loc.setLongitude(habitEventLoc[1]);
                MapController.initMap(this, loc);
            } else {
                //TODO
                MapController.askForMapPermission(this);
                mapToggle.setVisibility(View.GONE);
            }
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
                finish();
            }
        });


    }

<<<<<<< HEAD
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
            Boolean dontHavePic = !habitEvent.hasPicture();
            habitEvent.setPicBytes(picBytes);
            Boolean nowHasPic = habitEvent.hasPicture();
            habitEvent.setLocation(location);


            postHabitEvent.execute(DummyMainActivity.Event_Index, habitEvent.getJsonString());
            if (dontHavePic && nowHasPic) {
                //TODO add pic
                ElasticsearchController.AddItemsTask postHabitEventPicture =
                        new ElasticsearchController.AddItemsTask();
                postHabitEventPicture.execute(DummyMainActivity.Pic_Index, habitEvent.getPictureJsonString
                        ());
                try {

                    String id = postHabitEventPicture.get();
                    if(id == null){
                        throw new Exception("lol");
                    }
                    habitEvent.setPic_id(id);
                } catch (Exception e) {
                    Log.d("ESC", "Could not update habit event picture on first try.");
                    String[] s = {
                            DummyMainActivity.Pic_Index,
                            String.valueOf(SyncController.TASK_ADD),
                            habitEvent.getPictureJsonString()
                    };
                    SyncController.addToSync(s, habitEvent);

                }
            } else if (!dontHavePic && !nowHasPic) {
                // TODO delete pic
                ElasticsearchController.DeleteItemsTask postHabitEventPicture =
                        new ElasticsearchController.DeleteItemsTask();
                postHabitEventPicture.execute(DummyMainActivity.Pic_Index, habitEvent.getPic_id());
                try {
                    Boolean success = postHabitEventPicture.get();
                    if(!success){
                        throw new Exception("lol");
                    }

                } catch (Exception e) {
                    Log.d("ESC", "Could not update habit event picture on first try.");
                    String[] s = {
                            DummyMainActivity.Pic_Index,
                            String.valueOf(SyncController.TASK_DELETE),
                            habitEvent.getPic_id()
                    };
                    SyncController.addToSync(s, habitEvent);

                }
            } else if (!dontHavePic && nowHasPic) {
                // TODO update pic
                ElasticsearchController.UpdateItemsTask postHabitEventPicture =
                        new ElasticsearchController.UpdateItemsTask();
                postHabitEventPicture.execute(DummyMainActivity.Pic_Index, habitEvent.getPic_id(), habitEvent
                        .getPictureJsonString
                                ());
                try {
                    Boolean success = postHabitEventPicture.get();
                    if(!success){
                        throw new Exception("lol");
                    }

                } catch (Exception e) {
                    Log.d("ESC", "Could not update habit event picture on first try.");
                    String[] s = {
                            DummyMainActivity.Pic_Index,
                            String.valueOf(SyncController.TASK_UPDATE),
                            habitEvent.getPic_id(),
                            habitEvent.getPictureJsonString()
                    };
                    SyncController.addToSync(s, habitEvent);

                }

            }

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
                        habitEvent.getJsonString()
                };
                SyncController.addToSync(s, habitEvent);
            }

            // Andrew stuff

            //


            // TODO fix this for offline
            FileController.saveInFile(EditHabitEventActivity.this, DummyMainActivity.HABITEVENTFILENAME,
                    DummyMainActivity.myHabitEvents);

            finish();
        }
    }


=======
    /**
     * Attempt to get picture and make the corresponding buttons available
     * @param requestCode
     * @param resultCode
     * @param data
     */
>>>>>>> master
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AddHabitEventActivity.tryGetPic(this, requestCode, resultCode, data);
    }
}
