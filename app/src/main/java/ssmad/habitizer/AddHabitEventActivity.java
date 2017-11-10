package ssmad.habitizer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by cryst on 10/23/2017.
 */

/*
Ref getting pic
https://stackoverflow.com/questions/10165302/dialog-to-pick-image-from-gallery-or-from-camera
Ref getting map
https://stackoverflow.com/questions/16536414/how-to-use-mapview-in-android-using-google-map-v2
 */

public class AddHabitEventActivity extends AppCompatActivity {
    private final int GET_PIC_WITH_CAMERA = 0;
    private final int GET_PIC_FROM_GALLERY = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_habit_event);

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


        // Set stuff
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
                    setImage(data, "pic form camera");
                }

                break;
            case GET_PIC_FROM_GALLERY:
                if (resultCode == RESULT_OK) {
                    setImage(data, "pic form gallery");
                }
                break;
        }

    }

    public void setImage(Intent data, String toast) {
        ImageView picPreview = (ImageView) findViewById(R.id.pic_preview);
        Uri selectedImage = data.getData();
        picPreview.setImageURI(null);
        picPreview.setImageURI(selectedImage);
        picPreview.setVisibility(View.VISIBLE);
        if (toast != null) {
            DummyMainActivity.toastMe(toast, AddHabitEventActivity.this);
        }
    }

    public void addEvent() {

    }

    public void cancelEvent() {
        finish();
    }
}
