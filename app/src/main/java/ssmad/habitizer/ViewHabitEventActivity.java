/*
 *  Class Name: ViewHabitEventActivity
 *  Version: 1.0
 *  Date: November 13th, 2017
 *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  You may use, distribute, or modify this code under terms and conditions of the
 *  Code of Students Behaviour at University of Alberta
 */
package ssmad.habitizer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import static ssmad.habitizer.ViewHabitActivity.toEdit;

/**
 * For viewing a habit event
 * @author Minfeng, Sadman
 * @version 1.0
 * @see HabitEvent
 * @since 0.5
 */
public class ViewHabitEventActivity extends AppCompatActivity {
    private final int EDITING = 345;

    /**
     * Called when activity starts
     * Takes input
     * Connects buttons to actions
     * Option to delete habit events
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_habit_event);
        setup(getIntent().getIntExtra("event_position", 0));
    }

    private void deleteEvent(int position) {
        HabitEvent habitEvent = DummyMainActivity.myHabitEvents.get(position);
        ElasticsearchController.DeleteItemsTask postHabitEvent =
                new ElasticsearchController.DeleteItemsTask();

        postHabitEvent.execute(DummyMainActivity.Event_Index, habitEvent.getId());

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
        //DummyMainActivity.myHabitEvents.remove(position);
        //FileController.saveInFile(ViewHabitEventActivity.this, DummyMainActivity.HABITEVENTFILENAME,DummyMainActivity.myHabitEvents);
        finish();
    }

    private void setup(final int position){

        SyncController.sync(this);
        (findViewById(R.id.view_title)).setVisibility(View.VISIBLE);
        (findViewById(R.id.comment_input)).setVisibility(View.GONE);
        (findViewById(R.id.pic_check)).setVisibility(View.GONE);
        (findViewById(R.id.location_check)).setVisibility(View.GONE);
        (findViewById(R.id.comment_title)).setVisibility(View.GONE);
        TextView commentView = (TextView) findViewById(R.id.comment_view);
        commentView.setVisibility(View.VISIBLE);
        HabitEvent habitEvent = DummyMainActivity.myHabitEvents.get(position);
        commentView.setText(habitEvent.getComment());
        ((TextView)findViewById(R.id.what_habit)).setText(habitEvent.getTitle());
        if(habitEvent.hasPicture()){
            // OK Log.d("VIEW.EVENT.PIC", new String(habitEvent.getPicBytes()));
            Bitmap p = AddHabitEventActivity.getPicFromBytes(habitEvent.getPicBytes());
            if(p == null){
                DummyMainActivity.toastMe("Could not show picture", this);
            }else{

                AddHabitEventActivity.setPic(this, p);
            }
        }
        (findViewById(R.id.cancel)).setVisibility(View.GONE);
        Button doneButton = (Button)  findViewById(R.id.done_button);
        Button deleteButton = (Button)  findViewById(R.id.delete);
        deleteButton.setVisibility(View.VISIBLE);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEvent(position);
                finish();
            }
        });
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        doneButton.setVisibility(View.VISIBLE);
        Button editButton = (Button) findViewById(R.id.edit_button);
        (findViewById(R.id.add)).setVisibility(View.GONE);
        editButton.setVisibility(View.VISIBLE);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewHabitEventActivity.this, EditHabitEventActivity.class);
                intent.putExtra(toEdit, position);
                startActivityForResult(intent, EDITING);
            }
        });
        if(habitEvent.hasLocation()){

            LinearLayout mapToggle = (LinearLayout) findViewById(R.id.map_toggle);
            mapToggle.setVisibility(View.VISIBLE);
            Location loc = new Location("lol");
            loc.setLatitude(habitEvent.getLocation()[0]);
            loc.setLongitude(habitEvent.getLocation()[1]);
            MapController.initMap2(this, loc ,AddHabitEventActivity.EVENT_PERMISSION_CHECK);
        }
    }

    /**
     * Moves to activity given by intent, resets variables if request is to edit
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EDITING){
        }
        //restart
        setup(data.getIntExtra("event_position", 0));
    }
}