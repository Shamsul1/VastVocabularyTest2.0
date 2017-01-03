package com.example.shamsulkarim.vastvocabulary;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sk on 1/1/17.
 */

public class FavoriteRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteRecyclerViewAdapter.WordViewHolder> {


    List<Boolean> isFav = new ArrayList<>();
    List<Word> words = new ArrayList<>();
    Context context;


    public FavoriteRecyclerViewAdapter(Context context, List<Word> words) {
        this.context = context;
        this.words = words;
        addFav();
    }



    private void addFav(){

        for(int i = 0; i < 12; i++){

            isFav.add(false);



        }



    }


    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_favorite,parent,false);


        WordViewHolder viewHolder = new WordViewHolder(view);
        return viewHolder;
    }




    public void onBindViewHolder(WordViewHolder holder, int position) {

        Word word = words.get(position);

        if(isFav.get(position) == true){


            holder.favorite.setImageResource(R.drawable.love);
        }else {
         holder.favorite.setImageResource(R.drawable.nolove);


        }




        holder.wordView.setText(word.getWord());
        holder.translationView.setText(word.getTranslation());
        holder.grammarView.setText(word.getGrammar());
        holder.pronunciationView.setText(word.getPronun());
        holder.exampleView1.setText(word.getExample1());
        holder.exampleView2.setText(word.getExample2());
        holder.exampleView3.setText(word.getExample3());


    }


    @Override
    public int getItemCount() {
        return words.size();
    }



    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView wordView,translationView, grammarView, pronunciationView, exampleView1,exampleView2,exampleView3;
        ImageView favorite;


        public WordViewHolder(View itemView) {
            super(itemView);

            wordView = (TextView)itemView.findViewById(R.id.favorite_card_word);
            translationView = (TextView)itemView.findViewById(R.id.favorite_card_translation);
            grammarView = (TextView)itemView.findViewById(R.id.favorite_card_grammar);
            pronunciationView = (TextView)itemView.findViewById(R.id.favorite_card_pronunciation);
            exampleView1 = (TextView)itemView.findViewById(R.id.favorite_card_example1);
            exampleView2 = (TextView)itemView.findViewById(R.id.favorite_card_example2);
            exampleView3 = (TextView)itemView.findViewById(R.id.favorite_card_example3);

            favorite = (ImageView)itemView.findViewById(R.id.favorite_favorite);
            favorite.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {




            Toast.makeText(view.getContext(), "Hello",Toast.LENGTH_SHORT).show();

            if(view.getId() == R.id.favorite_favorite){


                if(favorite.getTag() == null){

                    isFav.set(getAdapterPosition(),true);

                    favorite.setImageResource(R.drawable.love);
                    favorite.setTag(R.drawable.love);
                }else {

                    favorite.setImageResource(R.drawable.nolove);
                    favorite.setTag(null);
                   isFav.set(getAdapterPosition(),false);
                }




            }
        }
    }





}
