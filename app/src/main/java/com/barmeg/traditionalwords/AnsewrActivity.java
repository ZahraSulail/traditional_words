package com.barmeg.traditionalwords;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AnsewrActivity extends AppCompatActivity {
    //TextView to shoe question answer
    private TextView mAnswerTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_answer);
        mAnswerTextView = findViewById( R.id.text_view_answer);
        String answer = getIntent().getStringExtra( "QUESTION_ANSWER" );
        if (answer!=null){

            mAnswerTextView.setText(answer);
        }

    }
    //Return button
    public void onReturnClicke(View view){
        finish();
    }
}
