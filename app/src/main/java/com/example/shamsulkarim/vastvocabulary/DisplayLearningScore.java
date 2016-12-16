package com.example.shamsulkarim.vastvocabulary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DisplayLearningScore extends AppCompatActivity {

    String[] recieveWord = new String[5];
    String[] recieveTranslation = new String[5];
    TextView resultWordView1,resultWordView2,resultWordView3,resultWordView4,resultWordView5,resultTranslationView1,resultTranslationView2,resultTranslationView3,resultTranslationView4,resultTranslationView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_learning_score);


        recieveWord = getIntent().getStringArrayExtra("words");
        recieveTranslation = getIntent().getStringArrayExtra("translation");

        resultWordView1 = (TextView)findViewById(R.id.result_word1);
        resultWordView2 = (TextView)findViewById(R.id.result_word2);
        resultWordView3 = (TextView)findViewById(R.id.result_word3);
        resultWordView4 = (TextView)findViewById(R.id.result_word4);
        resultWordView5 = (TextView)findViewById(R.id.result_word5);
        resultTranslationView1 = (TextView)findViewById(R.id.result_translation1);
        resultTranslationView2 = (TextView)findViewById(R.id.result_translation2);
        resultTranslationView3 = (TextView)findViewById(R.id.result_translation3);
        resultTranslationView4 = (TextView)findViewById(R.id.result_translation4);
        resultTranslationView5 = (TextView)findViewById(R.id.result_translation5);

        resultWordView1.setText(recieveWord[0]);
        resultWordView2.setText(recieveWord[1]);
        resultWordView3.setText(recieveWord[2]);
        resultWordView4.setText(recieveWord[3]);
        resultWordView5.setText(recieveWord[4]);
        resultTranslationView1.setText(recieveTranslation[0]);
        resultTranslationView2.setText(recieveTranslation[1]);
        resultTranslationView3.setText(recieveTranslation[2]);
        resultTranslationView4.setText(recieveTranslation[3]);
        resultTranslationView5.setText(recieveTranslation[4]);
    }


    public void homeActivity(View view){
        Intent intent = new Intent(this,MainActivity.class);
        this.startActivity(intent);

    }



}
