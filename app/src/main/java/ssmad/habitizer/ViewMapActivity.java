package ssmad.habitizer;

import android.app.Activity;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ViewMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_map);
        Button recenter= (Button) findViewById(R.id.center);
        Button nearby= (Button) findViewById(R.id.nearby);
        AddHabitEventActivity.initMap(this, null, recenter);
        final Activity fctx = this;
        recenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location current = AddHabitEventActivity.getCurrentLocation(fctx);
                AddHabitEventActivity.gotoLocation(fctx, current.getLatitude(), current
                        .getLongitude(), (Button) v);
                Marker marker = AddHabitEventActivity.gmap.addMarker(new MarkerOptions()
                        .position(new LatLng(current.getLatitude(), current
                                .getLongitude()))
                        .title("My position"));
                for(int i = 0; i < DummyMainActivity.myHabitEvents.size(); i++){
                    HabitEvent h = DummyMainActivity.myHabitEvents.get(i);
                    double[] loc = h.getLocation();
                    marker = AddHabitEventActivity.gmap.addMarker(new MarkerOptions()
                            .position(new LatLng(loc[0], loc[1]))
                            .title(h.getTitle()));
                }
            }
        });
    }
}
