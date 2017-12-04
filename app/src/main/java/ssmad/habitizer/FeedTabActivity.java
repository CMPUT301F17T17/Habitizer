package ssmad.habitizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

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
        Intent intent = getIntent();
        intent.getStringExtra("username");
        DummyMainActivity.initTabs(DummyMainActivity.VIEW_FEED, this, intent);
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
