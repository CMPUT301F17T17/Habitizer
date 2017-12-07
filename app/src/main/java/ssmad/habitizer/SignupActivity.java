/*
 *  Class Name: SignupActivity
 *  Version: 1.0
 *  Date: November 13th, 2017
 *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  You may use, distribute, or modify this code under terms and conditions of the
 *  Code of Students Behaviour at University of Alberta
 */
package ssmad.habitizer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
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
 * Activity for user signing up
 * @author Andrew
 * @version 1.0
 * @see UserProfile
 * @since 0.5
 */
public class SignupActivity extends AppCompatActivity {
    public static final String FILENAME= "account.sav";
    private EditText usernameText;
    private EditText passwordText;
    private Button signupButton;
    private static ArrayList<Account> accountList = new ArrayList<Account>();
    private String username;

    /**
     * Called when activity starts
     * Checks for existing username
     * Adds in new user on success
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usernameText = (EditText) findViewById(R.id.username_input);
        passwordText = (EditText) findViewById(R.id.password_input);
        signupButton =  (Button) findViewById(R.id.signup_btn);

        //loadFromFile();

        signupButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();
                Boolean find = find(username);
                Boolean correct = checkInput();

                if (find){
                    Toast.makeText(SignupActivity.this,
                            "Username already exists!", Toast.LENGTH_SHORT).show();
                } else if (correct){
                    //accountList.add(new Account(username, password));
                    //saveInFile(SignupActivity.this);

                    Intent intent = new Intent(v.getContext(), EditProfileActivity.class);
                    //intent.putExtra(EditProfileActivity.USER_NAME, username);
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    intent.putExtra("fromSignup", true);
                    startActivity(intent);
                    finish();
                }
        }   });
    }

    /**
     * Finds user in account list
     * @param username
     * @return
     */
    private Boolean find(String username){
        ElasticsearchController.GetUsersTask getUsersTask = new ElasticsearchController.GetUsersTask();
        getUsersTask.execute(username);
        try {
            if (!getUsersTask.get().isEmpty()) {
                return true;
            }
        } catch (Exception e) {
            Log.i("Error", "Failed to get the user accounts from the async object");
        }
        return false;
    }

    /**
     * Checks if constraints on input are met
     * @return
     */
    public Boolean checkInput(){
        Boolean correctness = true;
        String name = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        if(name.isEmpty() || name.trim().equals("") || password.length() > 12) {
            correctness = false;
            Toast.makeText(SignupActivity.this, "Must Input a username", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty() || password.trim().equals("") || password.length() > 12){
            correctness = false;
            Toast.makeText(SignupActivity.this, "Must input password in length(1-12)", Toast.LENGTH_SHORT).show();
        } else if (!name.matches("[0-9A-Za-z]*")){
            correctness = false;
            Toast.makeText(SignupActivity.this, "Username must be in a-z or A-Z or 0-9", Toast.LENGTH_SHORT).show();
        } else if (!password.matches("[0-9A-Za-z]*")){
            correctness = false;
            Toast.makeText(SignupActivity.this, "Password must be in a-z or A-Z or 0-9", Toast.LENGTH_SHORT).show();
        }

        return correctness;
    }

}
