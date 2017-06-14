package com.example.shamsulkarim.vastvocabulary;


import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shamsulkarim.vastvocabulary.Practice.Practice;
import com.example.shamsulkarim.vastvocabulary.WordAdapters.IntermediateAdapter;
import com.example.shamsulkarim.vastvocabulary.WordAdapters.WordRecyclerViewAdapter;
import com.example.shamsulkarim.vastvocabulary.WordAdapters.advanceAdapter;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;

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
    private List<Object> words = new ArrayList<>();
    boolean isShowingFabOption = false;
    public static List<Word> practiceWords = new ArrayList<>();
    boolean connected;

    TextView havenotlearned;
    private List<String> beginnerFav;
    BeginnerWordDatabase beginnerDatabase;
    IntermediatewordDatabase intermediatewordDatabase;
    AdvancedWordDatabase advancedWordDatabase;
    SharedPreferences sp;

    FloatingActionButton fab1,fab2,fab3;
    FloatingActionMenu fam;
    int languageId;


    public static List<String> bWord,aWord,iWord;
    List<Integer> bWordDatabasePosition, aWordDatabasePosition, iWordDatabasePosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_learned_words,container,false);
        practiceWords = new ArrayList<>();

        ConnectivityManager connectivityManager = (ConnectivityManager)v.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else{
            connected = false;
        }
        sp = v.getContext().getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);
        sp.getInt("language",0);
        beginnerFav = new ArrayList<>();
        intermediatewordDatabase = new IntermediatewordDatabase(getContext());
        advancedWordDatabase = new AdvancedWordDatabase(getContext());
        beginnerDatabase = new BeginnerWordDatabase(getContext());
        havenotlearned = (TextView)v.findViewById(R.id.havenotlearned);
        havenotlearned.setVisibility(View.INVISIBLE);
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
//        beginnerWordInitialization();


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





        int selection = 0;
        if (!sp.contains("prevLearnedSelection")){

            sp.edit().putInt("prevLearnedSelection",0).apply();
        }else {

            selection = sp.getInt("prevLearnedSelection",0);
        }



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(selection);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(getContext(),"i: "+i+" l: "+l,Toast.LENGTH_SHORT).show();



                if(i == 0){


                    sp.edit().putInt("prevLearnedSelection",0).apply();
                    beginnerWordInitialization();
                }
                if(i == 1){


                    sp.edit().putInt("prevLearnedSelection",1).apply();
                    intermediateWordInitialization();
                }

                if(i == 2){

                    sp.edit().putInt("prevLearnedSelection",2).apply();
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

        if(words.size() <= 0){
            havenotlearned.setVisibility(View.VISIBLE);
        }else {

            havenotlearned.setVisibility(View.INVISIBLE);

            if (connected) {

                addNativeExpressAds();

            }
        }

        adapter = new WordRecyclerViewAdapter(getContext(), words);
        recyclerView.setAdapter(adapter);

        if (words.size()> 0){

            if (connected) {


                setUpAndLoadNativeExpressAds();
            }

        }




    }


    private void advanceWordInitialization(){

        words.clear();


        words = getAdvanceWords();

        if(words.size() <= 0){
            havenotlearned.setVisibility(View.VISIBLE);
        }else {

            havenotlearned.setVisibility(View.INVISIBLE);

            if (connected) {

                addNativeExpressAds();

            }
        }

        adapter = new advanceAdapter(getContext(), words);
        recyclerView.setAdapter(adapter);

        if (words.size()> 0){

            if (connected) {


                setUpAndLoadNativeExpressAds();
            }

        }


    }

    private void intermediateWordInitialization(){

        words.clear();
        words = getIntermediateWords();


        if(words.size() <= 0){
            havenotlearned.setVisibility(View.VISIBLE);
        }else {

            havenotlearned.setVisibility(View.INVISIBLE);

            if (connected) {

                addNativeExpressAds();

            }
        }


        adapter = new IntermediateAdapter(getContext(), words);
        recyclerView.setAdapter(adapter);


        if (words.size()> 0){

            if (connected) {


                setUpAndLoadNativeExpressAds();
            }

        }
    }





    public List<Object> getAdvanceWords(){
        int advanceLearnedCount = sp.getInt("advanced",0);
         List<Object> getWords = new ArrayList<>();

        String[] extraArray = new String[getResources().getStringArray(R.array.advanced_words).length];
        String[] advanceWordArray = getResources().getStringArray(R.array.advanced_words);
        String[] advanceTranslationArray = getResources().getStringArray(R.array.advanced_translation);
        String[] advancePronunciationArray = getResources().getStringArray(R.array.advanced_pronunciation);
        String[] advanceGrammarArray = getResources().getStringArray(R.array.advanced_grammar);
        String[] advanceExampleArray1 = getResources().getStringArray(R.array.advanced_example1);




        if(languageId == 0){

            for(int i = 0; i < getResources().getStringArray(R.array.advanced_words).length; i++){


                extraArray[i] = "";
            }

        }

        if(languageId == 1){

            extraArray = getResources().getStringArray(R.array.advance_spanish);
        }
        if(languageId == 2){

            extraArray = getResources().getStringArray(R.array.advance_bengali);
        }
        if(languageId == 3){

            extraArray = getResources().getStringArray(R.array.advance_hindi);
        }


        int[] favNum = advanceFavoriteWordChecker();



        for(int i = 0 ; i < advanceLearnedCount; i++){

            getWords.add(new Word(advanceWordArray[i],advanceTranslationArray[i],extraArray[i],advancePronunciationArray[i],advanceGrammarArray[i],advanceExampleArray1[i],"advance",favNum[i],i));

        }

        return getWords;

    }

    public List<Object> getIntermediateWords(){
        List<Object> getWords = new ArrayList<>();
        int beginnerLearnedCount = sp.getInt("intermediate",0);

        String[]  intermediateWordArray= getResources().getStringArray(R.array.intermediate_words);
        String[] intermediateTranslationArray = getResources().getStringArray(R.array.intermediate_translation);
        String[] intermediatePronunciationArray = getResources().getStringArray(R.array.intermediate_pronunciation);
        String[] intermediateGrammarArray = getResources().getStringArray(R.array.intermediate_grammar);
        String[] intermediateExampleArray1 = getResources().getStringArray(R.array.intermediate_pronunciation);
        String[] extraArray = new String[getResources().getStringArray(R.array.intermediate_words).length];

        if(languageId == 0){

            for(int i = 0; i < getResources().getStringArray(R.array.intermediate_words).length; i++){


                extraArray[i] = "";
            }

        }

        if(languageId == 1){

            extraArray = getResources().getStringArray(R.array.intermediate_spanish);
        }
        if(languageId == 2){

            extraArray = getResources().getStringArray(R.array.intermediate_bengali);
        }
        if(languageId == 3){

            extraArray = getResources().getStringArray(R.array.intermediate_hindi);
        }



        int[] favNum = intermediateFavoriteWordChecker();

        for(int i = 0 ; i < beginnerLearnedCount; i++){

            getWords.add(new Word(intermediateWordArray[i], intermediateTranslationArray[i],extraArray[i], intermediatePronunciationArray[i], intermediateGrammarArray[i], intermediateExampleArray1[i],"intermediate",favNum[i],i));

        }

        return getWords;


    }

    public List<Object> getBeginnerWords(){

        List<Object> getWords = new ArrayList<>();

        SharedPreferences sp = getContext().getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);
        int beginnerLearnedCount = sp.getInt("beginner",0);
        String[]  beginnerWordArray= getResources().getStringArray(R.array.beginner_words);
        String[] beginnerTranslationArray = getResources().getStringArray(R.array.beginner_translation);
        String[] beginnerPronunciationArray = getResources().getStringArray(R.array.beginner_pronunciation);
        String[] beginnerGrammarArray = getResources().getStringArray(R.array.beginner_grammar);
        String[] beginnerExampleArray1 = getResources().getStringArray(R.array.beginner_example1);

        String[] extraArray = new String[getResources().getStringArray(R.array.beginner_words).length];

        if(languageId == 0){

            for(int i = 0; i < getResources().getStringArray(R.array.beginner_words).length; i++){


                extraArray[i] = "";
            }

        }

        if(languageId == 1){

            extraArray = getResources().getStringArray(R.array.beginner_spanish);
        }
        if(languageId == 2){

            extraArray = getResources().getStringArray(R.array.beginner_bengali);
        }
        if(languageId == 3){

            extraArray = getResources().getStringArray(R.array.beginner_hindi);
        }

        int[] favNum = favoriteWordChecker();



        for(int i = 0 ; i < beginnerLearnedCount; i++){

            getWords.add(new Word(beginnerWordArray[i],beginnerTranslationArray[i],extraArray[i],beginnerPronunciationArray[i],beginnerGrammarArray[i],beginnerExampleArray1[i],"beginner",favNum[i],i));

        }

        return getWords;

    }


    @Override
    public void onClick(View view) {

        if(view.getId() == fab1.getId()){



            sp.edit().putString("practice","learned").apply();

            practiceWords.clear();
            List <Object> wordObjects = getAdvanceWords();
            int wordObjectsSize = wordObjects.size();

            for(int i =0; i < wordObjectsSize; i++){

                Word word = (Word) wordObjects.get(i);
                practiceWords.add(word);

            }

            if(practiceWords.size() >= 5){
                getContext().startActivity(new Intent(getContext(), Practice.class));


            }else {
                Toast.makeText(getContext(),"not enough words", Toast.LENGTH_SHORT).show();

            }

        }

        if(view.getId() == fab2.getId()){


            sp.edit().putString("practice","learned").apply();

            practiceWords.clear();
//            practiceWords =
            List <Object> wordObjects = getIntermediateWords();
            int wordObjectsSize = wordObjects.size();

            for(int i =0; i < wordObjectsSize; i++){

                Word word = (Word) wordObjects.get(i);
                practiceWords.add(word);

            }

            if(practiceWords.size() >= 5){
                getContext().startActivity(new Intent(getContext(), Practice.class));


            }else {
                Toast.makeText(getContext(),"not enough words", Toast.LENGTH_SHORT).show();

            }
        }

        if(view.getId() == fab3.getId()){


            MainActivity.practice = "learned";
            sp.edit().putString("practice","learned").apply();

            practiceWords.clear();
            List <Object> wordObjects = getBeginnerWords();
            int wordObjectsSize = wordObjects.size();

            for(int i =0; i < wordObjectsSize; i++){

                Word word = (Word) wordObjects.get(i);
                practiceWords.add(word);

            }

            if(practiceWords.size() >= 5){
                getContext().startActivity(new Intent(getContext(), Practice.class));

                isShowingFabOption = false;

            }else {
                Toast.makeText(getContext(),"not enough words", Toast.LENGTH_SHORT).show();

                isShowingFabOption = false;
            }
        }

    }

    private void setUpAndLoadNativeExpressAds() {
        // Use a Runnable to ensure that the RecyclerView has been laid out before setting the
        // ad size for the Native Express ad. This allows us to set the Native Express ad's
        // width to match the full width of the RecyclerView.
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                final float scale = LearnedWords.this.getResources().getDisplayMetrics().density;
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

    private int[] favoriteWordChecker() {

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
