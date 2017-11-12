package ssmad.habitizer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static android.R.color.holo_blue_light;
import static android.R.color.holo_orange_dark;
import static android.R.color.white;


public class DummyMainActivity extends AppCompatActivity {
    public static ArrayList<HabitEvent> myHabitEvents;
    public static ArrayList<Habit> myHabits;
    public static ArrayAdapter<Habit> myHabitsAdapter;
    public static Map<String,Integer> myHabitDict;
    public static final int VIEW_HABIT = 0;
    public static final int VIEW_FEED = 1;
    public static final int VIEW_SOCIAL = 2;
    public static Activity currentActivity;
    private static Context thisContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy_main);
        thisContext = this;
        myHabitEvents = new ArrayList<>();
        myHabits = new ArrayList<>();
        myHabitDict = new HashMap<>();
        DEBUG_addHabits();

        Intent intent = new Intent(DummyMainActivity.this, HabitTabActivity.class);
        startActivityForResult(intent, VIEW_HABIT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent;
        switch (resultCode) {
            case VIEW_FEED:
                intent = new Intent(this, FeedTabActivity.class);
                startActivityForResult(intent, VIEW_FEED);
                break;
            case VIEW_HABIT:
                intent = new Intent(this, HabitTabActivity.class);
                startActivityForResult(intent, VIEW_HABIT);
                break;
            case VIEW_SOCIAL:
                intent = new Intent(this, SocialTabActivity.class);
                startActivityForResult(intent, VIEW_SOCIAL);
        }
    }

    public static void toastMe(String s, Context context) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
    public void DEBUG_addHabits(){
        for(int i = 0; i < 10; i++){
            String title = "Habit_"+Integer.toString(i);
            Habit h = new Habit(title, new Date(), "Reasonable reason "+Integer.toString(i));
            int[] days = new int[7];
            for(int j = 0; j < 7; j++){
                int r = 0;
                if((i+1)%(j+1)==1){
                    r = 1;
                }
                days[j] = r;
                Log.d("Days(i|j=r)", i+"|"+j+"="+r);
            }

            h.setDaysOfWeekDue(days);
            myHabits.add(h);
            myHabitDict.put(title, 0);
        }

    }

    public boolean isGoogleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return Boolean.TRUE;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            toastMe("Can't connect to play services!", this);
        }
        return Boolean.FALSE;
    }

    public static void initTabs(int type, Activity ctx) {
        DummyMainActivity.currentActivity = ctx;
        LinearLayout tabs = (LinearLayout) ctx.findViewById(R.id.tabs);
        View childTabs = ctx.getLayoutInflater().inflate(R.layout.main_tabs, null);


        Button toChange;
        Button bHabits = (Button) childTabs.findViewById(R.id.habits);
        Button bFeed = (Button) childTabs.findViewById(R.id.feed);
        Button bSocial = (Button) childTabs.findViewById(R.id.social);

        switch (type) {
            case VIEW_FEED:
                toChange = bFeed;
                break;
            case VIEW_HABIT:
                toChange = bHabits;
                break;
            case VIEW_SOCIAL:
                toChange = bSocial;
                break;
            default:
                toChange = bHabits;
        }
        // do colors
        toChange.setBackgroundColor(ctx.getResources().getColor(holo_blue_light, null));
        toChange.setTextColor(ctx.getResources().getColor(white, null));
        // set w/h
        LinearLayout tabsInner = (LinearLayout) childTabs.findViewById(R.id.tabs_inner);
        tabsInner.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        // init buttons
        if (bHabits != toChange) {
            bHabits.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentActivity.setResult(VIEW_HABIT);
                    currentActivity.finish();

                }
            });
        }
        if (bFeed != toChange) {
            bFeed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentActivity.setResult(VIEW_FEED);
                    currentActivity.finish();

                }
            });
        }
        if (bSocial != toChange) {
            bSocial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentActivity.setResult(VIEW_SOCIAL);
                    currentActivity.finish();
                }
            });
        }


        // add it
        tabs.addView(childTabs);


    }

}