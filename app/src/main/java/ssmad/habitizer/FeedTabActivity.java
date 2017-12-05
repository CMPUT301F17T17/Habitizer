package ssmad.habitizer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FeedTabActivity extends AppCompatActivity {
    ListView myHabitEventsListView;
    public static final int VIEWING = 234;

    private static Boolean toClear = false;
    @Override
    protected void onStart() {
        super.onStart();
        myHabitEventsListView = (ListView) findViewById(R.id.feed_list);
        DummyMainActivity.myHabitEventsAdapter = new MyFeedAdapter(FeedTabActivity.this, DummyMainActivity
                .myHabitEvents);
        myHabitEventsListView.setAdapter(DummyMainActivity.myHabitEventsAdapter);
        refreshFeed(null);
        DummyMainActivity.myHabitEventsAdapter.notifyDataSetChanged();


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_tab);
        Intent intent = getIntent();
        intent.getStringExtra("username");
        DummyMainActivity.initTabs(DummyMainActivity.VIEW_FEED, this, intent);

        final Button clearbtn = (Button) findViewById(R.id.clear_button);

        (findViewById(R.id.view_map)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedTabActivity.this, ViewMapActivity.class);
                startActivityForResult(intent, VIEWING);
            }
        });
        final Activity context = this;
        clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toClear = false;
                clearbtn.setVisibility(View.GONE);
                ((EditText) context.findViewById(R.id.search_input)).setText("");
                refreshFeed(null);
            }
        });
        (findViewById(R.id.search_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String word = ((EditText) context.findViewById(R.id.search_input)).getText().toString().trim();
                toClear = false;
                if (word.equals("")){
                    DummyMainActivity.toastMe("Please enter a valid word", context);
                }else if(word.contains(" ")){
                    DummyMainActivity.toastMe("Please enter only one word", context);
                }else{
                    refreshFeed(word);
                    toClear = true;
                }
                if(toClear){
                    clearbtn.setVisibility(View.VISIBLE);
                }else{
                    clearbtn.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DummyMainActivity.myHabitEventsAdapter.notifyDataSetChanged();
        if(requestCode == VIEWING){
            AddHabitEventActivity._resetVars();
        }
    }

    private void refreshFeed(String word) {
        Type listType;
        if(Utilities.isNetworkAvailable(FeedTabActivity.this)){
            ElasticsearchController.GetFeedTask getHabitsArrayGetTask = new ElasticsearchController.GetFeedTask();
            String query = String.format(
                "\"bool\":{" +
                    "\"must\":[" +
                        "{ \"term\" :" +
                            "{\"username\": \"%s\"}" +
                        "}", DummyMainActivity.currentUser);
            if(word != null){
                query = query +  String.format("," +
                            "{ \"term\" : " +
                                "{\"comment\": \"%s\"}" +
                            "}" +
                        "]}", word);
            }else{
                query = query + "]}";
            }
            getHabitsArrayGetTask.execute(DummyMainActivity.Event_Index, query);
            try{
                JsonArray jsonHabits =  getHabitsArrayGetTask.get();
                DummyMainActivity.myHabitEvents.clear();
                for (int i = 0; i < jsonHabits.size(); i++){
                    HabitEvent h = new HabitEvent();
                    JsonObject job  = jsonHabits.get(i).getAsJsonObject();
                    /* Gson g = new Gson();
                    String s = g.toJson(job);
                    Log.d("LOGIN.json", s);*/
                    h.fromJsonObject(job);
                    DummyMainActivity.myHabitEvents.add(h);
                }
                Collections.sort(DummyMainActivity.myHabitEvents, new Comparator<HabitEvent>() {
                    @Override
                    public int compare(HabitEvent o1, HabitEvent o2) {
                        return -o1.getCompletionDate().compareTo(o2.getCompletionDate());
                    }
                });
                FileController.saveInFile(FeedTabActivity.this, DummyMainActivity.HABITEVENTFILENAME, DummyMainActivity.myHabitEvents);
            }catch (Exception e){
                Log.d("ESC", "Adding habits events in login.");
            }
        }else{

            listType = new TypeToken<ArrayList<HabitEvent>>(){}.getType();
            DummyMainActivity.myHabitEvents.clear();
            ArrayList arr =  FileController.loadFromFile(FeedTabActivity.this, DummyMainActivity.HABITEVENTFILENAME, listType);
            if ( DummyMainActivity.myHabitEvents != null){
                DummyMainActivity.myHabitEvents.addAll(arr);
            }
        }
        DummyMainActivity.myHabitEventsAdapter.notifyDataSetChanged();
    }
}
