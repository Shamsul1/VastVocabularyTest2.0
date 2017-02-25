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

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class LearnedWords extends Fragment {



    Toolbar toolbar;
    private MaterialSpinner spinner;
    private RecyclerView recyclerView;
    RelativeLayout fab_option1, fab_option2,fab_option3;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Word> words = new ArrayList<>();
    boolean isShowingFabOption = false;
    ImageView fab;
    private float fabY;
    public static List<Word> practiceWords;


    public static List<String> bWord,aWord,iWord;
    List<Integer> bWordDatabasePosition, aWordDatabasePosition, iWordDatabasePosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_learned_words,container,false);
        practiceWords = new ArrayList<>();

//        spinnerInitializatin(v);
        fab = (ImageView)v.findViewById(R.id.fab_favorite_learned);
        fab_option1 = (RelativeLayout)v.findViewById(R.id.fab_option1_learned);
        fab_option2 = (RelativeLayout)v.findViewById(R.id.fab_option2_learned);
        fab_option3 = (RelativeLayout)v.findViewById(R.id.fab_option3_learned);

        fabY = fab.getY();
        fab_option1.setVisibility(View.INVISIBLE);
        fab_option2.setVisibility(View.INVISIBLE);
        fab_option3.setVisibility(View.INVISIBLE);





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



//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//
//                switch (adapterView.getSelectedItemPosition()){
//
//                    case 0:
//                        beginnerWordInitialization();
//                        break;
//
//                    case 1:
//                        intermediateWordInitialization();
//
//                        break;
//                    case 2:
//                        advanceWordInitialization();
//
//                        break;
//
//                    default:
//                        beginnerWordInitialization();
//                        break;
//
//                }
//
//                Toast.makeText(getContext(),adapterView.getSelectedItemPosition()+" Selected",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
 @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    System.out.println("Scrolled Downwards");
                    fabAnimation(false);

                    if(isShowingFabOption){
                        onFabTransitionBack();
                        onFabScaleBack();
                        fab.animate().rotation(-20f);
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isShowingFabOption){

                    onFabTransition();
                    onFabScale();
                    isShowingFabOption = true;


                }else {
                    onFabScaleBack();
                    onFabTransitionBack();

                    isShowingFabOption = false;
                }

            }
        });


        fab_option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"fab opton 1 clicked", Toast.LENGTH_SHORT).show();

                MainActivity.practice = "learned";
                practiceWords.clear();
                practiceWords = getAdvanceWords();

                if(practiceWords.size() >= 5){
                    getContext().startActivity(new Intent(getContext(), Practice.class));
                    onFabScaleBack();
                    onFabTransitionBack();

                }else {
                    Toast.makeText(getContext(),"not enough words", Toast.LENGTH_SHORT).show();
                    onFabScaleBack();
                    onFabTransitionBack();
                }



            }
        });

        fab_option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"fab opton 2 clicked", Toast.LENGTH_SHORT).show();

                MainActivity.practice = "learned";
                practiceWords.clear();
                practiceWords = getIntermediateWords();

                if(practiceWords.size() >= 5){
                    getContext().startActivity(new Intent(getContext(), Practice.class));
                    onFabScaleBack();
                    onFabTransitionBack();

                }else {
                    Toast.makeText(getContext(),"not enough words", Toast.LENGTH_SHORT).show();
                    onFabScaleBack();
                    onFabTransitionBack();
                }



            }
        });

        fab_option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"fab opton 3 clicked", Toast.LENGTH_SHORT).show();

                MainActivity.practice = "learned";
                practiceWords.clear();
                practiceWords = getBeginnerWords();

                if(practiceWords.size() >= 5){
                    getContext().startActivity(new Intent(getContext(), Practice.class));
                    onFabScaleBack();
                    onFabTransitionBack();
                    isShowingFabOption = false;

                }else {
                    Toast.makeText(getContext(),"not enough words", Toast.LENGTH_SHORT).show();
                    onFabScaleBack();
                    onFabTransitionBack();
                    isShowingFabOption = false;
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
            fab.animate().cancel();
            fab.animate().translationY(fabY);
        } else {
            fab.animate().cancel();
            fab.animate().translationY(fabY + 500);
        }
    }

//    private void spinnerInitializatin(View v){
//
//        String[] ITEMS = {"Intermediate", "Advance"};
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, ITEMS);
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner = (MaterialSpinner)v.findViewById(R.id.spinner_learned_words);
//        spinner.setHint("Beginner");
//        spinner.setAdapter(arrayAdapter);
//
//    }


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

    private void onFabTransition(){
        fab.animate().rotation(40f).setDuration(500L).setInterpolator(new AnticipateOvershootInterpolator());

        float position =  fab.getHeight();

        ValueAnimator va = ValueAnimator.ofFloat(position,0);

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                float value = (float) valueAnimator.getAnimatedValue();

                fab_option1.setTranslationY(value);
                fab_option2.setTranslationY(value);
                fab_option3.setTranslationY(value);



                fab_option1.setTranslationX(value);
                fab_option2.setTranslationX(value);
                fab_option3.setTranslationX(value);




            }
        });


        va.setDuration(500L);
        va.setInterpolator(new AnticipateOvershootInterpolator());
        va.start();


    }

    public void onFabScale(){




        ValueAnimator va = ValueAnimator.ofFloat(0,1 );

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){


            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                float value  = (float)valueAnimator.getAnimatedValue();



                fab_option1.setScaleX(value);
                fab_option1.setScaleY(value);

                fab_option2.setScaleY(value);
                fab_option2.setScaleX(value);

                fab_option3.setScaleX(value);
                fab_option3.setScaleY(value);


                fab_option1.setAlpha(value);
                fab_option2.setAlpha(value);
                fab_option3.setAlpha(value);

                fab_option1.setVisibility(View.VISIBLE);
                fab_option2.setVisibility(View.VISIBLE);
                fab_option3.setVisibility(View.VISIBLE);






            }
        });


        va.setDuration(500L);
        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.start();






    }

    private void onFabTransitionBack(){

        fab.animate().rotation(0f).setDuration(500).setInterpolator(new AnticipateOvershootInterpolator());
        float position =  fab.getHeight();

        ValueAnimator va = ValueAnimator.ofFloat(0,position);

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                float value = (float) valueAnimator.getAnimatedValue();

                fab_option1.setTranslationY(value);
                fab_option2.setTranslationY(value);
                fab_option3.setTranslationY(value);



                fab_option1.setTranslationX(value);
                fab_option2.setTranslationX(value);
                fab_option3.setTranslationX(value);




            }
        });


        va.setDuration(500L);
        va.setInterpolator(new AnticipateOvershootInterpolator());
        va.start();


    }


    public void onFabScaleBack(){




        ValueAnimator va = ValueAnimator.ofFloat(1,0 );

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){


            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                float value  = (float)valueAnimator.getAnimatedValue();



                fab_option1.setScaleX(value);
                fab_option1.setScaleY(value);

                fab_option2.setScaleY(value);
                fab_option2.setScaleX(value);

                fab_option3.setScaleX(value);
                fab_option3.setScaleY(value);








            }
        });


        va.setDuration(500L);
        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.start();

        fab_option1.setVisibility(View.INVISIBLE);
        fab_option2.setVisibility(View.INVISIBLE);
        fab_option3.setVisibility(View.INVISIBLE);




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


}
