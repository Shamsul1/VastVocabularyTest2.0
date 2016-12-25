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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DisplayLearningScore extends AppCompatActivity {

    RelativeLayout new_learned_word, display_learned_word,word_info,learn_info, left_info;

    int height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_learning_score);

        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        height = dm.heightPixels;

         new_learned_word = (RelativeLayout)findViewById(R.id.new_words_learned);
        display_learned_word = (RelativeLayout)findViewById(R.id.display_learned_words);
        word_info =(RelativeLayout)findViewById(R.id.word_info);
        learn_info =(RelativeLayout)findViewById(R.id.learn_info);
        left_info =(RelativeLayout)findViewById(R.id.left_info);

        new_learned_word.setY(-height);
        display_learned_word.setY(-height);
        word_info.setY(-height);
        left_info.setY(-height);
        learn_info.setY(-height);


        SharedPreferences sp = this.getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);
        String level = sp.getString("level", "beginner");







    }

    public void animationDisplayScore(View view){

        Handler handler = new Handler();

        comeIn();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                comeOut();
                comeInWords();

            }
        },1500L);


    }


    public void comeIn(){
        ValueAnimator va = ValueAnimator.ofFloat(-height,0);

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){


            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                new_learned_word.setTranslationY(value);
                word_info.setTranslationY(value);
                learn_info.setTranslationY(value);
                left_info.setTranslationY(value);


            }
        });

        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.setDuration(400L);
        va.start();


    }

    private void comeOut(){
        ValueAnimator va = ValueAnimator.ofFloat( 0,-height);

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                float value = (float) valueAnimator.getAnimatedValue();
                new_learned_word.setTranslationY(value);
                word_info.setTranslationY(value);
                learn_info.setTranslationY(value);
                left_info.setTranslationY(value);


            }
        });

        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.setDuration(400L);
        va.start();
    }

    private void comeInWords(){

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(-height, 0);

        valueAnimator.addUpdateListener( new ValueAnimator.AnimatorUpdateListener(){


            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                float value = (float) valueAnimator.getAnimatedValue();
                display_learned_word.setTranslationY(value);
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



}
