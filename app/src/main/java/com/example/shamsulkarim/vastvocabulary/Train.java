package com.example.shamsulkarim.vastvocabulary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Train extends AppCompatActivity {

    RelativeLayout translation_layout,button1,button2,button3,button4;
    List<Word> fiveWords, learnedWords, buttonQuestion, words, wordForQuestions;
    String[] sendData = new String[5];
    int[] wordCounter = new int[5];
    String[] wordArray, translationArray,sendWords;
    TextView wordView, translationView, countView;
    TextView answer1, answer2, answer3, answer4;
    ImageView next, fakeNext;
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

        // INITIALIZATION

        arrayInitializations();
        buttonInitializations();
        textViewInitializations();
        gettingResources();

        // INITIALIZING WORDS
        addingNewWords();
        wordCounter[0] = words.size();
        wordCounter[1] = learnedWords.size();

        if( !fiveWords.isEmpty()){

            wordView.setText(fiveWords.get(ia).getWord());
            translationView.setText(fiveWords.get(ia).getTranslation());
            fiveWords.get(ia).setSeen(true);

            ia++;

        }



    }
    //----------------------------------------------------------------------------------------------

    public void nextWord(View view) {

        // To Next Activity
        if (fiveWords.get(fiveWords.size() - 1).getCount() == 2) {
            addLearnedWordsToSend();
            Intent intent = new Intent(this, DisplayLearningScore.class);
            intent.putExtra("words", sendData).putExtra("wordCount", wordCounter).putExtra("Level",level);
            this.startActivity(intent);

        }

        /// IF SEEN IT TRUE THEN IT WOULD START TO ASK QUESTION
        if (id < fiveWords.size()  && fiveWords.size() != 0) {

            if(fiveWords.get(id).isSeen()){
                next.setVisibility(View.INVISIBLE);
                translation_layout.setVisibility(View.INVISIBLE);
                button1.setVisibility(View.VISIBLE);
                button2.setVisibility(View.VISIBLE);
                button3.setVisibility(View.VISIBLE);
                button4.setVisibility(View.VISIBLE);
                translationView.setText("");
                answers(view);}

        }

        /// IF ISSEEN IS FALSE IT WOULD SHOW WORDS WITH TRANSLATION
        if (ia < fiveWords.size() && !fiveWords.get(ia).isSeen()) {
            wordView.setText(fiveWords.get(ia).getWord());
            Toast.makeText(this, "IA: "+ia, Toast.LENGTH_SHORT).show();
            showWords(ia);
            ia++;
        }

        // IF ID EQUALS TO FIVEWORDS SIZE ID WOULD SET TO ZERO.
        if (id == fiveWords.size()) {
            id = 0;

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

        Toast.makeText(this, view.getId()+"", Toast.LENGTH_SHORT).show();
        String answer;

        if (view.getId() == button1.getId()) {
            answer = answer1.getText().toString();
            if (answer.equalsIgnoreCase(fiveWords.get(id).getTranslation())) {
                // Toast.makeText(this,"Correct",Toast.LENGTH_SHORT).show();
                fiveWords.get(id).setCount(1);
                countView.setText(fiveWords.get(id).getCount() + " ");
                id++;
            } else {
                showAnswer(fiveWords.get(id).getTranslation());
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
                showAnswer(fiveWords.get(id).getTranslation());
            }
        }
        if (view.getId() == button3.getId()) {
            answer = answer3.getText().toString();

            if (answer.equalsIgnoreCase(fiveWords.get(id).getTranslation())) {
                fiveWords.get(id).setCount(1);
                countView.setText(fiveWords.get(id).getCount() + " ");
                id++;
            } else {
                showAnswer(fiveWords.get(id).getTranslation());
            }
        }
        if (view.getId() == button4.getId()) {
            answer = answer4.getText().toString();

            if (answer.equalsIgnoreCase(fiveWords.get(id).getTranslation())) {
                fiveWords.get(id).setCount(1);
                countView.setText(fiveWords.get(id).getCount() + "");
                id++;
            } else {
                showAnswer(fiveWords.get(id).getTranslation());
            }
        }
        if (id == fiveWords.size()) {
            id = 0;

        }
        settingQuestionButton();
    }

    // This gets called when you have answered a wrong answer
    public void showAnswer(String wordAnswer) {
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
        fakeNext.setVisibility(View.VISIBLE);
        translationView.setText(wordAnswer);

    }

    // DummyNext
    public void dummyNext(View view) {
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
            sendData[i] = fiveWords.get(i).getWord();
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
            words.add(new Word(wordArray[i], translationArray[i]));

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

        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
    }

    private void textViewInitializations() {
        wordView = (TextView) findViewById(R.id.word);
        translationView = (TextView) findViewById(R.id.translation);
        countView = (TextView) findViewById(R.id.count);
    }

    private void gettingResources() {

            wordArray = getResources().getStringArray(R.array.words);
            translationArray = getResources().getStringArray(R.array.translation);


    }


}
