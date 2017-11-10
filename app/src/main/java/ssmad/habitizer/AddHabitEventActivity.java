package ssmad.habitizer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.IOException;


/**
 * Created by cryst on 10/23/2017.
 */

/*
Ref getting pic
https://stackoverflow.com/questions/10165302/dialog-to-pick-image-from-gallery-or-from-camera
Ref getting map
https://stackoverflow.com/questions/16536414/how-to-use-mapview-in-android-using-google-map-v2
Ref pic size reduce
https://stackoverflow.com/questions/16954109/reduce-the-size-of-a-bitmap-to-a-specified-size-in-android
 */

public class AddHabitEventActivity extends AppCompatActivity {
    private final int GET_PIC_WITH_CAMERA = 0;
    private final int GET_PIC_FROM_GALLERY = 1;
    private final int PIC_MAX_SIZE = 65536;

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
                    ImageView picPreview = (ImageView) findViewById(R.id.pic_preview);
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    Bitmap compressedPic = getResizedBitmap(bitmap, PIC_MAX_SIZE);
                    picPreview.setImageBitmap(compressedPic);
                    picPreview.setVisibility(View.VISIBLE);
                    DummyMainActivity.toastMe("get pic from camera", AddHabitEventActivity.this);

                }

                break;
            case GET_PIC_FROM_GALLERY:
                if (resultCode == RESULT_OK) {
                    ImageView picPreview = (ImageView) findViewById(R.id.pic_preview);
                    Uri imageUri = data.getData();
                    Bitmap bitmap;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    } catch (IOException e) {
                        DummyMainActivity.toastMe("Getting bitmap failed!", AddHabitEventActivity.this);
                        break;
                    }
                    Bitmap compressedPic = getResizedBitmap(bitmap, PIC_MAX_SIZE);
                    picPreview.setImageBitmap(compressedPic);
                    picPreview.setVisibility(View.VISIBLE);
                    DummyMainActivity.toastMe("get pic from gallery", AddHabitEventActivity.this);
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
        Bitmap image = pic.copy(pic.getConfig(), true);
        double width = (double) image.getWidth();
        double height = (double) image.getHeight();
        double size = (double) image.getAllocationByteCount();
        double maxs = (double) maxSize;
        Log.d("PhotoSize", Integer.toString((int)size));
        double div = 0.9;
        while(size > maxs){
            image = Bitmap.createScaledBitmap(image, (int) (width*div), (int) (height*div), true);
            size = (double) image.getAllocationByteCount();
            Log.d("PhotoSize|div", Integer.toString((int)size)+" | "+Double.toString(div));
            div = div * 0.9;
        }

        return image;



    }
}
