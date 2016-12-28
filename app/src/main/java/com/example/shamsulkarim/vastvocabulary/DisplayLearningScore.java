package com.example.shamsulkarim.vastvocabulary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayLearningScore extends AppCompatActivity {

    RelativeLayout  display_learned_word;
    private ImageView display_learned_word_back, small_star1,small_star2,big_star,session_completed,session_completed_background;

    TextView word1,word2,word3, word4,word5,tran1, tran2,tran3,tran4,tran5;
    String[] word,translation;

    int height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_learning_score);

        // INITIALIZATION


        textViewInitialization();
        layoutInitialization();
        initialSetUp();


        animationDisplayScore();

        word = getIntent().getStringArrayExtra("word");
        translation = getIntent().getStringArrayExtra("translation");



        SharedPreferences sp = this.getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);
        String level = sp.getString("level", "beginner");

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);

        int sharedLearned = sharedPreferences.getInt(level,0);

        if(sharedLearned >= 5){
            sharedLearned +=5;
        }
        if(sharedLearned <= 0){
            sharedLearned = 5;
        }


        
        sharedPreferences.edit().putInt(level,sharedLearned).apply();







    }

    public void animationDisplayScore(){

        display_learned_word.setY(-height);
        display_learned_word_back.setY(-height);

        sessionCompletedAnimation();
        sessionCompletedAnimationScale();

        Handler handler = new Handler();



        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                comeInWords();

            }
        },1500L);


    }






    private void comeInWords(){

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(-height, 0);

        valueAnimator.addUpdateListener( new ValueAnimator.AnimatorUpdateListener(){


            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                float value = (float) valueAnimator.getAnimatedValue();
                display_learned_word.setTranslationY(value);
                display_learned_word_back.setTranslationY(value);
            }
        });

        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setDuration(400L);
        valueAnimator.start();


    }











    public void homeActivity(View view){
        Intent intent = new Intent(this,MainActivity.class);
        this.startActivity(intent);

    }


    private void textViewInitialization(){

        word1 = (TextView)findViewById(R.id.word1);
        word2 = (TextView)findViewById(R.id.word2);
        word3 = (TextView)findViewById(R.id.word3);
        word4 = (TextView)findViewById(R.id.word4);
        word5 = (TextView)findViewById(R.id.word5);

        tran1 = (TextView)findViewById(R.id.tran1);
        tran2 = (TextView)findViewById(R.id.tran2);
        tran3 = (TextView)findViewById(R.id.tran3);
        tran4 = (TextView)findViewById(R.id.tran4);
        tran5 = (TextView)findViewById(R.id.tran5);


    }

    private void layoutInitialization(){

        display_learned_word = (RelativeLayout)findViewById(R.id.display_learned_words);
        display_learned_word_back = (ImageView)findViewById(R.id.display_learned_words_back);

        small_star1 = (ImageView)findViewById(R.id.small_star1);
        small_star2 = (ImageView)findViewById(R.id.small_star2);
        big_star = (ImageView)findViewById(R.id.big_star);
        session_completed = (ImageView)findViewById(R.id.session_completed);
        session_completed_background = (ImageView)findViewById(R.id.session_completed_background);



    }

    private  void initialSetUp(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        height = dm.heightPixels;

        display_learned_word_back.setY(-height);
        display_learned_word.setY(-height);



        word = getIntent().getStringArrayExtra("word");
        translation = getIntent().getStringArrayExtra("translation");

        Toast.makeText(this," "+word.length,Toast.LENGTH_SHORT).show();
        Toast.makeText(this," "+translation.length,Toast.LENGTH_SHORT).show();

        word1.setText(word[0]);
        word2.setText(word[1]);
        word3.setText(word[2]);
        word4.setText(word[3]);
        word5.setText(word[4]);

        tran1.setText(translation[0]);
        tran2.setText(translation[1]);
        tran3.setText(translation[2]);
        tran4.setText(translation[3]);
        tran5.setText(translation[4]);


    }

    public void trainAgain(View view){
        Intent intent = new Intent(this, Train.class);
        this.startActivity(intent);
        this.finish();



    }



    public void sessionCompletedAnimation(){



        ValueAnimator va = ValueAnimator.ofFloat(0,1 );

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){


            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                float value  = (float)valueAnimator.getAnimatedValue();



                session_completed_background.setAlpha(value/2);
                big_star.setAlpha(value);
                small_star1.setAlpha(value);
                small_star2.setAlpha(value);
                session_completed.setAlpha(value);



            }
        });
        va.setRepeatMode(ValueAnimator.REVERSE);
        va.setRepeatCount(1);
        va.setDuration(2000L);
        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.start();




    }

    public void sessionCompletedAnimationScale(){


        ValueAnimator va = ValueAnimator.ofFloat(0,1 );

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){


            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                float value  = (float)valueAnimator.getAnimatedValue();



                session_completed.setScaleX(value);
                session_completed.setScaleY(value);
                big_star.setScaleX(value);
                big_star.setScaleY(value);
                small_star1.setScaleY(value);
                small_star1.setScaleY(value);
                small_star2.setScaleY(value);
                small_star2.setScaleY(value);




            }
        });


        va.setDuration(2000L);
        va.setInterpolator(new AnticipateOvershootInterpolator());
        va.start();



    }



}
