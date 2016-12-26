package com.example.shamsulkarim.vastvocabulary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class StartTrainingActivity extends AppCompatActivity {

    private TextView title,learned,left,words;
    private ImageView sun,start_training_Button, cloud1,cloud2,cloud3;

    ImageView start_training_land;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_training);
        SharedPreferences sp = this.getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);
        String level = sp.getString("level","");





        initialization();
        levelPicker(level);

        animateSun();
        animateButton();
        animateCloud();

    }

    private void animateCloud(){


        float sunY = cloud1.getY();

        ValueAnimator va = ValueAnimator.ofFloat(sunY,-40);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                float  value = (float)valueAnimator.getAnimatedValue();
                cloud1.setTranslationX(value);
                cloud2.setTranslationX(value);
                cloud3.setTranslationX(value);

            }
        });

        va.setRepeatMode(ValueAnimator.REVERSE);
        va.setRepeatCount(ValueAnimator.INFINITE);
        va.setInterpolator(new LinearInterpolator());
        va.setDuration(3000L);
        va.start();



    }

    private void animateSun(){
        float sunY = sun.getY();
        sun.setY(sunY+100);

        ValueAnimator va = ValueAnimator.ofFloat(sunY,-60);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                float  value = (float)valueAnimator.getAnimatedValue();
                sun.setTranslationY(value);

            }
        });

        va.setInterpolator(new FastOutSlowInInterpolator());
        va.setDuration(1200L);
        va.start();




    }

    private void animateButton(){

        float sunY = start_training_Button.getY();

        ValueAnimator va = ValueAnimator.ofFloat(sunY,-15);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                float  value = (float)valueAnimator.getAnimatedValue();
                start_training_Button.setTranslationY(value);

            }
        });

        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.setDuration(1000L);
        va.start();











    }




































    public void onStartTraining(View view){
        Intent intent = new Intent(this, Train.class);
        this.startActivity(intent);
    }

    private void initialization(){
        start_training_land = (ImageView)findViewById(R.id.start_training_landscape);
        title = (TextView)findViewById(R.id.title);
        words = (TextView)findViewById(R.id.words_train);
        learned = (TextView)findViewById(R.id.words_learned_train);
        left = (TextView)findViewById(R.id.words_left_train);
        sun = (ImageView)findViewById(R.id.sun);
        start_training_Button = (ImageView)findViewById(R.id.start_trainging_button);
        cloud1 = (ImageView)findViewById(R.id.cloud1);
        cloud2 = (ImageView)findViewById(R.id.cloud2);
        cloud3 = (ImageView)findViewById(R.id.cloud3);
    }

    private void levelPicker(String level){

        if(level.equalsIgnoreCase("beginner") ){

            start_training_land.setImageResource(R.drawable.no_back_land1);
            title.setText("BEGINNER");

        }
        else if(level.equalsIgnoreCase("intermediate")){

            start_training_land.setImageResource(R.drawable.intermediate_result);
            title.setText("INTERMEDIATE");

        }
       else if(level.equalsIgnoreCase("advanced")){

            start_training_land.setImageResource(R.drawable.advance_result);
            title.setText("ADVANCED");
        }
    }

}
