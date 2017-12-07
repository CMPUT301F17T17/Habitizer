/*
 *  Class Name: SocialTabActivity
 *  Version: 1.0
 *  Date: November 13th, 2017
 *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  You may use, distribute, or modify this code under terms and conditions of the
 *  Code of Students Behaviour at University of Alberta
 */
package ssmad.habitizer;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 *Activity for displaying social related aspects of the app
 * @author Simon
 * @version 1.0
 * @since 0.1
 */

public class SocialTabActivity extends AppCompatActivity {
    public static ArrayList<Account> SocialAccounts = new ArrayList<>();
    public static ArrayAdapter<Account> SocialAccountsAdapter;
    public static int AdapterMode = 0;
    public static final int AdapterModeNothing = 0;
    public static final int AdapterModeRequests = 1;
    public static final int AdapterModeSentRequests = 2;
    @Override
    protected void onStart() {
        super.onStart();
        ListView SocialAccountsListView = (ListView) findViewById(R.id.multi_list);
        SocialAccountsAdapter = new SocialMultiAdapter(SocialTabActivity.this, SocialAccounts);
        SocialAccountsListView.setAdapter(SocialAccountsAdapter);
        SocialAccountsAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_tab);
        Intent intent = getIntent();
        intent.getStringExtra("username");
        DummyMainActivity.initTabs(DummyMainActivity.VIEW_SOCIAL, this, intent);

        Button following = (Button) findViewById(R.id.following_btn);
        Button followers = (Button) findViewById(R.id.followers_btn);
        Button requests = (Button) findViewById(R.id.requests_btn);
        Button requests_sent = (Button) findViewById(R.id.send_requests_btn);
        Button find = (Button) findViewById(R.id.find_btn);
        final Activity context = this;
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText) context.findViewById(R.id.user_search_input)).getText().toString().trim();
                ElasticsearchController.GetUsersTask getUsersTask = new ElasticsearchController.GetUsersTask();
                getUsersTask.execute(username);
                Account user;
                try {
                    if (!getUsersTask.get().isEmpty()) {
                        user = getUsersTask.get().get(0);
                        AdapterMode = AdapterModeNothing;
                        SocialAccounts.clear();
                        SocialAccounts.add(user);
                        SocialAccountsAdapter.notifyDataSetChanged();
                    }
                    else {
                        DummyMainActivity.toastMe("No such a user exists!", context);
                    }
                } catch (Exception e) {
                    Log.i("Error", "Failed to get the user accounts from the async object");
                }

            }
        });

        requests.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] arr =  DummyMainActivity.currentAccount.getRequests();
                    SocialAccounts.clear();
                    for (int i = 0; i <arr.length; i++) {
                        ElasticsearchController.GetUsersTask getUsersTask = new ElasticsearchController.GetUsersTask();
                        getUsersTask.execute(arr[i]);
                        Account user;
                        try {
                            if (!getUsersTask.get().isEmpty()) {
                                AdapterMode = AdapterModeRequests;
                                user = getUsersTask.get().get(0);
                                SocialAccounts.add(user);
                            }
                            else {
                                DummyMainActivity.toastMe("No such a user exists!", context);
                            }
                        } catch (Exception e) {
                            Log.i("Error", "Failed to get the user accounts from the async object");
                        }
                    }
                    SocialAccountsAdapter.notifyDataSetChanged();
                }
        });

        requests_sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] arr =  DummyMainActivity.currentAccount.getSent_requests();
                SocialAccounts.clear();
                for (int i = 0; i <arr.length; i++) {
                    ElasticsearchController.GetUsersTask getUsersTask = new ElasticsearchController.GetUsersTask();
                    getUsersTask.execute(arr[i]);
                    Account user;
                    try {
                        if (!getUsersTask.get().isEmpty()) {
                            AdapterMode = AdapterModeSentRequests;
                            user = getUsersTask.get().get(0);
                            SocialAccounts.add(user);
                        }
                        else {
                            DummyMainActivity.toastMe("No such a user exists!", context);
                        }
                    } catch (Exception e) {
                        Log.i("Error", "Failed to get the user accounts from the async object");
                    }
                }
                SocialAccountsAdapter.notifyDataSetChanged();
            }
        });

        followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] arr =  DummyMainActivity.currentAccount.getFollowers();
                SocialAccounts.clear();
                for (int i = 0; i <arr.length; i++) {
                    ElasticsearchController.GetUsersTask getUsersTask = new ElasticsearchController.GetUsersTask();
                    getUsersTask.execute(arr[i]);
                    Account user;
                    try {
                        if (!getUsersTask.get().isEmpty()) {
                            AdapterMode = AdapterModeNothing;
                            user = getUsersTask.get().get(0);
                            SocialAccounts.add(user);
                        }
                        else {
                            DummyMainActivity.toastMe("No such a user exists!", context);
                        }
                    } catch (Exception e) {
                        Log.i("Error", "Failed to get the user accounts from the async object");
                    }
                }
                SocialAccountsAdapter.notifyDataSetChanged();
            }
        });

        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] arr =  DummyMainActivity.currentAccount.getFollowing();
                SocialAccounts.clear();
                for (int i = 0; i <arr.length; i++) {
                    ElasticsearchController.GetUsersTask getUsersTask = new ElasticsearchController.GetUsersTask();
                    getUsersTask.execute(arr[i]);
                    Account user;
                    try {
                        if (!getUsersTask.get().isEmpty()) {
                            AdapterMode = AdapterModeNothing;
                            user = getUsersTask.get().get(0);
                            SocialAccounts.add(user);
                        }
                        else {
                            DummyMainActivity.toastMe("No such a user exists!", context);
                        }
                    } catch (Exception e) {
                        Log.i("Error", "Failed to get the user accounts from the async object");
                    }
                }
                SocialAccountsAdapter.notifyDataSetChanged();
            }
        });
    }
    //TODO 2 set result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == DummyMainActivity.VIEW_HABIT){
            setResult(DummyMainActivity.VIEW_HABIT, data);
            finish();
        }
    }
}
