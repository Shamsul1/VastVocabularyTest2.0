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
import android.widget.TextView;
import android.widget.Toast;

import com.example.shamsulkarim.vastvocabulary.Practice.Practice;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteWords extends Fragment  {



    BeginnerWordDatabase beginnerDatabase;
    IntermediatewordDatabase intermediateDatabase;
    AdvancedWordDatabase advanceDatabase;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    static public List<Word> words = new ArrayList<>();
    private float fabY;
    SharedPreferences sp;

    List<String> bWord,aWord,iWord;
    List<Integer> bWordDatabasePosition, aWordDatabasePosition, iWordDatabasePosition;
    boolean isFabOptionOn = false;
    TextView noFavorite;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorite_words,container,false);

        fab = (FloatingActionButton)v.findViewById(R.id.fab_favorite);

        fabY = fab.getY();

        sp = v.getContext().getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);
        initializingSQLDatabase(v);


        noFavorite = (TextView)v.findViewById(R.id.havenotlearned);
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


        int favoriteWordSize = words.size();

        if(favoriteWordSize >= 1){
          noFavorite.setVisibility(View.INVISIBLE);

        }else {
            noFavorite.setVisibility(View.VISIBLE);
        }

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

                    sp.edit().putString("practice","favorite").apply();
                    Intent intent = new Intent(getContext(),Practice.class);

                    getContext().startActivity(intent);

                }

            }
        });



        return v;
    }

//----------------------------------------------------------------------------------------------------
    private void getFavoriteWordRes(){


        Cursor aRes = advanceDatabase.getData();
        Cursor bRes = beginnerDatabase.getData();
        Cursor iRes = intermediateDatabase.getData();

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
        int languageId = sp.getInt("language",0);


        String[] beginnerSecondTranslation = new String[getResources().getStringArray(R.array.beginner_words).length];
        String[] intermediateSecondTranslation = new String[getResources().getStringArray(R.array.intermediate_words).length];
        String[] advanceSecondTranslation = new String[getResources().getStringArray(R.array.advanced_words).length];

        if(languageId == 0){

            for(int i = 0; i < getResources().getStringArray(R.array.beginner_words).length; i++){


                beginnerSecondTranslation[i] = "";
            }

        }

        if(languageId == 1){

            beginnerSecondTranslation = getResources().getStringArray(R.array.beginner_spanish);
            intermediateSecondTranslation = getResources().getStringArray(R.array.intermediate_spanish);
            advanceSecondTranslation = getResources().getStringArray(R.array.advance_spanish);
        }
        if(languageId == 2){

            beginnerSecondTranslation = getResources().getStringArray(R.array.beginner_bengali);
            intermediateSecondTranslation = getResources().getStringArray(R.array.intermediate_bengali);
            advanceSecondTranslation = getResources().getStringArray(R.array.advance_bengali);
        }
        if(languageId == 3){

            beginnerSecondTranslation = getResources().getStringArray(R.array.beginner_hindi);
            intermediateSecondTranslation = getResources().getStringArray(R.array.intermediate_hindi);
            advanceSecondTranslation = getResources().getStringArray(R.array.advance_hindi);
        }

        int aWordSize = aWord.size();
        int bWordSize = bWord.size();
        int iWordSize = iWord.size();

        String[] beginnerWordArray = getResources().getStringArray(R.array.beginner_words);
        String[] beginnerTranslationArray = getResources().getStringArray(R.array.beginner_translation);
        String[] beginnerPronunciationArray = getResources().getStringArray(R.array.beginner_pronunciation);
        String[] beginnerGrammarArray = getResources().getStringArray(R.array.beginner_grammar);
        String[] beginnerExampleArray1 = getResources().getStringArray(R.array.beginner_example1);
        String[] beginnerExampleArray2 = getResources().getStringArray(R.array.beginner_example2);
        String[] beginnerExampleArray3 = getResources().getStringArray(R.array.beginner_example3);

        String[] intermediateWordArray = getResources().getStringArray(R.array.intermediate_words);
        String[] intermediateTranslationArray = getResources().getStringArray(R.array.intermediate_translation);
        String[] intermediatePronunciationArray = getResources().getStringArray(R.array.intermediate_pronunciation);
        String[] intermediateGrammarArray = getResources().getStringArray(R.array.intermediate_grammar);
        String[] intermediateExampleArray1 = getResources().getStringArray(R.array.intermediate_example1);
        String[] intermediateExampleArray2 = getResources().getStringArray(R.array.intermediate_example2);
        String[] intermediateExampleArray3 = getResources().getStringArray(R.array.intermediate_example3);

        String[] advanceWordArray = getResources().getStringArray(R.array.advanced_words);
        String[] advanceTranslationArray = getResources().getStringArray(R.array.advanced_translation);
        String[] advancePronunciationArray = getResources().getStringArray(R.array.advanced_pronunciation);
        String[] advanceGrammarArray = getResources().getStringArray(R.array.advanced_grammar);
        String[] advanceExampleArray1 = getResources().getStringArray(R.array.advanced_example1);
        String[] advanceExampleArray2 = getResources().getStringArray(R.array.advanced_example2);
        String[] advanceExampleArray3 = getResources().getStringArray(R.array.advanced_example3);


        for(int i = 0; i < aWordSize; i++){






            if(aWord.get(i).equalsIgnoreCase("True")){

                Word word = new Word(advanceWordArray[i],advanceTranslationArray[i],advanceSecondTranslation[i],advancePronunciationArray[i],advanceGrammarArray[i],advanceExampleArray1[i],advanceExampleArray2[i], advanceExampleArray3[i],aWordDatabasePosition.get(i),"Advance");
                words.add(word);

            }

        }

        for(int i =0 ; i < bWordSize; i++){

            if(bWord.get(i).equalsIgnoreCase("True")){

                Word word = new Word(beginnerWordArray[i],beginnerTranslationArray[i],beginnerSecondTranslation[i],beginnerPronunciationArray[i],beginnerGrammarArray[i],beginnerExampleArray1[i],beginnerExampleArray2[i],beginnerExampleArray3[i],bWordDatabasePosition.get(i),"Beginner");

                words.add(word);
            }

        }

        for(int i =0 ; i < iWordSize; i++){

            if(iWord.get(i).equalsIgnoreCase("True")){

                Word word = new Word(intermediateWordArray[i],intermediateTranslationArray[i],intermediateSecondTranslation[i],intermediatePronunciationArray[i],intermediateGrammarArray[i],intermediateExampleArray1[i],intermediateExampleArray2[i],intermediateExampleArray3[i],iWordDatabasePosition.get(i),"Intermediate");
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




    private void initializingSQLDatabase(View v){

        beginnerDatabase = new BeginnerWordDatabase(v.getContext());
        advanceDatabase = new AdvancedWordDatabase(v.getContext());
        intermediateDatabase = new IntermediatewordDatabase(v.getContext());
    }




}
