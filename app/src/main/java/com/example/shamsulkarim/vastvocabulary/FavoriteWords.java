package com.example.shamsulkarim.vastvocabulary;


import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import java.util.LinkedList;
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




        Toolbar toolbar= (Toolbar)v.findViewById(R.id.favoriteToolbar);
        toolbar.setTitle("Favorite");
        toolbar.setTitleTextColor(Color.parseColor("#673AB7"));
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

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


        Cursor aRes = SplashScreen.advanceDatabase.getData();
        Cursor bRes = SplashScreen.beginnerDatabase.getData();
        Cursor iRes = SplashScreen.intermediateDatabase.getData();

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

    public   void addFavoriteWord(){

        words.clear();



        String[] beginnerSecondTranslation = new String[getResources().getStringArray(R.array.beginner_words).length];
        String[] intermediateSecondTranslation = new String[SplashScreen.intermediateSpanish.length];
        String[] advanceSecondTranslation = new String[SplashScreen.advanceHindi.length];

        if(SplashScreen.languageId == 0){

            for(int i = 0; i < getResources().getStringArray(R.array.beginner_words).length; i++){


                beginnerSecondTranslation[i] = "";
            }

        }

        if(SplashScreen.languageId == 1){

            beginnerSecondTranslation = SplashScreen.beginnerSpanish;
            intermediateSecondTranslation = SplashScreen.intermediateSpanish;
            advanceSecondTranslation = SplashScreen.advanceSpanish;
        }
        if(SplashScreen.languageId == 2){

            beginnerSecondTranslation = getResources().getStringArray(R.array.beginner_bengali);
            intermediateSecondTranslation = SplashScreen.intermediateBengali;
            advanceSecondTranslation = SplashScreen.advanceBengali;
        }
        if(SplashScreen.languageId == 3){

            beginnerSecondTranslation = getResources().getStringArray(R.array.beginner_hindi);
            intermediateSecondTranslation = SplashScreen.intermediateHindi;
            advanceSecondTranslation = SplashScreen.advanceHindi;
        }

        int aWordSize = aWord.size();
        int bWordSize = bWord.size();
        int iWordSize = iWord.size();


        for(int i = 0; i < aWordSize; i++){




            if(aWord.get(i).equalsIgnoreCase("True")){

                Word word = new Word(SplashScreen.advanceWordArray[i],SplashScreen.advanceTranslationArray[i],advanceSecondTranslation[i],SplashScreen.advancePronunciationArray[i],SplashScreen.advanceGrammarArray[i],SplashScreen.advanceExampleArray1[i],SplashScreen.advanceExampleArray2[i],SplashScreen.advanceExampleArray3[i],aWordDatabasePosition.get(i),"Advance");
                words.add(word);

            }

        }

        for(int i =0 ; i < bWordSize; i++){

            if(bWord.get(i).equalsIgnoreCase("True")){

                Word word = new Word(SplashScreen.beginnerWordArray[i],SplashScreen.beginnerTranslationArray[i],beginnerSecondTranslation[i],SplashScreen.beginnerPronunciationArray[i],SplashScreen.beginnerGrammarArray[i],SplashScreen.beginnerExampleArray1[i],SplashScreen.beginnerExampleArray2[i],SplashScreen.beginnerExampleArray3[i],bWordDatabasePosition.get(i),"Beginner");

                words.add(word);
            }

        }

        for(int i =0 ; i < iWordSize; i++){

            if(iWord.get(i).equalsIgnoreCase("True")){

                Word word = new Word(SplashScreen.intermediateWordArray[i],SplashScreen.intermediateTranslationArray[i],intermediateSecondTranslation[i],SplashScreen.intermediatePronunciationArray[i],SplashScreen.intermediateGrammarArray[i],SplashScreen.intermediateExampleArray1[i],SplashScreen.intermediateExampleArray2[i],SplashScreen.intermediateExampleArray3[i],iWordDatabasePosition.get(i),"Intermediate");
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
