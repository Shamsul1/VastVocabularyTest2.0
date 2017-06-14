package com.example.shamsulkarim.vastvocabulary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class StartTrainingActivity extends AppCompatActivity {

    private TextView title,learned,left,words;
    private ImageView titleBackground, wordBackground,learnedBackground, leftBackground,planet, white1,white2,white3, wordIcon,learnedIcon,leftIcon;
    private Button startTraining;
    Typeface ABeeZee ;

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

        ABeeZee = Typeface.createFromAsset(getAssets(),"fonts/ABeeZee-Regular.ttf");








        initialization();
        title.setTypeface(ABeeZee);
        learned.setTypeface(ABeeZee);
        left.setTypeface(ABeeZee);
        words.setTypeface(ABeeZee);
        startTraining.setTypeface(ABeeZee);
        levelPicker(level);

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                riseUpAnimation();
//            }
//        },100L);
//


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

        white1 = (ImageView)findViewById(R.id.white1_start_training);
        white2 = (ImageView)findViewById(R.id.white2_start_training);
        white3 = (ImageView)findViewById(R.id.white3_start_training);

        wordIcon = (ImageView) findViewById(R.id.word_icon_start_training);
        learnedIcon = (ImageView)findViewById(R.id.learned_icon_start_training);
        leftIcon = (ImageView)findViewById(R.id.left_icon_start_training);

//        words.setVisibility(View.INVISIBLE);
//        learned.setVisibility(View.INVISIBLE);
//        left.setVisibility(View.INVISIBLE);
//
//        wordBackground.setVisibility(View.INVISIBLE);
//        learnedBackground.setVisibility(View.INVISIBLE);
//        leftBackground.setVisibility(View.INVISIBLE);
//
//        white2.setVisibility(View.INVISIBLE);
//        white3.setVisibility(View.INVISIBLE);
//        white1.setVisibility(View.INVISIBLE);
//
//        learnedIcon.setVisibility(View.INVISIBLE);
//        wordIcon.setVisibility(View.INVISIBLE);
//        leftIcon.setVisibility(View.INVISIBLE);





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
            titleBackground.setBackgroundColor(Color.parseColor("#83a9ba"));
            wordBackground.setBackgroundColor(Color.parseColor("#83a9ba"));
            learnedBackground.setBackgroundColor(Color.parseColor("#83a9ba"));
            leftBackground.setBackgroundColor(Color.parseColor("#83a9ba"));
            planet.setImageResource(R.drawable.start_training_planet_intermediate);
            startTraining.setBackgroundColor(Color.parseColor("#83a9ba"));


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



//    private void riseUpAnimation(){
//
//
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        float width = dm.widthPixels;
//        float height = dm.heightPixels;
//
//        ValueAnimator va = ValueAnimator.ofFloat(width,0);
//
//
//        ValueAnimator vaheight = ValueAnimator.ofFloat(height,0);
//
//
//
//        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
//
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//
//                float value = (float)valueAnimator.getAnimatedValue();
//
//                words.setVisibility(View.VISIBLE);
//                learned.setVisibility(View.VISIBLE);
//                left.setVisibility(View.VISIBLE);
//
//                wordBackground.setVisibility(View.VISIBLE);
//                learnedBackground.setVisibility(View.VISIBLE);
//                leftBackground.setVisibility(View.VISIBLE);
//
//                white2.setVisibility(View.VISIBLE);
//                white3.setVisibility(View.VISIBLE);
//                white1.setVisibility(View.VISIBLE);
//
//                learnedIcon.setVisibility(View.VISIBLE);
//                wordIcon.setVisibility(View.VISIBLE);
//                leftIcon.setVisibility(View.VISIBLE);
//
//                words.setTranslationY(value/10);
//                learned.setTranslationY(value/10);
//                left.setTranslationY(value/10);
//
//                wordBackground.setTranslationY(value/10);
//                learnedBackground.setTranslationY(value/10);
//                leftBackground.setTranslationY(value/10);
//                white1.setTranslationY(value/10);
//                white2.setTranslationY(value/10);
//                white3.setTranslationY(value/10);
//                learnedIcon.setTranslationY(value/10);
//                wordIcon.setTranslationY(value/10);
//                leftIcon.setTranslationY(value/10);
//
//
//            }
//        });
//
//
//        va.setInterpolator(new DecelerateInterpolator());
//        va.setDuration(500L);
//        va.start();
//
//
//
//
//
//
//
//
//
//
//
//
//    }

}
