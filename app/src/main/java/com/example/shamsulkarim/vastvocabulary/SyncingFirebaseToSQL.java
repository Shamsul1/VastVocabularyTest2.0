package com.example.shamsulkarim.vastvocabulary;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SyncingFirebaseToSQL extends AppCompatActivity {


    String[] state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syncing_firebase_to_sql);

        state = new String[3];
        state[0] = "3";
        state[1] = "5";
        state[2]  ="1";

        updateSQL();

        startActivity(new Intent(this, MainActivity.class));
        finish();


    }




    private void updateSQL(){

        for(int i = 0; i < state.length; i++){

            SplashScreen.beginnerDatabase.updateFav(state[i]+"", "true");


        }



    }
}
