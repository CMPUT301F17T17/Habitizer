/*
 *  Class Name: FollowActivity
 *  Version: 0.5
 *  Date: November 13th, 2017
 *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  You may use, distribute, or modify this code under terms and conditions of the
 *  Code of Students Behaviour at University of Alberta
 */

package ssmad.habitizer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.robotium.solo.Solo;

/**
 * Created by dwai on 2017-10-23.
 */

/**
 *Activity for displaying Follow/Following related aspects of the app
 * @author Derrick
 * @version 0.1
 * @since 0.1
 */

public class FollowActivity extends AppCompatActivity {

    private Solo solo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_tab);
    }
}
