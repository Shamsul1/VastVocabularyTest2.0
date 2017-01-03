package com.example.shamsulkarim.vastvocabulary;

/**
 * Created by Shamsul Karim on 14-Dec-16.
 */

public class Word {


    int count;
    String word, translation,pronun,grammar,example1,example2,example3;
    boolean seen;
    boolean removable;
    String level;


    public Word(String word, String translation, String pronun, String grammar, String example1, String example2, String example3) {
        this.word = word;
        this.translation = translation;
        this.pronun = pronun;
        this.grammar = grammar;
        this.example1 = example1;
        this.example2 = example2;
        this.example3 = example3;
    }

    public Word(String word, String translation, String pronun, String grammar, String example1, String level) {
        this(word,translation,pronun,grammar,example1,"","",level);

    }

    public Word( String word, String translation, String pronun, String grammar, String example1, String example2, String example3,String level) {
        this.word = word;
        this.translation = translation;
        this.pronun = pronun;
        this.grammar = grammar;
        this.example1 = example1;
        this.example2 = example2;
        this.example3 = example3;
        this.level = level;
    }

    public String getPronun() {
        return pronun;
    }

    public String getGrammar() {
        return grammar;
    }

    public String getExample1() {
        return example1;
    }

    public String getExample2() {
        return example2;
    }

    public String getExample3() {
        return example3;
    }

    public boolean isRemovable() {
        return removable;
    }

    public void setRemovable(boolean removable) {
        this.removable = removable;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count += count;
    }



    public void setWord(String word) {
        this.word = word;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }
}
