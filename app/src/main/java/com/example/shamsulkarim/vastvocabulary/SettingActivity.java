package com.example.shamsulkarim.vastvocabulary;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);







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

        if(SplashScreen.sp.contains("language")){

            int i = SplashScreen.sp.getInt("language",0);


            if(i == 0){

                noLanguage.setText("No languages picked");
                languageIcon.setImageResource(0);
                locationIcon.setImageResource(0);
                speakersIcon.setImageResource(0);
            }else {

                languageIcon.setImageResource(R.drawable.language);
                locationIcon.setImageResource(R.drawable.location);
                speakersIcon.setImageResource(R.drawable.speakers);

                Toast.makeText(this, ""+i, Toast.LENGTH_SHORT).show();
                language.setText(langInfo.get(i).getName());
                location.setText(langInfo.get(i).getLocation());
                speakers.setText(langInfo.get(i).getSpeakers());
                noLanguage.setText("");
            }



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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_toolbar_menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()== R.id.setting_logout){

            SyncingFirebaseToSQL.firebaseAuth.signOut();
            startActivity(new Intent(this,SignInActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
