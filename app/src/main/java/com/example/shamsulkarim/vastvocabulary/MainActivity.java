package com.example.shamsulkarim.vastvocabulary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private BeginnerWordDatabase beginnerDatabase;
    private IntermediatewordDatabase intermediateDatabase;
    private AdvancedWordDatabase advanceDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        addBeginnerWordToSQLite();
        addIntermediateWordToSQLite();
        addAdvanceWordToSQLite();

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
                Wordactivity wordFragment = new Wordactivity();
                getSupportFragmentManager().beginTransaction().replace(R.id.frag,wordFragment).commit();

                homeView.setImageResource(R.drawable.ic_home);
                wordsView.setImageResource(R.drawable.ic_learn_active);
                learnedView.setImageResource(R.drawable.ic_learned);
                settingsView.setImageResource(R.drawable.ic_setting);
                break;
            case R.id.learned:
                LearnedWords learnedWords = new LearnedWords();
                getSupportFragmentManager().beginTransaction().replace(R.id.frag,learnedWords).commit();

                homeView.setImageResource(R.drawable.ic_home);
                wordsView.setImageResource(R.drawable.ic_learn);
                learnedView.setImageResource(R.drawable.ic_learned_active);
                settingsView.setImageResource(R.drawable.ic_setting);
                break;

            case R.id.settings:

                FavoriteWords favoriteWords = new FavoriteWords();
                getSupportFragmentManager().beginTransaction().replace(R.id.frag,favoriteWords).commit();

                homeView.setImageResource(R.drawable.ic_home);
                wordsView.setImageResource(R.drawable.ic_learn);
                learnedView.setImageResource(R.drawable.ic_learned);
                settingsView.setImageResource(R.drawable.ic_setting_active);
                break;


        }


    }




    private void addBeginnerWordToSQLite(){
        beginnerDatabase = new BeginnerWordDatabase(this);
        SharedPreferences sp = this.getSharedPreferences("com.example.shamsulkarim.vastvocabulary", Context.MODE_PRIVATE);


        if(!sp.contains("beginnerWordCount")){
            final int beginnerWordLength = getResources().getStringArray(R.array.beginner_words).length;
            sp.edit().putInt("beginnerWordCount",beginnerWordLength).apply();

            for(int i = 0; i < beginnerWordLength; i++){

                beginnerDatabase.insertData(""+i,"false","false");

            }

        }
        int PREVIOUSBEGINNERCOUNT = sp.getInt("beginnerWordCount",0);
        int CURRENTBEGINNERCOUNT = getResources().getStringArray(R.array.beginner_words).length;



        if(CURRENTBEGINNERCOUNT > PREVIOUSBEGINNERCOUNT){


            for(int i = PREVIOUSBEGINNERCOUNT; i < CURRENTBEGINNERCOUNT; i++){

                beginnerDatabase.insertData(""+i,"false","false");




            }
            sp.edit().putInt("beginnerWordCount",CURRENTBEGINNERCOUNT).apply();




        }else {



        }







    }

    private void addIntermediateWordToSQLite(){
        intermediateDatabase = new IntermediatewordDatabase(this);
        SharedPreferences sp = this.getSharedPreferences("com.example.shamsulkarim.vastvocabulary", Context.MODE_PRIVATE);

        if(!sp.contains("intermediateWordCount")){
            final int intermediateWordLength = getResources().getStringArray(R.array.intermediate_words).length;
            sp.edit().putInt("intermediateWordCount",intermediateWordLength).apply();

            for(int i = 0; i < intermediateWordLength; i++){

                intermediateDatabase.insertData(""+i,"false","false");

            }

        }
        int PREVIOUSBEGINNERCOUNT = sp.getInt("intermediateWordCount",0);
        int CURRENTBEGINNERCOUNT = getResources().getStringArray(R.array.intermediate_words).length;

        if(CURRENTBEGINNERCOUNT > PREVIOUSBEGINNERCOUNT){

            for(int i = PREVIOUSBEGINNERCOUNT; i < CURRENTBEGINNERCOUNT; i++){

                intermediateDatabase.insertData(""+i,"false","false");

            }
            sp.edit().putInt("intermediateWordCount",CURRENTBEGINNERCOUNT).apply();




        }else {




        }

    }

    private void addAdvanceWordToSQLite(){
        advanceDatabase = new AdvancedWordDatabase(this);
        SharedPreferences sp = this.getSharedPreferences("com.example.shamsulkarim.vastvocabulary", Context.MODE_PRIVATE);


        if(!sp.contains("advanceWordCount")){
            final int advanceWordLength = getResources().getStringArray(R.array.advanced_words).length;
            sp.edit().putInt("advanceWordCount",advanceWordLength).apply();

            for(int i = 0; i < advanceWordLength; i++){

                advanceDatabase.insertData(""+i,"false","false");

            }

        }
        int PREVIOUSBEGINNERCOUNT = sp.getInt("advanceWordCount",0);
        int CURRENTBEGINNERCOUNT = getResources().getStringArray(R.array.advanced_words).length;


        if(CURRENTBEGINNERCOUNT > PREVIOUSBEGINNERCOUNT){


            for(int i = PREVIOUSBEGINNERCOUNT; i < CURRENTBEGINNERCOUNT; i++){

                advanceDatabase.insertData(""+i,"false","false");




            }
            sp.edit().putInt("advanceWordCount",CURRENTBEGINNERCOUNT).apply();




        }else {



        }







    }





}
