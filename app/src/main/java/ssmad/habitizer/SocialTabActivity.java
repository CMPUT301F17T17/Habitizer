package ssmad.habitizer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.robotium.solo.Solo;

/**
 * Created by cryst on 10/23/2017.
 */

public class SocialTabActivity extends AppCompatActivity{

    private Solo solo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_tab);
    }

}
