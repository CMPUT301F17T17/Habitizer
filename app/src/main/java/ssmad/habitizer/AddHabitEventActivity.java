package ssmad.habitizer;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static java.security.AccessController.getContext;


/**
 * Created by cryst on 10/23/2017.
 */

/*
Ref getting pic
https://stackoverflow.com/questions/10165302/dialog-to-pick-image-from-gallery-or-from-camera
Ref getting map
https://stackoverflow.com/questions/16536414/how-to-use-mapview-in-android-using-google-map-v2
https://stackoverflow.com/questions/40142331/how-to-request-location-permission-on-android-6
https://www.youtube.com/watch?v=Z3mKhMkdUFk&feature=youtu.be
Ref pic size reduce
https://stackoverflow.com/questions/16954109/reduce-the-size-of-a-bitmap-to-a-specified-size-in-android
 */

public class AddHabitEventActivity extends AppCompatActivity implements OnMapReadyCallback {
    private final int GET_PIC_WITH_CAMERA = 0;
    private final int GET_PIC_FROM_GALLERY = 1;
    private final int MY_PERMISSIONS_REQUEST_LOCATION = 8;
    private final int PIC_MAX_SIZE = 65536;
    private static boolean picButtonsAreVisible = Boolean.FALSE;
    private static boolean picIsVisible = Boolean.FALSE;
    private static Bitmap pic;
    private GoogleMap gmap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_habit_event);
        Intent intent = getIntent();
        int position = intent.getIntExtra(HabitTabActivity.GENERIC_REQUEST_CODE,0);
        Habit habit = DummyMainActivity.myHabits.get(position);
        // Get stuff
        TextView habitTitle = (TextView) findViewById(R.id.what_habit);
        TextView habitEventComment = (EditText) findViewById(R.id.comment_input);
        ImageButton addTop = (ImageButton) findViewById(R.id.add_top);
        ImageButton cancelTop = (ImageButton) findViewById(R.id.cancel_top);
        Button add = (Button) findViewById(R.id.add);
        Button cancel = (Button) findViewById(R.id.cancel);
        Button fromCamera = (Button) findViewById(R.id.pic_camera);
        Button fromGallery = (Button) findViewById(R.id.pic_gallery);
        CheckBox locationCheck = (CheckBox) findViewById(R.id.location_check);
        CheckBox picCheck = (CheckBox) findViewById(R.id.pic_check);
        if (picButtonsAreVisible) {
            LinearLayout picToggle = (LinearLayout) findViewById(R.id.pic_toggle);
            picToggle.setVisibility(View.VISIBLE);
        }
        if (picIsVisible) {
            ImageView picPreview = (ImageView) findViewById(R.id.pic_preview);
            picPreview.setImageBitmap(pic);
            picPreview.setVisibility(View.VISIBLE);
        }

        // Set stuff
        habitTitle.setText(habit.getTitle());

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEvent();
            }
        });
        addTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEvent();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelEvent();
            }
        });
        cancelTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelEvent();
            }
        });

        picCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox thisBox = (CheckBox) v;
                LinearLayout picToggle = (LinearLayout) findViewById(R.id.pic_toggle);
                if (thisBox.isChecked()) {
                    picToggle.setVisibility(View.VISIBLE);
                } else {
                    picToggle.setVisibility(View.GONE);
                }
                picButtonsAreVisible = !picButtonsAreVisible;
            }
        });
        locationCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox thisBox = (CheckBox) v;
                LinearLayout mapToggle = (LinearLayout) findViewById(R.id.map_toggle);
                if (thisBox.isChecked()) {
                    mapToggle.setVisibility(View.VISIBLE);
                    initMap();
                } else {
                    mapToggle.setVisibility(View.GONE);
                }
            }
        });
        fromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, GET_PIC_WITH_CAMERA);
            }
        });
        fromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, GET_PIC_FROM_GALLERY);//one can be replaced with any action code
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case GET_PIC_WITH_CAMERA:
                if (resultCode == RESULT_OK) {
                    ImageView picPreview = (ImageView) findViewById(R.id.pic_preview);
                    pic = (Bitmap) data.getExtras().get("data");
                    Bitmap compressedPic = getResizedBitmap(pic, PIC_MAX_SIZE);

                    picPreview.setImageBitmap(compressedPic);
                    picPreview.setVisibility(View.VISIBLE);
                    DummyMainActivity.toastMe("get pic from camera", AddHabitEventActivity.this);
                    picIsVisible = Boolean.TRUE;

                }

                break;
            case GET_PIC_FROM_GALLERY:
                if (resultCode == RESULT_OK) {
                    ImageView picPreview = (ImageView) findViewById(R.id.pic_preview);
                    Uri imageUri = data.getData();
                    try {
                        pic = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    } catch (IOException e) {
                        DummyMainActivity.toastMe("Getting bitmap failed!", AddHabitEventActivity.this);
                        break;
                    }
                    Bitmap compressedPic = getResizedBitmap(pic, PIC_MAX_SIZE);
                    picPreview.setImageBitmap(compressedPic);
                    picPreview.setVisibility(View.VISIBLE);
                    DummyMainActivity.toastMe("get pic from gallery", AddHabitEventActivity.this);
                    picIsVisible = Boolean.TRUE;
                }
                break;
        }

    }


    public void addEvent() {

    }

    public void cancelEvent() {
        finish();
    }

    public Bitmap getResizedBitmap(Bitmap pic, int maxSize) {

        double div = 90.0;
        Bitmap image = pic.copy(pic.getConfig(), true);
        int size = image.getAllocationByteCount();

        int quality;
        while (size >= maxSize) {
            quality = (int) (div);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            pic.compress(Bitmap.CompressFormat.JPEG, quality, stream);

            byte[] byteArray = stream.toByteArray();
            image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            size = byteArray.length;
            Log.d("PhotoSize|Quality", size + " | " + quality);
            div = div * 0.9;
        }

        return image;

    }

    public void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        //gotoLocation(53.523079, -113.526329);
        Location loc = getLocationPermissionThenLocation();
        if(loc != null){

            double lat = loc.getLatitude();
            double lon = loc.getLongitude();

            //gotoLocation(53.523079, -113.526329);
            gotoLocation(lat, lon);
        }





    }

    private void gotoLocation(double lat, double lng) {
        float zoom = 15.0f;
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,zoom);
        gmap.moveCamera(update);
    }

    //https://github.com/CMPUT301W17T22/MoodSwing/blob/master/app/src/main/java/com/ualberta/cmput301w17t22/moodswing/MainActivity.java
    public Location getLocationPermissionThenLocation() {
        // Get the current location.
        // http://stackoverflow.com/questions/32491960/android-check-permission-for-locationmanager
        // Check if we have proper permissions to get the coarse lastKnownLocation.


        // Check if we have proper permissions to get the fine lastKnownLocation.
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.
                ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Log.i("debugMaps", "Requesting fine permission");
            // Request the permission.
            // Dummy request code 8 used.
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            return null;
        }else{
            return getCurrentLocation();
        }


    }

    public Location getCurrentLocation(){
        // Get the lastKnownLocation once every 5 seconds, or every 10 meters.
        LocationManager locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, false);

        //https://stackoverflow.com/questions/28935694/android-locationmanager-requestlocationupdates-cannot-be-resolved
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
        locationManager.requestLocationUpdates(provider, 5000, 10, listener);
        Location lastKnownLocationLocation = locationManager.getLastKnownLocation(provider);
        return lastKnownLocationLocation;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        Location loc = getCurrentLocation();
                        double lat = loc.getLatitude();
                        double lon = loc.getLongitude();

                        //gotoLocation(53.523079, -113.526329);
                        gotoLocation(lat, lon);


                    }

                } else {

                    DummyMainActivity.toastMe("Location permission was denied",
                            AddHabitEventActivity.this);

                }
                return;
            }

        }
    }


}
