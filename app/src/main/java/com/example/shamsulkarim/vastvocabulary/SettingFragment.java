package com.example.shamsulkarim.vastvocabulary;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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
import java.util.Set;
import java.util.TreeSet;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment implements View.OnClickListener {

    TextView userName;
    Button singOut;
    FirebaseAuth firebaseAuth;
    StringBuilder states;
    List<Integer>  savedBeginnerFav, savedAdvanceFav,savedIntermediateFav;
    int savedBeginnerLearned,  savedIntemediateLearned, savedAdvanceLearned;
    DatabaseReference ref;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    StringBuilder beginnerFavNum, beginnerLearnedNum, intermediateFavNum, intermediateLearnedNum, advanceFavNum, advanceLearnedNum;
    SharedPreferences sp ;


    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_setting,container,false);


         sp = getContext().getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);
        states = new StringBuilder();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        userName = (TextView)v.findViewById(R.id.userName);
        singOut = (Button)v.findViewById(R.id.signOut);
        userName.setText(user.getEmail());

        singOut.setOnClickListener(this);
        getFirebase();
        gettingNumsFromSharedPreference();

        savedBeginnerFav =  builderToNums(beginnerFavNum);
//        savedBeginnerLearned = builderToNums(beginnerLearnedNum);
        savedIntermediateFav = builderToNums(intermediateFavNum);
//        savedIntemediateLearned = builderToNums(intermediateLearnedNum);
        savedAdvanceFav  = builderToNums(advanceFavNum);
//        savedAdvanceLearned = builderToNums(advanceLearnedNum);


        printSavedNums();


        return v;

        //------------------------------------------------------------------------------------------------------
    }

    @Override
    public void onClick(View view) {


        if(singOut == view){

            firebaseAuth.signOut();

            this.startActivity(new Intent(getContext(), SignInActivity.class));
            getActivity().finish();
        }

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
//                sp.edit().putString("strData",str).apply();

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

        advanceFavNum = new StringBuilder(sp.getString("advanceFavNum","kkk"));

        savedAdvanceLearned = Integer.parseInt( sp.getString("advanceLearnedNum","kkk").trim());

        beginnerFavNum = new StringBuilder( sp.getString("beginnerFavNum","kkk"));

        savedBeginnerLearned = Integer.parseInt( sp.getString("beginnerLearnedNum","kkk").trim());

        intermediateFavNum = new StringBuilder(sp.getString("intermediateFavNum","kkk"));

        savedIntemediateLearned = Integer.parseInt(sp.getString("intermediateLearnedNum","kkk").trim());

        Toast.makeText(getContext(),""+beginnerFavNum+ beginnerLearnedNum+ intermediateFavNum+ intermediateLearnedNum+ advanceFavNum+advanceLearnedNum,Toast.LENGTH_SHORT).show();

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

        Toast.makeText(getContext(),"beginner fav: "+savedBeginnerFav,Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(),"beginner learned: "+savedBeginnerLearned,Toast.LENGTH_SHORT);
        Toast.makeText(getContext(),"intermediate fav: "+savedIntermediateFav,Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(),"intermediate learned: "+savedIntemediateLearned,Toast.LENGTH_SHORT);
        Toast.makeText(getContext(),"advance fav: "+savedAdvanceFav,Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(),"advance learned: "+savedAdvanceLearned,Toast.LENGTH_SHORT);


    }


}
