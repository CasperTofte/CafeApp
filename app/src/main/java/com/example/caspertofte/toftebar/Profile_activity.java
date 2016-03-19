package com.example.caspertofte.toftebar;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Profile_activity extends AppCompatActivity {

    EditText et_profile_firstName;
    EditText et_profile_lastName;
    EditText et_profile_email;
    ImageView iv_profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // references to the views
        et_profile_firstName = (EditText) findViewById(R.id.profile_firstName);
        et_profile_lastName = (EditText) findViewById(R.id.profile_lastName);
        et_profile_email = (EditText) findViewById(R.id.profile_email);
        iv_profile_image = (ImageView) findViewById(R.id.profile_image);

        //Retrieve info
        profileRetrieve();
    }

    // Capture image
    private static final int CAMERA_PIC_REQUEST = 1337;

    public void takeImage(View v) {
        Intent MyCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(MyCameraIntent, CAMERA_PIC_REQUEST);
    }

    // Retrieve image and display it in the ImageView
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST && resultCode ==
                Activity.RESULT_OK) {
            //Retrieve image data
            Bitmap newProfileImage = (Bitmap) data.getExtras().get("data");
            //Find the ImageView to display the image
            ImageView image = (ImageView) findViewById(R.id.profile_image);
            image.setImageBitmap(newProfileImage);
        }
    }


    // Save texts to SharedPreferences
    // Save image to an application file
    public void profileSave(View view){
        // Create or modify a shared preference
        // The data are only accessible to this app
        SharedPreferences mySharedPreferences = getSharedPreferences("profileSettings", Activity.MODE_PRIVATE);
        // Create an editor
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        // Get entered values
        editor.putString("firstName", et_profile_firstName.getText().toString());
        editor.putString("lastName", et_profile_lastName.getText().toString());
        editor.putString("E-mail", et_profile_email.getText().toString());

        // Save the preferences
        editor.commit();

        // Save image
        // TODO: Update method call
        profileSaveImageInternal();

        // Show a confirm-message to the user
        Toast.makeText(this, "Your profile was saved", Toast.LENGTH_SHORT).show();

        finish();
    }

    public void profileSaveImageExternal(){

        // TODO: Save image to external storage

        // Load image from view
        iv_profile_image.setDrawingCacheEnabled(true);
        Bitmap bitmap = iv_profile_image.getDrawingCache();
        //String root = Environment.getExternalStorageDirectory().toString();

        // Specify file root
        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File newDir = new File(root+"/images");
        newDir.mkdirs();

        // Logging - check where the image is saved
        File file = getFilesDir();
        Log.d("Output_path_", file.getAbsolutePath());

        // Image specifications
        String filename = "profileImage";
        File profileImage = new File (newDir, filename);
        if (profileImage.exists ()){
            Toast.makeText(this,"Fil fundet", Toast.LENGTH_LONG).show();
            //profileImage.delete();
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(profileImage);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void profileSaveImageInternal(){

        // TODO: Save image to internal storage

        // Load image from view
        iv_profile_image.setDrawingCacheEnabled(true);
        Bitmap bitmap = iv_profile_image.getDrawingCache();

        // Specify file root
        File root = getFilesDir();
        File imageDir = new File(root+"/images");
        imageDir.mkdirs();

        // Logging - check where the image is saved
        Log.d("Output_path_", imageDir.getAbsolutePath());

        // Image specifications
        String filename = "profileImage";
        File profileImage = new File (imageDir, filename);

        if (profileImage.exists ()){
            Toast.makeText(this,"Fil fundet", Toast.LENGTH_LONG).show();
            //profileImage.delete();
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(profileImage);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void profileLoadImageInternal(){
        String filename = "profileImage";
        File root = getFilesDir();
        File imageDir = new File(root+"/images");

        try {
            File loadProfileImage = new File(imageDir, filename);
            Bitmap profileImage = BitmapFactory.decodeStream(new FileInputStream(loadProfileImage));
            ImageView image = (ImageView) findViewById(R.id.profile_image);
            image.setImageBitmap(profileImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    public void profileCancel(View view){
        finish();
    }

    public void profileRetrieve(){
        SharedPreferences mySharedPreferences = getSharedPreferences("profileSettings", Activity.MODE_PRIVATE);

        // Retrieve saved info. If not existing, return blank.
        String firstname = mySharedPreferences.getString("firstName", "");
        String lastname = mySharedPreferences.getString("lastName", "");
        String email = mySharedPreferences.getString("E-mail", "");

        // Place the content in the views
        et_profile_firstName.setText(firstname);
        et_profile_lastName.setText(lastname);
        et_profile_email.setText(email);

        // Retrieve image
        // TODO: Retrieve image
        profileLoadImageInternal();
    }

    public void profileDelete(){
        // TODO: Delete all saved data including image
    }


}
