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
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 8;

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

    public static void askForMapPermission(Activity fctx) {
        ActivityCompat.requestPermissions(fctx,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_LOCATION);
    }

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
        locationManager.requestLocationUpdates(provider, 5000, 10, listener);
        Location lastKnownLocationLocation = locationManager.getLastKnownLocation(provider);
        return lastKnownLocationLocation;
    }

    public static void gotoLocation(Activity fctx, Location loc, GoogleMap gmap) {
        float zoom = 15.0f;
        LatLng ll = new LatLng(loc.getLatitude(), loc.getLongitude());
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        gmap.moveCamera(update);
    }

    public static void initMap(final Activity fctx, final Location loc) {
        MapFragment mapFragment = (MapFragment) fctx.getFragmentManager().findFragmentById(R.id
                .map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                myOnMapReady(fctx, googleMap, loc);
            }
        });
    }

    private static void myOnMapReady(Activity fctx, GoogleMap googleMap, Location loc) {
        gotoLocation(fctx, loc, googleMap);
    }
}
