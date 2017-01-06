package com.example.shamsulkarim.vastvocabulary;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class LearnedWords extends Fragment {



    private MaterialSpinner spinner;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Word> words = new ArrayList<>();


    List<String> bWord,aWord,iWord;
    List<Integer> bWordDatabasePosition, aWordDatabasePosition, iWordDatabasePosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_learned_words,container,false);
        spinnerInitializatin(v);


        SharedPreferences sp = getContext().getSharedPreferences("com.example.shamsulkarim.vastvocabulary", Context.MODE_PRIVATE);
        int spinnerPos = 0;

        if(!sp.contains("LearnedSpinnerPosition")){

            sp.edit().putInt("LearnedSpinnerPosition",0).apply();

        }else {

            spinnerPos = sp.getInt("LearnedSpinnerPosition",0);
            spinner.setSelection(spinnerPos);

        }

        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view_learned_words);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        beginnerWordInitialization();


        bWord = new ArrayList<>();
        aWord = new ArrayList<>();
        iWord = new ArrayList<>();
        bWordDatabasePosition = new ArrayList<>();
        aWordDatabasePosition = new ArrayList<>();
        iWordDatabasePosition = new ArrayList<>();



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                SharedPreferences sp = getContext().getSharedPreferences("com.example.shamsulkarim.vastvocabulary", Context.MODE_PRIVATE);
                sp.edit().putInt("LearnedSpinnerPosition",adapterView.getSelectedItemPosition()).apply();

                switch (adapterView.getSelectedItemPosition()){

                    case 0:
                        beginnerWordInitialization();
                        break;

                    case 1:
                        intermediateWordInitialization();

                        break;
                    case 2:
                        advanceWordInitialization();

                        break;

                    default:
                        beginnerWordInitialization();
                        break;

                }

                Toast.makeText(getContext(),adapterView.getSelectedItemPosition()+" Selected",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return v;

        //---------------------------------------------------------------------------------------------------
    }


    private void spinnerInitializatin(View v){

        String[] ITEMS = {"Intermediate", "Advance"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, ITEMS);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (MaterialSpinner)v.findViewById(R.id.spinner_learned_words);
        spinner.setHint("Beginner");
        spinner.setAdapter(arrayAdapter);

    }


    private void beginnerWordInitialization(){

        words.clear();
        SharedPreferences sp = getContext().getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);
        int beginnerLearnedCount = sp.getInt("beginner",0);

        String[] wordArray = getResources().getStringArray(R.array.beginner_words);
        String[] translationArray = getResources().getStringArray(R.array.beginner_translation);
        String[] grammarArray = getResources().getStringArray(R.array.beginner_grammar);
        String[] pronunciationArray = getResources().getStringArray(R.array.beginner_pronunciation);
        String[] exampleArray1 = getResources().getStringArray(R.array.beginner_example1);



        for(int i = 0 ; i < beginnerLearnedCount; i++){

            words.add(new Word(wordArray[i],translationArray[i],pronunciationArray[i],grammarArray[i],exampleArray1[i],"beginner"));

        }

        adapter = new WordRecyclerViewAdapter(getContext(), words);
        recyclerView.setAdapter(adapter);




    }


    private void advanceWordInitialization(){

        words.clear();
        SharedPreferences sp = getContext().getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);
        int beginnerLearnedCount = sp.getInt("advanced",0);

        String[] wordArray = getResources().getStringArray(R.array.advanced_words);
        String[] translationArray = getResources().getStringArray(R.array.advanced_translation);
        String[] grammarArray = getResources().getStringArray(R.array.advanced_grammar);
        String[] pronunciationArray = getResources().getStringArray(R.array.advanced_pronunciation);
        String[] exampleArray1 = getResources().getStringArray(R.array.advanced_example1);


        for(int i = 0 ; i < beginnerLearnedCount; i++){

            words.add(new Word(wordArray[i],translationArray[i],pronunciationArray[i],grammarArray[i],exampleArray1[i],"advance"));

        }


        adapter = new advanceAdapter(getContext(), words);
        recyclerView.setAdapter(adapter);

    }

    private void intermediateWordInitialization(){

        words.clear();
        SharedPreferences sp = getContext().getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);
        int beginnerLearnedCount = sp.getInt("intermediate",0);

        String[] wordArray = getResources().getStringArray(R.array.intermediate_words);
        String[] translationArray = getResources().getStringArray(R.array.intermediate_translation);
        String[] grammarArray = getResources().getStringArray(R.array.intermediate_grammar);
        String[] pronunciationArray = getResources().getStringArray(R.array.intermediate_pronunciation);
        String[] exampleArray1 = getResources().getStringArray(R.array.intermediate_example1);


        for(int i = 0 ; i < beginnerLearnedCount; i++){

            words.add(new Word(wordArray[i],translationArray[i],pronunciationArray[i],grammarArray[i],exampleArray1[i],"intermediate"));

        }


        adapter = new IntermediateAdapter(getContext(), words);
        recyclerView.setAdapter(adapter);

    }




}
