package ssmad.habitizer;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static ssmad.habitizer.ViewHabitActivity.toEdit;

public class ViewHabitEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_habit_event);
        (findViewById(R.id.view_title)).setVisibility(View.VISIBLE);
        (findViewById(R.id.comment_input)).setVisibility(View.GONE);
        (findViewById(R.id.pic_check)).setVisibility(View.GONE);
        (findViewById(R.id.location_check)).setVisibility(View.GONE);
        (findViewById(R.id.comment_title)).setVisibility(View.GONE);
        TextView commentView = (TextView) findViewById(R.id.comment_view);
        commentView.setVisibility(View.VISIBLE);
        HabitEvent habitEvent = DummyMainActivity.myHabitEvents.get(getIntent().getExtras().getInt
                (HabitTabActivity
                .GENERIC_REQUEST_CODE));
        commentView.setText(habitEvent.getComment());
        ((TextView)findViewById(R.id.what_habit)).setText(habitEvent.getTitle());
        if(habitEvent.hasPicture()){
            AddHabitEventActivity.setPicFromBytes(habitEvent.getPicBytes());
            AddHabitEventActivity.setPic(this);
            AddHabitEventActivity.picIsVisible = Boolean.TRUE;
            AddHabitEventActivity.setPicStuff(this);
        }
        (findViewById(R.id.cancel)).setVisibility(View.GONE);
        Button doneButton = (Button)  findViewById(R.id.done_button);
        AddHabitEventActivity.thisOneIsSpecialAndUnavailableSometimes = doneButton;
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddHabitEventActivity.resetVars(ViewHabitEventActivity.this);
                finish();
            }
        });
        doneButton.setVisibility(View.VISIBLE);
        Button editButton = (Button) findViewById(R.id.edit_button);
        (findViewById(R.id.add)).setVisibility(View.GONE);
        editButton.setVisibility(View.VISIBLE);
        final int position = getIntent().getIntExtra(HabitTabActivity
                .GENERIC_REQUEST_CODE, 0);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewHabitEventActivity.this, EditHabitEventActivity.class);
                intent.putExtra(toEdit, position);
                startActivityForResult(intent, 0);
            }
        });
        if(habitEvent.hasLocation()){
            double[] habitEventLoc = habitEvent.getLocation();
            Location l = new Location("lol");
            l.setLatitude(habitEventLoc[0]);
            l.setLongitude(habitEventLoc[1]);
            AddHabitEventActivity.initMap(this, l,doneButton);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //restart
        finish();
        startActivity(getIntent());
    }
}
