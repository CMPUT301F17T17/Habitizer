package ssmad.habitizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by cryst on 10/23/2017.
 */

public class AddHabitActivity extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_habit);
        Intent returnIntent = getIntent();
        Button addButton = (Button) findViewById(R.id.confirmH_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText title = (EditText) findViewById(R.id.habit_input);
                EditText reason = (EditText) findViewById(R.id.reason_input);
                Date start = new Date();
                Habit habit = new Habit(title.getText().toString(), start, reason.getText().toString());
                HabitTabActivity.myHabits.add(habit);
                HabitTabActivity.toastMe("New habit added!", AddHabitActivity.this);
                AddHabitActivity.this.finish();
            }
        });
    }
}
