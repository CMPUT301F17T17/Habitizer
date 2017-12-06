package ssmad.habitizer;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.DatePicker;
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

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    public static String USER_NAME = "Username of current user will store here";
    public static final String FILENAME= "userProfiles.sav";
    public static final int LIMITEDMODE = 23;


    private Integer REQUEST_GALLERY = 0;
    private Integer REQUEST_CAMERA = 1;
    private int PIC_MAX_SIZE = 65536;
    private static Bitmap pic;
    public Boolean selected = false;

    private static ArrayList<UserProfile> profileList = new ArrayList<UserProfile>();
    private static Account userInfo;

    private TextView nmText;
    private TextView btText;
    private TextView gdText;
    private ImageButton imageButton;
    private ImageView imageV;
    private EditText nameText;
    private TextView birthdayText;
    private Spinner genderSpn;
    private Button confirmButton;
    private ArrayAdapter<CharSequence> adapter;

    private TextView nameDisplay;
    private TextView birthDisplay;
    private TextView genderDisplay;
    private Button viewHabit;
    private Button follow;
    private Button editButton;
    private Button logoutButton;
    private LinearLayout nameLayout;
    private LinearLayout birthLayout;
    private LinearLayout genderLayout;
    private LinearLayout followLayout;
    private static String currentUser;
    private static Boolean fromSignup;
    //This part is for displaing profile

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_tab);
        //setContentView(R.layout.activity_habit_tab);
        Intent intent = getIntent();
        fromSignup = intent.getBooleanExtra("fromSignup", false);

        if (!(fromSignup || intent.hasExtra(SocialMultiAdapter.SOCIAL2ACCOUNT))){
            DummyMainActivity.initTabs(DummyMainActivity.VIEW_EDIT_PROFILE, EditProfileActivity.this, intent);}

        //This part is for editing profile
        nmText = (TextView) findViewById(R.id.nmText);
        btText = (TextView) findViewById(R.id.btText);
        gdText = (TextView) findViewById(R.id.gdText);
        imageButton = (ImageButton) findViewById(R.id.image_btn);
        imageV = (ImageView) findViewById(R.id.imageView);
        nameText = (EditText) findViewById(R.id.usr_name);
        birthdayText = (TextView) findViewById(R.id.birth_date);
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
        follow = (Button) findViewById(R.id.follow_btn);
        viewHabit = (Button) findViewById(R.id.viewHabits_btn);
        editButton = (Button) findViewById(R.id.edit_btn);
        logoutButton = (Button) findViewById(R.id.logout_btn);
        nameLayout = (LinearLayout) findViewById(R.id.lay_name);
        birthLayout = (LinearLayout) findViewById(R.id.lay_birth);
        genderLayout = (LinearLayout) findViewById(R.id.lay_gender);
        followLayout = (LinearLayout) findViewById(R.id.lay_follow);


       // final int pos = findUserProfile();
        if (fromSignup){ //
            onEditEvent(); //create profile when sign up or edit profile
        } else{
            onDisplayEvent(); //display profile
        }

    }

    private void onEditEvent(){
        Account user = findUser(getIntent().getStringExtra("username"));
        final Boolean find;
        if (user == null){
            find = false;
        } else {
            find = true;
        }

        if (find){
            nameText.setText(userInfo.getName());
            birthdayText.setText(userInfo.getBirthday());
            byte[] imageBytes = userInfo.getPortrait();
            imageButton.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes, 0,
                    imageBytes.length));
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

        birthdayText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar date = Calendar.getInstance();
                int year = date.get(Calendar.YEAR);
                int month = date.get(Calendar.MONTH);
                int day = date.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfileActivity.this,
                        EditProfileActivity.this, year, month, day);
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();

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
                String user = intent.getStringExtra("username");
                String password = intent.getStringExtra("password");
                //get bitmap from iamgeButton then transfer it to byte array
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap;
                // If image is selected from camera or gallery, selected image will be converted
                // to bitmap for saving. Otherwise, image in imageview would be converted
                if (selected) {
                    bitmap = ((BitmapDrawable) imageButton.getDrawable()).getBitmap();
                } else {
                    bitmap = ((BitmapDrawable) imageV.getDrawable()).getBitmap();
                }
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageByte = stream.toByteArray();

                String name = nameText.getText().toString();
                String birthday = birthdayText.getText().toString();
                String gender = genderSpn.getSelectedItem().toString();

                Boolean profileOk = checkInput();
                Account info;

                if (!find){
                    if (profileOk) {
                        userInfo = new Account(user, password, imageByte, name, birthday, gender);
                        saveUser(userInfo);
                        setResult(DummyMainActivity.VIEW_LOGIN, new Intent());
                        finish();
                    }
                } else {
                    if (profileOk) {
                        userInfo.setPortrait(imageByte);
                        userInfo.setName(name);
                        userInfo.setBirthday(birthday);
                        userInfo.setGender(gender);
                        saveUser(userInfo);
                        onDisplayUpdate(userInfo);
                    }

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("username", currentUser);
        setResult(DummyMainActivity.VIEW_EDIT_PROFILE,intent);
        super.onBackPressed();
    }

    private void onDisplayEvent(){
        Intent intent = getIntent();
        if(intent.hasExtra(SocialMultiAdapter.SOCIAL2ACCOUNT)) {
            final int position = intent.getIntExtra(SocialMultiAdapter.SOCIAL2ACCOUNT, 0);
            final Account targetUserInfo = SocialTabActivity.SocialAccounts.get(position);
            Log.i("cnmlgb", "why goes here");

            onDisplayUpdate(targetUserInfo);

            editButton.setVisibility(View.GONE);
            logoutButton.setVisibility(View.GONE);
            //TODO

            Boolean IAmFollower = Arrays.asList(targetUserInfo.getFollowers()).contains(userInfo.getUsername());
            Boolean HaveSentRequest = Arrays.asList(targetUserInfo.getRequests()).contains(userInfo.getUsername());

            follow.setVisibility(View.VISIBLE);
            if (IAmFollower && !HaveSentRequest) {
                viewHabit.setVisibility(View.VISIBLE);
                follow.setText("Unfollow");
            } else {
                follow.setText("Follow");
            }
            if (HaveSentRequest) {
                follow.setAlpha(0.5f);
            } else {
                final Boolean tofollow = IAmFollower;
                follow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Account targetUserInfo = SocialTabActivity.SocialAccounts.get(position);
                        if (!tofollow) {
                            targetUserInfo.setRequests(addOne(targetUserInfo.getRequests(), userInfo.getUsername()));
                            userInfo.setSent_requests(addOne(userInfo.getSent_requests(), targetUserInfo.getUsername()));
                        } else { //want to unfollow
                            targetUserInfo.setFollowers(minusOne(targetUserInfo.getFollowers(), userInfo.getUsername()));
                            userInfo.setFollowing(minusOne(userInfo.getFollowing(), targetUserInfo.getUsername()));
                        }
                        saveUser(userInfo);
                        saveUser(targetUserInfo);
                        finish();
                    }
                });
                //TODO 2 add view habit button
                viewHabit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("fromProfile", true);
                        intent.putExtra("targetUsername", targetUserInfo.getUsername());
                        setResult(DummyMainActivity.VIEW_HABIT, intent);
                        finish();
                    }
                });
            }
        }else {
            findUser(getIntent().getStringExtra("username"));
            onDisplayUpdate(userInfo);
            logoutButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //Intent intent = getIntent();

                    ArrayList<Account> accountArrayList = new ArrayList<Account>();
                    FileController.saveInFile(EditProfileActivity.this, LoginActivity.FILENAME, accountArrayList);
                    setResult(DummyMainActivity.VIEW_LOGIN, new Intent());
                    finish();
                }
            });

            editButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    editVisibility();
                    onEditEvent();
                }
            });
            }
    }

    private void onDisplayUpdate(Account userInfo) {
        String name = userInfo.getName();
        String birthday = userInfo.getBirthday();
        String gender = userInfo.getGender();
        byte[] imageBytes = userInfo.getPortrait();

        displayVisibility();
        nameDisplay.setText(name);
        birthDisplay.setText(birthday);
        genderDisplay.setText(gender);
        //set up image
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        imageV.setImageBitmap(bitmap);
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
        followLayout.setVisibility(View.VISIBLE);
        editButton.setVisibility(View.VISIBLE);
        logoutButton.setVisibility(View.VISIBLE);


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
        logoutButton.setVisibility(View.GONE);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String userBirthday = Integer.toString(year) + '-' + Integer.toString(month) + '-' + Integer.toString(dayOfMonth);
        birthdayText.setText(userBirthday);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            selected = true;
            if(requestCode == REQUEST_CAMERA){
                selectFromCamera(data);
            } else if(requestCode == REQUEST_GALLERY){
                selectFromGallery(data);
            }
        }
    }

    private void selectFromGallery(Intent data){
        Uri imageUri = data.getData();
        try {
            pic = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        } catch (IOException e) {
            Toast.makeText(EditProfileActivity.this, "Getting bitmap failed!", Toast.LENGTH_SHORT).show();
        }
        Bitmap compressedPic = getResizedBitmap(pic, PIC_MAX_SIZE);
        imageButton.setImageBitmap(compressedPic);
    }

    private void selectFromCamera(Intent data){
        pic = (Bitmap) data.getExtras().get("data");
        Bitmap compressedPic = getResizedBitmap(pic, PIC_MAX_SIZE);
        imageV = (ImageView) findViewById(R.id.imageView);
        imageButton.setImageBitmap(compressedPic);
    }

    public static Account findUser(String username) {
        currentUser = username;

        ElasticsearchController.GetUsersTask getUsersTask = new ElasticsearchController.GetUsersTask();
        getUsersTask.execute(currentUser);
        try {
            if (!getUsersTask.get().isEmpty()) {
                userInfo = getUsersTask.get().get(0);
                DummyMainActivity.currentAccount = userInfo;
                return userInfo;
            }
        } catch (Exception e) {
            Log.i("Error", "Failed to get the user accounts from the async object");
        }
        return null;
    }

    public Boolean checkInput() {
        Boolean correctness = true;
        String name = nameText.getText().toString();
        String date = birthdayText.getText().toString();
        String gender = genderSpn.getSelectedItem().toString();
        String[] part = date.split("-");

        if (name.isEmpty() || name.trim().equals("")) {
            correctness = false;
            Toast.makeText(EditProfileActivity.this, "Must Input a name", Toast.LENGTH_SHORT).show();
        } else if (!name.matches("[a-zA-Z]*")) {
            correctness = false;
            Toast.makeText(EditProfileActivity.this, "Input name must in a-z or A-Z", Toast.LENGTH_SHORT).show();
        } else if (gender.isEmpty()) {
            correctness = false;
            Toast.makeText(EditProfileActivity.this, "Must select a gender", Toast.LENGTH_SHORT).show();
        } else if (date.isEmpty()) {
            correctness = false;
            Toast.makeText(EditProfileActivity.this, "Must select a birthday", Toast.LENGTH_SHORT).show();
        }
        else if (Integer.parseInt(part[1]) > 12 || Integer.parseInt(part[2]) > 31) {
            correctness = false;
            Toast.makeText(EditProfileActivity.this, "Invalid date",
                    Toast.LENGTH_LONG).show();
        }
        return correctness;
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
            //Log.d("PhotoSize|Quality", size + " | " + quality);
            div = div * 0.9;
        }
        return image;
    }

    public void saveUser(Account info) {
        //post user profile
        ElasticsearchController.AddUsersTask addUsersTask = new ElasticsearchController.AddUsersTask();
        addUsersTask.execute(info);
    }

    public static String[] addOne(String[] arr, String s){
        String[] result = Arrays.copyOf(arr, arr.length+1);
        result[arr.length] = s;
        return result;
    }

    public static String[] minusOne(String[] arr, String s){
        List<String> result = new ArrayList<String>(Arrays.asList(arr));
        result.remove(s);
        String[] r = Arrays.copyOf(result.toArray(), result.size(), String[].class);
        return r;
    }
}