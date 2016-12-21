package com.example.shamsulkarim.vastvocabulary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class StartTrainingActivity extends AppCompatActivity {

    ImageView start_training_land;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_training);
        start_training_land = (ImageView)findViewById(R.id.start_training_landscape);
        SharedPreferences sp = this.getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);
        String level = sp.getString("level","");
        Toast.makeText(this,level,Toast.LENGTH_SHORT).show();

        if(level == "beginner"){
            start_training_land.setImageResource(R.drawable.no_back_land1);
        }
        if(level == "intermediate"){
            start_training_land.setImageResource(R.drawable.intermediate_full_land);
        }
        if(level == "advanced"){
            start_training_land.setImageResource(R.drawable.advance_full_landscape);
        }


    }


    public void onStartTraining(View view){


        Intent intent = new Intent(this, Train.class);
        this.startActivity(intent);
    }
}
