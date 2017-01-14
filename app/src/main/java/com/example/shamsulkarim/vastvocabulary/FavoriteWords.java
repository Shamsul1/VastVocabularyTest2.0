package com.example.shamsulkarim.vastvocabulary;


import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.shamsulkarim.vastvocabulary.Practice.Practice;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteWords extends Fragment  {




    private ImageView fab;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    static public List<Word> words = new ArrayList<>();
    private float fabY;

    List<String> bWord,aWord,iWord;
    List<Integer> bWordDatabasePosition, aWordDatabasePosition, iWordDatabasePosition;
    boolean isFabOptionOn = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorite_words,container,false);

        fab = (ImageView)v.findViewById(R.id.fab_favorite);

        fabY = fab.getY();





        bWord = new ArrayList<>();
        aWord = new ArrayList<>();
        iWord = new ArrayList<>();
        bWordDatabasePosition = new ArrayList<>();
        aWordDatabasePosition = new ArrayList<>();
        iWordDatabasePosition = new ArrayList<>();

        getFavoriteWordRes();
        addFavoriteWord();


        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view_favorite_words);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new FavoriteRecyclerViewAdapter(getContext(), words);
        recyclerView.setAdapter(adapter);




        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {



            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    System.out.println("Scrolled Downwards");
                    fabAnimation(false);

                    if(isFabOptionOn){
                        fab.animate().rotation(-20f);
                        isFabOptionOn = false;

                    }


                } else if (dy < 0) {
                    System.out.println("Scrolled Upwards");
                    fabAnimation(true);


                } else {

                    System.out.println("No Vertical Scrolled");
                }
            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(words.size() < 5){

                    Snackbar.make(view, "There are not enough words", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();


                }else {

                    MainActivity.practice = "favorite";
                    Intent intent = new Intent(getContext(),Practice.class);

                    getContext().startActivity(intent);

                }

            }
        });



        return v;
    }

//----------------------------------------------------------------------------------------------------
    private void getFavoriteWordRes(){

        Cursor aRes = MainActivity.advanceDatabase.getData();
        Cursor bRes = MainActivity.beginnerDatabase.getData();
        Cursor iRes = MainActivity.intermediateDatabase.getData();

        while (aRes.moveToNext()){

            aWord.add(aRes.getString(2));
            int pos = (Integer) aRes.getInt(0);
            aWordDatabasePosition.add(pos);

        }

        while (bRes.moveToNext()){

            bWord.add(bRes.getString(2));
            int pos = (Integer) bRes.getInt(0);
            bWordDatabasePosition.add(pos);

        }

        while (iRes.moveToNext()){

            iWord.add(iRes.getString(2));
            int pos = (Integer) iRes.getInt(0);
            iWordDatabasePosition.add(pos);

        }
    }

    private void addFavoriteWord(){

        words.clear();
        String[] advanceWordArray = getResources().getStringArray(R.array.advanced_words);
        String[] advanceTranslationArray = getResources().getStringArray(R.array.advanced_translation);
        String[] advanceGrammarArray = getResources().getStringArray(R.array.advanced_grammar);
        String[] advancePronunciationArray = getResources().getStringArray(R.array.advanced_pronunciation);
        String[] advanceExampleArray1 = getResources().getStringArray(R.array.advanced_example1);
        String[] advanceExampleArray2 = getResources().getStringArray(R.array.advanced_example2);
        String[] advanceExampleArray3 = getResources().getStringArray(R.array.advanced_example3);

        String[] beginnerWordArray = getResources().getStringArray(R.array.beginner_words);
        String[] beginnerTranslationArray = getResources().getStringArray(R.array.beginner_translation);
        String[] beginnerGrammarArray = getResources().getStringArray(R.array.beginner_grammar);
        String[] beginnerPronunciationArray = getResources().getStringArray(R.array.beginner_pronunciation);
        String[] beginnerExampleArray1 = getResources().getStringArray(R.array.beginner_example1);
        String[] beginnerExampleArray2 = getResources().getStringArray(R.array.beginner_example2);
        String[] beginnerExampleArray3 = getResources().getStringArray(R.array.beginner_example3);

        String[] intermediateWordArray = getResources().getStringArray(R.array.intermediate_words);
        String[] intermediateTranslationArray = getResources().getStringArray(R.array.intermediate_translation);
        String[] intermediateGrammarArray = getResources().getStringArray(R.array.intermediate_grammar);
        String[] intermediatePronunciationArray = getResources().getStringArray(R.array.intermediate_pronunciation);
        String[] intermediateExampleArray1 = getResources().getStringArray(R.array.intermediate_example1);
        String[] intermediateExampleArray2 = getResources().getStringArray(R.array.intermediate_example2);
        String[] intermediateExampleArray3 = getResources().getStringArray(R.array.intermediate_example3);




        for(int i = 0; i < aWord.size(); i++){

            if(aWord.get(i).equalsIgnoreCase("True")){

                Word word = new Word(advanceWordArray[i],advanceTranslationArray[i],advancePronunciationArray[i],advanceGrammarArray[i],advanceExampleArray1[i],advanceExampleArray2[i],advanceExampleArray3[i],aWordDatabasePosition.get(i),"Advance");
                words.add(word);

            }

        }

        for(int i =0 ; i < bWord.size(); i++){

            if(bWord.get(i).equalsIgnoreCase("True")){

                Word word = new Word(beginnerWordArray[i],beginnerTranslationArray[i],beginnerPronunciationArray[i],beginnerGrammarArray[i],beginnerExampleArray1[i],beginnerExampleArray2[i],beginnerExampleArray3[i],bWordDatabasePosition.get(i),"Beginner");

                words.add(word);
            }

        }

        for(int i =0 ; i < iWord.size(); i++){

            if(iWord.get(i).equalsIgnoreCase("True")){

                Word word = new Word(intermediateWordArray[i],intermediateTranslationArray[i],intermediatePronunciationArray[i],intermediateGrammarArray[i],intermediateExampleArray1[i],intermediateExampleArray2[i],intermediateExampleArray3[i],iWordDatabasePosition.get(i),"Intermediate");
                words.add(word);

            }

        }



    }


    protected void fabAnimation(boolean isVisible) {
        if (isVisible) {
            fab.animate().cancel();
            fab.animate().translationY(fabY);
        } else {
            fab.animate().cancel();
            fab.animate().translationY(fabY + 500);
        }
    }









}
