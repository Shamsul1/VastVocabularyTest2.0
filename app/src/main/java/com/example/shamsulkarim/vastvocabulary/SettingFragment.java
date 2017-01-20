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
        advanceLearnedNum = new StringBuilder( sp.getString("advanceLearnedNum","kkk"));
        beginnerFavNum = new StringBuilder( sp.getString("beginnerFavNum","kkk"));
        beginnerLearnedNum = new StringBuilder( sp.getString("beginnerLearnedNum","kkk"));
        intermediateFavNum = new StringBuilder(sp.getString("intermediateFavNum","kkk"));
        intermediateLearnedNum = new StringBuilder(sp.getString("intermediateLearnedNum","kkk"));

        Toast.makeText(getContext(),""+beginnerFavNum+ beginnerLearnedNum+ intermediateFavNum+ intermediateLearnedNum+ advanceFavNum+advanceLearnedNum,Toast.LENGTH_SHORT).show();

    }




}
