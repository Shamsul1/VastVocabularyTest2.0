package com.example.shamsulkarim.vastvocabulary;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shamsulkarim.vastvocabulary.WordAdapters.advanceAdapter;

import java.util.List;

/**
 * Created by karim on 1/24/17.
 */

public class ChooseLanguageAdapter extends RecyclerView.Adapter<ChooseLanguageAdapter.LanguageViewHolder> {


    Context context;
    List<Language> languageInfo;
    TextView language, location, speakers, noLanguage;
    ImageView languageIcon, locationIcon, speakersIcon;


    public ChooseLanguageAdapter(Context context, TextView language, TextView location, TextView speakers,TextView noLanguage,ImageView languageIcon, ImageView locationIcon, ImageView speakersIcon, List<Language> languageInfo) {
        this.context = context;

        this.language = language;
        this.location = location;
        this.speakers = speakers;
        this.languageInfo = languageInfo;
        this.noLanguage = noLanguage;
        this.languageIcon = languageIcon;
        this.locationIcon = locationIcon;
        this.speakersIcon = speakersIcon;
    }

    @Override
    public LanguageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.language_row,parent,false);


        LanguageViewHolder viewHolder = new LanguageViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LanguageViewHolder holder, int position) {


        holder.languageImage.setImageResource(languageInfo.get(position).getImage());
    }



    @Override
    public int getItemCount() {
        return languageInfo.size();
    }



    class LanguageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView languageImage, tickMark;



        public LanguageViewHolder(View itemView) {
            super(itemView);
            languageImage = (ImageView)itemView.findViewById(R.id.languagImage);
            tickMark  = (ImageView)itemView.findViewById(R.id.tickMark);


            languageImage.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            SplashScreen.sp.edit().putInt("language",getAdapterPosition()).apply();
            SplashScreen.languageId = getAdapterPosition();


//            if(getAdapterPosition() == 0){
//
//                noLanguage.setText("No language picked");
//                languageIcon.setImageResource(0);
//                locationIcon.setImageResource(0);
//                speakersIcon.setImageResource(0);
//                language.setText("");
//                location.setText("");
//                speakers.setText("");
//
//
//
//            }else {

                noLanguage.setText("");
                languageIcon.setImageResource(R.drawable.language);
                locationIcon.setImageResource(R.drawable.location);
                speakersIcon.setImageResource(R.drawable.speakers);

                language.setText(languageInfo.get(getAdapterPosition()).getName());
                location.setText(languageInfo.get(getAdapterPosition()).getLocation());
                speakers.setText(languageInfo.get(getAdapterPosition()).getSpeakers());
//
//            }



            Toast.makeText(view.getContext(), ""+getAdapterPosition(),Toast.LENGTH_SHORT).show();

        }
    }
}
