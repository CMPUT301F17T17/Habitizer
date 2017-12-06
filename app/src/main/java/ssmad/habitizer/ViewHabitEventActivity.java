package ssmad.habitizer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import static ssmad.habitizer.ViewHabitActivity.toEdit;

public class ViewHabitEventActivity extends AppCompatActivity {
    private final int EDITING = 345;

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
        final int position = getIntent().getExtras().getInt
                (HabitTabActivity
                        .GENERIC_REQUEST_CODE);
        HabitEvent habitEvent = DummyMainActivity.myHabitEvents.get(position);
        commentView.setText(habitEvent.getComment());
        ((TextView)findViewById(R.id.what_habit)).setText(habitEvent.getTitle());
        if(habitEvent.hasPicture()){
            Bitmap p = AddHabitEventActivity.getPicFromBytes(habitEvent.getPicBytes());
            AddHabitEventActivity.setPic(this, p);
        }
        (findViewById(R.id.cancel)).setVisibility(View.GONE);
        Button doneButton = (Button)  findViewById(R.id.done_button);
        Button deleteButton = (Button)  findViewById(R.id.delete);
        deleteButton.setVisibility(View.VISIBLE);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEvent(position);

                finish();
            }
        });
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        doneButton.setVisibility(View.VISIBLE);
        Button editButton = (Button) findViewById(R.id.edit_button);
        (findViewById(R.id.add)).setVisibility(View.GONE);
        editButton.setVisibility(View.VISIBLE);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewHabitEventActivity.this, EditHabitEventActivity.class);
                intent.putExtra(toEdit, position);
                startActivityForResult(intent, EDITING);
            }
        });
        if(habitEvent.hasLocation()){

            LinearLayout mapToggle = (LinearLayout) findViewById(R.id.map_toggle);
            Boolean IHaveMapPermission = MapController.checkMapPermission(this);
            if (IHaveMapPermission){
                mapToggle.setVisibility(View.VISIBLE);
                Location loc = new Location("lol");
                double[] habitEventLoc = habitEvent.getLocation();
                loc.setLatitude(habitEventLoc[0]);
                loc.setLongitude(habitEventLoc[1]);
                MapController.initMap(this, loc);
            }else{
                //TODO
                MapController.askForMapPermission(this);
                mapToggle.setVisibility(View.GONE);
            }
        }
    }

    private void deleteEvent(int position) {
        HabitEvent habitEvent = DummyMainActivity.myHabitEvents.get(position);
        ElasticsearchController.DeleteItemsTask postHabitEvent =
                new ElasticsearchController.DeleteItemsTask();

        postHabitEvent.execute(DummyMainActivity.Event_Index, habitEvent.getId());
        if(habitEvent.hasPicture()) {
            ElasticsearchController.DeleteItemsTask postHabitEventPicture =
                    new ElasticsearchController.DeleteItemsTask();
            postHabitEventPicture.execute(DummyMainActivity.Pic_Index, habitEvent.getPic_id());
            try {
                Boolean success = postHabitEventPicture.get();
                if(!success){
                    throw new Exception("lol");
                }

            } catch (Exception e) {
                Log.d("ESC", "Could not update habit event picture on first try.");
                String[] s = {
                        DummyMainActivity.Pic_Index,
                        String.valueOf(SyncController.TASK_DELETE),
                        habitEvent.getPic_id()
                };
                SyncController.addToSync(s, habitEvent);

            }
        }
        try {
            Boolean success = postHabitEvent.get();
            if(!success){
                throw new Exception("lol");
            }
        } catch (Exception e) {
            Log.d("ESC", "Could not update habit event on first try.");
            String[] s = {
                    DummyMainActivity.Event_Index,
                    String.valueOf(SyncController.TASK_UPDATE),
                    habitEvent.getJsonString()
            };
            SyncController.addToSync(s, habitEvent);
        }

        // Andrew stuff

        //


        // TODO fix this for offline
        DummyMainActivity.myHabitEvents.remove(position);
        FileController.saveInFile(ViewHabitEventActivity.this, DummyMainActivity.HABITEVENTFILENAME,
                DummyMainActivity.myHabitEvents);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EDITING){
        }
        //restart
        finish();
        startActivity(getIntent());
    }
}
