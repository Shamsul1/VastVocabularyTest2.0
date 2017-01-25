package com.example.shamsulkarim.vastvocabulary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{



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



        if(!SplashScreen.sp.contains("language")){


            SplashScreen.sp.edit().putInt("language",-1);


        }

        languageInfo = new Language();
        language = (TextView)findViewById(R.id.languageName);
        location = (TextView)findViewById(R.id.locationName);
        speakers = (TextView)findViewById(R.id.speakersCount);
        noLanguage = (TextView)findViewById(R.id.noLanguage);
        languageIcon = (ImageView)findViewById(R.id.languageIcon);
        locationIcon = (ImageView)findViewById(R.id.locationIcon);
        speakersIcon = (ImageView)findViewById(R.id.speakersIcon);




        recyclerView = (RecyclerView)findViewById(R.id.recycler_view_setting);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new ChooseLanguageAdapter(this, language,location,speakers,noLanguage, languageIcon,locationIcon,speakersIcon,languageInfo.getLanguages());
        recyclerView.setAdapter(adapter);



    }

    @Override
    public void onClick(View view) {



    }
}
