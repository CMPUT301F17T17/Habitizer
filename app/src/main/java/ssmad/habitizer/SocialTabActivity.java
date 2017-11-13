package ssmad.habitizer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SocialTabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_tab);
        DummyMainActivity.initTabs(DummyMainActivity.VIEW_SOCIAL, this);
    }
}
