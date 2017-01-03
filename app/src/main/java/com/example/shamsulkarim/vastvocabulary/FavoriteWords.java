package com.example.shamsulkarim.vastvocabulary;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shamsulkarim.vastvocabulary.WordAdapters.advanceAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteWords extends Fragment {




    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Word> words = new ArrayList<>();

    AdvancedWordDatabase aDB;
    BeginnerWordDatabase bDB;
    IntermediatewordDatabase iDB;

    List<String> bWord,aWord,iWord;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorite_words,container,false);

        aDB = new AdvancedWordDatabase(getContext());
        iDB= new IntermediatewordDatabase(getContext());
        bDB = new BeginnerWordDatabase(getContext());


        bWord = new ArrayList<>();
        aWord = new ArrayList<>();
        iWord = new ArrayList<>();

        getFavoriteWordRes();
        addFavoriteWord();










//        for(int i = 0 ; i < wordArray.length; i++){
//
//            Word word = new Word(wordArray[i],translationArray[i],pronunciationArray[i],grammarArray[i],exampleArray1[i],exampleArray2[i],exampleArray3[i],"Advanced");
//
//            words.add(word);
//
//        }


        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view_favorite_words);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new FavoriteRecyclerViewAdapter(getContext(), words);
        recyclerView.setAdapter(adapter);

        return v;
    }


    private void getFavoriteWordRes(){

        Cursor aRes = aDB.getData();
        Cursor bRes = bDB.getData();
        Cursor iRes = iDB.getData();

        while (aRes.moveToNext()){

            aWord.add(aRes.getString(2));

        }

        while (bRes.moveToNext()){

            bWord.add(bRes.getString(2));

        }

        while (iRes.moveToNext()){

            iWord.add(iRes.getString(2));

        }
    }

    private void addFavoriteWord(){

        String[] wordArray = getResources().getStringArray(R.array.advanced_words);
        String[] translationArray = getResources().getStringArray(R.array.advanced_translation);
        String[] grammarArray = getResources().getStringArray(R.array.advanced_grammar);
        String[] pronunciationArray = getResources().getStringArray(R.array.advanced_pronunciation);
        String[] exampleArray1 = getResources().getStringArray(R.array.advanced_example1);
        String[] exampleArray2 = getResources().getStringArray(R.array.advanced_example2);
        String[] exampleArray3 = getResources().getStringArray(R.array.advanced_example3);


        for(int i = 0; i < aWord.size(); i++){

            if(aWord.get(i).equalsIgnoreCase("True")){

                Word word = new Word(wordArray[i],translationArray[i],pronunciationArray[i],grammarArray[i],exampleArray1[i],exampleArray2[i],exampleArray3[i],"Advanced");
                words.add(word);

            }


        }



    }

}
