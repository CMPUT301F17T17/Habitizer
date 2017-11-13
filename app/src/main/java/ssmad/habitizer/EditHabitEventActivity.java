package ssmad.habitizer;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

import static ssmad.habitizer.ViewHabitActivity.toEdit;

public class EditHabitEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_habit_event);
        (findViewById(R.id.add)).setVisibility(View.GONE);
        (findViewById(R.id.cancel)).setVisibility(View.GONE);
        (findViewById(R.id.edit_title)).setVisibility(View.VISIBLE);
        final int position = getIntent().getExtras().getInt(HabitTabActivity.GENERIC_REQUEST_CODE);
        HabitEvent habitEvent = DummyMainActivity.myHabitEvents.get(position);
        ((EditText) findViewById(R.id.comment_input)).setText(habitEvent.getComment());
        ((TextView)findViewById(R.id.what_habit)).setText(habitEvent.getTitle());
        CheckBox picCheck = (CheckBox) findViewById(R.id.pic_check);
        if(habitEvent.hasPicture()){
            /*AddHabitEventActivity.picIsVisible = Boolean.TRUE;
            AddHabitEventActivity.picButtonsAreVisible = Boolean.TRUE;
            AddHabitEventActivity.setPicFromBytes(habitEvent.getPicBytes());
            AddHabitEventActivity.setPicStuff(this);
            AddHabitEventActivity.setPic(this);
            picCheck.toggle();*/
        }

        if(habitEvent.hasLocation()){
            /*double[] habitEventLoc = habitEvent.getLocation();
            Location l = new Location("lol");
            l.setLatitude(habitEventLoc[0]);
            l.setLongitude(habitEventLoc[1]);
            AddHabitEventActivity.initMap(this, l);*/
        }


        Button doneButton = (Button)  findViewById(R.id.done_button);
        /*AddHabitEventActivity.setUpCheckBoxes(this, doneButton);
        AddHabitEventActivity.setUpPicButtons(this, doneButton);*/

        final Activity fctx= this;
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* HabitEvent habitEvent1 = DummyMainActivity.myHabitEvents.get(position);
                if(AddHabitEventActivity.habitEventCheckFix(fctx)){
                    habitEvent1.setPicBytes(AddHabitEventActivity.picBytes);
                    habitEvent1.setLocation(AddHabitEventActivity.location);
                    habitEvent1.setComment(AddHabitEventActivity.comment);

                    finish();
                }*/
            }
        });
        doneButton.setVisibility(View.VISIBLE);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AddHabitEventActivity.tryGetPic(this, requestCode, resultCode, data);
        AddHabitEventActivity.makeButtonAvailable((Button) findViewById(R.id.done_button));
    }
}
