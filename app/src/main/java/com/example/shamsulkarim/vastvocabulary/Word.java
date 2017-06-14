package com.example.shamsulkarim.vastvocabulary;

/**
 * Created by Shamsul Karim on 14-Dec-16.
 */

public class Word {


    int count;
    String word, translation,extra,pronun,grammar,example1,example2,example3;
    boolean seen;
    boolean removable;
    String level;
    int databasePosition;
    int favNum;
    boolean isFavorite;
    int number;






    public Word(String word, String translation,String extra, String pronun, String grammar, String example1, String example2, String example3, int databasePosition, String level) {
        this.word = word;
        this.translation = translation;
        this.pronun = pronun;
        this.grammar = grammar;
        this.example1 = example1;
        this.example2 = example2;
        this.example3 = example3;
        this.databasePosition = databasePosition;
        this.level = level;
        this.extra = extra;
    }

    public Word(String word, String translation, String pronun, String grammar, String example1, String example2, String example3) {
        this(word,translation,pronun,grammar,example1,example2,example3,"");

    }
    public Word(String word, String translation,String extra, String pronun, String grammar, String example1, String example2, String example3,String nothing) {
        this(word,translation,pronun,grammar,example1,example2,example3,"");
        this.extra = extra;

    }

    public Word(String word, String translation,String extra, String pronun, String grammar, String example1, String level,int favNum,int number) {
        this.word = word;
        this.translation = translation;
        this.pronun = pronun;
        this.grammar = grammar;
        this.example1 = example1;
        this.level = level;
        this.extra = extra;
        this.favNum = favNum;
        this.number = number;


    }

    public Word(String word, String translation, String pronun, String grammar, String example1, String level) {
        this.word = word;
        this.translation = translation;
        this.pronun = pronun;
        this.grammar = grammar;
        this.example1 = example1;
        this.level = level;
        this.extra = extra;

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

    public boolean isFavorite() {
        return isFavorite;
    }

    public int getFavNum() {
        return favNum;
    }

    public void setFavNum(int favNum) {
        this.favNum = favNum;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getLevel() {
        return level;
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
    public void setCountToZero(int count) {
        this.count = count;
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

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
