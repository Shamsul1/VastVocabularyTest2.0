package com.example.shamsulkarim.vastvocabulary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class NoWordLeft extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_word_left);
    }



    public void home_nomorewords(View view){
        Intent intent = new Intent(this, MainActivity.class);

        this.startActivity(intent);
        this.finish();


    }


    public void reset(View view){
        SharedPreferences sp = this.getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);
        String level = sp.getString("level", "beginner");

        sp.edit().putInt(level,0).apply();

        Intent intent = new Intent(this, StartTrainingActivity.class);
        this.startActivity(intent);
        this.finish();




    }
}
