package com.example.shamsulkarim.vastvocabulary;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shamsulkarim.vastvocabulary.WordAdapters.IntermediateAdapter;
import com.example.shamsulkarim.vastvocabulary.WordAdapters.WordRecyclerViewAdapter;
import com.example.shamsulkarim.vastvocabulary.WordAdapters.advanceAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;


public class Wordactivity extends Fragment {

    MaterialSpinner spinner;


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Object> words = new ArrayList<>();
    private List<String> beginnerFav;
    Toolbar toolbar;
    BeginnerWordDatabase beginnerDatabase;
    IntermediatewordDatabase intermediatewordDatabase;
    AdvancedWordDatabase advancedWordDatabase;
    SharedPreferences sp;
    int languageId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_wordactivity, container, false);

//        beginnerWordInitialization();

        sp = v.getContext().getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);
        beginnerFav = new ArrayList<>();
        beginnerDatabase = new BeginnerWordDatabase(getContext());
        intermediatewordDatabase = new IntermediatewordDatabase(getContext());
        advancedWordDatabase = new AdvancedWordDatabase(getContext());
        toolbar = (Toolbar) v.findViewById(R.id.word_toolbar);
        toolbar.setTitle("Words");
        toolbar.setTitleTextColor(Color.parseColor("#673AB7"));
        setHasOptionsMenu(true);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        languageId = sp.getInt("language",0);


//
//        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view_word);
//        layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setHasFixedSize(true);
//        adapter = new WordRecyclerViewAdapter(getContext(), words);
//        recyclerView.setAdapter(adapter);

        return v;
    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.learned_toolbar_menus, menu);

        MenuItem item = menu.findItem(R.id.spinner_learned);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);


        int selection = 0;

        if (!sp.contains("prevWordSelection")) {

            sp.edit().putInt("prevWordSelection", 0).apply();
        } else {

            selection = sp.getInt("prevWordSelection", 0);
        }


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(selection);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(getContext(), "i: " + i + " l: " + l, Toast.LENGTH_SHORT).show();


                if (i == 0) {


                    sp.edit().putInt("prevWordSelection", 0).apply();
                    beginnerWordInitialization2(getView());
                }
                if (i == 1) {


                    sp.edit().putInt("prevWordSelection", 1).apply();
                    intermediateWordInitialization(getView());
                }

                if (i == 2) {

                    sp.edit().putInt("prevWordSelection", 2).apply();
                    advanceWordInitialization(getView());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


    private void beginnerWordInitialization2(View v) {

        words.clear();
        String[] extraArray = new String[getResources().getStringArray(R.array.beginner_words).length];
        String[] beginnerWordArray = getResources().getStringArray(R.array.beginner_words);
        String[] beginnerTranslationArray = getResources().getStringArray(R.array.beginner_translation);
        String[] beginnerPronunciationArray = getResources().getStringArray(R.array.beginner_pronunciation);
        String[] beginnerGrammarArray = getResources().getStringArray(R.array.beginner_grammar);
        String[] beginnerExampleArray1 = getResources().getStringArray(R.array.beginner_example1);





        if (languageId == 1) {


            extraArray = getResources().getStringArray(R.array.beginner_spanish);
        }
        if (languageId == 2) {


            extraArray = getResources().getStringArray(R.array.beginner_bengali);
        }
        if (languageId == 3) {


            extraArray = getResources().getStringArray(R.array.beginner_hindi);
        }

        int len = getResources().getStringArray(R.array.beginner_words).length;

        int[] favNum = beginnerFavoriteWordChecker();

        for (int i = 0; i < len; i++) {

            words.add(new Word(beginnerWordArray[i], beginnerTranslationArray[i], extraArray[i], beginnerPronunciationArray[i], beginnerGrammarArray[i], beginnerExampleArray1[i], "beginner", favNum[i],i));

        }
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_word);
        if (MainActivity.connected) {

            addNativeExpressAds();

        }

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new WordRecyclerViewAdapter(getContext(), words);
        recyclerView.setAdapter(adapter);
        if (MainActivity.connected) {


            setUpAndLoadNativeExpressAds();
        }


    }

    private void intermediateWordInitialization(View v) {
        words.clear();


        String[] extraArray = new String[getResources().getStringArray(R.array.beginner_words).length];
        String[] intermediateWordArray = getResources().getStringArray(R.array.intermediate_words);
        String[] intermediateTranslationArray = getResources().getStringArray(R.array.intermediate_translation);
        String[] intermediatePronunciationArray = getResources().getStringArray(R.array.intermediate_pronunciation);
        String[] intermediateGrammarArray = getResources().getStringArray(R.array.intermediate_grammar);
        String[] intermediateExampleArray1 = getResources().getStringArray(R.array.intermediate_example1);



        if (languageId == 1) {

            extraArray = getResources().getStringArray(R.array.intermediate_spanish);
        }
        if (languageId == 2) {

            extraArray = getResources().getStringArray(R.array.intermediate_bengali);
        }
        if (languageId == 3) {

            extraArray = getResources().getStringArray(R.array.intermediate_hindi);
        }
        int[] favNum = intermediateFavoriteWordChecker();
        int intermediateLen = getResources().getStringArray(R.array.intermediate_words).length;



        for (int i = 0; i < intermediateLen; i++) {

            words.add(new Word(intermediateWordArray[i], intermediateTranslationArray[i], extraArray[i], intermediatePronunciationArray[i], intermediateGrammarArray[i], intermediateExampleArray1[i], "intermediate", favNum[i],i));

        }


        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_word);
        if (MainActivity.connected) {

            addNativeExpressAds();

        }

