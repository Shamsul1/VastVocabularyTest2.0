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
import android.widget.Toast;

public class DisplayLearningScore extends AppCompatActivity {

    RelativeLayout new_learned_word, display_learned_word,word_info,learn_info, left_info;
    private ImageView display_learned_word_back;

    TextView word1,word2,word3, word4,word5,tran1, tran2,tran3,tran4,tran5, word_displayscore,learn_displayscore, left_displayscore;
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



        if(level.equalsIgnoreCase("beginner") ){

            if(sharedLearned> getResources().getStringArray(R.array.beginner_words).length){
                sharedLearned = getResources().getStringArray(R.array.beginner_words).length;


            }

            word_displayscore.setText(getResources().getStringArray(R.array.beginner_words).length+" words");
            learn_displayscore.setText(sharedLearned+" words learned");
            left_displayscore.setText(getResources().getStringArray(R.array.beginner_words).length-sharedLearned+" words left");

        }
        else if(level.equalsIgnoreCase("intermediate")){

            if(sharedLearned> getResources().getStringArray(R.array.intermediate_words).length){

                sharedLearned = getResources().getStringArray(R.array.intermediate_words).length;
            }

            word_displayscore.setText(getResources().getStringArray(R.array.intermediate_words).length+" words");
            learn_displayscore.setText(sharedLearned+" words learned");
            left_displayscore.setText(getResources().getStringArray(R.array.intermediate_words).length-sharedLearned+" words left");

        }
        else if(level.equalsIgnoreCase("advanced")){

            if(sharedLearned> getResources().getStringArray(R.array.advanced_words).length){

                sharedLearned = getResources().getStringArray(R.array.advanced_words).length;
            }

            word_displayscore.setText(getResources().getStringArray(R.array.advanced_words).length+" words");
            learn_displayscore.setText(sharedLearned+" words learned");
            left_displayscore.setText(getResources().getStringArray(R.array.advanced_words).length-sharedLearned+" words left");


        }

        if(sharedLearned> getResources().getStringArray(R.array.beginner_words).length){

            sharedLearned = getResources().getStringArray(R.array.beginner_words).length;
        }
        
        sharedPreferences.edit().putInt(level,sharedLearned).apply();







    }

    public void animationDisplayScore(){

        display_learned_word.setY(-height);
        display_learned_word_back.setY(-height);

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

        word_displayscore = (TextView)findViewById(R.id.word_displayscore);
        learn_displayscore = (TextView)findViewById(R.id.learned_displayscore);
        left_displayscore = (TextView)findViewById(R.id.left_displayscore);


    }

    private void layoutInitialization(){

        new_learned_word = (RelativeLayout)findViewById(R.id.new_words_learned);
        display_learned_word = (RelativeLayout)findViewById(R.id.display_learned_words);
        word_info =(RelativeLayout)findViewById(R.id.word_info);
        learn_info =(RelativeLayout)findViewById(R.id.learn_info);
        left_info =(RelativeLayout)findViewById(R.id.left_info);
        display_learned_word_back = (ImageView)findViewById(R.id.display_learned_words_back);



    }

    private  void initialSetUp(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        height = dm.heightPixels;

        display_learned_word_back.setY(-height);
        new_learned_word.setY(-height);
        display_learned_word.setY(-height);
        word_info.setY(-height);
        left_info.setY(-height);
        learn_info.setY(-height);


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



}
