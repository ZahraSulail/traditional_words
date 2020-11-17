package com.barmeg.traditionalwords;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.Random;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import static com.barmeg.traditionalwords.MainActivity.APP_PREF;
import static com.barmeg.traditionalwords.MainActivity.QUESTIONS;
import static com.barmeg.traditionalwords.MainActivity.SHARE_IMAGE;
import static com.barmeg.traditionalwords.MainActivity.SHARE_TITLE;
import static com.barmeg.traditionalwords.R.id.image_view_question;

public class ShareActivity extends AppCompatActivity {

    //EditText to enter title for sharing
    private EditText mEditTextShareTitle;

    //ImageView to show question image
    private ImageView mShareImageView;

    //Integer variable resourceId
    private int resourceId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_share);

        //Find view by Ids to write a title and show question image
        mEditTextShareTitle = findViewById(R.id.edit_text_share_title);
        mShareImageView = findViewById(image_view_question);

        //Share prefrences code to share image and title
        SharedPreferences sharedPreferences = getSharedPreferences( SHARE_IMAGE, MODE_PRIVATE );
        String questionTitle = sharedPreferences.getString(SHARE_TITLE,  "");
        int questionImage = sharedPreferences.getInt( SHARE_IMAGE, 0 );

        mEditTextShareTitle.setText(questionTitle);
        //GetIntent to get image id
        resourceId = getIntent().getIntExtra(SHARE_IMAGE, 0);

        //Set image resources to mShareImageView
        mShareImageView.setImageResource(resourceId);
    }

    //ShareImage code
    public void shareImage(View view) {
       // كود مشاركة الصورة هنا
       String questionTitle = mEditTextShareTitle.getText().toString();
       Intent shareIntent = new Intent(Intent.ACTION_SEND);
       shareIntent.setType( "text/plain");
       shareIntent.putExtra( Intent.EXTRA_TEXT, questionTitle );
       Resources resources = getApplicationContext().getResources();
       Uri uri = Uri.parse( ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + resources.getResourcePackageName(resourceId)
               + '/' + resources.getResourceTypeName(resourceId) + '/' + resources.getResourceEntryName(resourceId) );
       shareIntent.setType("image/png");
       shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
       startActivity(shareIntent);

       SharedPreferences sharedPreferences = getSharedPreferences( APP_PREF, MODE_PRIVATE );
       SharedPreferences.Editor editor = sharedPreferences.edit();
       editor.putString(SHARE_TITLE, questionTitle );
       editor.putInt( SHARE_IMAGE, resourceId );
       editor.apply();
    }


}


