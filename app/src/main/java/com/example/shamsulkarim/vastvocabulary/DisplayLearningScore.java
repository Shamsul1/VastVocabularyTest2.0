package com.example.shamsulkarim.vastvocabulary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shamsulkarim.vastvocabulary.WordAdapters.DisplayLearnedWordsAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.List;

public class DisplayLearningScore extends AppCompatActivity implements View.OnClickListener {

    private ImageView background, stars, ripple, planet;
    RelativeLayout  display_learned_word;
    private ImageView display_learned_word_back, small_star1,small_star2,big_star,session_completed,session_completed_background;

    TextView word1,word2,word3, word4,word5,tran1, tran2,tran3,tran4,tran5;
    String[] word,translation;
    private Button homeButton, trainAgainButton;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    String recyclerViewColor;
    double screenInches;
    String toastMsg;
    int screenSize;


    int height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_display_learning_score);

        // Determining Screen Size ---------------------------------------------------------------
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        double wi=(double)width/(double)dm.xdpi;
        double hi=(double)height/(double)dm.ydpi;
        double x = Math.pow(wi,2);
        double y = Math.pow(hi,2);
        screenInches = Math.sqrt(x+y);


        screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;


        switch(screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                toastMsg = "Large screen";
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                toastMsg = "Normal screen";
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                toastMsg = "Small screen";
                break;
            default:
                toastMsg = "Screen size is neither large, normal or small";
        }
        //----------------------------------------------------------------------------------------


        // INITIALIZATION

        trainAgainButton = (Button)findViewById(R.id.trainAgain_displayLearningScore);
        homeButton = (Button)findViewById(R.id.home_displayLearningScore);
        trainAgainButton.setOnClickListener(this);
        homeButton.setOnClickListener(this);

        word = getIntent().getStringArrayExtra("word");
        translation = getIntent().getStringArrayExtra("translation");

        planet = (ImageView)findViewById(R.id.planet_displayLearningScore);
        background = (ImageView)findViewById(R.id.background);
        stars = (ImageView)findViewById(R.id.back_stars);
        ripple = (ImageView)findViewById(R.id.session_completed);

        AdView adView = (AdView)findViewById(R.id.banner);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        background.setAlpha(0f);
        stars.setScaleY(0f);
        stars.setScaleX(0f);
        ripple.setAlpha(0f);







        SharedPreferences sp = this.getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);
        String level = sp.getString("level", "beginner");

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);

        int sharedLearned = sharedPreferences.getInt(level,0);

        if(sharedLearned >= Train.wordObjects.size()){
            sharedLearned +=Train.wordObjects.size();
        }
        if(sharedLearned <= 0){
            sharedLearned = Train.wordObjects.size();
        }
        recyclerViewColor = "#f05e2a";

        if(level.equalsIgnoreCase("beginner")){

            recyclerViewColor = "#f05e2a";

            if(sharedLearned > getResources().getStringArray(R.array.beginner_words).length){

                sharedLearned = getResources().getStringArray(R.array.beginner_words).length;
            }

        }

        if(level.equalsIgnoreCase("intermediate")){

            recyclerViewColor ="#83a9ba";

            if(sharedLearned > getResources().getStringArray(R.array.intermediate_words).length){

                sharedLearned = getResources().getStringArray(R.array.intermediate_words).length;
            }

        }

        if(level.equalsIgnoreCase("advance")){

            recyclerViewColor = "#f9b24e";
            if(sharedLearned > getResources().getStringArray(R.array.advanced_words).length){

                sharedLearned = getResources().getStringArray(R.array.advanced_words).length;
            }

        }



        // Choosing Planet Image according to screen size -----------------------------------

        if(level.equalsIgnoreCase("beginner")){



            if(screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL){


                if(screenInches < 4.8 ){

                    Toast.makeText(this,"< 5 ",Toast.LENGTH_SHORT).show();
                    planet.setImageResource(R.drawable.beginner_planet_lessthan_display_learning_score);


                }else {


                    planet.setImageResource(R.drawable.beginner_planet_normal_display_learning_score);

                }


            }else {


                planet.setImageResource(R.drawable.beginner_planet_train);


            }



        }
        if(level.equalsIgnoreCase("intermediate")){



            if(screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL){


                if(screenInches < 4.8 ){

                    Toast.makeText(this,"< 5 ",Toast.LENGTH_SHORT).show();
                    planet.setImageResource(R.drawable.intermediate_planet_lessthan_display_learning_score);


                }else {


                    planet.setImageResource(R.drawable.intermediate_planet_display_learning_score);

                }


            }else {


                planet.setImageResource(R.drawable.intermediate_planet_train);


            }


        }

        if(level.equalsIgnoreCase("advance")){
            if(screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL){


                if(screenInches < 4.8 ){

                    Toast.makeText(this,"< 5 ",Toast.LENGTH_SHORT).show();
                    planet.setImageResource(R.drawable.advance_planet_lessthan_display_learning_score);


                }else {


                    planet.setImageResource(R.drawable.advance_planet_display_learning_score);

                }


            }else {


                planet.setImageResource(R.drawable.advance_planet_train);


            }

        }

        Toast.makeText(this, level,Toast.LENGTH_SHORT).show();

        sharedPreferences.edit().putInt(level,sharedLearned).apply();

        homeButton.setBackgroundColor(Color.parseColor(recyclerViewColor));
        trainAgainButton.setBackgroundColor(Color.parseColor(recyclerViewColor));

        recyclerView = (RecyclerView)findViewById(R.id.display_learned_words_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new DisplayLearnedWordsAdapter(this,Train.wordObjects,recyclerViewColor);
        recyclerView.setAdapter(adapter);

        animation();


    }


    public void animation(){

        Handler handler = new Handler();

        riseHighRecyclerView();
        riseDownPlanet();

        backgroundAlpha();
        buttonAlpha();
        riseHigh();
        alpha();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                scale();

            }
        },500L);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                scaleDown();
                riseDown();

            }
        },1500L);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fadeAway();
            }
        },1800L);


    }

    public void backgroundAlpha(){

        ValueAnimator va = ValueAnimator.ofFloat(0,0.90f);

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float)animation.getAnimatedValue();
                background.setAlpha(value);

            }
        });

        va.setRepeatMode(ValueAnimator.REVERSE);
        va.setRepeatCount(1);
        va.setDuration(1800L);
        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.start();


    }

    public void buttonAlpha(){

        ValueAnimator va = ValueAnimator.ofFloat(1,0.20f);

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float)animation.getAnimatedValue();
                homeButton.setAlpha(value);
                trainAgainButton.setAlpha(value);

            }
        });

        va.setRepeatMode(ValueAnimator.REVERSE);
        va.setRepeatCount(1);
        va.setDuration(1800L);
        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.start();


    }

    public void riseHigh(){

        ValueAnimator va = ValueAnimator.ofFloat(100,0);

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (Float)animation.getAnimatedValue();
                ripple.setTranslationY(value);
            }
        });
        va.setDuration(800L);
        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.start();

    }

    public void riseHighRecyclerView(){

        ValueAnimator va = ValueAnimator.ofFloat(100,0);

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (Float)animation.getAnimatedValue();
                recyclerView.setTranslationY(value);
            }
        });
        va.setDuration(500L);
        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.start();

    }

    public void riseDownPlanet(){

        ValueAnimator va = ValueAnimator.ofFloat(100,0);

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (Float)animation.getAnimatedValue();
                planet.setTranslationY(-value);
            }
        });
        va.setDuration(500L);
        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.start();

    }

    public void scale(){


        ValueAnimator va = ValueAnimator.ofFloat(0,1);

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();

                stars.setScaleX(value);
                stars.setScaleY(value);
            }
        });

        va.setDuration(500L);
        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.start();

    }

    public void alpha(){

        ValueAnimator va = ValueAnimator.ofFloat(0,1);

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float)animation.getAnimatedValue();
                ripple.setAlpha(value);

            }
        });


        va.setDuration(300L);
        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.start();


    }

    public void scaleDown(){
        ValueAnimator va = ValueAnimator.ofFloat(1,0);

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();

                stars.setScaleX(value);
                stars.setScaleY(value);
            }
        });

        va.setDuration(500L);
        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.start();

    }

    public void riseDown(){


        ValueAnimator va = ValueAnimator.ofFloat(0,100);

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (Float)animation.getAnimatedValue();
                ripple.setTranslationY(value/6);
            }
        });
        va.setDuration(800L);
        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.start();

    }

    public void fadeAway(){



        ValueAnimator va = ValueAnimator.ofFloat(1,0);

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float)animation.getAnimatedValue();
                ripple.setAlpha(value);

            }
        });


        va.setDuration(600L);
        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.start();



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


    @Override
    public void onClick(View view) {


        if(view.getId() == homeButton.getId()){

            startActivity(new Intent(this, MainActivity.class));
            this.finish();
        }

        if (view.getId() == trainAgainButton.getId()){

            startActivity(new Intent(this, Train.class));
            this.finish();
        }
    }
}
