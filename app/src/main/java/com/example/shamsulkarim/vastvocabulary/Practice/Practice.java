package com.example.shamsulkarim.vastvocabulary.Practice;

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

import com.example.shamsulkarim.vastvocabulary.DisplayLearningScore;
import com.example.shamsulkarim.vastvocabulary.FavoriteWords;
import com.example.shamsulkarim.vastvocabulary.LearnedWords;
import com.example.shamsulkarim.vastvocabulary.MainActivity;
import com.example.shamsulkarim.vastvocabulary.NoWordLeft;
import com.example.shamsulkarim.vastvocabulary.PracticeFinishedActivity;
import com.example.shamsulkarim.vastvocabulary.R;
import com.example.shamsulkarim.vastvocabulary.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Practice extends AppCompatActivity {



    RelativeLayout translation_layout,button1,button2,button3,button4;
    List<Word> fiveWords, buttonQuestion, wordForQuestions;




    TextView wordView, translationView, countView,grammarView,pronunView,exampleView1,exampleView2,exampleView3;
    TextView answer1, answer2, answer3, answer4;
    ImageView next, fakeNext,train_land;
    int id = 1;
    int ia = 0;
    int question = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
         id = 1;
         ia = 0;


        buttonQuestion = new ArrayList<>();


        gettingResources();
        buttonInitializations();
        textViewInitializations();


        // INITIALIZING WORDS
        addingNewWords();
        for(int i = 0; i < fiveWords.size(); i++){
            fiveWords.get(i).setCountToZero(0);

            Toast.makeText(this,""+fiveWords.get(i).getCount(),Toast.LENGTH_SHORT).show();

        }


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


        train_land = (ImageView)findViewById(R.id.train_land);


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

    private void comeInAnimation(){
        Handler handler = new Handler();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float width = dm.widthPixels;
        float height = dm.heightPixels;

        next.setX(-width);
        translation_layout.setY(-height);

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
                        translation_layout.setTranslationY(value/2);
                    }
                });


                va.setInterpolator(new AccelerateDecelerateInterpolator());
                va.setDuration(500L);
                va.start();



            }
        },500L);




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
