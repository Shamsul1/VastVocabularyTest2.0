package com.example.shamsulkarim.vastvocabulary;

import android.app.ProgressDialog;
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


   static FirebaseAuth firebaseAuth;
    StringBuilder states;

    DatabaseReference ref;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    StringBuilder beginnerFavNum, intermediateFavNum, advanceFavNum;
    SharedPreferences sp;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syncing_firebase_to_sql);


        sp = this.getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Syncing your progress...");
        progressDialog.show();


        states = new StringBuilder();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        getFirebase();

        delay();


//        updateSQL();



    }



    public void getFirebase(){

        ref.child(user.getUid()).addChildEventListener(new ChildEventListener() {

            int i = 0;
            String[] strData = new String[7];
            StringBuilder sb = new StringBuilder();

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                String state = dataSnapshot.getValue(String.class);

                strData[i] = state;


                if(strData[6] != null){

                    for(int j = 0; j < strData.length; j++){

                        if(j == 0){

                            sp.edit().putString("advanceFavNum",strData[0]).apply();

                        }
                        if(j == 1){

                            sp.edit().putString("advanceLearnedNum",strData[1]).apply();
                            sp.edit().putInt("advanced", Integer.parseInt(strData[1])).apply();

                        }
                        if(j == 2){

                            sp.edit().putString("beginnerFavNum",strData[2]).apply();

                        }
                        if(j == 3){

                            sp.edit().putString("beginnerLearnedNum",strData[3]).apply();
                            sp.edit().putInt("beginner", Integer.parseInt(strData[3])).apply();

                        }
                        if(j == 4){

                            sp.edit().putString("intermediateFavNum", strData[4]).apply();
                        }
                        if(j == 5){

                            sp.edit().putString("intermediateLearnedNum",strData[5]).apply();
                            sp.edit().putInt("intermediate", Integer.parseInt(strData[5])).apply();

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

        SplashScreen.savedAdvanceLearned = Integer.parseInt( sp.getString("advanceLearnedNum","0").trim());

        beginnerFavNum = new StringBuilder( sp.getString("beginnerFavNum","0"));

        SplashScreen.savedBeginnerLearned = Integer.parseInt( sp.getString("beginnerLearnedNum","0").trim());

        intermediateFavNum = new StringBuilder(sp.getString("intermediateFavNum","0"));

        SplashScreen.savedIntemediateLearned = Integer.parseInt(sp.getString("intermediateLearnedNum","0").trim());



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


        Toast.makeText(this,"beginner fav: "+SplashScreen.savedBeginnerFav,Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"beginner learned: "+SplashScreen.savedBeginnerLearned,Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"intermediate fav: "+SplashScreen.savedIntermediateFav,Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"intermediate learned: "+SplashScreen.savedIntemediateLearned,Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"advance fav: "+SplashScreen.savedAdvanceFav,Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"advance learned: "+SplashScreen.savedAdvanceLearned,Toast.LENGTH_SHORT).show();


    }

    private void addingBuilderToNums(){


        SplashScreen.savedAdvanceFav.clear();
        SplashScreen.savedBeginnerFav.clear();
        SplashScreen.savedIntermediateFav.clear();

        SplashScreen.savedBeginnerFav =  builderToNums(beginnerFavNum);
////        savedBeginnerLearned = builderToNums(beginnerLearnedNum);
        SplashScreen.savedIntermediateFav = builderToNums(intermediateFavNum);
////        savedIntemediateLearned = builderToNums(intermediateLearnedNum);
        SplashScreen.savedAdvanceFav  = builderToNums(advanceFavNum);
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
        }, 10000L);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }, 10000L);





    }

//    private void updateSQL(){
//
//        for(int i = 0; i < savedBeginnerFav.size(); i++){
//
//            SplashScreen.beginnerDatabase.updateFav(savedBeginnerFav.get(i)+"", "true");
//
//
//        }
//
//        Toast.makeText(this, "sql updated",Toast.LENGTH_SHORT).show();
//
//
//
//    }
}
