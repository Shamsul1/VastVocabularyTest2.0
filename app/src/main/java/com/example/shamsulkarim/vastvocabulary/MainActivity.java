package com.example.shamsulkarim.vastvocabulary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private ImageView fab;

    // getting database instances
    //-------------------------------

    StringBuilder beginnerFavNum, intermediateFavNum, advanceFavNum;
    int savedBeginnerLearned,  savedIntemediateLearned, savedAdvanceLearned;
    List<Integer> savedBeginnerFav, savedAdvanceFav,savedIntermediateFav;
    StringBuilder states;
    SharedPreferences sp ;

    //-------------------------------
    DatabaseReference ref;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    StringBuilder beginnerFavNumBuilder, intermediateFavNumBuilder, advanceFavNumBuilder;
    ImageView homeView,wordsView,learnedView,settingsView,favoriteView;

    public static String practice;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference();
        user = firebaseAuth.getCurrentUser();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, RegisterActivity.class));
        }

        homeView = (ImageView)findViewById(R.id.home);
        wordsView = (ImageView)findViewById(R.id.words);
        learnedView = (ImageView)findViewById(R.id.learned);
        settingsView = (ImageView)findViewById(R.id.settings);
        favoriteView = (ImageView)findViewById(R.id.favorite_home);
        beginnerFavNumBuilder = new StringBuilder();
        intermediateFavNumBuilder = new StringBuilder();
        advanceFavNumBuilder  = new StringBuilder();



        practice = "";
        fab = (ImageView)findViewById(R.id.fab_favorite);


        ImageView homeView = (ImageView)findViewById(R.id.home);

        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag,homeFragment).commit();

        homeView.setImageResource(R.drawable.ic_action_home_active);






    }
    public void onStartTrainingActivity(View view){
        Intent intent = new Intent(this,StartTrainingActivity.class);
        sp = this.getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);
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

                SettingFragment setting = new SettingFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frag,setting).commit();

                homeView.setImageResource(R.drawable.ic_action_home);
                wordsView.setImageResource(R.drawable.ic_book);
                learnedView.setImageResource(R.drawable.ic_social_school);
                settingsView.setImageResource(R.drawable.ic_action_settings_active);
                favoriteView.setImageResource(R.drawable.ic_action_favorite);
                break;


        }


    }





    private void addFavNumber(){

        Cursor beginner = SplashScreen.beginnerDatabase.getData();
        Cursor intermediate = SplashScreen.intermediateDatabase.getData();
        Cursor advance = SplashScreen.advanceDatabase.getData();

        while (beginner.moveToNext()){

            if(beginner.getString(2).equalsIgnoreCase("true")){
                beginnerFavNumBuilder.append("+"+beginner.getString(1));
            }
        }

        while (intermediate.moveToNext()){

            if(intermediate.getString(2).equalsIgnoreCase("true")){
                intermediateFavNumBuilder.append("+"+intermediate.getString(1));
            }
        }

        while (advance.moveToNext()){

            if(advance.getString(2).equalsIgnoreCase("true")){
                advanceFavNumBuilder.append("+"+advance.getString(1));
            }
        }


    }

    // Firebase
    //----------------------------------------------------------------------------------------------
    private void updateFirebase(){
        addFavNumber();
        SharedPreferences sp = this.getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);

        String advanceLearnedNum = String.valueOf(sp.getInt("advanced",0));
        String beginnerLearnedNum = String.valueOf(sp.getInt("beginner",0));
        String intermediateLearnedNum = String.valueOf(sp.getInt("intermediate",0));

        String advanceFavNumString = String.valueOf(advanceFavNumBuilder);
        String intermediateNumString = String.valueOf(intermediateFavNumBuilder);
        String beginnerNumString  = String.valueOf(beginnerFavNumBuilder);

        FavLearnedState favLearnedState = new FavLearnedState(beginnerLearnedNum,intermediateLearnedNum,advanceLearnedNum,beginnerNumString,intermediateNumString,advanceFavNumString);

        ref.child(user.getUid()).setValue(favLearnedState);

        Toast.makeText(this, "Saving states....", Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onStop() {
        super.onStop();

        updateFirebase();

    }
}

