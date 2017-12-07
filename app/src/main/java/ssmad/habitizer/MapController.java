/*
 *  Class Name: MapController
 *  Version: 1.0
 *  Date: December 6th, 2017
 *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  You may use, distribute, or modify this code under terms and conditions of the
 *  Code of Students Behaviour at University of Alberta
 *
 */

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
 * Controller for map, getting permission, location, and displaying
 * @author Sadman
 * @version 1.0
 * @since 1.0
 */
public class MapController {
    public static GoogleMap gmap;

    /**
     * Gets google map
     * @return
     */
    public static GoogleMap getGmap() {
        return gmap;
    }

    public static Location sloc;
    private static int code = 0;

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 8;

    /**
     * Gets location
     * @return
     */
    public static Location getLocation(){
        return sloc;
    }


    /**
     * Checks for permission to access location, we do this first
     * @param fctx
     * @return
     */
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

    /**
     * Asks for map permission
     * 1.5th step if ^ is false
     */
    public static void askForMapPermission(Activity fctx) {
        ActivityCompat.requestPermissions(fctx,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_LOCATION);
    }

    /**
     * Get current location after receiving permission to access location
     * @param fctx
     * @return
     */
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

    /**
     * Go to given location on the map when ready
     * @param fctx
     * @param googleMap
     * @param loc
     */
    private static void myOnMapReady(Activity fctx, GoogleMap googleMap, Location loc) {
        gotoLocation(fctx, loc);
    }

    /**
     * For actually moving the map to the desired location.
     * @param fctx
     * @param loc
     */
    public static void gotoLocation(Activity fctx, Location loc) {
        Location locc = loc;
        sloc = loc;
        if(loc == null){
            locc = getCurrentLocation(fctx);
        }
        if(code == AddHabitEventActivity.EVENT_PERMISSION_CHECK){
        }

        float zoom = 15.0f;
        if(locc == null){
            DummyMainActivity.toastMe("Could not get location", fctx);
        }else{
            double[] d = {locc.getLatitude(), locc.getLongitude()};
            AddHabitEventActivity.setLocation(d);

            LatLng ll = new LatLng(locc.getLatitude(), locc.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
            gmap.moveCamera(update);
        }
    }

    //3. do this at the end

    /**
     * Last step, we initialize the map
     * @param fctx
     * @param loc
     */
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

    /**
     * Gets location permission and then location
     * @param ctx
     * @param permissionCode
     * @param loc
     */
    public static void getLocationPermissionThenLocation(Activity ctx, final int
            permissionCode, Location loc) {
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
