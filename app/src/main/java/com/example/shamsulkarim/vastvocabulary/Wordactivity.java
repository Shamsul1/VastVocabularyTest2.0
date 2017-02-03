package com.example.shamsulkarim.vastvocabulary;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

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



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_wordactivity,container,false);

        spinnerInitializatin(v);
        beginnerWordInitialization();


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (adapterView.getSelectedItemPosition()){

                    case 1:
                        beginnerWordInitialization2(getView());

                        break;
                    case 2:
                        intermediateWordInitialization(getView());

                        break;
                    case 3:
                        advanceWordInitialization(getView());
                        break;
                    default:
                        beginnerWordInitialization2(getView());
                        break;

                }

                Toast.makeText(getContext(),adapterView.getSelectedItemPosition()+" Selected",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view_word);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new WordRecyclerViewAdapter(getContext(), words);
        recyclerView.setAdapter(adapter);

        return v;
    }


    private void spinnerInitializatin(View v){

        String[] ITEMS = {"Beginner", "Intermediate", "Advance"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, ITEMS);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (MaterialSpinner)v.findViewById(R.id.spinner);
        spinner.setHint(ITEMS[0]);
        spinner.setAdapter(arrayAdapter);


    }


    private void beginnerWordInitialization(){

        words.clear();

        String[] wordArray = getResources().getStringArray(R.array.beginner_words);
        String[] translationArray = getResources().getStringArray(R.array.beginner_translation);
        String[] grammarArray = getResources().getStringArray(R.array.beginner_grammar);
        String[] pronunciationArray = getResources().getStringArray(R.array.beginner_pronunciation);
        String[] exampleArray1 = getResources().getStringArray(R.array.beginner_example1);

        for(int i = 0 ; i < wordArray.length; i++){

            words.add(new Word(wordArray[i],translationArray[i],pronunciationArray[i],grammarArray[i],exampleArray1[i],"beginnr"));

        }



    }

    private void beginnerWordInitialization2(View v){

        words.clear();
        Toast.makeText(getContext(),SplashScreen.languageId+"",Toast.LENGTH_SHORT).show();
        String[] wordArray = getResources().getStringArray(R.array.beginner_words);
        String[] translationArray = getResources().getStringArray(R.array.beginner_translation);
        String[] grammarArray = getResources().getStringArray(R.array.beginner_grammar);
        String[] pronunciationArray = getResources().getStringArray(R.array.beginner_pronunciation);
        String[] exampleArray1 = getResources().getStringArray(R.array.beginner_example1);
        String[] extraArray = new String[getResources().getStringArray(R.array.beginner_words).length];

        if(SplashScreen.languageId == 0){

            for(int i = 0; i < getResources().getStringArray(R.array.beginner_words).length; i++){


                extraArray[i] = "";
            }

        }

        if(SplashScreen.languageId == 1){

            extraArray = getResources().getStringArray(R.array.beginner_spanish);
        }
        if(SplashScreen.languageId == 2){

            extraArray = getResources().getStringArray(R.array.beginner_bengali);
        }
        if(SplashScreen.languageId == 3){

            extraArray = getResources().getStringArray(R.array.beginner_hindi);
        }



        for(int i = 0 ; i < wordArray.length; i++){

            words.add(new Word(wordArray[i],translationArray[i],extraArray[i],pronunciationArray[i],grammarArray[i],exampleArray1[i],"beginner",0));

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

        String[] wordArray = getResources().getStringArray(R.array.intermediate_words);
        String[] translationArray = getResources().getStringArray(R.array.intermediate_translation);
        String[] grammarArray = getResources().getStringArray(R.array.intermediate_grammar);
        String[] pronunciationArray = getResources().getStringArray(R.array.intermediate_pronunciation);
        String[] exampleArray1 = getResources().getStringArray(R.array.intermediate_example1);

        for(int i = 0 ; i < wordArray.length; i++){

            words.add(new Word(wordArray[i],translationArray[i],pronunciationArray[i],grammarArray[i],exampleArray1[i],"intermediate"));

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
        String[] wordArray = getResources().getStringArray(R.array.advanced_words);
        String[] translationArray = getResources().getStringArray(R.array.advanced_translation);
        String[] grammarArray = getResources().getStringArray(R.array.advanced_grammar);
        String[] pronunciationArray = getResources().getStringArray(R.array.advanced_pronunciation);
        String[] exampleArray1 = getResources().getStringArray(R.array.advanced_example1);

        for(int i = 0 ; i < wordArray.length; i++){

            words.add(new Word(wordArray[i],translationArray[i],pronunciationArray[i],grammarArray[i],exampleArray1[i],"advance"));

        }


        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view_word);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new advanceAdapter(getContext(), words);
        recyclerView.setAdapter(adapter);

    }



}
