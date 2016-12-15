package com.example.shamsulkarim.vastvocabulary;

/**
 * Created by Shamsul Karim on 14-Dec-16.
 */

public class Word {


    int count;
    String word, translation;
    boolean seen;
    boolean removable;

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

    public Word(String word, String translation) {
        this.word = word;
        this.translation = translation;
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
