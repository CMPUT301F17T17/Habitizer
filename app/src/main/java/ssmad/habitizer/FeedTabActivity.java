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

/**
 * Created by cryst on 10/22/2017.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

/**
 *Activity for displaying social related aspects of the app
 * @author Sadman
 * @version 0.5
 * @since 0.5
 */

public class FeedTabActivity extends AppCompatActivity {
    ListView myHabitEventsListView;
    public static final int VIEWING = 234;
    @Override
    protected void onStart() {
        super.onStart();
        myHabitEventsListView = (ListView) findViewById(R.id.feed_list);
        DummyMainActivity.myHabitEventsAdapter = new MyFeedAdapter(FeedTabActivity.this, DummyMainActivity
                .myHabitEvents);
        myHabitEventsListView.setAdapter(DummyMainActivity.myHabitEventsAdapter);
        DummyMainActivity.myHabitEventsAdapter.notifyDataSetChanged();


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_tab);
        DummyMainActivity.initTabs(DummyMainActivity.VIEW_FEED, this);
        (findViewById(R.id.view_map)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FeedTabActivity.this, ViewMapActivity.class);
                startActivityForResult(intent, VIEWING);
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
}
