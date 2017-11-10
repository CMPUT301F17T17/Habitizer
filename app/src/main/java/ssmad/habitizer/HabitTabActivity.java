package ssmad.habitizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by cryst on 10/22/2017.
 */

public class HabitTabActivity extends AppCompatActivity {
    public static ArrayList<Habit> myHabits = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_tab);

        Button button = (Button) findViewById(R.id.add_habit_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HabitTabActivity.this, AddHabitActivity.class);
                startActivity(intent);
            }
        });
    }
}


