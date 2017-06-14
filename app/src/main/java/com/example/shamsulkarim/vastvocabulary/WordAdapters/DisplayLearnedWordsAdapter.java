package com.example.shamsulkarim.vastvocabulary.WordAdapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.shamsulkarim.vastvocabulary.R;
import com.example.shamsulkarim.vastvocabulary.SplashScreen;
import com.example.shamsulkarim.vastvocabulary.Word;

import java.util.List;

/**
 * Created by karim on 3/16/17.
 */

public class DisplayLearnedWordsAdapter extends RecyclerView.Adapter<DisplayLearnedWordsAdapter.DisplayLearnedWordsViewHolder>{

    List<Word> words;
    Context ctx;
    String color;

    public DisplayLearnedWordsAdapter(Context ctx, List<Word> words, String color){

        this.ctx = ctx;
        this.words = words;
        this.color = color;

    }

    @Override
    public DisplayLearnedWordsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_learned_words_row,parent,false);

        DisplayLearnedWordsViewHolder viewHolder = new DisplayLearnedWordsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DisplayLearnedWordsViewHolder holder, int position) {

        Word word = words.get(position);

        holder.background.setBackgroundColor(Color.parseColor(color));

        holder.word.setText(word.getWord());
        if(SplashScreen.languageId>= 1){

            holder.translation.setText(word.getExtra());
        }else{

            holder.translation.setText(word.getTranslation());
        }

    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    class DisplayLearnedWordsViewHolder extends RecyclerView.ViewHolder{

        TextView word, translation;
        ImageView background;

        public DisplayLearnedWordsViewHolder(View itemView) {
            super(itemView);

            background = (ImageView) itemView.findViewById(R.id.word_background_display_learned_words);
            word = (TextView) itemView.findViewById(R.id.word_display_learned_words);
            translation = (TextView) itemView.findViewById(R.id.translation_display_learned_words);
        }
    }
}
