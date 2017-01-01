package com.example.shamsulkarim.vastvocabulary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sk on 1/1/17.
 */

public class LearnedWordAdapter extends RecyclerView.Adapter<LearnedWordAdapter.WordViewHolder>{


    List<Word> words = new ArrayList<>();
    Context context;


    public LearnedWordAdapter(Context context, List<Word> words) {
        this.context = context;
        this.words = words;
    }


    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_row_layout,parent,false);


        WordViewHolder viewHolder = new WordViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {

        Word word = words.get(position);

        holder.favorite.setImageResource(R.drawable.nolove);
        holder.wordView.setText(word.getWord());
        holder.translationView.setText(word.getTranslation());
        holder.grammarView.setText(word.getGrammar());
        holder.pronunciationView.setText(word.getPronun());
        holder.exampleView1.setText(word.getExample1());


    }


    @Override
    public int getItemCount() {
        return words.size();
    }



    class WordViewHolder extends RecyclerView.ViewHolder{

        TextView wordView,translationView, grammarView, pronunciationView, exampleView1;
        ImageView favorite;


        public WordViewHolder(View itemView) {
            super(itemView);

            wordView = (TextView)itemView.findViewById(R.id.card_word);
            translationView = (TextView)itemView.findViewById(R.id.card_translation);
            grammarView = (TextView)itemView.findViewById(R.id.card_grammar);
            pronunciationView = (TextView)itemView.findViewById(R.id.card_pronunciation);
            exampleView1 = (TextView)itemView.findViewById(R.id.card_example1);

            favorite = (ImageView)itemView.findViewById(R.id.favorite);

        }
    }

}
