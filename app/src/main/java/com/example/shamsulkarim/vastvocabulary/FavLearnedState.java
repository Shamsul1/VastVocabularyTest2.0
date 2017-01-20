package com.example.shamsulkarim.vastvocabulary;

/**
 * Created by karim on 1/20/17.
 */

public class FavLearnedState {


    String beginnerLearnedCount, intermediateLearnedCount, advanceLearnedCount;
    String beginnerFavCount, intermediateFavCount, advanceFavCount;

    public FavLearnedState(String beginnerLearnedCount, String intermediateLearnedCount, String advanceLearnedCount, String beginnerFavCount, String intermediateFavCount, String advanceFavCount) {
        this.beginnerLearnedCount = beginnerLearnedCount;
        this.intermediateLearnedCount = intermediateLearnedCount;
        this.advanceLearnedCount = advanceLearnedCount;
        this.beginnerFavCount = beginnerFavCount;
        this.intermediateFavCount = intermediateFavCount;
        this.advanceFavCount = advanceFavCount;
    }


    public String getBeginnerLearnedCount() {
        return beginnerLearnedCount;
    }

    public void setBeginnerLearnedCount(String beginnerLearnedCount) {
        this.beginnerLearnedCount = beginnerLearnedCount;
    }

    public String getIntermediateLearnedCount() {
        return intermediateLearnedCount;
    }

    public void setIntermediateLearnedCount(String intermediateLearnedCount) {
        this.intermediateLearnedCount = intermediateLearnedCount;
    }

    public String getAdvanceLearnedCount() {
        return advanceLearnedCount;
    }

    public void setAdvanceLearnedCount(String advanceLearnedCount) {
        this.advanceLearnedCount = advanceLearnedCount;
    }

    public String getBeginnerFavCount() {
        return beginnerFavCount;
    }

    public void setBeginnerFavCount(String beginnerFavCount) {
        this.beginnerFavCount = beginnerFavCount;
    }

    public String getIntermediateFavCount() {
        return intermediateFavCount;
    }

    public void setIntermediateFavCount(String intermediateFavCount) {
        this.intermediateFavCount = intermediateFavCount;
    }

    public String getAdvanceFavCount() {
        return advanceFavCount;
    }

    public void setAdvanceFavCount(String advanceFavCount) {
        this.advanceFavCount = advanceFavCount;
    }
}
