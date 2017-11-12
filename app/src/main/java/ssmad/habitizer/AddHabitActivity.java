package ssmad.habitizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by cryst on 10/23/2017.
 */

public class AddHabitActivity extends AppCompatActivity {
    private final int MAX_REASON_LENGTH = 30;
    private final int MAX_TITLE_LENGTH = 10;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_habit);
        Button addButton = (Button) findViewById(R.id.confirmH_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = ((EditText) findViewById(R.id.habit_input)).getText().toString();
                Log.d("title",title);
                if (title.isEmpty()) {
                    DummyMainActivity.toastMe("Must enter title", AddHabitActivity.this);
                } else if (title.length() > MAX_TITLE_LENGTH) {
                    DummyMainActivity.toastMe("Title must be less than " + MAX_TITLE_LENGTH + " chars", AddHabitActivity.this);
                } else if (habitExists(title)) {
                    DummyMainActivity.toastMe("Habit already exists", AddHabitActivity.this);

                } else {
                    String reason = ((EditText) findViewById(R.id.reason_input)).getText().toString();
                    if (reason.length() > MAX_REASON_LENGTH) {
                        DummyMainActivity.toastMe("Reason must be less than " + MAX_REASON_LENGTH + " chars",
                                AddHabitActivity.this);
                    }else{
                        Date start = new Date();
                        Habit habit = new Habit(title, start, reason);
                        DummyMainActivity.myHabits.add(habit);
                        DummyMainActivity.myHabitDict.put(habit.getTitle(), 0);
                        DummyMainActivity.toastMe("New habit added!", AddHabitActivity.this);
                        AddHabitActivity.this.finish();
                    }

                }
            }
        });
    }

    private boolean habitExists(String name) {
        return DummyMainActivity.myHabitDict.containsKey(name);
    }
}
