package ssmad.habitizer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class DummyMainActivity extends AppCompatActivity {
    public ArrayList<HabitEvent> myHabitEvents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_main);
        myHabitEvents = new ArrayList<>();
        Intent intent = new Intent(DummyMainActivity.this, HabitTabActivity.class);
        startActivity(intent);

    }
    public static void toastMe(String s, Context context){
        Toast.makeText(context,s, Toast.LENGTH_SHORT).show();
    }
}
