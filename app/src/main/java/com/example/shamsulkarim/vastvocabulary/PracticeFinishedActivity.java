package com.example.shamsulkarim.vastvocabulary;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shamsulkarim.vastvocabulary.Practice.Practice;
import com.example.shamsulkarim.vastvocabulary.WordAdapters.DisplayLearnedWordsAdapter;

public class PracticeFinishedActivity extends AppCompatActivity implements View.OnClickListener{

    double screenInches;
    String toastMsg;
    int screenSize;
    private ImageView background, stars, ripple, planet;
    private Button homeButton, trainAgainButton;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_display_learning_score);
        planet = (ImageView)findViewById(R.id.planet_displayLearningScore);

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



        if(screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL){


            if(screenInches < 4.8 ){

                planet.setImageResource(R.drawable.practice_planet_lessthan);


            }else {


                planet.setImageResource(R.drawable.practice_planet_normal);

            }


        }else {


            planet.setImageResource(R.drawable.planet_practice_large);


        }
        //----------------------------------------------------------------------------------------



        background = (ImageView)findViewById(R.id.background);
        stars = (ImageView)findViewById(R.id.back_stars);
        ripple = (ImageView)findViewById(R.id.session_completed);


        background.setAlpha(0f);
        stars.setScaleY(0f);
        stars.setScaleX(0f);
        ripple.setAlpha(0f);

        trainAgainButton = (Button)findViewById(R.id.trainAgain_displayLearningScore);
        homeButton = (Button)findViewById(R.id.home_displayLearningScore);
        trainAgainButton.setOnClickListener(this);
        homeButton.setOnClickListener(this);

        homeButton.setBackgroundColor(Color.parseColor("#673AB7"));
        trainAgainButton.setBackgroundColor(Color.parseColor("#673AB7"));

        recyclerView = (RecyclerView)findViewById(R.id.display_learned_words_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new DisplayLearnedWordsAdapter(this,Practice.practicedWords,"#673AB7");
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


    @Override
    public void onClick(View view) {
        if(view.getId() == homeButton.getId()){

            startActivity(new Intent(this, MainActivity.class));
            this.finish();
        }

        if (view.getId() == trainAgainButton.getId()){

            startActivity(new Intent(this, Practice.class));
            this.finish();
        }
    }
}
