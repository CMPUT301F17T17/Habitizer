package ssmad.habitizer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class SignupActivity extends AppCompatActivity {
    public static final String FILENAME= "account.sav";
    private EditText usernameText;
    private EditText passwordText;
    private Button signupButton;
    private static ArrayList<Account> accountList = new ArrayList<Account>();
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usernameText = (EditText) findViewById(R.id.username_input);
        passwordText = (EditText) findViewById(R.id.password_input);
        signupButton =  (Button) findViewById(R.id.signup_btn);

        loadFromFile();

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
                    accountList.add(new Account(username, password));
                    saveInFile(SignupActivity.this);
                    Intent intent = new Intent();
                    intent.putExtra(EditProfileActivity.USER_NAME, username);
                    setResult(DummyMainActivity.VIEW_EDIT_PROFILE, intent);
                    finish();
                }
        }   });
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            //Code taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt Sept.22,2016
            Type listType = new TypeToken<ArrayList<Account>>(){}.getType();
            accountList = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            accountList = new ArrayList<Account>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    public static void saveInFile(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(accountList, writer);
            writer.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    private Boolean find(String username){
        for(int i=0; i < accountList.size(); i++){
            if(accountList.get(i).getUserName().equals(username)){
                return true;
            }
        }
        return false;
    }

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