package ssmad.habitizer;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
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
import java.util.Date;

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
https://developer.amazon.com/docs/maps/display-interactive.html
Ref pic size reduce
https://stackoverflow.com/questions/16954109/reduce-the-size-of-a-bitmap-to-a-specified-size-in-android
 */

public class AddHabitEventActivity extends AppCompatActivity {
    public static final int GET_PIC_WITH_CAMERA = 0;
    public static final int GET_PIC_FROM_GALLERY = 1;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 8;
    public static final int PIC_MAX_SIZE = 65536;
    public static final int COMMENT_MAX_SIZE = 30;
    public static boolean picButtonsAreVisible = Boolean.FALSE;
    public static boolean picIsVisible = Boolean.FALSE;
    public static boolean mapIsVisible = Boolean.FALSE;
    public static boolean picWasChanged = Boolean.FALSE;
    public static boolean locWasChanged = Boolean.FALSE;
    public static double[] location;
    public static String comment;
    public static Bitmap pic;
    public static byte[] picBytes;
    public static GoogleMap gmap;
    public static Button thisOneIsSpecialAndUnavailableSometimes;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_habit_event);
        // Get stuff
        final int position = getIntent().getExtras().getInt(HabitTabActivity.GENERIC_REQUEST_CODE);
        final Habit habit = DummyMainActivity.myHabits.get(position);
        final TextView habitTitle = (TextView) findViewById(R.id.what_habit);
        (findViewById(R.id.add_title)).setVisibility(View.VISIBLE);
        //final TextView habitEventComment = (EditText) findViewById(R.id.comment_input);
        final Button add = (Button) findViewById(R.id.add);
        thisOneIsSpecialAndUnavailableSometimes = add;
        Button cancel = (Button) findViewById(R.id.cancel);


        setPicStuff(this);
        setLocStuff(this);


        // Set stuff
        habitTitle.setText(habit.getTitle());
        add.setOnClickListener(new View.OnClickListener() {
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
        setUpCheckBoxes(this, (Button) findViewById(R.id.add));
        setUpPicButtons(this, (Button) findViewById(R.id.add));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // OK
        super.onActivityResult(requestCode, resultCode, data);
        tryGetPic(this, requestCode, resultCode, data);
        makeButtonAvailable((Button) findViewById(R.id.add));
    }

    public static void setUpCheckBoxes(Activity ctx, final Button btn) {
        CheckBox locationCheck = (CheckBox) ctx.findViewById(R.id.location_check);
        CheckBox picCheck = (CheckBox) ctx.findViewById(R.id.pic_check);
        final Activity fctx = ctx;
        picCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox thisBox = (CheckBox) v;
                if (thisBox.isChecked()) {
                    picButtonsAreVisible = Boolean.TRUE;
                } else {
                    picButtonsAreVisible = Boolean.FALSE;
                    picIsVisible = Boolean.FALSE;
                    pic = null;
                    picBytes = null;
                }
                setPicStuff(fctx);
            }
        });
        locationCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox thisBox = (CheckBox) v;
                if (thisBox.isChecked()) {

                    makeButtonUnavailable(btn);
                    initMap(fctx, null, btn);
                } else {
                    mapIsVisible = Boolean.FALSE;
                    location = null;
                    setLocStuff(fctx);
                    makeButtonAvailable(btn);
                }
            }
        });
    }

    public static void setUpPicButtons(Activity ctx, final Button btn) { // OK
        Button fromCamera = (Button) ctx.findViewById(R.id.pic_camera);
        Button fromGallery = (Button) ctx.findViewById(R.id.pic_gallery);
        final Activity fctx = ctx;
        fromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeButtonUnavailable(btn);
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fctx.startActivityForResult(takePicture, GET_PIC_WITH_CAMERA);

            }
        });
        fromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeButtonUnavailable(btn);
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                fctx.startActivityForResult(pickPhoto, GET_PIC_FROM_GALLERY);
            }
        });
    }

    public static void setLocStuff(Activity ctx) {
        LinearLayout mapToggle = (LinearLayout) ctx.findViewById(R.id.map_toggle);
        if (mapIsVisible) {
            mapToggle.setVisibility(View.VISIBLE);
        } else {
            mapToggle.setVisibility(View.GONE);

        }
    }

    public static void setPicStuff(Activity ctx) { // OK
        LinearLayout picToggle = (LinearLayout) ctx.findViewById(R.id.pic_toggle);
        if (picButtonsAreVisible) {
            picToggle.setVisibility(View.VISIBLE);
        } else {
            picToggle.setVisibility(View.GONE);

        }
        ImageView picPreview = (ImageView) ctx.findViewById(R.id.pic_preview);
        if (picIsVisible) {
            picPreview.setVisibility(View.VISIBLE);
        } else {
            picPreview.setVisibility(View.GONE);
        }
        picPreview.setImageBitmap(pic);
    }

    public static void tryGetPic(Activity ctx, int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case GET_PIC_WITH_CAMERA:
                if (resultCode == RESULT_OK) {
                    pic = (Bitmap) data.getExtras().get("data");
                    setPic(ctx);
                    picIsVisible = Boolean.TRUE;
                    setPicStuff(ctx);
                    DummyMainActivity.toastMe("get pic from camera", ctx);
                } else {
                    getPictureFail(ctx);
                }
                break;
            case GET_PIC_FROM_GALLERY:
                if (resultCode == RESULT_OK) {
                    Uri imageUri = data.getData();
                    try {
                        pic = MediaStore.Images.Media.getBitmap(ctx.getContentResolver(), imageUri);
                    } catch (IOException e) {
                        getPictureFail(ctx);
                    }
                    setPic(ctx);
                    picIsVisible = Boolean.TRUE;
                    setPicStuff(ctx);
                    DummyMainActivity.toastMe("get pic from gallery", ctx);
                } else {
                    getPictureFail(ctx);
                }
                break;
        }
    }

    public static void getPictureFail(Activity ctx) {
        DummyMainActivity.toastMe("Failed to get pic", ctx);
        AddHabitEventActivity.picIsVisible = Boolean.FALSE;
        AddHabitEventActivity.picButtonsAreVisible = Boolean.FALSE;
        setPicStuff(ctx);
        ((CheckBox) ctx.findViewById(R.id.pic_check)).setChecked(Boolean.FALSE);
        picBytes = null;
        pic = null;


    }

    public static void setPic(Activity ctx) { // OK
        byte[] b = getCompressedByteFromBitmap(pic, PIC_MAX_SIZE);
        _setPic(ctx, b);

    }
    public static void _setPic(Activity ctx, byte[] bytes){
        ImageView picPreview = (ImageView) ctx.findViewById(R.id.pic_preview);
        picBytes = bytes;
        picWasChanged = true;
        //picPreview.setImageBitmap(pic);
        setPicFromBytes(picBytes);
        picPreview.setImageBitmap(pic);
    }

    public static byte[] getCompressedByteFromBitmap(Bitmap pic, int maxSize) {
        double div = 100.0;
        ByteArrayOutputStream stream;
        int size;
        byte[] image;
        int quality;
        do {
            quality = (int) div;
            stream = new ByteArrayOutputStream();
            pic.compress(Bitmap.CompressFormat.JPEG, quality, stream);
            image = stream.toByteArray();
            size = image.length;
            div = div * 0.9;
        } while (size >= maxSize);


        return image;
    }

    public static void setPicFromBytes(byte[] bytes) {
        pic = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public void addEvent() {
        Habit habit = DummyMainActivity.myHabits.get(getIntent().getExtras().getInt
                (HabitTabActivity.GENERIC_REQUEST_CODE));
        if (habitEventCheckFix(this)) {
            byte[] fpb = picIsVisible ? picBytes : null;
            HabitEvent habitEvent = new HabitEvent(habit.getTitle(), new Date(), fpb, location,
                    comment);
            ElasticsearchController.AddItemsTask postHabitEvent = new ElasticsearchController.AddItemsTask();



            habitEvent.setHabit_id(habit.getId());
            habitEvent.setUsername(habit.getUsername());
            postHabitEvent.execute(DummyMainActivity.Event_Index, habitEvent.getJsonString());
            try{
                String id = postHabitEvent.get();
                habitEvent.setId(id);
            }catch (Exception e){
                Log.d("ESC", "Could not update habit event on first try.");
            }
            DummyMainActivity.myHabitEvents.add(habitEvent);
            FileController.saveInFile(AddHabitEventActivity.this, DummyMainActivity.HABITEVENTFILENAME, DummyMainActivity.myHabitEvents);

            finish();
        }

    }

    public static boolean habitEventCheckFix(Activity ctx) {
        comment = ((EditText) ctx.findViewById(R.id.comment_input)).getText().toString();
        if (!mapIsVisible) {
            location = null;
        }
        if (!picIsVisible) {
            pic = null;
            picBytes = null;
        }
        if (comment.length() > COMMENT_MAX_SIZE) {
            DummyMainActivity.toastMe("Comment must be less than " + COMMENT_MAX_SIZE + " chars",
                    ctx);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public void cancelEvent() {
        finish();
    }

    public static void resetVars(Activity ctx) {
        location = null;
        pic = null;
        picBytes = null;
        picIsVisible = false;
        picButtonsAreVisible = false;
        mapIsVisible = false;
        setPicStuff(ctx);
        setLocStuff(ctx);
    }
    public static void _resetVars() {
        location = null;
        pic = null;
        picBytes = null;
        picIsVisible = false;
        picButtonsAreVisible = false;
        mapIsVisible = false;
        picWasChanged = false;
        locWasChanged = false;
    }

    public static void makeButtonUnavailable(Button button) {
        button.setAlpha(0.5f);
        button.setClickable(Boolean.FALSE);
    }

    public static void makeButtonAvailable(Button button) {
        button.setAlpha(1.0f);
        button.setClickable(Boolean.TRUE);
    }

    public static void initMap(Activity ctx, final Location location, final Button btn) {
        MapFragment mapFragment = (MapFragment) ctx.getFragmentManager().findFragmentById(R.id.map);
        final Activity fctx = ctx;
        final Location floc = location;
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                myOnMapReady(fctx, googleMap, location, btn);
            }
        });

    }

    public static void myOnMapReady(Activity ctx, GoogleMap googleMap, Location location, final
                                    Button btn) {
        gmap = googleMap;
        Location loc = location;
        if (location == null) {
            loc = getLocationPermissionThenLocation(ctx, btn);
        }
        if (loc != null) {
            double lat = loc.getLatitude();
            double lon = loc.getLongitude();
            gotoLocation(ctx, lat, lon, btn);
        }



    }

    public static void gotoLocation(Activity ctx, double lat, double lng, final Button btn) {

        float zoom = 15.0f;
        location = new double[]{lat, lng};
        locWasChanged = true;
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        gmap.moveCamera(update);
        mapIsVisible = Boolean.TRUE;
        makeButtonAvailable(btn);
        setLocStuff(ctx);

    }

    public static Location getCurrentLocation(Activity ctx) {
        // Get the lastKnownLocation once every 5 seconds, or every 10 meters.
        LocationManager locationManager = (LocationManager) ctx.getSystemService(ctx
                .LOCATION_SERVICE);
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

    public static Location getLocationPermissionThenLocation(Activity ctx, final Button btn) {
        //https://github.com/CMPUT301W17T22/MoodSwing/blob/master/app/src/main/java/com/ualberta/cmput301w17t22/moodswing/MainActivity.java
        // Get the current location.
        // http://stackoverflow.com/questions/32491960/android-check-permission-for-locationmanager
        // Check if we have proper permissions to get the coarse lastKnownLocation.


        // Check if we have proper permissions to get the fine lastKnownLocation.
        if (ContextCompat.checkSelfPermission(ctx, android.Manifest.permission.
                ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationFail(ctx, btn);

            Log.i("debugMaps", "Requesting fine permission");
            // Request the permission.
            // Dummy request code 8 used.
            ActivityCompat.requestPermissions(ctx,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);

            return null;
        } else {
            return getCurrentLocation(ctx);
        }


    }

    public static void locationFail(Activity ctx, final Button btn) {
        AddHabitEventActivity.mapIsVisible = Boolean.FALSE;
        setLocStuff(ctx);
        ((CheckBox) ctx.findViewById(R.id.location_check)).setChecked(Boolean.FALSE);
        makeButtonAvailable(btn);
        location = null;
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
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission
                            .ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {


                        DummyMainActivity.toastMe("Can now set location", this);


                    }

                }
            }

        }
    }
}
