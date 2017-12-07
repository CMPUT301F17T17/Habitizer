package ssmad.habitizer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sadman on 2017-11-13.
 */

public class MyFeedAdapter extends ArrayAdapter<HabitEvent> {
    MyFeedAdapter( Context context, ArrayList<HabitEvent> habitEvents) {
        super(context, R.layout.myfeed_list_view, habitEvents);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Inflating from xml
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View custom = inflater.inflate(R.layout.myfeed_list_view, parent, false);
        HabitEvent habitEvent = getItem(position);
        ((TextView) custom.findViewById(R.id.habit)).setText(habitEvent.getTitle());
        ((TextView) custom.findViewById(R.id.comment)).setText(habitEvent.getComment());

        CheckBox picCheck = (CheckBox) custom.findViewById(R.id.pic_check);
        if(habitEvent.hasPicture()){
            picCheck.toggle();
        }
        picCheck.setClickable(Boolean.FALSE);
        CheckBox locCheck = (CheckBox) custom.findViewById(R.id.loc_check);
        if(habitEvent.hasLocation()){
            locCheck.toggle();
        }
        locCheck.setClickable(Boolean.FALSE);
        final int fpos = position;
        (custom.findViewById(R.id.outer)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewHabitEventActivity.class);
                intent.putExtra("event_position", fpos);
                ((Activity) getContext()).startActivityForResult(intent, FeedTabActivity.VIEWING);
            }
        });


        return custom;
    }
}