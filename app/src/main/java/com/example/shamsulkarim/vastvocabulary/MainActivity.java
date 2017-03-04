package com.example.shamsulkarim.vastvocabulary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.shamsulkarim.vastvocabulary.WordAdapters.HomeRecyclerViewAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;


public class MainActivity extends AppCompatActivity implements BottomNavigation.OnMenuItemSelectionListener {


    private ImageView fab;

    // getting database instances
    //-------------------------------

    BottomNavigation bottomNavigation;
    SharedPreferences sp ;



    //-------------------------------
    DatabaseReference ref;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    StringBuilder beginnerFavNumBuilder, intermediateFavNumBuilder, advanceFavNumBuilder;
    ImageView homeView,wordsView,learnedView,settingsView,favoriteView, bottomBarBackground;

    public static String practice;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);






        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference();
        user = firebaseAuth.getCurrentUser();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, SignInActivity.class));
        }

        bottomNavigation = (BottomNavigation)findViewById(R.id.BottomNavigation);
        bottomNavigation.setOnMenuItemClickListener(this);

        homeView = (ImageView)findViewById(R.id.home);
        wordsView = (ImageView)findViewById(R.id.words);
        learnedView = (ImageView)findViewById(R.id.learned);
        settingsView = (ImageView)findViewById(R.id.settings);
        favoriteView = (ImageView)findViewById(R.id.favorite_home);
        bottomBarBackground = (ImageView)findViewById(R.id.bottom_bar_backgroung);
        beginnerFavNumBuilder = new StringBuilder();
        intermediateFavNumBuilder = new StringBuilder();
        advanceFavNumBuilder  = new StringBuilder();



        practice = "";
        fab = (ImageView)findViewById(R.id.fab_favorite);


        ImageView homeView = (ImageView)findViewById(R.id.home);

        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag,homeFragment).commit();



        syncSQL();





    }




    private void addFavNumber(){

        Cursor beginner = SplashScreen.beginnerDatabase.getData();
        Cursor intermediate = SplashScreen.intermediateDatabase.getData();
        Cursor advance = SplashScreen.advanceDatabase.getData();
        beginnerFavNumBuilder = new StringBuilder();
        intermediateFavNumBuilder = new StringBuilder();
        advanceFavNumBuilder = new StringBuilder();


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

        FavLearnedState favLearnedState = new FavLearnedState(sp.getString("userName","Boo Boo"),beginnerLearnedNum,intermediateLearnedNum,advanceLearnedNum,beginnerNumString,intermediateNumString,advanceFavNumString);

        ref.child(user.getUid()).setValue(favLearnedState);

        Toast.makeText(this, "Saving states....", Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onStop() {
        super.onStop();

        updateFirebase();

    }


    private void syncSQL(){

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

//                int begi = SplashScreen.savedBeginnerLearned;
//                int inte = SplashScreen.savedIntemediateLearned;
//                int adva = SplashScreen.savedAdvanceLearned;
//
//                sp.edit().putInt("beginner", begi);
//                sp.edit().putInt("intermediate", inte);
//                sp.edit().putInt("advanced", adva);





                if(SplashScreen.savedBeginnerFav.size() > 0){

                    for(int i = 0; i < SplashScreen.savedBeginnerFav.size(); i++){

                        int begi = SplashScreen.savedBeginnerFav.get(i)+1;

                        SplashScreen.beginnerDatabase.updateFav(""+begi,"True");

                    }


                }

                if(SplashScreen.savedIntermediateFav.size() > 0){

                    for(int j = 0; j < SplashScreen.savedIntermediateFav.size(); j++){

                        int inte = SplashScreen.savedIntermediateFav.get(j)+1;

                        SplashScreen.intermediateDatabase.updateFav(""+inte,"True");


                    }

                }

                if(SplashScreen.savedAdvanceFav.size() > 0){

                    for(int k = 0; k < SplashScreen.savedAdvanceFav.size(); k++){

                        int adva = SplashScreen.savedAdvanceFav.get(k)+1;
                        SplashScreen.advanceDatabase.updateFav(""+adva,"True");



                    }

                }



                Toast.makeText(getApplicationContext(), "syncing sql...", Toast.LENGTH_SHORT).show();
            }
        }, 1000L);






    }

    @Override
    public void onMenuItemSelect(@IdRes int i, int i1, boolean b) {
        Handler handler = new Handler();
                switch (i1){

            case 0:
                HomeFragment homeFragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frag,homeFragment).commit();

                break;

            case 1:


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Wordactivity wordFragment = new Wordactivity();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frag,wordFragment).commit();

                    }
                },150L);


                break;
            case 2:

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LearnedWords learnedWords = new LearnedWords();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frag,learnedWords).commit();

                    }
                },150L);

                break;

            case 3:

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FavoriteWords favoriteWords = new FavoriteWords();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frag,favoriteWords).commit();


                    }
                },150L);

                break;

            case 4:

                SettingFragment setting = new SettingFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frag,setting).commit();


                break;


        }

    }

    @Override
    public void onMenuItemReselect(@IdRes int i, int i1, boolean b) {

    }
}

