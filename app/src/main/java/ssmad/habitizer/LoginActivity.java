/*
<<<<<<< HEAD
 *  Class Name: HabitEvent
=======
 *  Class Name: LoginActivity
>>>>>>> master
 *  Version: 0.5
 *  Date: November 13th, 2017
 *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  You may use, distribute, or modify this code under terms and conditions of the
 *  Code of Students Behaviour at University of Alberta
 */
<<<<<<< HEAD
=======

>>>>>>> master
package ssmad.habitizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * Login Activity, for logging in and signing up
 * @author Andrew
 * @version 0.5
 * @since 0.5
 */
public class LoginActivity extends AppCompatActivity {
    public static final String FILENAME= "account2.sav";
    private EditText usernameText;
    private EditText passwordText;
    private Button loginButton;
    private Button signupButton;
    private ArrayList<Account> accountList = null;

    public static final int SIGNUP = 1212;

    /**
     * Called when activity starts
     * Takes in input for logging in, and connects buttons to actions
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Type listType = new TypeToken<ArrayList<Account>>(){}.getType();
        accountList = FileController.loadFromFile(this, FILENAME, listType);
        if (accountList != null && accountList.size() == 1) {
            Account a = accountList.get(0);
            Intent intent = new Intent();
            intent.putExtra("username", a.getUserName());
            DummyMainActivity.currentUser = a.getUserName();
            postLogin(a.getUserName());
            setResult(DummyMainActivity.VIEW_EDIT_PROFILE, intent);
            finish();
        }
        setContentView(R.layout.activity_login);

        usernameText = (EditText) findViewById(R.id.username_input);
        passwordText = (EditText) findViewById(R.id.password_input);
        loginButton = (Button) findViewById(R.id.login_btn);
        signupButton =  (Button) findViewById(R.id.signup_btn);

        //loadFromFile();

        loginButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();

                Boolean find = find(username, password);

                if (find) {
                    Intent intent = new Intent();
                    intent.putExtra("username", username);
                    DummyMainActivity.currentUser = username;
                    Account a = EditProfileActivity.findUser(username);
                    if (accountList == null) {
                        accountList = new ArrayList<Account>();
                        accountList.add(a);
                    } else {
                        accountList.clear();
                        accountList.add(a);
                    }
                    FileController.saveInFile(LoginActivity.this, FILENAME, accountList);
                    postLogin(username);
                    setResult(DummyMainActivity.VIEW_EDIT_PROFILE, intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Login failed: username or password is incorrect!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult( DummyMainActivity.VIEW_SIGN_UP,new Intent());
                finish();
            }
        });

    }

<<<<<<< HEAD
    private void postLogin(String username) {
        Type listType;
        if(Utilities.isNetworkAvailable(LoginActivity.this)){
            ElasticsearchController.GetItemsTask getHabitsArrayGetTask = new ElasticsearchController.GetItemsTask();
            getHabitsArrayGetTask.execute(DummyMainActivity.Habit_Index, "username", username);
            try{
                JsonArray jsonHabits =  getHabitsArrayGetTask.get();
                DummyMainActivity.myHabits = new ArrayList<>();
                for (int i = 0; i < jsonHabits.size(); i++){
                    Habit h = new Habit();
                    JsonObject job  = jsonHabits.get(i).getAsJsonObject();
                    /* Gson g = new Gson();
                    String s = g.toJson(job);
                    Log.d("LOGIN.json", s);*/
                    h.fromJsonObject(job);
                    DummyMainActivity.myHabits.add(h);
                }
            }catch (Exception e){
                Log.d("ESC", "Adding habits in login.");
            }
        }else{

            listType = new TypeToken<ArrayList<Habit>>(){}.getType();
            DummyMainActivity.myHabits =  FileController.loadFromFile(LoginActivity.this, DummyMainActivity.HABITFILENAME, listType);
            if ( DummyMainActivity.myHabits == null){
                DummyMainActivity.myHabits = new ArrayList<>();
            }
        }
    }

=======
    /**
     * Sends user to edit profile if requested
     * @param requestCode
     * @param resultCode
     * @param data
     */
>>>>>>> master
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == DummyMainActivity.VIEW_EDIT_PROFILE){
            Intent intent = new Intent();
            intent.replaceExtras(data);
            setResult(DummyMainActivity.VIEW_EDIT_PROFILE, intent);
            finish();
        }
    }

<<<<<<< HEAD
=======

    /**
     * Check if username and password match for successful login
     * @param username
     * @param password
     * @return
     */
>>>>>>> master
    private Boolean find(String username, String password) {
        ElasticsearchController.GetUsersTask getUsersTask = new ElasticsearchController.GetUsersTask();
        getUsersTask.execute(username);
        try {
            if (!getUsersTask.get().isEmpty()) {
                Account user = getUsersTask.get().get(0);
                if (user.getPassword().equals(password)){
                    return true;
                }
            }
        } catch (Exception e) {
            Log.i("Error", "Failed to get the user accounts from the async object");
        }
        return false;
    }

}
