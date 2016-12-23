package com.example.shamsulkarim.vastvocabulary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class StartTrainingActivity extends AppCompatActivity {

    private TextView title,learned,left,words;
    private ImageView sun;

    ImageView start_training_land;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_training);

        start_training_land = (ImageView)findViewById(R.id.start_training_landscape);
        title = (TextView)findViewById(R.id.title);
        words = (TextView)findViewById(R.id.words_train);
        learned = (TextView)findViewById(R.id.words_learned_train);
        left = (TextView)findViewById(R.id.words_left_train);
        sun = (ImageView)findViewById(R.id.sun);

        sun.animate().translationY(-100f).setDuration(1000);


        SharedPreferences sp = this.getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);
        String level = sp.getString("level","");
        Toast.makeText(this,level,Toast.LENGTH_SHORT).show();

        if(level == "beginner"){
            start_training_land.setImageResource(R.drawable.no_back_land1);
            title.setText("BEGINNER");
        }
        if(level == "intermediate"){
            start_training_land.setImageResource(R.drawable.intermediate_full_land);
            title.setText("INTERMEDIATE");
        }
        if(level == "advanced"){
            start_training_land.setImageResource(R.drawable.advance_full_landscape);
            title.setText("ADVANCED");
        }


    }


    public void onStartTraining(View view){


        Intent intent = new Intent(this, Train.class);
        this.startActivity(intent);
    }
}
