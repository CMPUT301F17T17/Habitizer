/*
 *  CMPUT301F16T17
 *
 *  Project pt 4
 *
 *  November 13th, 2017
 *
 *  Copyright Notice
 *
 */

package ssmad.habitizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import static android.R.color.holo_blue_light;
import static android.R.color.white;

/**
 * Created by cryst on 10/22/2017.
 */

public class HabitTabActivity extends AppCompatActivity {


    public static String  GENERIC_REQUEST_CODE = "GENERIC.REQUEST.CODE";
    public static final int ADDING_EVENT = 123;
    ListView myHabitsListView;

    @Override
    protected void onStart() {
        super.onStart();
        myHabitsListView = (ListView) findViewById(R.id.habits_listview);
        DummyMainActivity.myHabitsAdapter = new MyHabitsAdapter(HabitTabActivity.this, DummyMainActivity.myHabits);
        myHabitsListView.setAdapter(DummyMainActivity.myHabitsAdapter);
        DummyMainActivity.myHabitsAdapter.notifyDataSetChanged();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_tab);
        //LinearLayout tabs = (LinearLayout) findViewById(R.id.tabs);
        //View childTabs = getLayoutInflater().inflate(R.layout.main_tabs, null);
        DummyMainActivity.initTabs(DummyMainActivity.VIEW_HABIT, HabitTabActivity.this);

        //tabs.addView(childTabs);




        Button button = (Button) findViewById(R.id.add_habit_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HabitTabActivity.this, AddHabitActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADDING_EVENT){
            AddHabitEventActivity._resetVars();
        }
        DummyMainActivity.myHabitsAdapter.notifyDataSetChanged();
    }



}


