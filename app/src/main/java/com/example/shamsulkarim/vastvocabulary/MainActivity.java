package com.example.shamsulkarim.vastvocabulary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView homeView = (ImageView)findViewById(R.id.home);

        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag,homeFragment).commit();

        homeView.setImageResource(R.drawable.ic_home_active);


    }
    public void onStartTrainingActivity(View view){
        Intent intent = new Intent(this,StartTrainingActivity.class);
        SharedPreferences sp = this.getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);
        sp.edit().putString("Hello","hello").apply();

        if(view.getId() == R.id.beginner){
            sp.edit().putString("level","beginner").apply();
            this.startActivity(intent);


        }
        if(view.getId() == R.id.intermediate){
            sp.edit().putString("level","intermediate").apply();
            this.startActivity(intent);


        }
        if(view.getId() == R.id.advanced){
            sp.edit().putString("level","advanced").apply();
            this.startActivity(intent);


        }


    }



    public void onBottomClick(View view){
        ImageView homeView = (ImageView)findViewById(R.id.home);
        ImageView wordsView = (ImageView)findViewById(R.id.words);
        ImageView learnedView = (ImageView)findViewById(R.id.learned);
        ImageView settingsView = (ImageView)findViewById(R.id.settings);



        switch (view.getId()){

            case R.id.home:
                HomeFragment homeFragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frag,homeFragment).commit();

                homeView.setImageResource(R.drawable.ic_home_active);
                wordsView.setImageResource(R.drawable.ic_learn);
                learnedView.setImageResource(R.drawable.ic_learned);
                settingsView.setImageResource(R.drawable.ic_setting);
                break;

            case R.id.words:

                homeView.setImageResource(R.drawable.ic_home);
                wordsView.setImageResource(R.drawable.ic_learn_active);
                learnedView.setImageResource(R.drawable.ic_learned);
                settingsView.setImageResource(R.drawable.ic_setting);
                break;
            case R.id.learned:

                homeView.setImageResource(R.drawable.ic_home);
                wordsView.setImageResource(R.drawable.ic_learn);
                learnedView.setImageResource(R.drawable.ic_learned_active);
                settingsView.setImageResource(R.drawable.ic_setting);
                break;

            case R.id.settings:

                homeView.setImageResource(R.drawable.ic_home);
                wordsView.setImageResource(R.drawable.ic_learn);
                learnedView.setImageResource(R.drawable.ic_learned);
                settingsView.setImageResource(R.drawable.ic_setting_active);
                break;


        }


    }




}
