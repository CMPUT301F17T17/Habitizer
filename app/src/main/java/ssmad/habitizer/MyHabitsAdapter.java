package ssmad.habitizer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static ssmad.habitizer.R.drawable.days_border_valid;

/**
 * Created by Sadman on 2017-11-10.
 */

public class MyHabitsAdapter extends ArrayAdapter<Habit> {
    private static final int[] days = {R.id.m,
            R.id.t, R.id.w, R.id.th,
            R.id.f, R.id.s, R.id.su};
    MyHabitsAdapter(Context context, ArrayList<Habit> myHabits) {
        super(context, R.layout.myhabits_list_view, myHabits);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Inflating from xml
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View custom = inflater.inflate(R.layout.myhabits_list_view, parent, false);

        // Getting things
        String habTitle = getItem(position).getTitle();
        String habReason = getItem(position).getReason();
        int[] habDueDays = getItem(position).getDaysOfWeekDue();
        TextView titleView = (TextView) custom.findViewById(R.id.title);
        TextView reasonView = (TextView) custom.findViewById(R.id.reason);
        Button add = (Button) custom.findViewById(R.id.add);
        LinearLayout main = (LinearLayout) custom.findViewById(R.id.main);

        LinearLayout daysOuter = (LinearLayout) custom.findViewById(R.id.days_outer);
        View childdays = inflater.inflate(R.layout.days, null);

        // Setting things

        for(int i = 0; i < 7; i++){
            if(habDueDays[i] == 1){
                TextView dayImageView = (TextView) childdays.findViewById(days[i]);
                dayImageView.setBackground(getContext().getDrawable(R.drawable.days_border_valid));
                Log.d("setting tint(pos|day)",position+"|"+i);
            }
        }

        LinearLayout daysInner = (LinearLayout) childdays.findViewById(R.id.days_inner);
        daysInner.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        daysOuter.addView(childdays);


        titleView.setText(habTitle);
        reasonView.setText(habReason);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddHabitEventActivity.class);
                intent.putExtra(HabitTabActivity.GENERIC_REQUEST_CODE, position);
                ((Activity) getContext()).startActivityForResult(intent, 0);
            }
        });
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewHabitActivity.class);
                intent.putExtra(HabitTabActivity.GENERIC_REQUEST_CODE, position);
                ((Activity) getContext()).startActivityForResult(intent, 0);
            }
        });


        return custom;
    }
}
