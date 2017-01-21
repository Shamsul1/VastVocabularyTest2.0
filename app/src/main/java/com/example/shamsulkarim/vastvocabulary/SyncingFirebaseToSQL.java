package com.example.shamsulkarim.vastvocabulary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class SyncingFirebaseToSQL extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    StringBuilder states;
    List<Integer> savedBeginnerFav, savedAdvanceFav,savedIntermediateFav;
    int savedBeginnerLearned,  savedIntemediateLearned, savedAdvanceLearned;
    DatabaseReference ref;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    StringBuilder beginnerFavNum, intermediateFavNum, advanceFavNum;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syncing_firebase_to_sql);


        sp = this.getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);

        savedIntermediateFav = new ArrayList<>();
        savedAdvanceFav = new ArrayList<>();
        savedBeginnerFav = new ArrayList<>();

        states = new StringBuilder();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        getFirebase();

        delay();


        updateSQL();
        startActivity(new Intent(this, MainActivity.class));
        finish();


    }



    public void getFirebase(){

        ref.child(user.getUid()).addChildEventListener(new ChildEventListener() {

            int i = 0;
            String[] strData = new String[6];
            StringBuilder sb = new StringBuilder();

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                String state = dataSnapshot.getValue(String.class);

                strData[i] = state;


                if(strData[5] != null){

                    for(int j = 0; j < strData.length; j++){

                        if(j == 0){

                            sp.edit().putString("advanceFavNum",strData[0]).apply();

                        }
                        if(j == 1){

                            sp.edit().putString("advanceLearnedNum",strData[1]).apply();

                        }
                        if(j == 2){

                            sp.edit().putString("beginnerFavNum",strData[2]).apply();

                        }
                        if(j == 3){

                            sp.edit().putString("beginnerLearnedNum",strData[3]).apply();

                        }
                        if(j == 4){

                            sp.edit().putString("intermediateFavNum", strData[4]).apply();
                        }
                        if(j == 5){

                            sp.edit().putString("intermediateLearnedNum",strData[5]).apply();

                        }

                    }

                }
                i++;


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void gettingNumsFromSharedPreference(){



        advanceFavNum = new StringBuilder(sp.getString("advanceFavNum","0"));

        savedAdvanceLearned = Integer.parseInt( sp.getString("advanceLearnedNum","0").trim());

        beginnerFavNum = new StringBuilder( sp.getString("beginnerFavNum","0"));

        savedBeginnerLearned = Integer.parseInt( sp.getString("beginnerLearnedNum","0").trim());

        intermediateFavNum = new StringBuilder(sp.getString("intermediateFavNum","0"));

        savedIntemediateLearned = Integer.parseInt(sp.getString("intermediateLearnedNum","0").trim());




    }


    private List<Integer> builderToNums(StringBuilder numBuilder){
        List<Integer> backToNums = new ArrayList<>();
        int plusCount = 0;
        int plusI = 0;

        for(int i = 0; i < numBuilder.length(); i++){

            if(numBuilder.charAt(i) == '+'){

                plusCount++;
            }
        }

        int plusPosition[] = new int[plusCount];

        for(int j = 0; j < numBuilder.length(); j++){

            if(numBuilder.charAt(j) == '+'){
                plusPosition[plusI] = j;

                plusI++;

            }

        }

        for( int k = 0; k < plusPosition.length-1; k++){


            if(numBuilder.charAt(plusPosition[k]) == '+'){

                String strNum = numBuilder.substring(plusPosition[k]+1, plusPosition[k+1]);
                backToNums.add(Integer.parseInt(strNum));
            }
        }


        if( numBuilder.length() > 0 && numBuilder != null){

            String lastNum = numBuilder.substring(plusPosition[plusCount-1]+1, numBuilder.length());
            backToNums.add(Integer.parseInt(lastNum));

        }

        return backToNums;
    }

    private void printSavedNums(){


        Toast.makeText(this,"beginner fav: "+savedBeginnerFav,Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"beginner learned: "+savedBeginnerLearned,Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"intermediate fav: "+savedIntermediateFav,Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"intermediate learned: "+savedIntemediateLearned,Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"advance fav: "+savedAdvanceFav,Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"advance learned: "+savedAdvanceLearned,Toast.LENGTH_SHORT).show();


    }

    private void addingBuilderToNums(){


        savedAdvanceFav.clear();
        savedBeginnerFav.clear();
        savedIntermediateFav.clear();

        savedBeginnerFav =  builderToNums(beginnerFavNum);
////        savedBeginnerLearned = builderToNums(beginnerLearnedNum);
        savedIntermediateFav = builderToNums(intermediateFavNum);
////        savedIntemediateLearned = builderToNums(intermediateLearnedNum);
        savedAdvanceFav  = builderToNums(advanceFavNum);
//       savedAdvanceLearned = builderToNums(advanceLearnedNum);

    }

    private void delay(){

        android.os.Handler handler = new android.os.Handler();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gettingNumsFromSharedPreference();
                addingBuilderToNums();
                printSavedNums();
            }
        }, 1000L);






    }

    private void updateSQL(){

//        for(int i = 0; i < state.length; i++){
//
//            SplashScreen.beginnerDatabase.updateFav(state[i]+"", "true");
//
//
//        }



    }
}
