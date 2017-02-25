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
    public static int languageId;
    public static String languageName[] = {"","spanish","bengali","hindi"};


    //word resource variables
    public static String[] advanceWordArray,advanceTranslationArray,advanceGrammarArray,advancePronunciationArray,advanceExampleArray1,advanceExampleArray2,advanceExampleArray3;
    public static String[] beginnerWordArray,beginnerTranslationArray,beginnerGrammarArray,beginnerPronunciationArray,beginnerExampleArray1,beginnerExampleArray2,beginnerExampleArray3;
    public static String[] intermediateWordArray,intermediateTranslationArray,intermediateGrammarArray,intermediatePronunciationArray,intermediateExampleArray1,intermediateExampleArray2,intermediateExampleArray3;
    public static String[] beginnerSpanish, beginnerBengali,beginnerHindi;
    public static String[] intermediateSpanish, intermediateBengali,intermediateHindi;
    public static String[] advanceSpanish, advanceBengali,advanceHindi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        advanceWordArray = getResources().getStringArray(R.array.advanced_words);
        advanceTranslationArray = getResources().getStringArray(R.array.advanced_translation);
        advanceGrammarArray = getResources().getStringArray(R.array.advanced_grammar);
        advancePronunciationArray = getResources().getStringArray(R.array.advanced_pronunciation);
        advanceExampleArray1 = getResources().getStringArray(R.array.advanced_example1);
        advanceExampleArray2 = getResources().getStringArray(R.array.advanced_example2);
        advanceExampleArray3 = getResources().getStringArray(R.array.advanced_example3);

        beginnerWordArray = getResources().getStringArray(R.array.beginner_words);
        beginnerTranslationArray = getResources().getStringArray(R.array.beginner_translation);
        beginnerGrammarArray = getResources().getStringArray(R.array.beginner_grammar);
        beginnerPronunciationArray = getResources().getStringArray(R.array.beginner_pronunciation);
        beginnerExampleArray1 = getResources().getStringArray(R.array.beginner_example1);
        beginnerExampleArray2 = getResources().getStringArray(R.array.beginner_example2);
        beginnerExampleArray3 = getResources().getStringArray(R.array.beginner_example3);

        intermediateWordArray = getResources().getStringArray(R.array.intermediate_words);
        intermediateTranslationArray = getResources().getStringArray(R.array.intermediate_translation);
        intermediateGrammarArray = getResources().getStringArray(R.array.intermediate_grammar);
        intermediatePronunciationArray = getResources().getStringArray(R.array.intermediate_pronunciation);
        intermediateExampleArray1 = getResources().getStringArray(R.array.intermediate_example1);
        intermediateExampleArray2 = getResources().getStringArray(R.array.intermediate_example2);
        intermediateExampleArray3 = getResources().getStringArray(R.array.intermediate_example3);

        beginnerSpanish = getResources().getStringArray(R.array.beginner_spanish);
        beginnerBengali = getResources().getStringArray(R.array.beginner_bengali);
        beginnerHindi = getResources().getStringArray(R.array.beginner_hindi);

        intermediateSpanish = getResources().getStringArray(R.array.intermediate_spanish);
        intermediateBengali = getResources().getStringArray(R.array.intermediate_bengali);
        intermediateHindi = getResources().getStringArray(R.array.intermediate_hindi);

        advanceSpanish = getResources().getStringArray(R.array.advance_spanish);
        advanceBengali = getResources().getStringArray(R.array.advance_bengali);
        advanceHindi = getResources().getStringArray(R.array.advance_hindi);


        sp = this.getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);
        sp.edit().putString("advanceFavNum","").apply();
        sp.edit().putString("advanceLearnedNum","0").apply();
        sp.edit().putString("beginnerFavNum","").apply();
        sp.edit().putString("beginnerLearnedNum","0").apply();
        sp.edit().putString("intermediateFavNum", "").apply();
        sp.edit().putString("intermediateLearnedNum","0").apply();

        if(!SplashScreen.sp.contains("language")){


            SplashScreen.sp.edit().putInt("language",-1).apply();


        }
        languageId = sp.getInt("language",0);

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
        }, 1500L);

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
