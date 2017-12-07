/*
 *  Class Name: ViewHabitActivity
 *  Version: 0.5
 *  Date: November 13th, 2017
 *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  You may use, distribute, or modify this code under terms and conditions of the
 *  Code of Students Behaviour at University of Alberta
 */
package ssmad.habitizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Views Habit
 * @author Minfeng, Sadman
 * @version 0.5
 * @see Habit
 * @since 0.5
 */
public class ViewHabitActivity extends AppCompatActivity {
    public static final  String toEdit = "TO.EDIT";

    /**
     * Called when activity starts
     * Connects buttons to actions
     * Option to delete habits
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_habit);
        Button eventButton = (Button) findViewById(R.id.events_btn);
        Button editButton = (Button) findViewById(R.id.edit_button);
        Button doneButton = (Button) findViewById(R.id.done_button);
        final int position = getIntent().getExtras().getInt(HabitTabActivity.GENERIC_REQUEST_CODE);
        Habit habit = DummyMainActivity.myHabits.get(position);
        TextView habitView = (TextView)findViewById(R.id.habit_view);
        habitView.setVisibility(View.VISIBLE);
        habitView.setText(habit.getTitle());
        TextView reasonView = (TextView)findViewById(R.id.reason_view);
        reasonView.setVisibility(View.VISIBLE);
        reasonView.setText(habit.getReason());
        (findViewById(R.id.viewing)).setVisibility(View.VISIBLE);
        editButton.setVisibility(View.VISIBLE);
        doneButton.setVisibility(View.VISIBLE);
        Button deleteButton = (Button)  findViewById(R.id.delete);
        deleteButton.setVisibility(View.VISIBLE);
        eventButton.setVisibility(View.VISIBLE);
        eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = DummyMainActivity.myHabits.get(position).getId();
                Intent intent = new Intent();
                intent.putExtra("fromHabit", true);
                intent.putExtra("currentHabitId", id);
                setResult(0, intent);
                finish();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = DummyMainActivity.myHabits.get(position).getId();
                DummyMainActivity.myHabits.remove(position);
                ElasticsearchController.DeleteItemsTask deleteHabit = new ElasticsearchController.DeleteItemsTask();
                //TODO

                deleteHabit.execute(DummyMainActivity.Habit_Index, id);
                Boolean success = false;
                try{
                    success = deleteHabit.get();
                }catch (Exception e){
                    Log.d("ESC", "Could not delete habit on first try.");
                }
                FileController.saveInFile(ViewHabitActivity.this, DummyMainActivity.HABITFILENAME, DummyMainActivity.myHabits);
                finish();
            }
        });
        AddHabitActivity.setDays(habit);
        AddHabitActivity.setUpDays(this);
        AddHabitActivity.resetDays();
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewHabitActivity.this, EditHabitActivity.class);
                intent.putExtra(toEdit, position);
                startActivityForResult(intent, 0);

            }
        });

    }

    /**
     * Moves to activity given by intent
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //restart
        finish();
        startActivity(getIntent());
    }
}
