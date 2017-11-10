package ssmad.habitizer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sadman on 2017-11-10.
 */

public class MyHabitsAdapter extends ArrayAdapter<Habit> {
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

        TextView titleView = (TextView) custom.findViewById(R.id.title);
        TextView reasonView = (TextView) custom.findViewById(R.id.reason);
        Button add = (Button) custom.findViewById(R.id.add);
        LinearLayout main = (LinearLayout) custom.findViewById(R.id.main);

        // Setting things
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
                Intent intent = new Intent(getContext(), EditHabitActivity.class);
                intent.putExtra(HabitTabActivity.GENERIC_REQUEST_CODE, position);
                //TODO
                //((Activity) getContext()).startActivityForResult(intent, 0);
            }
        });


        return custom;
    }
}
