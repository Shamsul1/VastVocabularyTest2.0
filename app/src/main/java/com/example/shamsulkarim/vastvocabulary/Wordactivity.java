package com.example.shamsulkarim.vastvocabulary;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

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

        String[] ITEMS = {"BEGINNER", "INTERMEDIATE", "ADVANCE"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, ITEMS);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (MaterialSpinner)v.findViewById(R.id.spinner);
        spinner.setHint(ITEMS[0]);
        spinner.setAdapter(arrayAdapter);




        String[] wordArray = getResources().getStringArray(R.array.beginner_words);
        String[] translationArray = getResources().getStringArray(R.array.beginner_translation);
        String[] grammarArray = getResources().getStringArray(R.array.beginner_grammar);
        String[] pronunciationArray = getResources().getStringArray(R.array.beginner_pronunciation);
        String[] exampleArray1 = getResources().getStringArray(R.array.beginner_example1);

        for(int i = 0 ; i < wordArray.length; i++){

            words.add(new Word(wordArray[i],translationArray[i],pronunciationArray[i],grammarArray[i],exampleArray1[i]));

        }


        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view_word);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new WordRecyclerViewAdapter(getContext(), words);
        recyclerView.setAdapter(adapter);

        return v;
    }
}
