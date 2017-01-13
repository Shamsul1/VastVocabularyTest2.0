package com.example.shamsulkarim.vastvocabulary.WordAdapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shamsulkarim.vastvocabulary.AdvancedWordDatabase;
import com.example.shamsulkarim.vastvocabulary.IntermediatewordDatabase;
import com.example.shamsulkarim.vastvocabulary.R;
import com.example.shamsulkarim.vastvocabulary.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sk on 1/3/17.
 */

public class advanceAdapter extends RecyclerView.Adapter<advanceAdapter.WordViewHolder>{




    List<String> isFav = new ArrayList<>();
    List<Word> words = new ArrayList<>();
    Context context;
    AdvancedWordDatabase db;


    public advanceAdapter(Context context, List<Word> words) {
        this.context = context;
        this.words = words;
        db = new AdvancedWordDatabase(context);
        favoriteWordChecker();
    }



    private void favoriteWordChecker(){

        isFav.clear();
        Cursor res = db.getData();


        while (res.moveToNext()){

            isFav.add(res.getString(2));

        }


    }


    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_row_layout,parent,false);


        WordViewHolder viewHolder = new WordViewHolder(view);
        return viewHolder;
    }




    public void onBindViewHolder(WordViewHolder holder, int position) {

        Word word = words.get(position);

        if(isFav.get(position).equalsIgnoreCase("true")){

            holder.favorite.setImageResource(R.drawable.love);

        }else {

            holder.favorite.setImageResource(R.drawable.nolove);

        }




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



    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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
            favorite.setTag(null);

            favorite.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {


            int position = 1+getAdapterPosition();



            if(view.getId() == R.id.favorite){

                if(favorite.getTag() == null){

                    isFav.set(getAdapterPosition(),"True");
                    db.updateFav(position+"","True");
                    favorite.setImageResource(R.drawable.love);
                    favorite.setTag(R.drawable.love);

                }
                else {


                    isFav.set(getAdapterPosition(),"False");

                    db.updateFav(position+"","False");
                    favorite.setImageResource(R.drawable.nolove);
                    favorite.setTag(null);


                }




            }




        }
    }



}
