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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

import static android.R.color.holo_blue_light;
import static android.R.color.white;

public class HabitTabActivity extends AppCompatActivity {


    public static String  GENERIC_REQUEST_CODE = "GENERIC.REQUEST.CODE";
    public static final int ADDING_EVENT = 123;
    public static Boolean isFromProfile = false; //TODO 2
    ListView myHabitsListView;

    @Override
    protected void onStart() {
        super.onStart();
        //TODO 2 searching another user habits for viewing
        Intent intent = getIntent();
        ArrayList<Habit> habitList;
        if(intent != null && intent.getBooleanExtra("fromProfile", false)){
            isFromProfile = true;
            if(Utilities.isNetworkAvailable(HabitTabActivity.this)){
                ElasticsearchController.GetItemsTask getHabitsArrayGetTask = new ElasticsearchController.GetItemsTask();
                getHabitsArrayGetTask.execute(DummyMainActivity.Habit_Index, "username", intent.getStringExtra("targetUsername"));
                try{
                    JsonArray jsonHabits =  getHabitsArrayGetTask.get();
                    habitList = new ArrayList<>();
                    for (int i = 0; i < jsonHabits.size(); i++){
                        Habit h = new Habit();
                        JsonObject job  = jsonHabits.get(i).getAsJsonObject();
                        h.fromJsonObject(job);
                        habitList.add(h);
                    }
                }catch (Exception e){
                    habitList = null;
                    Log.d("ESC", "Adding habits in login.");
                }
            }else{
                habitList = null;
                DummyMainActivity.toastMe("No internet", HabitTabActivity.this);
            }
        }else {
            isFromProfile = false;
            habitList = DummyMainActivity.myHabits;
        }
        myHabitsListView = (ListView) findViewById(R.id.habits_listview);
        DummyMainActivity.myHabitsAdapter = new MyHabitsAdapter(HabitTabActivity.this, habitList);
        myHabitsListView.setAdapter(DummyMainActivity.myHabitsAdapter);
        DummyMainActivity.myHabitsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        setContentView(R.layout.activity_habit_tab);
        Button button = (Button) findViewById(R.id.add_habit_button);

        super.onCreate(savedInstanceState);
        //TODO 2
        if (intent != null && intent.getBooleanExtra("fromProfile", false)){
            button.setVisibility(View.GONE);
        }else {
            //LinearLayout tabs = (LinearLayout) findViewById(R.id.tabs);
            //View childTabs = getLayoutInflater().inflate(R.layout.main_tabs, null);
            DummyMainActivity.initTabs(DummyMainActivity.VIEW_HABIT, HabitTabActivity.this, intent);
            //tabs.addView(childTabs);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HabitTabActivity.this, AddHabitActivity.class);
                    startActivityForResult(intent, 0);
                }
            });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null && data.getBooleanExtra("fromHabit", false)){
            setResult(DummyMainActivity.VIEW_FEED, data);
            finish();
        }
        if(requestCode == ADDING_EVENT){
        }
        DummyMainActivity.myHabitsAdapter.notifyDataSetChanged();
    }



}


