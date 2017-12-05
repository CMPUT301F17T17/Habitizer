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

public class SocialTabActivity extends AppCompatActivity {
    public static ArrayList<Account> SocialAccounts = new ArrayList<>();
    public static ArrayAdapter<Account> SocialAccountsAdapter;

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
        Button follower = (Button) findViewById(R.id.follower_btn);
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




    }
}
