package com.example.shamsulkarim.vastvocabulary;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;


public class Wordactivity extends Fragment {

MaterialSpinner spinner;

    private BeginnerWordDatabase beginnerDatabase;
    private IntermediatewordDatabase intermediateDatabase;
    private AdvancedWordDatabase advanceDatabase;
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
        addBeginnerWordToSQLite();






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






    private void addBeginnerWordToSQLite(){
        beginnerDatabase = new BeginnerWordDatabase(getContext());
        SharedPreferences sp = getActivity().getSharedPreferences("com.example.shamsulkarim.vastvocabulary", Context.MODE_PRIVATE);


        if(!sp.contains("beginnerWordCount")){
            final int beginnerWordLength = getResources().getStringArray(R.array.beginner_words).length;
            sp.edit().putInt("beginnerWordCount",beginnerWordLength).apply();

            for(int i = 0; i < beginnerWordLength; i++){

                beginnerDatabase.insertData(""+i,"false","false");

            }

        }
        int PREVIOUSBEGINNERCOUNT = sp.getInt("beginnerWordCount",0);
        int CURRENTBEGINNERCOUNT = getResources().getStringArray(R.array.beginner_words).length;

        Toast.makeText(getContext(),"prev: "+PREVIOUSBEGINNERCOUNT+" current: "+CURRENTBEGINNERCOUNT,Toast.LENGTH_SHORT).show();

        if(CURRENTBEGINNERCOUNT > PREVIOUSBEGINNERCOUNT){


            for(int i = PREVIOUSBEGINNERCOUNT; i < CURRENTBEGINNERCOUNT; i++){

                beginnerDatabase.insertData(""+i,"false","false");




            }
            sp.edit().putInt("beginnerWordCount",CURRENTBEGINNERCOUNT).apply();

            Toast.makeText(getContext(),PREVIOUSBEGINNERCOUNT-CURRENTBEGINNERCOUNT+" word added",Toast.LENGTH_SHORT).show();


        }else {

            Toast.makeText(getContext(),"NO new words added",Toast.LENGTH_SHORT).show();


        }







    }

    private void addIntermediateWordToSQLite(){
        intermediateDatabase = new IntermediatewordDatabase(getContext());
        SharedPreferences sp = getActivity().getSharedPreferences("com.example.shamsulkarim.vastvocabulary", Context.MODE_PRIVATE);

        if(!sp.contains("intermediateWordCount")){
            final int intermediateWordLength = getResources().getStringArray(R.array.intermediate_words).length;
            sp.edit().putInt("intermediateWordCount",intermediateWordLength).apply();

            for(int i = 0; i < intermediateWordLength; i++){

                intermediateDatabase.insertData(""+i,"false","false");

            }

        }
        int PREVIOUSBEGINNERCOUNT = sp.getInt("intermediateWordCount",0);
        int CURRENTBEGINNERCOUNT = getResources().getStringArray(R.array.intermediate_words).length;

        if(CURRENTBEGINNERCOUNT > PREVIOUSBEGINNERCOUNT){

            for(int i = PREVIOUSBEGINNERCOUNT; i < CURRENTBEGINNERCOUNT; i++){

                intermediateDatabase.insertData(""+i,"false","false");

            }
            sp.edit().putInt("intermediateWordCount",CURRENTBEGINNERCOUNT).apply();




        }else {

            Toast.makeText(getContext(),"NO new words added intermediate",Toast.LENGTH_SHORT).show();


        }

    }

    private void addAdvanceWordToSQLite(){
        advanceDatabase = new AdvancedWordDatabase(getContext());
        SharedPreferences sp = getActivity().getSharedPreferences("com.example.shamsulkarim.vastvocabulary", Context.MODE_PRIVATE);


        if(!sp.contains("advanceWordCount")){
            final int advanceWordLength = getResources().getStringArray(R.array.advanced_words).length;
            sp.edit().putInt("advanceWordCount",advanceWordLength).apply();

            for(int i = 0; i < advanceWordLength; i++){

                advanceDatabase.insertData(""+i,"false","false");

            }

        }
        int PREVIOUSBEGINNERCOUNT = sp.getInt("advanceWordCount",0);
        int CURRENTBEGINNERCOUNT = getResources().getStringArray(R.array.advanced_words).length;


        if(CURRENTBEGINNERCOUNT > PREVIOUSBEGINNERCOUNT){


            for(int i = PREVIOUSBEGINNERCOUNT; i < CURRENTBEGINNERCOUNT; i++){

                advanceDatabase.insertData(""+i,"false","false");




            }
            sp.edit().putInt("advanceWordCount",CURRENTBEGINNERCOUNT).apply();




        }else {

            Toast.makeText(getContext(),"NO new words added advance",Toast.LENGTH_SHORT).show();


        }







    }



























    private void spinnerInitializatin(View v){

        String[] ITEMS = {"BEGINNER", "INTERMEDIATE", "ADVANCE"};
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

            words.add(new Word(wordArray[i],translationArray[i],pronunciationArray[i],grammarArray[i],exampleArray1[i]));

        }



    }

    private void beginnerWordInitialization2(View v){

        words.clear();

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



    }

    private void intermediateWordInitialization(View v){
        words.clear();

        String[] wordArray = getResources().getStringArray(R.array.intermediate_words);
        String[] translationArray = getResources().getStringArray(R.array.intermediate_translation);
        String[] grammarArray = getResources().getStringArray(R.array.intermediate_grammar);
        String[] pronunciationArray = getResources().getStringArray(R.array.intermediate_pronunciation);
        String[] exampleArray1 = getResources().getStringArray(R.array.intermediate_example1);

        for(int i = 0 ; i < wordArray.length; i++){

            words.add(new Word(wordArray[i],translationArray[i],pronunciationArray[i],grammarArray[i],exampleArray1[i]));

        }


        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view_word);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new WordRecyclerViewAdapter(getContext(), words);
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

            words.add(new Word(wordArray[i],translationArray[i],pronunciationArray[i],grammarArray[i],exampleArray1[i]));

        }


        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view_word);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new WordRecyclerViewAdapter(getContext(), words);
        recyclerView.setAdapter(adapter);

    }



}
