package com.example.shamsulkarim.vastvocabulary;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LearnedWords extends Fragment {



    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Word> words = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_learned_words,container,false);

        String[] wordArray = getResources().getStringArray(R.array.intermediate_words);
        String[] translationArray = getResources().getStringArray(R.array.intermediate_translation);
        String[] grammarArray = getResources().getStringArray(R.array.intermediate_grammar);
        String[] pronunciationArray = getResources().getStringArray(R.array.intermediate_pronunciation);
        String[] exampleArray1 = getResources().getStringArray(R.array.intermediate_example1);

        for(int i = 0 ; i < wordArray.length; i++){

            words.add(new Word(wordArray[i],translationArray[i],pronunciationArray[i],grammarArray[i],exampleArray1[i]));

        }


        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view_learned_words);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new WordRecyclerViewAdapter(getContext(), words);
        recyclerView.setAdapter(adapter);

        return v;
    }

}
