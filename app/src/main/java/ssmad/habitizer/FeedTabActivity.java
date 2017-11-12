package ssmad.habitizer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FeedTabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_tab);
        DummyMainActivity.initTabs(DummyMainActivity.VIEW_FEED, this);
    }
}
