package com.example.shamsulkarim.vastvocabulary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.transition.Fade;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class SettingActivity extends AppCompatActivity{



    Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager layoutManager;
    TextView language, location, speakers, noLanguage;
    Language languageInfo;
    ImageView languageIcon, locationIcon, speakersIcon;
    Spinner words_per_session_spinner, repeat_per_session_spinner;
    SharedPreferences sp;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        sp = this.getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);

        Toast.makeText(getApplicationContext(),""+SplashScreen.wordsPerSession, Toast.LENGTH_SHORT).show();



        words_per_session_spinner = (Spinner) findViewById(R.id.words_per_session_spinner);
        repeat_per_session_spinner = (Spinner) findViewById(R.id.repeat_word_per_session_spinner);

        if(SplashScreen.repeatationPerSession == 5){


            repeat_per_session_spinner.setSelection(0);
        }
         else if(SplashScreen.repeatationPerSession == 4){


            repeat_per_session_spinner.setSelection(1);
        }else {

            repeat_per_session_spinner.setSelection(2);

        }

        if(SplashScreen.wordsPerSession == 5){


            words_per_session_spinner.setSelection(0);
        }
        else if(SplashScreen.wordsPerSession == 4){


            words_per_session_spinner.setSelection(1);
        }else {

            words_per_session_spinner.setSelection(2);

        }




        words_per_session_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                Toast.makeText(getApplicationContext(), i+"",Toast.LENGTH_LONG).show();

                if(i == 0){


                    SplashScreen.wordsPerSession = 5;
                    sp.edit().putInt("wordsPerSession",SplashScreen.wordsPerSession).apply();
                    Toast.makeText(getApplicationContext(),""+SplashScreen.wordsPerSession, Toast.LENGTH_SHORT).show();

                }if( i == 1){


                    SplashScreen.wordsPerSession = 4;

                    sp.edit().putInt("wordsPerSession",SplashScreen.wordsPerSession).apply();
                }
                if(i == 2) {


                    SplashScreen.wordsPerSession = 3;
                    sp.edit().putInt("wordsPerSession",SplashScreen.wordsPerSession).apply();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        repeat_per_session_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                Toast.makeText(getApplicationContext(), i+"",Toast.LENGTH_LONG).show();

                if(i == 0){


                    SplashScreen.repeatationPerSession = 5;
                    sp.edit().putInt("repeatationPerSession",SplashScreen.repeatationPerSession).apply();
                    Toast.makeText(getApplicationContext(),"Repeatation: "+SplashScreen.repeatationPerSession, Toast.LENGTH_SHORT).show();

                }if( i == 1){


                    SplashScreen.repeatationPerSession = 4;
                    sp.edit().putInt("repeatationPerSession",SplashScreen.repeatationPerSession).apply();
                }if( i == 2){


                    SplashScreen.repeatationPerSession = 3;
                    sp.edit().putInt("repeatationPerSession",SplashScreen.repeatationPerSession).apply();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






        toolbar = (Toolbar)findViewById(R.id.setting_toolbar);
        toolbar.setTitle("Settings");

        toolbar.setTitleTextColor(Color.parseColor("#673AB7"));
        setSupportActionBar(toolbar);
        languageInfo = new Language();
        List<Language> langInfo = languageInfo.getLanguages();
        language = (TextView)findViewById(R.id.languageName);
        location = (TextView)findViewById(R.id.locationName);
        speakers = (TextView)findViewById(R.id.speakersCount);
        noLanguage = (TextView)findViewById(R.id.noLanguage);

        languageIcon = (ImageView)findViewById(R.id.languageIcon);
        locationIcon = (ImageView)findViewById(R.id.locationIcon);
        speakersIcon = (ImageView)findViewById(R.id.speakersIcon);

        if(sp.contains("language")){

            int i = sp.getInt("language",0);




                languageIcon.setImageResource(R.drawable.ic_language);
                locationIcon.setImageResource(R.drawable.ic_location);
                speakersIcon.setImageResource(R.drawable.ic_speakers);

                Toast.makeText(this, ""+i, Toast.LENGTH_SHORT).show();
                language.setText(langInfo.get(i).getName());
                location.setText(langInfo.get(i).getLocation());
                speakers.setText(langInfo.get(i).getSpeakers());
                noLanguage.setText("");




        }



        recyclerView = (RecyclerView)findViewById(R.id.recycler_view_setting);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new ChooseLanguageAdapter(this, language,location,speakers,noLanguage, languageIcon,locationIcon,speakersIcon,langInfo);
        recyclerView.setAdapter(adapter);

    }

    public void settingOnSave(View view){

        finish();
        startActivity(new Intent(this,MainActivity.class));

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.setting_toolbar_menus, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        if(item.getItemId()== R.id.setting_logout){
//
//            SyncingFirebaseToSQL.firebaseAuth.signOut();
//            startActivity(new Intent(this,SignInActivity.class));
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
