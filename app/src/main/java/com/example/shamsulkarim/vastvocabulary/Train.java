package com.example.shamsulkarim.vastvocabulary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Train extends AppCompatActivity {


    boolean firstTime = true;
    RelativeLayout translation_layout;
    Button button1,button2,button3,button4;
    List<Word> fiveWords, learnedWords, buttonQuestion, words, wordForQuestions;
    String[] sendWord = new String[5];
    String[] sendTranslation = new String[5];



    int[] wordCounter = new int[5];
    String[] wordArray, translationArray,sendWords,grammarArray,pronunArray,example1array,example2Array,example3Array, beginnerTranslationExtra;
    TextView wordView, translationView, english,grammarView,pronunView,exampleView1,exampleView2,exampleView3, translationExtraView,secondLanguageName;
    ImageView next, fakeNext, trainPlanet, whiteBackground, boardTopBackground;
    int id = 0;
    int ia = 0;
    int question = 4;
    int COUNTWORDS = 0;
    int wordsPerSession = 5;
    String level;
    double screenInches;
    String toastMsg,secondLanguage;
    int screenSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        // picking layout
        if(SplashScreen.languageId >= 1){

            setContentView(R.layout.activity_train_extra);
        }else {
            setContentView(R.layout.activity_train);
        }
        // Determining Screen Size ---------------------------------------------------------------
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        double wi=(double)width/(double)dm.xdpi;
        double hi=(double)height/(double)dm.ydpi;
        double x = Math.pow(wi,2);
        double y = Math.pow(hi,2);
        screenInches = Math.sqrt(x+y);


        screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;


        switch(screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                toastMsg = "Large screen";
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                toastMsg = "Normal screen";
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                toastMsg = "Small screen";
                break;
            default:
                toastMsg = "Screen size is neither large, normal or small";
        }
        //----------------------------------------------------------------------------------------



        SharedPreferences sp = this.getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);
        level = sp.getString("level","NOTHING");

        // INITIALIZATION
        buttonInitializations();
        textViewInitializations();
        gettingResources();
        arrayInitializations();


        Toast.makeText(this,level,Toast.LENGTH_SHORT).show();

        // INITIALIZING WORDS
        addingNewWords();
        wordCounter[0] = words.size();
        wordCounter[1] = learnedWords.size();

        if( !fiveWords.isEmpty()){

            if(SplashScreen.languageId >= 1){

                translationExtraView.setText(fiveWords.get(ia).getExtra());
                secondLanguage = SplashScreen.languageName[SplashScreen.languageId];

                secondLanguageName.setText(secondLanguage);
            }
            wordView.setText(fiveWords.get(ia).getWord());
            translationView.setText(fiveWords.get(ia).getTranslation());
            pronunView.setText(fiveWords.get(ia).getPronun());
            grammarView.setText(fiveWords.get(ia).getGrammar());
            exampleView1.setText(fiveWords.get(ia).getExample1());
            exampleView2.setText(fiveWords.get(ia).getExample2());
            exampleView3.setText(fiveWords.get(ia).getExample3());;
            fiveWords.get(ia).setSeen(true);

            ia++;

        }

         comeInAnimation();






    }
    //----------------------------------------------------------------------------------------------

    public  void neext(View view){

        final View view1 = view;

        Handler handler = new Handler();

        nextAnimation();


        handler.postDelayed(new Runnable() {


            @Override
            public void run() {
                nextWord(view1);


            }
        },400);
    }






    public void nextWord(View view) {


        // To Next Activity
        if (fiveWords.get(fiveWords.size() - 2).getCount() == 2) {
            addLearnedWordsToSend();
            this.finish();
            Intent intent = new Intent(this, DisplayLearningScore.class);
            intent.putExtra("word", sendWord).putExtra("translation",sendTranslation).putExtra("wordCount", wordCounter).putExtra("level",level);
            this.startActivity(intent);

        }else{
            //----------

            /// IF SEEN IT TRUE THEN IT WOULD START TO ASK QUESTION
            if (id < fiveWords.size()  && fiveWords.size() != 0) {

                if (fiveWords.get(id).isSeen()) {
                    next.setVisibility(View.INVISIBLE);
                    boardTopBackground.setVisibility(View.INVISIBLE);
                    whiteBackground.setVisibility(View.INVISIBLE);
                    button1.setVisibility(View.VISIBLE);
                    button2.setVisibility(View.VISIBLE);
                    button3.setVisibility(View.VISIBLE);
                    button4.setVisibility(View.VISIBLE);

                    if(SplashScreen.languageId >= 1){

                        translationExtraView.setText("");
                        secondLanguageName.setText("");
                        english.setText("");
                    }
                    translationView.setText("");
                    grammarView.setText("");
                    pronunView.setText("");
                    exampleView1.setText("");
                    exampleView2.setText("");
                    exampleView3.setText("");


                    answers(view);
                    settingQuestionButton();

                }

                /// IF ISSEEN IS FALSE IT WOULD SHOW WORDS WITH TRANSLATION
                if (ia < fiveWords.size() && !fiveWords.get(ia).isSeen()) {
                    // wordView.setText(fiveWords.get(ia).getWord());
                    Toast.makeText(this, "IA: " + ia, Toast.LENGTH_SHORT).show();
                    showWords(ia);
                    ia++;
                }

                // IF ID EQUALS TO FIVEWORDS SIZE ID WOULD SET TO ZERO.
                if (id == fiveWords.size()) {
                    id = 0;

                }

            }


            //----------
        }

}

    public void showWords(int index) {
        next.setVisibility(View.VISIBLE);
        boardTopBackground.setVisibility(View.VISIBLE);
        whiteBackground.setVisibility(View.VISIBLE);
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
        translationView.setText(fiveWords.get(index).getTranslation());

        if(SplashScreen.languageId >= 1){
            translationExtraView.setText(fiveWords.get(index).getExtra());
            english.setText("english");
            secondLanguageName.setText(secondLanguage);
        }

        wordView.setText(fiveWords.get(index).getWord());

        pronunView.setText(fiveWords.get(index).getPronun());
        grammarView.setText(fiveWords.get(index).getGrammar());
        exampleView1.setText(fiveWords.get(index).getExample1());
        exampleView2.setText(fiveWords.get(index).getExample2());
        exampleView3.setText(fiveWords.get(index).getExample3());

        fiveWords.get(index).setSeen(true);
    }

    // Setting Up Qustions For Buttons
    public void settingQuestionButton() {
        buttonQuestion = settingUpQustion();

        if(SplashScreen.languageId >= 1){


            button1.setText(buttonQuestion.get(0).getExtra());
            button2.setText(buttonQuestion.get(1).getExtra());
            button3.setText(buttonQuestion.get(2).getExtra());
            button4.setText(buttonQuestion.get(3).getExtra());
        }else{

            button1.setText(buttonQuestion.get(0).getTranslation());
            button2.setText(buttonQuestion.get(1).getTranslation());
            button3.setText(buttonQuestion.get(2).getTranslation());
            button4.setText(buttonQuestion.get(3).getTranslation());

        }


        wordView.setText(fiveWords.get(id).getWord());

    }

    // This method gets called when you are asnwering the questions
    public void answers(View view) {


        String answer;


        if (view.getId() == button1.getId()) {
            answer = button1.getText().toString();

            if(SplashScreen.languageId >= 1){


                if (answer.equalsIgnoreCase(fiveWords.get(id).getExtra())) {
                    // Toast.makeText(this,"Correct",Toast.LENGTH_SHORT).show();
                    fiveWords.get(id).setCount(1);
//                    countView.setText(fiveWords.get(id).getCount() + " ");
                    id++;
                } else {
                    showAnswer(fiveWords.get(id));
                    // Toast.makeText(this,"Wrong ",Toast.LENGTH_SHORT).show();

                }


            }else {


                if (answer.equalsIgnoreCase(fiveWords.get(id).getTranslation())) {
                    // Toast.makeText(this,"Correct",Toast.LENGTH_SHORT).show();
                    fiveWords.get(id).setCount(1);
//                    countView.setText(fiveWords.get(id).getCount() + " ");
                    id++;
                } else {
                    showAnswer(fiveWords.get(id));
                    // Toast.makeText(this,"Wrong ",Toast.LENGTH_SHORT).show();

                }



            }




        }
        // Button 2

        if (view.getId() == button2.getId()) {
            answer = button2.getText().toString();


            if (SplashScreen.languageId >= 1){

                if (answer.equalsIgnoreCase(fiveWords.get(id).getExtra())) {
                    fiveWords.get(id).setCount(1);
//                    countView.setText(fiveWords.get(id).getCount() + " ");
                    id++;

                } else {
                    showAnswer(fiveWords.get(id));
                }


            }else {


                if (answer.equalsIgnoreCase(fiveWords.get(id).getTranslation())) {
                    fiveWords.get(id).setCount(1);
//                    countView.setText(fiveWords.get(id).getCount() + " ");
                    id++;

                } else {
                    showAnswer(fiveWords.get(id));
                }


            }


        }



        // Button 3
        if (view.getId() == button3.getId()) {
            answer = button3.getText().toString();


            if(SplashScreen.languageId >= 1){
                if (answer.equalsIgnoreCase(fiveWords.get(id).getExtra())) {
                    fiveWords.get(id).setCount(1);
//                    countView.setText(fiveWords.get(id).getCount() + " ");
                    id++;

                } else {
                    showAnswer(fiveWords.get(id));
                }




            }else {


                if (answer.equalsIgnoreCase(fiveWords.get(id).getTranslation())) {
                    fiveWords.get(id).setCount(1);
//                    countView.setText(fiveWords.get(id).getCount() + " ");
                    id++;

                } else {
                    showAnswer(fiveWords.get(id));
                }


            }


        }


        // Button 4
        if (view.getId() == button4.getId()) {
            answer = button4.getText().toString();


            if(SplashScreen.languageId >= 1 ){



                if (answer.equalsIgnoreCase(fiveWords.get(id).getExtra())) {
                    fiveWords.get(id).setCount(1);
//                    countView.setText(fiveWords.get(id).getCount() + "");
                    id++;

                } else {
                    showAnswer(fiveWords.get(id));
                }


            }else {



                if (answer.equalsIgnoreCase(fiveWords.get(id).getTranslation())) {
                    fiveWords.get(id).setCount(1);
//                    countView.setText(fiveWords.get(id).getCount() + "");
                    id++;

                } else {
                    showAnswer(fiveWords.get(id));
                }


            }


        }
        if (id == fiveWords.size()) {
            id = 0;

        }

    }

    // This gets called when you have answered a wrong answer

    public void showAnswer(Word wordAnswer) {
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
        fakeNext.setVisibility(View.VISIBLE);

        if(SplashScreen.languageId >= 1){
            translationExtraView.setText(wordAnswer.getExtra());
            secondLanguageName.setText(secondLanguage);
            english.setText("english");
        }
        boardTopBackground.setVisibility(View.VISIBLE);
        whiteBackground.setVisibility(View.VISIBLE);
        translationView.setText(wordAnswer.getTranslation());
        pronunView.setText(wordAnswer.getPronun());
        grammarView.setText(wordAnswer.getGrammar());
        exampleView1.setText(wordAnswer.getExample1());
        exampleView2.setText(wordAnswer.getExample2());
        exampleView3.setText(wordAnswer.getExample3());

//        translation_layout.setVisibility(View.VISIBLE);

        wrongAnswerAnimation();

    }


    // DummyNext
    public void dummyNext(View view) {
//        translation_layout.setVisibility(View.INVISIBLE);
        boardTopBackground.setVisibility(View.INVISIBLE);
        whiteBackground.setVisibility(View.INVISIBLE);
        translationView.setText("");
        grammarView.setText("");
        pronunView.setText("");
        exampleView1.setText("");
        exampleView2.setText("");
        exampleView3.setText("");

        if(SplashScreen.languageId >= 1) {
            translationExtraView.setText("");
            secondLanguageName.setText("");
            english.setText("");
        }

        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        button4.setVisibility(View.VISIBLE);
        fakeNext.setVisibility(View.INVISIBLE);
    }

    private List<Word> settingUpQustion() {
        buttonQuestion.clear();
        wordForQuestions = new ArrayList<>();
        wordForQuestions.addAll(words);

        Collections.shuffle(wordForQuestions);
        for (int i = 0; i < question-1; i++) {
            buttonQuestion.add(wordForQuestions.get(i));


        }
        if (buttonQuestion.contains(fiveWords.get(id))) {

            for (int k = 0; k < fiveWords.size(); k++) {

                if (!buttonQuestion.contains(fiveWords.get(k)) && buttonQuestion.size() < 4) {
                    buttonQuestion.add(fiveWords.get(k));
                }

            }


        } else {
            buttonQuestion.add(fiveWords.get(id));
        }

        if(buttonQuestion.size() <= 3){
            buttonQuestion.add(wordForQuestions.get(1));
        }

        Collections.shuffle(buttonQuestion);

        return buttonQuestion;

    }


    private void addLearnedWordsToSend() {
        for (int i = 0; i < fiveWords.size(); i++) {
            sendWord[i] = fiveWords.get(i).getWord();
            sendTranslation[i] = fiveWords.get(i).getTranslation();
            learnedWords.add(fiveWords.get(i));
            if (words.contains(fiveWords.get(i))) {
                words.remove(words.indexOf(fiveWords.get(i)));
            }
        }

        wordCounter[0] = words.size();
        wordCounter[1] = learnedWords.size();
    }





    private void addingNewWords() {

        SharedPreferences sp = this.getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);
        if(!sp.contains(level)){
            sp.edit().putInt(level,COUNTWORDS).apply();
        }
        int countWords = sp.getInt(level,0);

        Toast.makeText(this, level+" "+countWords, Toast.LENGTH_SHORT).show();


        for (int i = 0; i < wordArray.length; i++) {
            words.add(new Word(wordArray[i], translationArray[i],beginnerTranslationExtra[i], pronunArray[i],grammarArray[i],example1array[i],example2Array[i],example3Array[i],""));

        }

        for (int j = 0; j < words.size(); j++) {
            if (learnedWords.size() > 0) {
                if (words.contains(learnedWords.get(j))) {

                    words.remove(words.indexOf(learnedWords.get(j)));
                }

            }
        }
        wordsPerSession += countWords;

        if( wordsPerSession > words.size()){
            wordsPerSession = words.size();
        }



        if(wordsPerSession <= words.size()){
            for ( int k = countWords; k < wordsPerSession; k++) {
                fiveWords.add(words.get(k));
            }
        }
        if(countWords >= words.size()){
            Toast.makeText(this, "There are no more Words", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this,NoWordLeft.class);
            this.startActivity(intent);
            this.finish();
        }





    }
    private void arrayInitializations() {
        words = new ArrayList<>();
        fiveWords = new ArrayList<>();
        learnedWords = new ArrayList<>();
        buttonQuestion = new ArrayList<>();
        sendWords = new String[5];
    }

    private void buttonInitializations() {

        fakeNext = (ImageView) findViewById(R.id.dummy_next);
        whiteBackground = (ImageView)findViewById(R.id.white_background_train);
        boardTopBackground = (ImageView)findViewById(R.id.board_top_background);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);

            button1.setVisibility(View.INVISIBLE);
            button2.setVisibility(View.INVISIBLE);
            button3.setVisibility(View.INVISIBLE);
            button4.setVisibility(View.INVISIBLE);

        fakeNext.setVisibility(View.INVISIBLE);

        next = (ImageView) findViewById(R.id.next);
        trainPlanet = (ImageView)findViewById(R.id.planet_train);



    }

    private void textViewInitializations() {


        wordView = (TextView) findViewById(R.id.word);
        translationView = (TextView) findViewById(R.id.translation);
        grammarView = (TextView) findViewById(R.id.grammar);
        pronunView = (TextView) findViewById(R.id.pronunciation);
        exampleView1 = (TextView) findViewById(R.id.example1);
        exampleView2 = (TextView) findViewById(R.id.example2);
        exampleView3 = (TextView) findViewById(R.id.example3);

        if(SplashScreen.languageId >= 1){
            translationExtraView = (TextView) findViewById(R.id.translationExtra_train);
            secondLanguageName = (TextView) findViewById(R.id.second_language_name);
            english = (TextView)findViewById(R.id.english);
        }






    }

    private void gettingResources() {
         beginnerTranslationExtra = new String[getResources().getStringArray(R.array.beginner_words).length];



        if(level.equalsIgnoreCase("beginner") ){


            // Choosing Planet Image according to screen size -----------------------------------

            if(screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL){


                if(screenInches < 4.8 ){

                    Toast.makeText(this,"< 5 ",Toast.LENGTH_SHORT).show();

                    trainPlanet.setImageResource(R.drawable.beginner_planet_train_normal_lessthan);

                }else {


                    trainPlanet.setImageResource(R.drawable.beginner_planet_train_normal);

                }


            }else {


                trainPlanet.setImageResource(R.drawable.beginner_planet_train);

            }

            if(screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE){

                next.setImageResource(R.drawable.beginner_next_train_xlarge);
                fakeNext.setImageResource(R.drawable.beginner_next_train_xlarge);

            }
            if( screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE){

                next.setImageResource(R.drawable.beginner_next_train_xlarge);
                fakeNext.setImageResource(R.drawable.beginner_next_train_xlarge);


            }
            else{

                next.setImageResource(R.drawable.beginner_next_train);
                fakeNext.setImageResource(R.drawable.beginner_next_train);

            }
            //-----------------------------------------------------------------------------------


            boardTopBackground.setBackgroundColor(Color.parseColor("#f05e2a"));
            button1.setBackgroundColor(Color.parseColor("#f05e2a"));
            button4.setBackgroundColor(Color.parseColor("#f05e2a"));
            button3.setBackgroundColor(Color.parseColor("#f05e2a"));
            button2.setBackgroundColor(Color.parseColor("#f05e2a"));

            wordArray = getResources().getStringArray(R.array.beginner_words);
            translationArray = getResources().getStringArray(R.array.beginner_translation);
            grammarArray =  getResources().getStringArray(R.array.beginner_grammar);
            pronunArray =  getResources().getStringArray(R.array.beginner_pronunciation);
            example1array = getResources().getStringArray(R.array.beginner_example1);
            example2Array = getResources().getStringArray(R.array.beginner_example2);
            example3Array = getResources().getStringArray(R.array.beginner_example3);

            if(SplashScreen.languageId == 1){


                beginnerTranslationExtra = getResources().getStringArray(R.array.beginner_spanish);
            }
            else if(SplashScreen.languageId == 2){


                beginnerTranslationExtra = getResources().getStringArray(R.array.beginner_bengali);
            }
            else if(SplashScreen.languageId == 3){


                beginnerTranslationExtra = getResources().getStringArray(R.array.beginner_hindi);
            }else {

                for(int i = 0; i < getResources().getStringArray(R.array.beginner_words).length; i++){

                    beginnerTranslationExtra[i] = "";

                }

            }

        }
        else if(level.equalsIgnoreCase("intermediate")){

            // Choosing Planet Image according to screen size -----------------------------------

            if(screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL){


                if(screenInches < 4.8 ){

                    Toast.makeText(this,"< 5 ",Toast.LENGTH_SHORT).show();

                    trainPlanet.setImageResource(R.drawable.intermediate_planet_train_noraml_lessthan);

                }else {


                    trainPlanet.setImageResource(R.drawable.intermediate_planet_train_normal);

                }


            }else {


                trainPlanet.setImageResource(R.drawable.intermediate_planet_train);

            }

            if(screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE){

                next.setImageResource(R.drawable.intermediate_next_train_xlarge);
                fakeNext.setImageResource(R.drawable.intermediate_next_train_xlarge);

            }
            if( screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE){

                next.setImageResource(R.drawable.intermediate_next_train_xlarge);
                fakeNext.setImageResource(R.drawable.intermediate_next_train_xlarge);


            }
            else{

                next.setImageResource(R.drawable.intermediate_next_train);
                fakeNext.setImageResource(R.drawable.intermediate_next_train);

            }
            //-----------------------------------------------------------------------------------


            button1.setBackgroundColor(Color.parseColor("#83a9ba"));
            button2.setBackgroundColor(Color.parseColor("#83a9ba"));
            button3.setBackgroundColor(Color.parseColor("#83a9ba"));
            button4.setBackgroundColor(Color.parseColor("#83a9ba"));

            boardTopBackground.setBackgroundColor(Color.parseColor("#83a9ba"));
            wordArray = getResources().getStringArray(R.array.intermediate_words);
            translationArray = getResources().getStringArray(R.array.intermediate_translation);
            grammarArray =  getResources().getStringArray(R.array.intermediate_grammar);
            pronunArray =  getResources().getStringArray(R.array.intermediate_pronunciation);
            example1array = getResources().getStringArray(R.array.intermediate_example1);
            example2Array = getResources().getStringArray(R.array.intermediate_example2);
            example3Array = getResources().getStringArray(R.array.intermediate_example3);


            if(SplashScreen.languageId == 1){


                beginnerTranslationExtra =SplashScreen.intermediateSpanish;
            }
            else if(SplashScreen.languageId == 2){


                beginnerTranslationExtra = SplashScreen.intermediateBengali;
            }
            else if(SplashScreen.languageId == 3){


                beginnerTranslationExtra = SplashScreen.intermediateHindi;
            }else {

                for(int i = 0; i < getResources().getStringArray(R.array.intermediate_words).length; i++){

                    beginnerTranslationExtra[i] = "";

                }

            }

        }
        else if(level.equalsIgnoreCase("advance")){


            if(SplashScreen.languageId == 1){


                beginnerTranslationExtra =SplashScreen.advanceSpanish;
            }
            else if(SplashScreen.languageId == 2){


                beginnerTranslationExtra = SplashScreen.advanceBengali;
            }
            else if(SplashScreen.languageId == 3){


                beginnerTranslationExtra = SplashScreen.advanceHindi;
            }else {

                for(int i = 0; i < getResources().getStringArray(R.array.advanced_words).length; i++){

                    beginnerTranslationExtra[i] = "";

                }

            }


            // Choosing Planet Image according to screen size -----------------------------------

            if(screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL){


                if(screenInches < 4.8 ){

                    Toast.makeText(this,"< 5 ",Toast.LENGTH_SHORT).show();
                    trainPlanet.setImageResource(R.drawable.advance_planet_train_normal_lessthan);


                }else {


                    trainPlanet.setImageResource(R.drawable.advance_planet_train_normal);

                }


            }else {


                trainPlanet.setImageResource(R.drawable.advance_planet_train);


            }


            if(screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE){

                next.setImageResource(R.drawable.advance_next_train_xlarge);
                fakeNext.setImageResource(R.drawable.advance_next_train_xlarge);

            }
            if( screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE){

                next.setImageResource(R.drawable.advance_next_train_xlarge);
                fakeNext.setImageResource(R.drawable.advance_next_train_xlarge);


            }
            else{

                next.setImageResource(R.drawable.advance_next_train);
                fakeNext.setImageResource(R.drawable.advance_next_train);

            }
            //-----------------------------------------------------------------------------------


            button1.setBackgroundColor(Color.parseColor("#f9b24e"));
            button2.setBackgroundColor(Color.parseColor("#f9b24e"));
            button3.setBackgroundColor(Color.parseColor("#f9b24e"));
            button4.setBackgroundColor(Color.parseColor("#f9b24e"));

            boardTopBackground.setBackgroundColor(Color.parseColor("#f9b24e"));
            wordArray = getResources().getStringArray(R.array.advanced_words);
            translationArray = getResources().getStringArray(R.array.advanced_translation);
            grammarArray =  getResources().getStringArray(R.array.advanced_grammar);
            pronunArray =  getResources().getStringArray(R.array.advanced_pronunciation);
            example1array = getResources().getStringArray(R.array.advanced_example1);
            example2Array = getResources().getStringArray(R.array.advanced_example2);
            example3Array = getResources().getStringArray(R.array.advanced_example3);

        }




    }



    private void comeInAnimation(){
        Handler handler = new Handler();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float width = dm.widthPixels;
        float height = dm.heightPixels;

        next.setX(-width);
//        translation_layout.setY(-height);
        whiteBackground.setY(-height);
        boardTopBackground.setY(-height);
        grammarView.setY(-height);
        pronunView.setY(-height);
        translationView.setY(-height);
        exampleView1.setY(-height);
        exampleView2.setY(-height);
        exampleView3.setY(-height);
        if(SplashScreen.languageId >= 1) {

            translationExtraView.setY(-height);
            secondLanguageName.setY(-height);
            english.setY(-height);
        }



        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                float width = dm.widthPixels;
                float height = dm.heightPixels;

                final ValueAnimator va = ValueAnimator.ofFloat(width,0);

                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {

                        float value = (float)valueAnimator.getAnimatedValue();
                        next.setTranslationX(value);
                    }
                });


                va.setInterpolator(new AccelerateDecelerateInterpolator());
                va.setDuration(500L);
                va.start();

                ValueAnimator vaheight = ValueAnimator.ofFloat(height,0);

                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {

                        float value = (float)valueAnimator.getAnimatedValue();
//                        translation_layout.setTranslationY(value/2);
                        whiteBackground.setTranslationY(value/2);
                        boardTopBackground.setTranslationY(value/2);
                        grammarView.setTranslationY(value/2);
                        pronunView.setTranslationY(value/2);
                        translationView.setTranslationY(value/2);
                        exampleView1.setTranslationY(value/2);
                        exampleView2.setTranslationY(value/2);
                        exampleView3.setTranslationY(value/2);

                        if(SplashScreen.languageId >= 1) {
                            translationExtraView.setTranslationY((value / 2));
                            secondLanguageName.setTranslationY(value / 2);
                            english.setTranslationY(value/2);
                        }

                    }
                });


                va.setInterpolator(new AccelerateDecelerateInterpolator());
                va.setDuration(500L);
                va.start();



            }
        },500L);






    }

    private void nextAnimation(){
        float alpha = trainPlanet.getAlpha();
        ValueAnimator va = ValueAnimator.ofFloat(alpha,0);

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                float value = (float) valueAnimator.getAnimatedValue();
                boardTopBackground.setAlpha(value);
                whiteBackground.setAlpha(value);
                pronunView.setAlpha(value);
                grammarView.setAlpha(value);
                translationView.setAlpha(value);
                exampleView3.setAlpha(value);
                exampleView2.setAlpha(value);
                exampleView1.setAlpha(value);

                if(SplashScreen.languageId >= 1) {
                    translationExtraView.setAlpha((value));
                    secondLanguageName.setAlpha(value);
                    english.setAlpha(value);
                }

                button1.setAlpha(value);
                button2.setAlpha(value);
                button3.setAlpha(value);
                button4.setAlpha(value);

            }
        });


        va.setRepeatMode(ValueAnimator.REVERSE);
        va.setRepeatCount(1);
        va.setDuration(400L);
        va.start();
    }









    private void wrongAnswerAnimation(){


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float width = dm.widthPixels;
        float height = dm.heightPixels;

        ValueAnimator va = ValueAnimator.ofFloat(width,0);

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                float value = (float)valueAnimator.getAnimatedValue();
                fakeNext.setTranslationX(value);
            }
        });


        va.setInterpolator(new FastOutSlowInInterpolator());
        va.setDuration(500L);
        va.start();

        ValueAnimator vaheight = ValueAnimator.ofFloat(height,0);

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                float value = (float)valueAnimator.getAnimatedValue();
                whiteBackground.setTranslationY(value/2);
                boardTopBackground.setTranslationY(value/2);
                grammarView.setTranslationY(value/2);
                pronunView.setTranslationY(value/2);
                translationView.setTranslationY(value/2);
                exampleView1.setTranslationY(value/2);
                exampleView2.setTranslationY(value/2);
                exampleView3.setTranslationY(value/2);

                if(SplashScreen.languageId >= 1) {
                    translationExtraView.setTranslationY((value / 2));
                    secondLanguageName.setTranslationY(value / 2);
                    english.setTranslationY(value/2);
                }
//                translation_layout.setTranslationY(value/2);
            }
        });


        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.setDuration(500L);
        va.start();












    }




}
