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
import com.google.firebase.database.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Handler;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment implements View.OnClickListener {

    TextView userName, totalLearned, totalWord, totalFavorite;
    SharedPreferences sp;
    int totalFavCount;


    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_setting, container, false);




        sp = getContext().getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);
        userName = (TextView)v.findViewById(R.id.userNameProfile);
        totalLearned = (TextView)v.findViewById(R.id.total_learned_count_view);
        totalFavorite = (TextView)v.findViewById(R.id.total_favorite_count_view);
        totalWord = (TextView)v.findViewById(R.id.total_word_count_view);

        int totalWordCount = getResources().getStringArray(R.array.beginner_words).length+getResources().getStringArray(R.array.intermediate_words).length+getResources().getStringArray(R.array.advanced_words).length;
        int totalLearnedCount = sp.getInt("beginner",0)+sp.getInt("intermediate",0)+sp.getInt("advanced",0);


        int totalFavCount = getFavCount();
        userName.setText(sp.getString("userName", "Boo Boo"));


        totalWord.setText(""+totalWordCount);
        totalLearned.setText(""+totalLearnedCount);
        totalFavorite.setText(""+totalFavCount);

        return v;

        //------------------------------------------------------------------------------------------------------
    }

    @Override
    public void onClick(View view) {


    }


    private int getFavCount() {
        int count = 0;


        Cursor res = SplashScreen.advanceDatabase.getData();
        Cursor res1 = SplashScreen.beginnerDatabase.getData();
        Cursor res2 = SplashScreen.advanceDatabase.getData();


        while (res.moveToNext()) {

            if (res.getString(2).equalsIgnoreCase("true")) {

                count++;
            }


        }

        while (res1.moveToNext()) {

            if (res1.getString(2).equalsIgnoreCase("true")) {

                count++;
            }


        }

        while (res2.moveToNext()) {

            if (res2.getString(2).equalsIgnoreCase("true")) {

                count++;
            }




        }

        return count;
    }

}
