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



public class LoginActivity extends AppCompatActivity {
    public static final String FILENAME= "account.sav";
    private EditText usernameText;
    private EditText passwordText;
    private Button loginButton;
    private Button signupButton;
    //private ArrayList<Account> accountList = new ArrayList<Account>();

    public static final int SIGNUP = 1212;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
/**
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

    private Boolean find(String username, String password){
        for(int i=0; i < accountList.size(); i++){
            if(accountList.get(i).getUserName().equals(username) &&
                    accountList.get(i).getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }**/
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