//        if (MainActivity.connected) {
//
//            addNativeExpressAds();
//            setUpAndLoadNativeExpressAds();
//        }

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new IntermediateAdapter(getContext(), words);
        recyclerView.setAdapter(adapter);
        if (MainActivity.connected) {


            setUpAndLoadNativeExpressAds();
        }



    }

    private void advanceWordInitialization(View v) {

        words.clear();


        String[] extraArray = new String[getResources().getStringArray(R.array.beginner_words).length];
        String[] advanceWordArray = getResources().getStringArray(R.array.advanced_words);
        String[] advanceTranslationArray = getResources().getStringArray(R.array.advanced_translation);
        String[] advancePronunciationArray = getResources().getStringArray(R.array.advanced_pronunciation);
        String[] advanceGrammarArray = getResources().getStringArray(R.array.advanced_grammar);
        String[] advanceExampleArray1 = getResources().getStringArray(R.array.advanced_example1);




        if (languageId == 1) {

            extraArray = getResources().getStringArray(R.array.advance_spanish);
        }
        if (languageId == 2) {

            extraArray = getResources().getStringArray(R.array.advance_bengali);
        }
        if (languageId == 3) {

            extraArray = getResources().getStringArray(R.array.advance_hindi);
        }

        int[] favNum = advanceFavoriteWordChecker();
        int len = getResources().getStringArray(R.array.advanced_words).length;

        for (int i = 0; i < len; i++) {

            words.add(new Word(advanceWordArray[i], advanceTranslationArray[i], extraArray[i], advancePronunciationArray[i], advanceGrammarArray[i], advanceExampleArray1[i], "advance", favNum[i],i));

        }


        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_word);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_word);
        if (MainActivity.connected) {

            addNativeExpressAds();

        }
