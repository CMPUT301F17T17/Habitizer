package ssmad.habitizer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by cryst on 10/22/2017.
 */

public class HabitTabActivity extends AppCompatActivity {
    public static ArrayList<Habit> myHabits = new ArrayList<>();
    private ArrayAdapter<Habit> myHabitsAdapter;
    public static String  GENERIC_REQUEST_CODE = "GENERIC.REQUEST.CODE";
    ListView myHabitsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_tab);

        myHabitsListView = (ListView) findViewById(R.id.habits_listview);
        myHabitsAdapter = new MyHabitsAdapter(HabitTabActivity.this, myHabits);
        myHabitsListView.setAdapter(myHabitsAdapter);

        Button button = (Button) findViewById(R.id.add_habit_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HabitTabActivity.this, AddHabitActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        myHabitsAdapter.notifyDataSetChanged();
    }
    public static void toastMe(String s, Context context){
        Toast.makeText(context,s, Toast.LENGTH_SHORT).show();
    }
}


