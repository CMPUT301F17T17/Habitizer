package ssmad.habitizer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class EditProfileActivity extends AppCompatActivity {
    public static final String USER_NAME = "Username of current user will store here";
    public static final String FILENAME= "userProfiles.sav";

    private Integer REQUEST_GALLERY = 0;
    private Integer REQUEST_CAMERA = 1;
    private static ArrayList<UserProfile> profileList = new ArrayList<UserProfile>();

    private ImageButton imageButton;
    private ImageView imageV;
    private EditText nameText;
    private EditText birthdayText;
    private Spinner genderSpn;
    private Button confirmButton;
    private ArrayAdapter<CharSequence> adapter;

    //private String name;
    //private String date;
    //private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        imageButton = (ImageButton) findViewById(R.id.image_btn);
        imageV = (ImageView) findViewById(R.id.imageView);
        nameText = (EditText) findViewById(R.id.usr_name);
        birthdayText = (EditText) findViewById(R.id.birth_date);
        genderSpn = (Spinner) findViewById(R.id.gender_spn);
        confirmButton = (Button) findViewById(R.id.confirm_btn);
        adapter = ArrayAdapter.createFromResource(this, R.array.gender_array,
                android.R.layout.simple_spinner_item);
        //
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        genderSpn.setAdapter(adapter);

        final int pos = findUserProfile();

        if (pos < 0){

        } else{
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(EditProfileActivity.this);
                View dialog = inflater.inflate(R.layout.select_portrait, null);

                AlertDialog.Builder dialog_build = new AlertDialog.Builder(EditProfileActivity.this);

                dialog_build.setView(dialog);
                dialog_build
                        .setTitle("Select One");
                final AlertDialog alertDialog = dialog_build.create();
                alertDialog.show();

                alertDialog.findViewById(R.id.camera_btn).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        Intent cameraPic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraPic, REQUEST_CAMERA);
                    }
                });
                alertDialog.findViewById(R.id.gallery_btn).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        Intent galleryPic = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryPic, REQUEST_GALLERY);
                    }
                });
                alertDialog.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

            }
        });

        genderSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //checkInput();
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (pos < 0){
                    Boolean profileOk = checkInput();
                    if (profileOk) {
                        Bitmap bitmap = ((BitmapDrawable) imageButton.getDrawable()).getBitmap();
                        String name = nameText.getText().toString();
                        String birthday = birthdayText.getText().toString();
                        String gender = genderSpn.getSelectedItem().toString();

                        profileList.add(new UserProfile(USER_NAME, bitmap, name, birthday, gender));
                        saveInFile(EditProfileActivity.this);

                        Intent intent = new Intent(EditProfileActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }

                //profileList.add(new UserProfile(USER_NAME, image, name, birthday, gender));
                //profileList.get(pos).setName(name);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CAMERA){
                selectFromCamera(data);
                //imageV.setVisibility(View.VISIBLE);
            } else if(requestCode == REQUEST_GALLERY){
                selectFromGallery(data);
                //imageV.setVisibility(View.VISIBLE);
            }
            //imageButton.setVisibility(View.GONE);
        }
    }

    private void selectFromGallery(Intent data){
        ImageView picPreview = (ImageView) findViewById(R.id.pic_preview);
        Uri imageUri = data.getData();
        Bitmap bitmap = null;
        if (data != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                Toast.makeText(EditProfileActivity.this, "Getting bitmap failed!", Toast.LENGTH_SHORT).show();
            }
        }
        imageButton.setImageBitmap(bitmap);
    }

    private void selectFromCamera(Intent data){
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageV = (ImageView) findViewById(R.id.imageView);
        imageButton.setImageBitmap(bitmap);
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            //Code taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt Sept.22,2016
            Type listType = new TypeToken<ArrayList<Account>>(){}.getType();
            profileList = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            profileList = new ArrayList<UserProfile>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    public static void saveInFile(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(profileList, writer);
            writer.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    public int findUserProfile(){
        loadFromFile();
        for(int i=0; i < profileList.size(); i++){
            if(profileList.get(i).getUsername().equals(USER_NAME)){
                return i;
            }
        }
        return -1;
    }

    public Boolean checkInput(){
        Boolean correctness = true;
        String name = nameText.getText().toString();
        String date = birthdayText.getText().toString();
        String gender = genderSpn.getSelectedItem().toString();

        if(name.isEmpty() || name.trim().equals("")) {
            correctness = false;
            Toast.makeText(EditProfileActivity.this, "Must Input a name", Toast.LENGTH_SHORT).show();
        } else if (gender.isEmpty()){
            correctness = false;
            Toast.makeText(EditProfileActivity.this, "Must select a gender", Toast.LENGTH_SHORT).show();
        } else if (!date.matches("\\d{4}-\\d{2}-\\d{2}")){
            correctness = false;
            Toast.makeText(EditProfileActivity.this, "Must Input Birthday in format: yyyy-mm-dd",
                    Toast.LENGTH_LONG).show();
        }
        return correctness;
    }
}