//        if (MainActivity.connected) {
//
//            addNativeExpressAds();
//            setUpAndLoadNativeExpressAds();
//        }

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new advanceAdapter(getContext(), words);
        recyclerView.setAdapter(adapter);

        if (MainActivity.connected) {


            setUpAndLoadNativeExpressAds();
        }


    }


    private void setUpAndLoadNativeExpressAds() {
        // Use a Runnable to ensure that the RecyclerView has been laid out before setting the
        // ad size for the Native Express ad. This allows us to set the Native Express ad's
        // width to match the full width of the RecyclerView.
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                final float scale = Wordactivity.this.getResources().getDisplayMetrics().density;
                // Set the ad size and ad unit ID for each Native Express ad in the items list.
                for (int i = 0; i <= words.size(); i += 12) {
                    final NativeExpressAdView adView =
                            (NativeExpressAdView) words.get(i);
                    final RelativeLayout cardView = (RelativeLayout) getView().findViewById(R.id.ad_view_container);
                    final int adWidth = cardView.getWidth() - cardView.getPaddingLeft()
                            - cardView.getPaddingRight();
                    AdSize adSize = new AdSize((int) (adWidth / scale), 80);
                    adView.setAdSize(adSize);
                    adView.setAdUnitId("ca-app-pub-7815894766256601/9351876774");
                }

                // Load the first Native Express ad in the items list.
                loadNativeExpressAd(0);
            }
        },100L);
    }

    private void loadNativeExpressAd(final int index) {

        if (index >= words.size()) {
            return;
        }

        Object item = words.get(index);
        if (!(item instanceof NativeExpressAdView)) {
            throw new ClassCastException("Expected item at index " + index + " to be a Native"
                    + " Express ad.");
        }

        final NativeExpressAdView adView = (NativeExpressAdView) item;

        // Set an AdListener on the NativeExpressAdView to wait for the previous Native Express ad
        // to finish loading before loading the next ad in the items list.
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                // The previous Native Express ad loaded successfully, call this method again to
                // load the next ad in the items list.
                loadNativeExpressAd(index + 12);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // The previous Native Express ad failed to load. Call this method again to load
                // the next ad in the items list.
                Log.e("MainActivity", "The previous Native Express ad failed to load. Attempting to"
                        + " load the next Native Express ad in the items list.");
                loadNativeExpressAd(index + 12);
            }
        });

        // Load the Native Express ad.
        adView.loadAd(new AdRequest.Builder().build());
    }

    private void addNativeExpressAds() {

        // Loop through the items array and place a new Native Express ad in every ith position in
        // the items List.
        for (int i = 0; i <= words.size(); i += 12) {
            final NativeExpressAdView adView = new NativeExpressAdView(getContext());
            words.add(i, adView);
        }
    }

    private int[] beginnerFavoriteWordChecker() {

        beginnerFav.clear();
        Cursor beginnerRes = beginnerDatabase.getData();


        while (beginnerRes.moveToNext()) {

            beginnerFav.add(beginnerRes.getString(2));

        }


        int[] favNum = new int[beginnerFav.size()];

        for (int i = 0; i < beginnerFav.size(); i++) {

            if (beginnerFav.get(i).equalsIgnoreCase("true")) {

                favNum[i] = 1;


            } else {

                favNum[i] = 0;

            }

        }

        return favNum;


    }

    private int[] intermediateFavoriteWordChecker() {

        beginnerFav.clear();
        Cursor beginnerRes = intermediatewordDatabase.getData();


        while (beginnerRes.moveToNext()) {

            beginnerFav.add(beginnerRes.getString(2));

        }


        int[] favNum = new int[beginnerFav.size()];

        for (int i = 0; i < beginnerFav.size(); i++) {

            if (beginnerFav.get(i).equalsIgnoreCase("true")) {

                favNum[i] = 1;


            } else {

                favNum[i] = 0;

            }

        }

        return favNum;


    }

    private int[] advanceFavoriteWordChecker() {

        beginnerFav.clear();
        Cursor beginnerRes = advancedWordDatabase.getData();


        while (beginnerRes.moveToNext()) {

            beginnerFav.add(beginnerRes.getString(2));

        }


        int[] favNum = new int[beginnerFav.size()];

        for (int i = 0; i < beginnerFav.size(); i++) {

            if (beginnerFav.get(i).equalsIgnoreCase("true")) {

                favNum[i] = 1;


            } else {

                favNum[i] = 0;

            }

        }

        return favNum;


    }
}