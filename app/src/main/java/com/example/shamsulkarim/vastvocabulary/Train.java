package com.example.shamsulkarim.vastvocabulary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Train extends AppCompatActivity {


    boolean firstTime = true;
    RelativeLayout translation_layout,button1,button2,button3,button4;
    List<Word> fiveWords, learnedWords, buttonQuestion, words, wordForQuestions;
    String[] sendWord = new String[5];
    String[] sendTranslation = new String[5];



    int[] wordCounter = new int[5];
    String[] wordArray, translationArray,sendWords,grammarArray,pronunArray,example1array,example2Array,example3Array;
    TextView wordView, translationView, countView,grammarView,pronunView,exampleView1,exampleView2,exampleView3;
    TextView answer1, answer2, answer3, answer4;
    ImageView next, fakeNext,train_land;
    int id = 0;
    int ia = 0;
    int question = 4;
    int COUNTWORDS = 0;
    int wordsPerSession = 5;
    String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        SharedPreferences sp = this.getSharedPreferences("com.example.shamsulkarim.vocabulary", Context.MODE_PRIVATE);
        level = sp.getString("level","NOTHING");

        // INITIALIZATION

        gettingResources();
        arrayInitializations();
        buttonInitializations();
        textViewInitializations();

        Toast.makeText(this,level,Toast.LENGTH_SHORT).show();

        // INITIALIZING WORDS
        addingNewWords();
        wordCounter[0] = words.size();
        wordCounter[1] = learnedWords.size();

        if( !fiveWords.isEmpty()){

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
                    translation_layout.setVisibility(View.INVISIBLE);
                    button1.setVisibility(View.VISIBLE);
                    button2.setVisibility(View.VISIBLE);
                    button3.setVisibility(View.VISIBLE);
                    button4.setVisibility(View.VISIBLE);
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
        translation_layout.setVisibility(View.VISIBLE);
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
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

        answer1.setText(buttonQuestion.get(0).getTranslation());
        answer2.setText(buttonQuestion.get(1).getTranslation());
        answer3.setText(buttonQuestion.get(2).getTranslation());
        answer4.setText(buttonQuestion.get(3).getTranslation());
        wordView.setText(fiveWords.get(id).getWord());
        countView.setText(fiveWords.get(id).getCount() + " ");

    }

    // This method gets called when you are asnwering the questions
    public void answers(View view) {


        String answer;


        if (view.getId() == button1.getId()) {
            answer = answer1.getText().toString();
            if (answer.equalsIgnoreCase(fiveWords.get(id).getTranslation())) {
                // Toast.makeText(this,"Correct",Toast.LENGTH_SHORT).show();
                fiveWords.get(id).setCount(1);
                countView.setText(fiveWords.get(id).getCount() + " ");
                id++;
            } else {
                showAnswer(fiveWords.get(id));
                // Toast.makeText(this,"Wrong ",Toast.LENGTH_SHORT).show();

            }
        }

        if (view.getId() == button2.getId()) {
            answer = answer2.getText().toString();

            if (answer.equalsIgnoreCase(fiveWords.get(id).getTranslation())) {
                fiveWords.get(id).setCount(1);
                countView.setText(fiveWords.get(id).getCount() + " ");
                id++;

            } else {
                showAnswer(fiveWords.get(id));
            }
        }
        if (view.getId() == button3.getId()) {
            answer = answer3.getText().toString();

            if (answer.equalsIgnoreCase(fiveWords.get(id).getTranslation())) {
                fiveWords.get(id).setCount(1);
                countView.setText(fiveWords.get(id).getCount() + " ");
                id++;

            } else {
                showAnswer(fiveWords.get(id));
            }
        }
        if (view.getId() == button4.getId()) {
            answer = answer4.getText().toString();

            if (answer.equalsIgnoreCase(fiveWords.get(id).getTranslation())) {
                fiveWords.get(id).setCount(1);
                countView.setText(fiveWords.get(id).getCount() + "");
                id++;

            } else {
                showAnswer(fiveWords.get(id));
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

        translationView.setText(wordAnswer.getTranslation());
        pronunView.setText(wordAnswer.getPronun());
        grammarView.setText(wordAnswer.getGrammar());
        exampleView1.setText(wordAnswer.getExample1());
        exampleView2.setText(wordAnswer.getExample2());
        exampleView3.setText(wordAnswer.getExample3());

        translation_layout.setVisibility(View.VISIBLE);

        wrongAnswerAnimation();

    }


    // DummyNext
    public void dummyNext(View view) {
        translation_layout.setVisibility(View.INVISIBLE);
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
            words.add(new Word(wordArray[i], translationArray[i],pronunArray[i],grammarArray[i],example1array[i],example2Array[i],example3Array[i]));

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
        translation_layout = (RelativeLayout)findViewById(R.id.translation_layout);
        fakeNext = (ImageView) findViewById(R.id.dummy_next);


        button1 = (RelativeLayout)findViewById(R.id.button1);
        button2 = (RelativeLayout)findViewById(R.id.button2);
        button3 = (RelativeLayout)findViewById(R.id.button3);
        button4 = (RelativeLayout)findViewById(R.id.button4);

        answer1 = (TextView) findViewById(R.id.answer1);
        answer2 = (TextView) findViewById(R.id.answer2);
        answer3 = (TextView) findViewById(R.id.answer3);
        answer4 = (TextView) findViewById(R.id.answer4);

        next = (ImageView) findViewById(R.id.next);
        train_land = (ImageView)findViewById(R.id.train_land);


        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
    }

    private void textViewInitializations() {
        wordView = (TextView) findViewById(R.id.word);
        translationView = (TextView) findViewById(R.id.translation);
        countView = (TextView) findViewById(R.id.count);
        grammarView = (TextView) findViewById(R.id.grammar);
        pronunView = (TextView) findViewById(R.id.pronunciation);
        exampleView1 = (TextView) findViewById(R.id.example1);
        exampleView2 = (TextView) findViewById(R.id.example2);
        exampleView3 = (TextView) findViewById(R.id.example3);




    }

    private void gettingResources() {


        train_land = (ImageView)findViewById(R.id.train_land);


        if(level.equalsIgnoreCase("beginner") ){

            train_land.setImageResource(R.drawable.beginner_ful_land2);
            wordArray = getResources().getStringArray(R.array.beginner_words);
            translationArray = getResources().getStringArray(R.array.beginner_translation);
            grammarArray =  getResources().getStringArray(R.array.beginner_grammar);
            pronunArray =  getResources().getStringArray(R.array.beginner_pronunciation);
            example1array = getResources().getStringArray(R.array.beginner_example1);
            example2Array = getResources().getStringArray(R.array.beginner_example2);
            example3Array = getResources().getStringArray(R.array.beginner_example3);


        }
        else if(level.equalsIgnoreCase("intermediate")){

            train_land.setImageResource(R.drawable.new_intermediate);
            wordArray = getResources().getStringArray(R.array.intermediate_words);
            translationArray = getResources().getStringArray(R.array.intermediate_translation);
            grammarArray =  getResources().getStringArray(R.array.intermediate_grammar);
            pronunArray =  getResources().getStringArray(R.array.intermediate_pronunciation);
            example1array = getResources().getStringArray(R.array.intermediate_example1);
            example2Array = getResources().getStringArray(R.array.intermediate_example2);
            example3Array = getResources().getStringArray(R.array.intermediate_example3);

        }
        else if(level.equalsIgnoreCase("advanced")){

            train_land.setImageResource(R.drawable.new_advance);
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
                translation_layout.setTranslationY(value/2);
            }
        });


        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.setDuration(500L);
        va.start();




    }

    private void nextAnimation(){
        float alpha = train_land.getAlpha();

        ValueAnimator va = ValueAnimator.ofFloat(alpha,0);

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                float value = (float) valueAnimator.getAnimatedValue();
                translation_layout.setAlpha(value);
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
                translation_layout.setTranslationY(value/2);
            }
        });


        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.setDuration(500L);
        va.start();












    }




}
