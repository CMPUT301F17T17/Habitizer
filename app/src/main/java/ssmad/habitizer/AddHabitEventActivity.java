/*
 *  Class Name: HabitEvent
 *  Version: 0.5
 *  Date: November 13th, 2017
 *  Copyright (c) TEAM SSMAD, CMPUT 301, University of Alberta - All Rights Reserved.
 *  You may use, distribute, or modify this code under terms and conditions of the
 *  Code of Students Behaviour at University of Alberta
 */
package ssmad.habitizer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;


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
    //public static final int MY_PERMISSIONS_REQUEST_LOCATION = 8;
    public static final int PIC_MAX_SIZE = 65536;
    public static final int COMMENT_MAX_SIZE = 30;
    public static boolean picIsVisible = Boolean.FALSE;
    /*public static boolean picButtonsAreVisible = Boolean.FALSE;
    public static boolean mapIsVisible = Boolean.FALSE;
    public static boolean picWasChanged = Boolean.FALSE;
    public static boolean locWasChanged = Boolean.FALSE;*/
    public static double[] location;
    public static String comment;
    //public static Bitmap pic;
    public static byte[] picBytes;
    //public static GoogleMap gmap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_habit_event);
        SyncController.sync(this);
        // Get stuff
        final int position = getIntent().getExtras().getInt(HabitTabActivity.GENERIC_REQUEST_CODE);
        final Habit habit = DummyMainActivity.myHabits.get(position);
        final TextView habitTitle = (TextView) findViewById(R.id.what_habit);
        (findViewById(R.id.add_title)).setVisibility(View.VISIBLE);
        final Button add = (Button) findViewById(R.id.add);
        Button cancel = (Button) findViewById(R.id.cancel);


        //setPicStuff(this);
        //setLocStuff(this);


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
        setUpCheckBoxes(this);
        setUpPicButtons(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // OK
        super.onActivityResult(requestCode, resultCode, data);
        tryGetPic(this, requestCode, resultCode, data);
    }


    public static void setUpCheckBoxes(Activity ctx) {
        CheckBox locationCheck = (CheckBox) ctx.findViewById(R.id.location_check);
        CheckBox picCheck = (CheckBox) ctx.findViewById(R.id.pic_check);
        final Activity fctx = ctx;
        picBytes = null;
        picCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox thisBox = (CheckBox) v;
                picIsVisible = false;
                LinearLayout picToggle = (LinearLayout) fctx.findViewById(R.id.pic_toggle);
                if (thisBox.isChecked()) {
                    picToggle.setVisibility(View.VISIBLE);
                } else {
                    picToggle.setVisibility(View.GONE);
                }
            }




        });
        locationCheck.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                CheckBox thisBox = (CheckBox) v;
                location = null;
                LinearLayout mapToggle = (LinearLayout) fctx.findViewById(R.id.map_toggle);
                if (thisBox.isChecked()) {
                    Boolean IHaveMapPermission = MapController.checkMapPermission(fctx);
                    if (IHaveMapPermission){
                        mapToggle.setVisibility(View.VISIBLE);
                        Location loc = MapController.getCurrentLocation(fctx);
                        double[] d = {loc.getLatitude(), loc.getLongitude()};
                        location = d.clone();
                        MapController.initMap(fctx, loc);
                    }else{
                        MapController.askForMapPermission(fctx);
                        thisBox.setChecked(false);
                        mapToggle.setVisibility(View.GONE);
                    }
                } else {
                    mapToggle.setVisibility(View.GONE);
                }
            }
        });
    }

    public static void setUpPicButtons(Activity ctx) { // OK
        Button fromCamera = (Button) ctx.findViewById(R.id.pic_camera);
        Button fromGallery = (Button) ctx.findViewById(R.id.pic_gallery);
        final Activity fctx = ctx;
        fromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fctx.startActivityForResult(takePicture, GET_PIC_WITH_CAMERA);

            }
        });
        fromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                fctx.startActivityForResult(pickPhoto, GET_PIC_FROM_GALLERY);
            }
        });
    }



    public static Bitmap getPicFromBytes(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static void tryGetPic(Activity ctx, int requestCode, int resultCode, Intent data) {

        switch (requestCode) {


            case GET_PIC_WITH_CAMERA:
                if (resultCode == RESULT_OK) {
                    Bitmap pic = (Bitmap) data.getExtras().get("data");
                    setPic(ctx, pic);
                    DummyMainActivity.toastMe("get pic from camera", ctx);
                } else {
                    getPictureFail(ctx);
                }
                break;
            case GET_PIC_FROM_GALLERY:
                if (resultCode == RESULT_OK) {
                    Uri imageUri = data.getData();
                    try {
                        Bitmap pic = MediaStore.Images.Media.getBitmap(ctx.getContentResolver(),
                                imageUri);
                        setPic(ctx, pic);
                    } catch (IOException e) {
                        getPictureFail(ctx);
                    }
                    DummyMainActivity.toastMe("get pic from gallery", ctx);
                } else {
                    getPictureFail(ctx);
                }
                break;
        }
    }

    public static void getPictureFail(Activity ctx) {
        DummyMainActivity.toastMe("Failed to get pic", ctx);
        ImageView picPreview = (ImageView) ctx.findViewById(R.id.pic_preview);
        picPreview.setImageBitmap(null);
    }

    public static void setPic(Activity ctx, Bitmap pic) { // OK
        picBytes = getCompressedByteFromBitmap(pic, PIC_MAX_SIZE);
        ImageView picPreview = (ImageView) ctx.findViewById(R.id.pic_preview);
        picPreview.setImageBitmap(pic);
        picPreview.setVisibility(View.VISIBLE);
        picIsVisible = true;

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



    public void addEvent() {
        int position = getIntent()
                .getExtras()
                .getInt(HabitTabActivity.GENERIC_REQUEST_CODE);
        Habit habit = DummyMainActivity.myHabits.get(position);
        String comment = ((EditText) findViewById(R.id.comment_input)).getText().toString();
        if (comment.length() > COMMENT_MAX_SIZE) {
            DummyMainActivity.toastMe("Comment must be less than " + COMMENT_MAX_SIZE + " chars",
                    this);
        } else {

            ElasticsearchController.AddItemsTask postHabitEvent =
                    new ElasticsearchController.AddItemsTask();

            String title = habit.getTitle();
            Date completeDate = new Date();
            HabitEvent habitEvent = new HabitEvent(title, completeDate, picBytes, location,
                    comment);
            habitEvent.setHabit_id(habit.getId());
            habitEvent.setUsername(habit.getUsername());
            postHabitEvent.execute(DummyMainActivity.Event_Index, habitEvent.getJsonString());
            if(habitEvent.hasPicture()){
                ElasticsearchController.AddItemsTask postHabitEventPicture =
                        new ElasticsearchController.AddItemsTask();
                postHabitEventPicture.execute(DummyMainActivity.Pic_Index,habitEvent.getPictureJsonString
                        ());
                try{

                    String id = postHabitEventPicture.get();
                    if(id == null){
                        throw new Exception("lol");
                    }
                    habitEvent.setPic_id(id);
                }catch (Exception e){
                    Log.d("ESC", "Could not update habit event picture on first try.");
                    String[] s = {
                            DummyMainActivity.Pic_Index,
                            String.valueOf(SyncController.TASK_ADD),
                            habitEvent.getPictureJsonString()
                    };
                    SyncController.addToSync(s,habitEvent);

                }
            }
            try{
                String id = postHabitEvent.get();
                if(id == null){
                    throw new Exception("lol");
                }
                habitEvent.setId(id);
            }catch (Exception e){
                Log.d("ESC", "Could not update habit event on first try.");
                String[] s = {
                        DummyMainActivity.Event_Index,
                        String.valueOf(SyncController.TASK_ADD),
                        habitEvent.getJsonString()
                };
                SyncController.addToSync(s,habitEvent);
            }

            // Andrew stuff
			Calendar calendar = Calendar.getInstance();
            calendar.setTime(completeDate);
            int[] daysCompleteList = habit.getDaysOfWeekComplete();
            int day = calendar.get(Calendar.DAY_OF_WEEK) -1;
            if (day == 0){
                day = 7;
            }
            daysCompleteList[day - 1] = 1;
            habit.setDaysOfWeekComplete(daysCompleteList);

            ElasticsearchController.UpdateItemsTask updateHabitTask = new ElasticsearchController.UpdateItemsTask();
            updateHabitTask.execute(DummyMainActivity.Habit_Index, habit.getId(), habit.getJsonString());
            //


            DummyMainActivity.myHabitEvents.add(habitEvent);
            // TODO fix this for offline
            FileController.saveInFile(AddHabitEventActivity.this, DummyMainActivity.HABITEVENTFILENAME, DummyMainActivity.myHabitEvents);
			//setResult(0 ,new Intent());
            finish();
        }

    }

    public void cancelEvent() {
		//setResult(0 ,new Intent());
        finish();
    }
}
