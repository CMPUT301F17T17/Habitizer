package ssmad.habitizer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class EditProfileActivity extends AppCompatActivity {
    public static String USER_NAME = "Username of current user will store here";
    public static final String FILENAME= "userProfiles.sav";

    private Integer REQUEST_GALLERY = 0;
    private Integer REQUEST_CAMERA = 1;
    private static ArrayList<UserProfile> profileList = new ArrayList<UserProfile>();

    private TextView nmText;
    private TextView btText;
    private TextView gdText;
    private ImageButton imageButton;
    private ImageView imageV;
    private EditText nameText;
    private EditText birthdayText;
    private Spinner genderSpn;
    private Button confirmButton;
    private ArrayAdapter<CharSequence> adapter;

    private TextView nameDisplay;
    private TextView birthDisplay;
    private TextView genderDisplay;
    private Button habbitButton;
    private Button followerButton;
    private Button followingButton;
    private Button editButton;
    private LinearLayout nameLayout;
    private LinearLayout birthLayout;
    private LinearLayout genderLayout;
    private LinearLayout followLayout;

    //This part is for displaing profile

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        //This part is for editing profile
        nmText = (TextView) findViewById(R.id.nmText);
        btText = (TextView) findViewById(R.id.btText);
        gdText = (TextView) findViewById(R.id.gdText);
        imageButton = (ImageButton) findViewById(R.id.image_btn);
        imageV = (ImageView) findViewById(R.id.imageView);
        nameText = (EditText) findViewById(R.id.usr_name);
        birthdayText = (EditText) findViewById(R.id.birth_date);
        genderSpn = (Spinner) findViewById(R.id.gender_spn);
        confirmButton = (Button) findViewById(R.id.confirm_btn);
        adapter = ArrayAdapter.createFromResource(this, R.array.gender_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        genderSpn.setAdapter(adapter);

        //This part is for displaying profile
        nameDisplay = (TextView) findViewById(R.id.name_dis);
        birthDisplay = (TextView) findViewById(R.id.birth_dis);
        genderDisplay = (TextView) findViewById(R.id.gender_dis);
        habbitButton = (Button) findViewById(R.id.habit_btn);
        followerButton = (Button) findViewById(R.id.follower_btn);
        followingButton = (Button) findViewById(R.id.following_btn);
        editButton = (Button) findViewById(R.id.edit_btn);
        nameLayout = (LinearLayout) findViewById(R.id.lay_name);
        birthLayout = (LinearLayout) findViewById(R.id.lay_birth);
        genderLayout = (LinearLayout) findViewById(R.id.lay_gender);
        followLayout = (LinearLayout) findViewById(R.id.lay_follow);

        final int pos = findUserProfile();

        if (pos < 0){ //
            onEditEvent(pos); //create profile when sign up or edit profile
        } else{
            onDisplayEvent(pos); //display profile
        }

    }

    private void onEditEvent(final int pos){
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

        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //get extra user name
                Intent intent = getIntent();
                String user = intent.getStringExtra(EditProfileActivity.USER_NAME);
                //get bitmap from iamgeButton then transfer it to byte array
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = ((BitmapDrawable) imageButton.getDrawable()).getBitmap();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageByte = stream.toByteArray();

                String name = nameText.getText().toString();
                String birthday = birthdayText.getText().toString();
                String gender = genderSpn.getSelectedItem().toString();

                if (pos < 0){
                    Boolean profileOk = checkInput();
                    if (profileOk) {
                        profileList.add(new UserProfile(user, imageByte, name, birthday, gender));
                        saveInFile(EditProfileActivity.this);

                        intent = new Intent(EditProfileActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Boolean profileOk = checkInput();
                    if (profileOk) {
                        profileList.get(pos).setPortrait(imageByte);
                        profileList.get(pos).setName(name);
                        profileList.get(pos).setBirthday(birthday);
                        profileList.get(pos).setGender(gender);

                        onDisplayEvent(pos);
                    }

                }
            }
        });
    }

    private void onDisplayEvent(final int pos){
        String name = profileList.get(pos).getName();
        String birthday = profileList.get(pos).getBirthday();
        String gender = profileList.get(pos).getGender();
        byte[] imageBytes = profileList.get(pos).getPortrait();

        displayVisibility();

        nameDisplay.setText(name);
        birthDisplay.setText(birthday);
        genderDisplay.setText(gender);
        //set up image
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        imageV.setImageBitmap(bitmap);

        habbitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this, HabitTabActivity.class);
                startActivity(intent);
            }
        });

        followerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                followerEvent();
            }
        });

        followingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                followingEvent();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editVisibility();
                onEditEvent(pos);
            }
        });
    }

    private void displayVisibility(){
        imageButton.setVisibility(View.GONE);
        nmText.setVisibility(View.GONE);
        nameText.setVisibility(View.GONE);
        btText.setVisibility(View.GONE);
        birthdayText.setVisibility(View.GONE);
        gdText.setVisibility(View.GONE);
        genderSpn.setVisibility(View.GONE);
        confirmButton.setVisibility(View.GONE);

        imageV.setVisibility(View.VISIBLE);
        nameLayout.setVisibility(View.VISIBLE);
        birthLayout.setVisibility(View.VISIBLE);
        genderLayout.setVisibility(View.VISIBLE);
        habbitButton.setVisibility(View.VISIBLE);
        followLayout.setVisibility(View.VISIBLE);
        editButton.setVisibility(View.VISIBLE);

    }

    private void editVisibility(){
        imageButton.setVisibility(View.VISIBLE);
        nmText.setVisibility(View.VISIBLE);
        nameText.setVisibility(View.VISIBLE);
        btText.setVisibility(View.VISIBLE);
        birthdayText.setVisibility(View.VISIBLE);
        gdText.setVisibility(View.VISIBLE);
        genderSpn.setVisibility(View.VISIBLE);
        confirmButton.setVisibility(View.VISIBLE);

        nameLayout.setVisibility(View.GONE);
        birthLayout.setVisibility(View.GONE);
        genderLayout.setVisibility(View.GONE);
        followLayout.setVisibility(View.GONE);
        editButton.setVisibility(View.GONE);
        imageV.setVisibility(View.GONE);
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
        Uri imageUri = data.getData();
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        } catch (IOException e) {
            Toast.makeText(EditProfileActivity.this, "Getting bitmap failed!", Toast.LENGTH_SHORT).show();
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
            Type listType = new TypeToken<ArrayList<UserProfile>>(){}.getType();
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
        Intent intent = getIntent();
        String user = intent.getStringExtra(this.USER_NAME);
        for(int i=0; i < profileList.size(); i++){
            if(profileList.get(i).getUsername().equals(user)){
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

    public void followerEvent(){
        //Not handling with this event for now
    }

    public void followingEvent() {
        //Not handling with this event for now
    }
}