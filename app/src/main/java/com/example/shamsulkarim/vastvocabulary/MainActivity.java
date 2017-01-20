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

    static BeginnerWordDatabase beginnerDatabase;
    static IntermediatewordDatabase intermediateDatabase;
    static AdvancedWordDatabase advanceDatabase;
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

        addBeginnerWordToSQLite();
        addIntermediateWordToSQLite();
        addAdvanceWordToSQLite();

        ImageView homeView = (ImageView)findViewById(R.id.home);

        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag,homeFragment).commit();

        homeView.setImageResource(R.drawable.ic_action_home_active);



        // getting data from database initializtion
        //---------------------------------------------------------------------
//        getFirebase();
//        gettingNumsFromSharedPreference();
//
//        savedBeginnerFav =  builderToNums(beginnerFavNum);
//        savedIntermediateFav = builderToNums(intermediateFavNum);
//        savedAdvanceFav  = builderToNums(advanceFavNum);
//
//        printSavedNums();


        //---------------------------------------------------------------------






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

    private void addFavNumber(){

        Cursor beginner = MainActivity.beginnerDatabase.getData();
        Cursor intermediate = MainActivity.intermediateDatabase.getData();
        Cursor advance = MainActivity.advanceDatabase.getData();

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

//    public void getFirebase(){
//
//        ref.child(user.getUid()).addChildEventListener(new ChildEventListener() {
//
//            int i = 0;
//            String[] strData = new String[6];
//            StringBuilder sb = new StringBuilder();
//
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//
//                String state = dataSnapshot.getValue(String.class);
//
//                strData[i] = state;
//
//
//                if(strData[5] != null){
//
//                    for(int j = 0; j < strData.length; j++){
//
//                        if(j == 0){
//
//                            sp.edit().putString("advanceFavNum",strData[0]).apply();
//
//                        }
//                        if(j == 1){
//
//                            sp.edit().putString("advanceLearnedNum",strData[1]).apply();
//
//                        }
//                        if(j == 2){
//
//                            sp.edit().putString("beginnerFavNum",strData[2]).apply();
//
//                        }
//                        if(j == 3){
//
//                            sp.edit().putString("beginnerLearnedNum",strData[3]).apply();
//
//                        }
//                        if(j == 4){
//
//                            sp.edit().putString("intermediateFavNum", strData[4]).apply();
//                        }
//                        if(j == 5){
//
//                            sp.edit().putString("intermediateLearnedNum",strData[5]).apply();
//
//                        }
//
//                    }
//
//                }
//                i++;
//
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//    }
//
//    private void gettingNumsFromSharedPreference(){
//
//        advanceFavNum = new StringBuilder(sp.getString("advanceFavNum","kkk"));
//
//        savedAdvanceLearned = Integer.parseInt( sp.getString("advanceLearnedNum","kkk").trim());
//
//        beginnerFavNum = new StringBuilder( sp.getString("beginnerFavNum","kkk"));
//
//        savedBeginnerLearned = Integer.parseInt( sp.getString("beginnerLearnedNum","kkk").trim());
//
//        intermediateFavNum = new StringBuilder(sp.getString("intermediateFavNum","kkk"));
//
//        savedIntemediateLearned = Integer.parseInt(sp.getString("intermediateLearnedNum","kkk").trim());
//
//    }
//
//    private List<Integer> builderToNums(StringBuilder numBuilder){
//        List<Integer> backToNums = new ArrayList<>();
//        int plusCount = 0;
//        int plusI = 0;
//
//        for(int i = 0; i < numBuilder.length(); i++){
//
//            if(numBuilder.charAt(i) == '+'){
//
//                plusCount++;
//            }
//        }
//
//        int plusPosition[] = new int[plusCount];
//
//        for(int j = 0; j < numBuilder.length(); j++){
//
//            if(numBuilder.charAt(j) == '+'){
//                plusPosition[plusI] = j;
//
//                plusI++;
//
//            }
//
//        }
//
//        for( int k = 0; k < plusPosition.length-1; k++){
//
//
//            if(numBuilder.charAt(plusPosition[k]) == '+'){
//
//                String strNum = numBuilder.substring(plusPosition[k]+1, plusPosition[k+1]);
//                backToNums.add(Integer.parseInt(strNum));
//            }
//        }
//
//
//        if( numBuilder.length() > 0 && numBuilder != null){
//
//            String lastNum = numBuilder.substring(plusPosition[plusCount-1]+1, numBuilder.length());
//            backToNums.add(Integer.parseInt(lastNum));
//
//        }
//
//        return backToNums;
//    }
//
//    private void printSavedNums(){
//
//        Toast.makeText(this,"beginner fav: "+savedBeginnerFav,Toast.LENGTH_SHORT).show();
//        Toast.makeText(this,"beginner learned: "+savedBeginnerLearned,Toast.LENGTH_SHORT).show();
//        Toast.makeText(this,"intermediate fav: "+savedIntermediateFav,Toast.LENGTH_SHORT).show();
//        Toast.makeText(this,"intermediate learned: "+savedIntemediateLearned,Toast.LENGTH_SHORT).show();
//        Toast.makeText(this,"advance fav: "+savedAdvanceFav,Toast.LENGTH_SHORT).show();
//        Toast.makeText(this,"advance learned: "+savedAdvanceLearned,Toast.LENGTH_SHORT).show();
//
//
//    }
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onStop() {
        super.onStop();

        updateFirebase();

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



