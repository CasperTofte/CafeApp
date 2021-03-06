package com.example.caspertofte.toftebar;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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

    // Instantiate the views
    EditText et_profile_firstName;
    EditText et_profile_lastName;
    EditText et_profile_email;
    ImageView iv_profile_image;
    Bitmap NewProfileImage;

    String filename = "profileImage.jpg";

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
        if (requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK) {
            //Retrieve image data
           NewProfileImage = (Bitmap) data.getExtras().get("data");
            //Find the ImageView and save the image in the view
            if(iv_profile_image !=null) {
                iv_profile_image.setImageBitmap(NewProfileImage);
            }
        }
    }


    // Save texts to SharedPreferences
    // Save image to an application file
    public void profileSave(View view){
        String firstname = et_profile_firstName.getText().toString();
        String lastname = et_profile_lastName.getText().toString();
        String email = et_profile_email.getText().toString();

        // Create or modify a shared preference
        // The data are only accessible to this app
        SharedPreferences mySharedPreferences = getSharedPreferences("profileSettings", Activity.MODE_PRIVATE);
        // Create an editor
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        // Get entered values if they exist
        if(!firstname.equals("")) { editor.putString("firstName", firstname);}
        if(!lastname.equals("")) {editor.putString("lastName", lastname);}
        if(!email.equals("")) {editor.putString("E-mail", email);}

/*  Old way to submit preferences
        editor.putString("firstName", et_profile_firstName.getText().toString());
        editor.putString("lastName", et_profile_lastName.getText().toString());
        editor.putString("E-mail", et_profile_email.getText().toString());
*/
        // Save the preferences
        editor.commit();

        // Save image if it exists
        if(iv_profile_image.getDrawable()!=null) {
            Log.d("Image:", "Image is not null and has been set");
            profileSaveImageInternalOptimized(this, NewProfileImage, filename);
        }

        // Show a confirm-message
        Toast.makeText(this, R.string.profileSaved, Toast.LENGTH_SHORT).show();

        finish();
    }

    public void profileSaveImageInternalOptimized(Context context, Bitmap b, String filename){
        // Save image to internal storage
        // Image is passed into the method

        // Specify file root
        File root = getFilesDir();
        File imageDir = new File(root+"/images/");
        imageDir.mkdirs();  // Create folder

        // Logging where the image is saved
        Log.d("Save image output path", imageDir.getAbsolutePath());

        // Image specifications
        File profileImage = new File (imageDir, filename);

        try {
            FileOutputStream outputStream = new FileOutputStream(profileImage);
            b.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void profileLoadImageInternalOptimized(String filename){
        String path = this.getFilesDir().getAbsolutePath()+"/images/"+filename;
        File file = new File(path);
        Log.d("Load image path", path);
        if (file.exists()) {
            iv_profile_image.setImageDrawable(Drawable.createFromPath(path));
            Log.d("Image status", "Image retrieved");
        }
    }


    public void profileCancel(View view){ finish(); }

    public void profileRetrieve(){
        SharedPreferences mySharedPreferences = getSharedPreferences("profileSettings", Activity.MODE_PRIVATE);

        // Retrieve saved info if they exist. If not existing, return blank.
        String firstname = mySharedPreferences.getString("firstName", "n/a");
        String lastname = mySharedPreferences.getString("lastName", "n/a");
        String email = mySharedPreferences.getString("E-mail", "n/a");

        // Place the content in the views
        if(!firstname.equals("n/a")) {et_profile_firstName.setText(firstname);}
        if(!lastname.equals("n/a")) {et_profile_lastName.setText(lastname);}
        if(!email.equals("n/a")) {et_profile_email.setText(email);}

        /*et_profile_firstName.setText(firstname);
        et_profile_lastName.setText(lastname);
        et_profile_email.setText(email);*/

        // Retrieve image
        //profileLoadImageInternal();
        profileLoadImageInternalOptimized(filename);
    }

    public void profileDelete(View view){
        SharedPreferences mySharedPreferences = getSharedPreferences("profileSettings", Activity.MODE_PRIVATE);
        // Clear all preferences
        mySharedPreferences.edit().clear().commit();
        Log.d("Preferences:","Deleted");

        // Delete profile image
        String path = this.getFilesDir().getAbsolutePath()+"/images/"+filename;
        File profileImage = new File(path);
        Log.d("Image for delete path:", path);
        profileImage.delete();

        Toast.makeText(this, R.string.profileDeleted, Toast.LENGTH_SHORT).show();

        finish();
    }

/*    public void profileLoadImageInternal(){
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
    }*/

    /*public void profileSaveImageExternal(){
        // Save image to external storage (SD)

        // Load image from view
        iv_profile_image.setDrawingCacheEnabled(true);
        Bitmap bitmap = iv_profile_image.getDrawingCache();
        //String root = Environment.getExternalStorageDirectory().toString();

        // Specify file root
        File rootExternal = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File newExternalDir = new File(rootExternal+"/images");
        newExternalDir.mkdirs();

        // Logging - check where the image is saved
        File file = getFilesDir();
        Log.d("Output_path_", file.getAbsolutePath());

        // Image specifications
        String filename = "profileImage";
        File profileImage = new File (newExternalDir, filename);
        if (profileImage.exists ()){
            Toast.makeText(this, R.string.profileImageExists, Toast.LENGTH_SHORT).show();
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
    }*/

    /*public void profileSaveImageInternal(){
        // Save image to internal storage

        // Load image from view
        iv_profile_image.setDrawingCacheEnabled(true);
        Bitmap bitmap = iv_profile_image.getDrawingCache();

        // Specify file root
        File root = getFilesDir();
        File imageDir = new File(root+"/images");
        imageDir.mkdirs();  // Create folder

        // Logging - check where the image is saved
        Log.d("Output_path_", imageDir.getAbsolutePath());

        // Image specifications
        String filename = "profileImage";
        File profileImage = new File (imageDir, filename);

        try {
            FileOutputStream outputStream = new FileOutputStream(profileImage);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}