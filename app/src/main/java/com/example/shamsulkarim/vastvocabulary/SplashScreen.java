package com.example.shamsulkarim.vastvocabulary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

//    SQL Database Initialization
    //--------------------------------------------
    static BeginnerWordDatabase beginnerDatabase;
    static IntermediatewordDatabase intermediateDatabase;
    static AdvancedWordDatabase advanceDatabase;
    static SharedPreferences sp;
    static List<Integer> savedBeginnerFav, savedAdvanceFav,savedIntermediateFav;
    static int savedBeginnerLearned,  savedIntemediateLearned, savedAdvanceLearned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sp = this.getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);
        sp.edit().putString("advanceFavNum","").apply();
        sp.edit().putString("advanceLearnedNum","0").apply();
        sp.edit().putString("beginnerFavNum","").apply();
        sp.edit().putString("beginnerLearnedNum","0").apply();
        sp.edit().putString("intermediateFavNum", "").apply();
        sp.edit().putString("intermediateLearnedNum","0").apply();

        savedBeginnerLearned = 0;
        savedIntemediateLearned = 0;
        savedAdvanceLearned = 0;
        savedIntermediateFav = new ArrayList<>();
        savedAdvanceFav = new ArrayList<>();
        savedBeginnerFav = new ArrayList<>();

        initializingSQLDatabase();
        addBeginnerWordToSQLite();
        addIntermediateWordToSQLite();
        addAdvanceWordToSQLite();

        launchMainActivity();
        
    }




    private void launchMainActivity(){

        Handler handler =new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                finish();
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));


            }
        }, 3000L);

    }



    private void initializingSQLDatabase(){

        beginnerDatabase = new BeginnerWordDatabase(this);
        advanceDatabase = new AdvancedWordDatabase(this);
        intermediateDatabase = new IntermediatewordDatabase(this);
    }

// SQL Database Initialization
//---------------------------------------------------
    private void addBeginnerWordToSQLite(){

        SharedPreferences sp = this.getSharedPreferences("com.example.shamsulkarim.vastvocabulary", Context.MODE_PRIVATE);


        if(!sp.contains("beginnerWordCount")){
            final int beginnerWordLength = getResources().getStringArray(R.array.beginner_words).length;
            sp.edit().putInt("beginnerWordCount",beginnerWordLength).apply();

            for(int i = 0; i < beginnerWordLength; i++){

                SplashScreen.beginnerDatabase.insertData(""+i,"false","false");

            }

        }
        int PREVIOUSBEGINNERCOUNT = sp.getInt("beginnerWordCount",0);
        int CURRENTBEGINNERCOUNT = getResources().getStringArray(R.array.beginner_words).length;



        if(CURRENTBEGINNERCOUNT > PREVIOUSBEGINNERCOUNT){


            for(int i = PREVIOUSBEGINNERCOUNT; i < CURRENTBEGINNERCOUNT; i++){

                SplashScreen.beginnerDatabase.insertData(""+i,"false","false");




            }
            sp.edit().putInt("beginnerWordCount",CURRENTBEGINNERCOUNT).apply();




        }else {



        }







    }

    private void addIntermediateWordToSQLite(){

        SharedPreferences sp = this.getSharedPreferences("com.example.shamsulkarim.vastvocabulary", Context.MODE_PRIVATE);

        if(!sp.contains("intermediateWordCount")){
            final int intermediateWordLength = getResources().getStringArray(R.array.intermediate_words).length;
            sp.edit().putInt("intermediateWordCount",intermediateWordLength).apply();

            for(int i = 0; i < intermediateWordLength; i++){

                SplashScreen.intermediateDatabase.insertData(""+i,"false","false");

            }

        }
        int PREVIOUSBEGINNERCOUNT = sp.getInt("intermediateWordCount",0);
        int CURRENTBEGINNERCOUNT = getResources().getStringArray(R.array.intermediate_words).length;

        if(CURRENTBEGINNERCOUNT > PREVIOUSBEGINNERCOUNT){

            for(int i = PREVIOUSBEGINNERCOUNT; i < CURRENTBEGINNERCOUNT; i++){

                SplashScreen.intermediateDatabase.insertData(""+i,"false","false");

            }
            sp.edit().putInt("intermediateWordCount",CURRENTBEGINNERCOUNT).apply();




        }else {




        }

    }

    private void addAdvanceWordToSQLite(){

        SharedPreferences sp = this.getSharedPreferences("com.example.shamsulkarim.vastvocabulary", Context.MODE_PRIVATE);


        if(!sp.contains("advanceWordCount")){
            final int advanceWordLength = getResources().getStringArray(R.array.advanced_words).length;
            sp.edit().putInt("advanceWordCount",advanceWordLength).apply();

            for(int i = 0; i < advanceWordLength; i++){

                SplashScreen.advanceDatabase.insertData(""+i,"false","false");

            }

        }
        int PREVIOUSBEGINNERCOUNT = sp.getInt("advanceWordCount",0);
        int CURRENTBEGINNERCOUNT = getResources().getStringArray(R.array.advanced_words).length;


        if(CURRENTBEGINNERCOUNT > PREVIOUSBEGINNERCOUNT){


            for(int i = PREVIOUSBEGINNERCOUNT; i < CURRENTBEGINNERCOUNT; i++){

                SplashScreen.advanceDatabase.insertData(""+i,"false","false");




            }
            sp.edit().putInt("advanceWordCount",CURRENTBEGINNERCOUNT).apply();




        }else {



        }


    }





}
