package com.example.shamsulkarim.vastvocabulary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class MainActivity extends AppCompatActivity {

    static BeginnerWordDatabase beginnerDatabase;
    static IntermediatewordDatabase intermediateDatabase;
    static AdvancedWordDatabase advanceDatabase;
    private ImageView fab;
    ImageView homeView,wordsView,learnedView,settingsView,favoriteView;

    public static String practice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeView = (ImageView)findViewById(R.id.home);
        wordsView = (ImageView)findViewById(R.id.words);
        learnedView = (ImageView)findViewById(R.id.learned);
        settingsView = (ImageView)findViewById(R.id.settings);
        favoriteView = (ImageView)findViewById(R.id.favorite_home);




        practice = "";
        fab = (ImageView)findViewById(R.id.fab_favorite);

        addBeginnerWordToSQLite();
        addIntermediateWordToSQLite();
        addAdvanceWordToSQLite();

        ImageView homeView = (ImageView)findViewById(R.id.home);

        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag,homeFragment).commit();

        homeView.setImageResource(R.drawable.ic_action_home_active);


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
;



        switch (view.getId()){

            case R.id.home:
                HomeFragment homeFragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frag,homeFragment).commit();

                homeView.setImageResource(R.drawable.ic_action_home_active);
                wordsView.setImageResource(R.drawable.ic_book);
                learnedView.setImageResource(R.drawable.ic_social_school);
                settingsView.setImageResource(R.drawable.ic_action_settings);
                favoriteView.setImageResource(R.drawable.ic_action_favorite);
                break;

            case R.id.words:
                Wordactivity wordFragment = new Wordactivity();
                getSupportFragmentManager().beginTransaction().replace(R.id.frag,wordFragment).commit();

                homeView.setImageResource(R.drawable.ic_action_home);
                wordsView.setImageResource(R.drawable.ic_book_active);
                learnedView.setImageResource(R.drawable.ic_social_school);
                settingsView.setImageResource(R.drawable.ic_action_settings);
                favoriteView.setImageResource(R.drawable.ic_action_favorite);
                break;
            case R.id.learned:
                LearnedWords learnedWords = new LearnedWords();
                getSupportFragmentManager().beginTransaction().replace(R.id.frag,learnedWords).commit();

                homeView.setImageResource(R.drawable.ic_action_home);
                wordsView.setImageResource(R.drawable.ic_book);
                learnedView.setImageResource(R.drawable.ic_social_school_active);
                settingsView.setImageResource(R.drawable.ic_action_settings);
                favoriteView.setImageResource(R.drawable.ic_action_favorite);
                break;

            case R.id.favorite_home:

                FavoriteWords favoriteWords = new FavoriteWords();
                getSupportFragmentManager().beginTransaction().replace(R.id.frag,favoriteWords).commit();

                homeView.setImageResource(R.drawable.ic_action_home);
                wordsView.setImageResource(R.drawable.ic_book);
                learnedView.setImageResource(R.drawable.ic_social_school);
                settingsView.setImageResource(R.drawable.ic_action_settings);
                favoriteView.setImageResource(R.drawable.ic_action_favorite_active);
                break;

            case R.id.settings:



                homeView.setImageResource(R.drawable.ic_action_home);
                wordsView.setImageResource(R.drawable.ic_book);
                learnedView.setImageResource(R.drawable.ic_social_school);
                settingsView.setImageResource(R.drawable.ic_action_settings_active);
                favoriteView.setImageResource(R.drawable.ic_action_favorite);
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

//    public void onFabClick(View view){
//
//        Toast.makeText(this,"OnFabClick",Toast.LENGTH_SHORT).show();
////        onFabScale();
////        onFabTransition();
//
//        fab_option1.animate().rotation(360f).setDuration(100L);
//
//
//
//    }


}
