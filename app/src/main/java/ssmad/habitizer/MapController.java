package ssmad.habitizer;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Sadman on 2017-12-05.
 */

public class MapController {
    public static GoogleMap gmap;

    public static GoogleMap getGmap() {
        return gmap;
    }

    public static Location sloc;
    private static int code = 0;

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 8;

    public static Location getLocation(){
        return sloc;
    }


    // 1. do first
    public static Boolean checkMapPermission(Activity fctx) {
        Boolean IhavePermission = false;
        IhavePermission =
                ContextCompat.checkSelfPermission(fctx,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(fctx,
                                android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                                PackageManager.PERMISSION_GRANTED;
        return IhavePermission;
    }

    //1.5 if ^ is false
    public static void askForMapPermission(Activity fctx) {
        ActivityCompat.requestPermissions(fctx,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_LOCATION);
    }

    //2. do this after permission
    public static Location getCurrentLocation(Activity fctx) {
        LocationManager locationManager =
                (LocationManager) fctx.getSystemService(fctx.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };
        // Already checked permission
        if (ActivityCompat.checkSelfPermission(fctx, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(fctx, android.Manifest.permission
                .ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return null;
        }
        locationManager.requestLocationUpdates(provider, 5000, 10, listener);
        Location lastKnownLocationLocation = locationManager.getLastKnownLocation(provider);
        return lastKnownLocationLocation;
    }

    private static void myOnMapReady(Activity fctx, GoogleMap googleMap, Location loc) {
        gotoLocation(fctx, loc);
    }
    public static void gotoLocation(Activity fctx, Location loc) {
        Location locc = loc;
        sloc = loc;
        if(loc == null){
            locc = getCurrentLocation(fctx);
        }
        if(code == AddHabitEventActivity.EVENT_PERMISSION_CHECK){
            double[] d = {locc.getLatitude(), locc.getLongitude()};
            AddHabitEventActivity.setLocation(d);
        }

        float zoom = 15.0f;
        LatLng ll = new LatLng(locc.getLatitude(), locc.getLongitude());
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        gmap.moveCamera(update);
    }

    //3. do this at the end
    public static void initMap(final Activity fctx, final Location loc) {
        MapFragment mapFragment = (MapFragment) fctx.getFragmentManager().findFragmentById(R.id
                .map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gmap = googleMap;
                myOnMapReady(fctx, googleMap, loc);
            }
        });
    }

    public static void initMap2(final Activity ctx, final Location location, final int
            permissionCode) {
        code = permissionCode;
        MapFragment mapFragment = (MapFragment) ctx.getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                myOnMapReady2(ctx, googleMap, location, permissionCode);
            }
        });

    }
    public static void myOnMapReady2(final Activity ctx, GoogleMap googleMap, Location location,
                                     final
    int permissionCode) {
        gmap = googleMap;
        Location loc = location;
        getLocationPermissionThenLocation(ctx, permissionCode, loc);

    }
    public static void getLocationPermissionThenLocation(Activity ctx, final int
            permissionCode, Location loc) {
        //https://github.com/CMPUT301W17T22/MoodSwing/blob/master/app/src/main/java/com/ualberta/cmput301w17t22/moodswing/MainActivity.java
        // Get the current location.
        // http://stackoverflow.com/questions/32491960/android-check-permission-for-locationmanager
        // Check if we have proper permissions to get the coarse lastKnownLocation.


        // Check if we have proper permissions to get the fine lastKnownLocation.
        if (ContextCompat.checkSelfPermission(ctx, android.Manifest.permission.
                ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Log.i("debugMaps", "Requesting fine permission");
            // Request the permission.
            // Dummy request code 8 used.
            ActivityCompat.requestPermissions(ctx,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, permissionCode);

        } else {
            gotoLocation(ctx, loc);

        }


    }

}
