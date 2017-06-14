package com.example.shamsulkarim.vastvocabulary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karim on 1/25/17.
 */

public class Language {


    int image;
    String name, location, speakers;



    public Language(int image, String name, String location, String speakers) {
        this.image = image;
        this.name = name;
        this.location = location;
        this.speakers = speakers;
    }

    public Language() {

    }

    public List<Language> getLanguages(){

        List<Language> languages = new ArrayList<>();


        languages.add(new Language(R.drawable.english,"English","America, Australia, England etc","510 million"));
        languages.add(new Language(R.drawable.spanish,"Spanish","Peru, Spain, Mexico etc","420 million"));
        languages.add(new Language(R.drawable.bangla, "Bengali", "Bangladesh, India", "215 million"));
        languages.add(new Language(R.drawable.hindi, "Hindi","India","490 million"));





       return languages;

    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSpeakers() {
        return speakers;
    }

    public void setSpeakers(String speakers) {
        this.speakers = speakers;
    }
}
