/*
 *  Class Name: ViewMapActivity
 *  Version: 1.0
 *  Date: December 6th, 2017
 *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  You may use, distribute, or modify this code under terms and conditions of the
 *  Code of Students Behaviour at University of Alberta
 *
 */

package ssmad.habitizer;

import android.app.Activity;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

/**
 * Activity for viewing map
 * @author Sadman
 * @version 1.0
 * @see MapController
 * @since 1.0
 */
public class ViewMapActivity extends AppCompatActivity {
    public static final int MAP_PERMISSION_CHECK = 10;
    private final double NEAREST_DIST = 5000.0;

    /**
     * Called when activity starts, checks permissions and gets locations
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_map);
        Button recenter= (Button) findViewById(R.id.center);
        Button nearby= (Button) findViewById(R.id.nearby);



        MapController.initMap2(this, null, MAP_PERMISSION_CHECK);
        final Activity fctx = this;
        recenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapController.getGmap().clear();
                Location current = MapController.getCurrentLocation(fctx);
                MapController.gotoLocation(fctx, current);
                MarkerOptions marker = new MarkerOptions()
                        .position(new LatLng(current.getLatitude(), current
                                .getLongitude()))
                        .title("My position");
                MapController.getGmap().addMarker(marker);
                for(int i = 0; i < DummyMainActivity.myHabitEvents.size(); i++){
                    HabitEvent h = DummyMainActivity.myHabitEvents.get(i);
                    if(h.hasLocation()){
                        double[] loc = h.getLocation();
                        marker = new MarkerOptions()
                                .position(new LatLng(loc[0], loc[1]))
                                .title(h.getTitle());
                        MapController.getGmap().addMarker(marker);

                    }
                }
            }
        });
        nearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapController.getGmap().clear();
                Location current = MapController.getCurrentLocation(fctx);
                MapController.gotoLocation(fctx, current);
                MarkerOptions marker = new MarkerOptions()
                        .position(new LatLng(current.getLatitude(), current
                                .getLongitude()))
                        .title("My position");
                MapController.getGmap().addMarker(marker);
                for(int i = 0; i < DummyMainActivity.myHabitEvents.size(); i++){
                    HabitEvent h = DummyMainActivity.myHabitEvents.get(i);
                    if(h.hasLocation()){
                        double[] loc = h.getLocation();
                        Location nloc = new Location("lol2");
                        nloc.setLatitude(loc[0]);
                        nloc.setLongitude(loc[1]);
                        double diff =  current.distanceTo(nloc);
                        Boolean toofar = diff < NEAREST_DIST;
                        marker = new MarkerOptions()
                                .position(new LatLng(loc[0], loc[1]))
                                .title(h.getTitle()).alpha(toofar ? 1.0f: 0.5f);
                        MapController.getGmap().addMarker(marker);

                    }
                }
            }
        });
    }
}
