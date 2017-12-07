/*
<<<<<<< HEAD
 *  Class Name: HabitEvent
=======
 *  Class Name: EditHabitActivity
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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Used for editing habits
 * @author Minfeng, Sadman
 * @version 0.5
 * @see Habit
 * @since 0.5
 */
public class EditHabitActivity extends AppCompatActivity {

    /**
     * Called when activity starts, takes input, sets new values, confirm button
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_habit);

        final int position = getIntent().getExtras().getInt(ViewHabitActivity.toEdit);
        final Habit habit = DummyMainActivity.myHabits.get(position);

        final TextView habitInput = (TextView)findViewById(R.id.habit_input);
        habitInput.setVisibility(View.VISIBLE);
        habitInput.setText(habit.getTitle());

        final TextView reasonInput = (TextView)findViewById(R.id.reason_input);
        reasonInput.setVisibility(View.VISIBLE);
        reasonInput.setText(habit.getReason());

        (findViewById(R.id.editing)).setVisibility(View.VISIBLE);

        Button doneButton = (Button) findViewById(R.id.done_button);
        doneButton.setVisibility(View.VISIBLE);

        AddHabitActivity.setDays(habit);
        AddHabitActivity.setUpDays(this);
        AddHabitActivity.makeDaysEditable(this);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AddHabitActivity.habitChecksOut(EditHabitActivity.this)){
                    habit.setTitle(habitInput.getText().toString());
                    habit.setReason(reasonInput.getText().toString());
                    habit.setDaysOfWeekDue(AddHabitActivity.days);
                    AddHabitActivity.resetDays();
                    String id = habit.getId();
                    ElasticsearchController.UpdateItemsTask updateHabit = new ElasticsearchController.UpdateItemsTask();
                    //TODO

                    updateHabit.execute(DummyMainActivity.Habit_Index, id, habit.getJsonString());
                    Boolean success = false;
                    try{
                        success = updateHabit.get();
                    }catch (Exception e){
                        Log.d("ESC", "Could not update habit on first try.");
                    }
                    FileController.saveInFile(EditHabitActivity.this, DummyMainActivity.HABITFILENAME, DummyMainActivity.myHabits);
                    EditHabitActivity.this.finish();
                }
            }
        });

    }
}
