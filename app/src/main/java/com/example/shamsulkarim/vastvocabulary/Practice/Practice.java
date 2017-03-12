package com.example.shamsulkarim.vastvocabulary.Practice;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Handler;
import android.provider.ContactsContract;
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

import com.example.shamsulkarim.vastvocabulary.DisplayLearningScore;
import com.example.shamsulkarim.vastvocabulary.FavoriteWords;
import com.example.shamsulkarim.vastvocabulary.LearnedWords;
import com.example.shamsulkarim.vastvocabulary.MainActivity;
import com.example.shamsulkarim.vastvocabulary.NoWordLeft;
import com.example.shamsulkarim.vastvocabulary.PracticeFinishedActivity;
import com.example.shamsulkarim.vastvocabulary.R;
import com.example.shamsulkarim.vastvocabulary.SplashScreen;
import com.example.shamsulkarim.vastvocabulary.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Practice extends AppCompatActivity {



    List<Word> fiveWords, buttonQuestion, wordForQuestions;
    TextView wordView, english, translationView,translationExtraView,secondLanguageName,grammarView,pronunView,exampleView1,exampleView2,exampleView3;
    ImageView next, fakeNext,trainPlanet,whiteBackground,boardTopBackground;
    Button button1,button2, button3, button4;
    int id = 1;
    int ia = 0;
    int question = 4;
    String toastMsg,secondLanguage;
    int screenSize;
    double screenInches;

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
        //------------------------------------------------------------------------



         id = 1;
         ia = 0;


        buttonQuestion = new ArrayList<>();


        gettingResources();
        initialization();
        textViewInitializations();


        // INITIALIZING WORDS
        addingNewWords();
        for(int i = 0; i < fiveWords.size(); i++){
            fiveWords.get(i).setCountToZero(0);

            Toast.makeText(this,""+fiveWords.get(i).getCount(),Toast.LENGTH_SHORT).show();

        }


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


        Toast.makeText(this,"ia: "+ia+" id: "+id,Toast.LENGTH_SHORT).show();


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
        if (fiveWords.get(fiveWords.size() - 1).getCount() == 2) {
            this.startActivity(new Intent(this, PracticeFinishedActivity.class));

            this.finish();


        }else{
            //----------

            /// IF SEEN IT TRUE THEN IT WOULD START TO ASK QUESTION
            if (id < fiveWords.size()  && fiveWords.size() != 0) {


                Toast.makeText(this, "ia: "+ia+" id: "+id,Toast.LENGTH_SHORT).show();

                if (fiveWords.get(id).isSeen()) {
                    Toast.makeText(this, "ia: "+ia+" id: "+id,Toast.LENGTH_SHORT).show();
                    next.setVisibility(View.INVISIBLE);
                    button1.setVisibility(View.VISIBLE);
                    button2.setVisibility(View.VISIBLE);
                    button3.setVisibility(View.VISIBLE);
                    button4.setVisibility(View.VISIBLE);
                    whiteBackground.setVisibility(View.INVISIBLE);
                    boardTopBackground.setVisibility(View.INVISIBLE);
                    translationView.setText("");
                    grammarView.setText("");
                    pronunView.setText("");
                    exampleView1.setText("");
                    exampleView2.setText("");
                    exampleView3.setText("");

                    if(SplashScreen.languageId >= 1){

                        translationExtraView.setText("");
                        secondLanguageName.setText("");
                        english.setText("");
                    }


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

        if(SplashScreen.languageId >= 1){
            translationExtraView.setText(fiveWords.get(index).getExtra());
            english.setText("english");
            secondLanguageName.setText(secondLanguage);
        }

        translationView.setText(fiveWords.get(index).getTranslation());
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
//    public void answers(View view) {
//
//
//        String answer;
//
//
//        if (view.getId() == button1.getId()) {
//            answer = button1.getText().toString();
//            if (answer.equalsIgnoreCase(fiveWords.get(id).getTranslation())) {
//                // Toast.makeText(this,"Correct",Toast.LENGTH_SHORT).show();
//                fiveWords.get(id).setCount(1);
//                id++;
//            } else {
//                showAnswer(fiveWords.get(id));
//                // Toast.makeText(this,"Wrong ",Toast.LENGTH_SHORT).show();
//
//            }
//        }
//
//        if (view.getId() == button2.getId()) {
//            answer = button2.getText().toString();
//
//            if (answer.equalsIgnoreCase(fiveWords.get(id).getTranslation())) {
//                fiveWords.get(id).setCount(1);
//                id++;
//
//            } else {
//                showAnswer(fiveWords.get(id));
//            }
//        }
//        if (view.getId() == button3.getId()) {
//            answer = button3.getText().toString();
//
//            if (answer.equalsIgnoreCase(fiveWords.get(id).getTranslation())) {
//                fiveWords.get(id).setCount(1);
//                id++;
//
//            } else {
//                showAnswer(fiveWords.get(id));
//            }
//        }
//        if (view.getId() == button4.getId()) {
//            answer = button4.getText().toString();
//
//            if (answer.equalsIgnoreCase(fiveWords.get(id).getTranslation())) {
//                fiveWords.get(id).setCount(1);
//                id++;
//
//            } else {
//                showAnswer(fiveWords.get(id));
//            }
//        }
//        if (id == fiveWords.size()) {
//            id = 0;
//
//        }
//
//    }

    // This gets called when you have answered a wrong answer

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

        translationView.setText(wordAnswer.getTranslation());
        pronunView.setText(wordAnswer.getPronun());
        grammarView.setText(wordAnswer.getGrammar());
        exampleView1.setText(wordAnswer.getExample1());
        exampleView2.setText(wordAnswer.getExample2());
        exampleView3.setText(wordAnswer.getExample3());

        boardTopBackground.setVisibility(View.VISIBLE);
        whiteBackground.setVisibility(View.VISIBLE);

        wrongAnswerAnimation();

    }

    // DummyNext
    public void dummyNext(View view) {
        boardTopBackground.setVisibility(View.INVISIBLE);
        whiteBackground.setVisibility(View.INVISIBLE);
        pronunView.setText("");
        grammarView.setText("");
        translationView.setText("");
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
        Toast.makeText(this,"Dummy next called", Toast.LENGTH_SHORT).show();
    }




    private List<Word> settingUpQustion() {
        buttonQuestion.clear();
        wordForQuestions = new ArrayList<>();
        wordForQuestions.addAll(fiveWords);


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



    private void addingNewWords() {

        fiveWords = new ArrayList<>();
      fiveWords.clear();

      if(MainActivity.practice.equalsIgnoreCase("favorite")){
          fiveWords = FavoriteWords.words;

          for(int i = 0; i < fiveWords.size(); i++){

              fiveWords.get(i).setSeen(false);


          }
          Toast.makeText(this,""+fiveWords.size(),Toast.LENGTH_SHORT).show();

      }

        if(MainActivity.practice.equalsIgnoreCase("learned")){
            fiveWords = LearnedWords.practiceWords;

            for(int i = 0; i < fiveWords.size(); i++){

                fiveWords.get(i).setSeen(false);



            }
            Toast.makeText(this,""+fiveWords.size(),Toast.LENGTH_SHORT).show();

        }





    }

    private void gettingResources() {





    }

    private void initialization() {

        trainPlanet = (ImageView)findViewById(R.id.planet_train);
        whiteBackground = (ImageView)findViewById(R.id.white_background_train);
        boardTopBackground = (ImageView)findViewById(R.id.board_top_background);
        fakeNext = (ImageView) findViewById(R.id.dummy_next);
        next = (ImageView) findViewById(R.id.next);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        trainPlanet = (ImageView)findViewById(R.id.planet_train);

        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
        fakeNext.setVisibility(View.INVISIBLE);

        boardTopBackground.setBackgroundColor(Color.parseColor("#673AB7"));
        button1.setBackgroundColor(Color.parseColor("#673AB7"));
        button4.setBackgroundColor(Color.parseColor("#673AB7"));
        button3.setBackgroundColor(Color.parseColor("#673AB7"));
        button2.setBackgroundColor(Color.parseColor("#673AB7"));

        // Setting up Resources according to screen size and density
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

    private void comeInAnimation(){
        Handler handler = new Handler();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float width = dm.widthPixels;
        float height = dm.heightPixels;

        next.setX(-width);
        boardTopBackground.setY(-height);
        whiteBackground.setY(-height);
        pronunView.setY(-height);
        grammarView.setY(-height);
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

                ValueAnimator va = ValueAnimator.ofFloat(width,0);

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
                       whiteBackground.setTranslationY(value/2);
                        boardTopBackground.setTranslationY(value/2);
                        translationView.setTranslationY(value/2);
                        pronunView.setTranslationY(value/2);
                        grammarView.setTranslationY(value/2);
                        exampleView3.setTranslationY(value/2);
                        exampleView2.setTranslationY(value/2);
                        exampleView1.setTranslationY(value/2);

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
                translationView.setAlpha(value);
                grammarView.setAlpha(value);
                exampleView1.setAlpha(value);
                exampleView2.setAlpha(value);
                exampleView3.setAlpha(value);
                button1.setAlpha(value);
                button2.setAlpha(value);
                button3.setAlpha(value);
                button4.setAlpha(value);

                if(SplashScreen.languageId >= 1) {
                    translationExtraView.setAlpha((value));
                    secondLanguageName.setAlpha(value);
                    english.setAlpha(value);
                }

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
                translationView.setTranslationY(value/2);
                pronunView.setTranslationY(value/2);
                grammarView.setTranslationY(value/2);
                exampleView3.setTranslationY(value/2);
                exampleView2.setTranslationY(value/2);
                exampleView1.setTranslationY(value/2);

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



}
