package com.example.shamsulkarim.vastvocabulary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartTrainingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_training);
    }


    public void onStartTraining(View view){

        Intent intent = new Intent(this, Train.class);
        this.startActivity(intent);
    }
}
