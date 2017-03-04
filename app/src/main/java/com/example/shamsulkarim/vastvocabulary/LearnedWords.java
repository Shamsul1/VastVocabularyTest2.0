package com.example.shamsulkarim.vastvocabulary;


import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shamsulkarim.vastvocabulary.Practice.Practice;
import com.example.shamsulkarim.vastvocabulary.WordAdapters.IntermediateAdapter;
import com.example.shamsulkarim.vastvocabulary.WordAdapters.WordRecyclerViewAdapter;
import com.example.shamsulkarim.vastvocabulary.WordAdapters.advanceAdapter;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class LearnedWords extends Fragment implements View.OnClickListener{



    Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Word> words = new ArrayList<>();
    boolean isShowingFabOption = false;
    public static List<Word> practiceWords;

    FloatingActionButton fab1,fab2,fab3;
    FloatingActionMenu fam;


    public static List<String> bWord,aWord,iWord;
    List<Integer> bWordDatabasePosition, aWordDatabasePosition, iWordDatabasePosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_learned_words,container,false);
        practiceWords = new ArrayList<>();



        fab1 = (FloatingActionButton)v.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)v.findViewById(R.id.fab2);
        fab3 = (FloatingActionButton)v.findViewById(R.id.fab3);
        fam = (FloatingActionMenu)v.findViewById(R.id.fam);

        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);

        toolbar = (Toolbar)v.findViewById(R.id.learned_toolbar);
        toolbar.setTitle("Learned");
        toolbar.setTitleTextColor(Color.parseColor("#673AB7"));
        setHasOptionsMenu(true);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

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


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
 @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    System.out.println("Scrolled Downwards");
                    fabAnimation(false);

                    if(isShowingFabOption){

                        fam.animate().rotation(-20f);
                        isShowingFabOption = false;

                    }


                } else if (dy < 0) {
                    System.out.println("Scrolled Upwards");
                    fabAnimation(true);


                } else {

                    System.out.println("No Vertical Scrolled");
                }
            }
        });







        return v;

        //---------------------------------------------------------------------------------------------------
    }


    ///// Spinner--------------------------


    @Override
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


                    beginnerWordInitialization();
                }
                if(i == 1){


                    intermediateWordInitialization();
                }

                if(i == 2){

                    advanceWordInitialization();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






    }

    protected void fabAnimation(boolean isVisible) {
        if (isVisible) {
            fam.animate().cancel();
            fam.animate().translationY(0f);
        } else {
            fam.animate().cancel();
            fam.animate().translationY(60f + 500);
        }
    }




    private void beginnerWordInitialization(){

        words.clear();

        words = getBeginnerWords();

        adapter = new WordRecyclerViewAdapter(getContext(), words);
        recyclerView.setAdapter(adapter);




    }


    private void advanceWordInitialization(){

        words.clear();


        words = getAdvanceWords();


        adapter = new advanceAdapter(getContext(), words);
        recyclerView.setAdapter(adapter);

    }

    private void intermediateWordInitialization(){

        words.clear();
        words = getIntermediateWords();


        adapter = new IntermediateAdapter(getContext(), words);
        recyclerView.setAdapter(adapter);

    }





    public List<Word> getAdvanceWords(){
        int advanceLearnedCount = SplashScreen.sp.getInt("advanced",0);
         List<Word> getWords = new ArrayList<>();

        String[] extraArray = new String[getResources().getStringArray(R.array.advanced_words).length];

        if(SplashScreen.languageId == 0){

            for(int i = 0; i < getResources().getStringArray(R.array.advanced_words).length; i++){


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






        for(int i = 0 ; i < advanceLearnedCount; i++){

            getWords.add(new Word(SplashScreen.advanceWordArray[i],SplashScreen.advanceTranslationArray[i],extraArray[i],SplashScreen.advancePronunciationArray[i],SplashScreen.advanceGrammarArray[i],SplashScreen.advanceExampleArray1[i],"advance",0));

        }

        return getWords;

    }

    public List<Word> getIntermediateWords(){
        List<Word> getWords = new ArrayList<>();
        int beginnerLearnedCount = SplashScreen.sp.getInt("intermediate",0);


        String[] extraArray = new String[getResources().getStringArray(R.array.intermediate_words).length];

        if(SplashScreen.languageId == 0){

            for(int i = 0; i < getResources().getStringArray(R.array.intermediate_words).length; i++){


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





        for(int i = 0 ; i < beginnerLearnedCount; i++){

            getWords.add(new Word(SplashScreen.intermediateWordArray[i],SplashScreen.intermediateTranslationArray[i],extraArray[i],SplashScreen.intermediatePronunciationArray[i],SplashScreen.intermediateGrammarArray[i],SplashScreen.intermediateExampleArray1[i],"intermediate",0));

        }

        return getWords;


    }

    public List<Word> getBeginnerWords(){

        List<Word> getWords = new ArrayList<>();

        SharedPreferences sp = getContext().getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);
        int beginnerLearnedCount = sp.getInt("beginner",0);

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




        for(int i = 0 ; i < beginnerLearnedCount; i++){

            getWords.add(new Word(SplashScreen.beginnerWordArray[i],SplashScreen.beginnerTranslationArray[i],extraArray[i],SplashScreen.beginnerPronunciationArray[i],SplashScreen.beginnerGrammarArray[i],SplashScreen.beginnerExampleArray1[i],"beginner",0));

        }

        return getWords;

    }


    @Override
    public void onClick(View view) {

        if(view.getId() == fab1.getId()){
            Toast.makeText(getContext(),"fab opton 1 clicked", Toast.LENGTH_SHORT).show();

            MainActivity.practice = "learned";
            practiceWords.clear();
            practiceWords = getAdvanceWords();

            if(practiceWords.size() >= 5){
                getContext().startActivity(new Intent(getContext(), Practice.class));


            }else {
                Toast.makeText(getContext(),"not enough words", Toast.LENGTH_SHORT).show();

            }

        }

        if(view.getId() == fab2.getId()){
            Toast.makeText(getContext(),"fab opton 2 clicked", Toast.LENGTH_SHORT).show();

            MainActivity.practice = "learned";
            practiceWords.clear();
            practiceWords = getIntermediateWords();

            if(practiceWords.size() >= 5){
                getContext().startActivity(new Intent(getContext(), Practice.class));


            }else {
                Toast.makeText(getContext(),"not enough words", Toast.LENGTH_SHORT).show();

            }
        }

        if(view.getId() == fab3.getId()){
            Toast.makeText(getContext(),"fab opton 3 clicked", Toast.LENGTH_SHORT).show();

            MainActivity.practice = "learned";
            practiceWords.clear();
            practiceWords = getBeginnerWords();

            if(practiceWords.size() >= 5){
                getContext().startActivity(new Intent(getContext(), Practice.class));

                isShowingFabOption = false;

            }else {
                Toast.makeText(getContext(),"not enough words", Toast.LENGTH_SHORT).show();

                isShowingFabOption = false;
            }
        }

    }
}
