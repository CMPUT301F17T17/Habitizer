package ssmad.habitizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class FeedTabActivity extends AppCompatActivity {
    ListView myHabitEventsListView;
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DummyMainActivity.myHabitsAdapter.notifyDataSetChanged();
    }
}
