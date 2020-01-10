package com.barmeg.traditionalwords;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //Costants difeinition to call them within the app
    private static final String BUNDLE_CURRENT_INDEX = "BUNDLE_CURRENT_INDEX";
    public static final String APP_PREF = "APP_PREF";
    public static final String APP_LANG = "APP_LANG";
    public static final String QUESTION_ANSWER = "QUESTION_ANSWER";
    public static final String SHARE_QUESTION = "SHARE_QUESTION";
    public static final String SHARE_IMAGE = "SHARE_IMAGE";
    public static final String SHARE_TITLE = "SHARE_TITLE";

    //ImageView to show the question image
    ImageView questionImageView;

    //AN integer array to store images Ids
   public  static final int []  QUESTIONS = {
            R.drawable.icon_1,
            R.drawable.icon_2,
            R.drawable.icon_3,
            R.drawable.icon_4,
            R.drawable.icon_5,
            R.drawable.icon_6,
            R.drawable.icon_7,
            R.drawable.icon_8,
            R.drawable.icon_9,
            R.drawable.icon_10,
            R.drawable.icon_11,
            R.drawable.icon_12,
            R.drawable.icon_13
   };

   //String answer array
    private String [] ANSWERS;

    //String answer description array
    private String [] ANSWER_DESCRIPTION;

    // Private string variables
    private String mCurentAnswer, mCurrentAnswerDescription;

    //Private integer variable
    private int mCurrentQuestion;

    //Random variable to use it for showing the images randomly
    private Random mRandom;

    //Index variable
    int mCurrentIndex = 0;

    // String variable for language
    private String language;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        //SharePrefrences to save app language
        SharedPreferences sharedPreferences = getSharedPreferences(APP_PREF, MODE_PRIVATE);
        String appLang = sharedPreferences.getString(APP_LANG, "");
        if(!appLang.equals(""))
            LocaleHelper.setLocale(this, appLang);
        setContentView(R.layout.activity_main);

        //Find View by Id to show question image
        questionImageView = findViewById( R.id.image_view_question);

        //Get arrays resources by get getResources method
        ANSWERS = getResources().getStringArray(R.array.answers);
        ANSWER_DESCRIPTION = getResources().getStringArray(R.array.answer_description);


    }


    @Override
    //This code to show languages opttions as a menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater  = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    // selsect language from the menu
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()== R.id.menuChangeLang){
            showLanguageDialog(language);
            return true;
        }else{

            return super.onOptionsItemSelected(item);
        }}
    //create menu dialog
    private void showLanguageDialog(String language){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.change_lang_text)
                .setItems(R.array.languages, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String language = "ar";
                        switch (which) {
                            case 0:
                                language = "ar";
                                break;
                            case 1:
                                language = "en";
                                break;
                            case 2:
                                language = "fr";
                                break;
                        }

                        LocaleHelper.setLocale(MainActivity.this, language);
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);


                    }
                }).create();
        alertDialog.show();

    }
    // save language after exit the app
    private void saveLanguage(String lang){
        SharedPreferences sharedPreferences = getSharedPreferences(APP_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(APP_LANG, lang);
        editor.apply();;
    }

    //Cjhange image code
    public void changImage(View view){
        mRandom = new Random();
        mCurrentIndex = mRandom.nextInt(QUESTIONS.length);
        mCurrentQuestion = QUESTIONS[mCurrentIndex];
        mCurentAnswer = ANSWERS[mCurrentIndex];
        mCurrentAnswerDescription = ANSWER_DESCRIPTION[mCurrentIndex];
        showImage();
    }

    //Save instance state code
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState( outState );
        outState.putInt( BUNDLE_CURRENT_INDEX, mCurrentIndex );
    }

    //Show image code
    public void showImage(){
        Drawable imageDrawable = ContextCompat.getDrawable(this, QUESTIONS[mCurrentIndex]);
        questionImageView.setImageDrawable(imageDrawable);
    }
    //REstoreInstanceState cod
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState( savedInstanceState );
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt( BUNDLE_CURRENT_INDEX );
        }
    }

    //Intent code to open answer activity
    public void showAnswer(View view){
        Intent intent = new Intent(MainActivity.this, AnsewrActivity.class);
        intent.putExtra(QUESTION_ANSWER, mCurentAnswer + " " + mCurrentAnswerDescription);
        startActivity(intent);
    }
    //Intent code to open share activity and share image
    public void shareButton(View view){
        Intent intent = new Intent(MainActivity.this, ShareActivity.class );
        intent.putExtra(SHARE_QUESTION, mCurrentQuestion);
        intent.putExtra(  SHARE_IMAGE, QUESTIONS[mCurrentIndex]);
        startActivity(intent);
    }

    //Restart method
    protected void onRestart() {
        super.onRestart();
    }

}
