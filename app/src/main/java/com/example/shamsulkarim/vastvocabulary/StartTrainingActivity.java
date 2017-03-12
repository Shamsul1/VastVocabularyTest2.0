package com.example.shamsulkarim.vastvocabulary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.Image;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class StartTrainingActivity extends AppCompatActivity {

    private TextView title,learned,left,words;
    private ImageView titleBackground, wordBackground,learnedBackground, leftBackground,planet;
    private Button startTraining;

    private int learnedWordCount,totalWordCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start_training);
        SharedPreferences sp = this.getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);
        String level = sp.getString("level","");
         learnedWordCount = sp.getInt(level,0);

        Toast.makeText(this,level,Toast.LENGTH_LONG).show();







        initialization();
        levelPicker(level);



    }

    public void onStartTraining(View view){
        Intent intent = new Intent(this, Train.class);
        this.startActivity(intent);
        this.finish();
    }

    private void initialization(){
        titleBackground = (ImageView)findViewById(R.id.title_background_start_training);
        wordBackground = (ImageView)findViewById(R.id.words_background_start_training);
        learnedBackground = (ImageView)findViewById(R.id.learned_background_start_training);
        leftBackground = (ImageView)findViewById(R.id.left_background_start_training);
        planet = (ImageView)findViewById(R.id.planet_start_training);
        startTraining = (Button)findViewById(R.id.button_start_training);

        title = (TextView)findViewById(R.id.title_start_training);
        words = (TextView)findViewById(R.id.word_text_start_training);
        learned = (TextView)findViewById(R.id.learned_text_start_training);
        left = (TextView)findViewById(R.id.left_text_start_training);

    }

    private void levelPicker(String level){

        if(level.equalsIgnoreCase("beginner") ){

            totalWordCount = getResources().getStringArray(R.array.beginner_words).length;

            titleBackground.setBackgroundColor(Color.parseColor("#f05e2a"));
            wordBackground.setBackgroundColor(Color.parseColor("#f05e2a"));
            learnedBackground.setBackgroundColor(Color.parseColor("#f05e2a"));
            leftBackground.setBackgroundColor(Color.parseColor("#f05e2a"));
            planet.setImageResource(R.drawable.start_training_planet_beginner);
            startTraining.setBackgroundColor(Color.parseColor("#f05e2a"));


            title.setText("BEGINNER");
            words.setText(totalWordCount+" words");
            learned.setText(learnedWordCount+" words learned");
            left.setText(totalWordCount-learnedWordCount+" words left");

        }
        else if(level.equalsIgnoreCase("intermediate")){

            totalWordCount = getResources().getStringArray(R.array.intermediate_words).length;
            titleBackground.setBackgroundColor(Color.parseColor("#b5e2ed"));
            wordBackground.setBackgroundColor(Color.parseColor("#b5e2ed"));
            learnedBackground.setBackgroundColor(Color.parseColor("#b5e2ed"));
            leftBackground.setBackgroundColor(Color.parseColor("#b5e2ed"));
            planet.setImageResource(R.drawable.start_training_planet_intermediate);
            startTraining.setBackgroundColor(Color.parseColor("#b5e2ed"));


            title.setText("INTERMEDIATE");
            words.setText(totalWordCount+" words");
            learned.setText(learnedWordCount+" words learned");
            left.setText(totalWordCount-learnedWordCount+" words left");

        }
       else if(level.equalsIgnoreCase("advance")){

            totalWordCount = getResources().getStringArray(R.array.advanced_words).length;
            titleBackground.setBackgroundColor(Color.parseColor("#f9b24e"));
            wordBackground.setBackgroundColor(Color.parseColor("#f9b24e"));
            learnedBackground.setBackgroundColor(Color.parseColor("#f9b24e"));
            leftBackground.setBackgroundColor(Color.parseColor("#f9b24e"));
            startTraining.setBackgroundColor(Color.parseColor("#f9b24e"));
            planet.setImageResource(R.drawable.start_training_planet_advance);
            title.setText("ADVANCE");
            words.setText(totalWordCount+" words");
            learned.setText(learnedWordCount+" words learned");
            left.setText(totalWordCount-learnedWordCount+" words left");
        }
    }

}
