/*
 *  Class Name: SocialTabActivity
 *  Version: 0.5
 *  Date: November 13th, 2017
 *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  You may use, distribute, or modify this code under terms and conditions of the
 *  Code of Students Behaviour at University of Alberta
 */

package ssmad.habitizer;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 *Activity for displaying social related aspects of the app
 * @author Simon
 * @version 0.1
 * @since 0.1
 */
public class SocialTabActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_tab);
        DummyMainActivity.initTabs(DummyMainActivity.VIEW_SOCIAL, this);
    }
}
