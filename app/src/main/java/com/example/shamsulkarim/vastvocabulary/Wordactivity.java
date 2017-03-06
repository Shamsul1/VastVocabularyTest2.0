package com.example.shamsulkarim.vastvocabulary;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Spinner;
import android.widget.Toast;

import com.example.shamsulkarim.vastvocabulary.WordAdapters.IntermediateAdapter;
import com.example.shamsulkarim.vastvocabulary.WordAdapters.WordRecyclerViewAdapter;
import com.example.shamsulkarim.vastvocabulary.WordAdapters.advanceAdapter;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;


public class Wordactivity extends Fragment {

MaterialSpinner spinner;


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Word> words = new ArrayList<>();
    Toolbar toolbar;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_wordactivity,container,false);

//        beginnerWordInitialization();


        toolbar = (Toolbar)v.findViewById(R.id.word_toolbar);
        toolbar.setTitle("Words");
        toolbar.setTitleTextColor(Color.parseColor("#673AB7"));
        setHasOptionsMenu(true);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);



//
//        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view_word);
//        layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setHasFixedSize(true);
//        adapter = new WordRecyclerViewAdapter(getContext(), words);
//        recyclerView.setAdapter(adapter);

        return v;
    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.learned_toolbar_menus,menu);

        MenuItem item = menu.findItem(R.id.spinner_learned);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);





        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(getContext(),"i: "+i+" l: "+l,Toast.LENGTH_SHORT).show();



                if(i == 0){


                    beginnerWordInitialization2(getView());
                }
                if(i == 1){


                    intermediateWordInitialization(getView());
                }

                if(i == 2){

                    advanceWordInitialization(getView());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






    }




    private void beginnerWordInitialization2(View v){

        words.clear();

        String[] extraArray = new String[getResources().getStringArray(R.array.beginner_words).length];

        if(SplashScreen.languageId == 0){

            for(int i = 0; i < getResources().getStringArray(R.array.beginner_words).length; i++){


                extraArray[i] = "";
            }

        }

        if(SplashScreen.languageId == 1){

            extraArray = SplashScreen.beginnerSpanish;
        }
        if(SplashScreen.languageId == 2){

            extraArray = SplashScreen.beginnerBengali;
        }
        if(SplashScreen.languageId == 3){

            extraArray = SplashScreen.beginnerHindi;
        }

        int len = SplashScreen.beginnerWordArray.length;

        for(int i = 0 ; i < len; i++){

            words.add(new Word(SplashScreen.beginnerWordArray[i],SplashScreen.beginnerTranslationArray[i],extraArray[i], SplashScreen.beginnerPronunciationArray[i],SplashScreen.beginnerGrammarArray[i],SplashScreen.beginnerExampleArray1[i],"beginner",0));

        }
        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view_word);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new WordRecyclerViewAdapter(getContext(), words);
        recyclerView.setAdapter(adapter);



    }

    private void intermediateWordInitialization(View v){
        words.clear();


        String[] extraArray = new String[getResources().getStringArray(R.array.beginner_words).length];

        if(SplashScreen.languageId == 0){

            for(int i = 0; i < getResources().getStringArray(R.array.beginner_words).length; i++){


                extraArray[i] = "";
            }

        }

        if(SplashScreen.languageId == 1){

            extraArray = SplashScreen.intermediateSpanish;
        }
        if(SplashScreen.languageId == 2){

            extraArray = SplashScreen.intermediateBengali;
        }
        if(SplashScreen.languageId == 3){

            extraArray = SplashScreen.intermediateHindi;
        }




        for(int i = 0 ; i < SplashScreen.intermediateWordArray.length; i++){

            words.add(new Word(SplashScreen.intermediateWordArray[i],SplashScreen.intermediateTranslationArray[i],extraArray[i],SplashScreen.intermediatePronunciationArray[i],SplashScreen.intermediateGrammarArray[i],SplashScreen.intermediateExampleArray1[i],"intermediate",0));

        }


        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view_word);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new IntermediateAdapter(getContext(), words);
        recyclerView.setAdapter(adapter);


    }

    private void advanceWordInitialization(View v){

        words.clear();


        String[] extraArray = new String[getResources().getStringArray(R.array.beginner_words).length];

        if(SplashScreen.languageId == 0){

            for(int i = 0; i < getResources().getStringArray(R.array.beginner_words).length; i++){


                extraArray[i] = "";
            }

        }

        if(SplashScreen.languageId == 1){

            extraArray = SplashScreen.advanceSpanish;
        }
        if(SplashScreen.languageId == 2){

            extraArray = SplashScreen.advanceBengali;
        }
        if(SplashScreen.languageId == 3){

            extraArray = SplashScreen.advanceHindi;
        }






        for(int i = 0 ; i < SplashScreen.advanceWordArray.length; i++){

            words.add(new Word(SplashScreen.advanceWordArray[i],SplashScreen.advanceTranslationArray[i],extraArray[i],SplashScreen.advancePronunciationArray[i], SplashScreen.advanceGrammarArray[i],SplashScreen.advanceExampleArray1[i],"advance",0));

        }


        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view_word);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new advanceAdapter(getContext(), words);
        recyclerView.setAdapter(adapter);

    }



}
