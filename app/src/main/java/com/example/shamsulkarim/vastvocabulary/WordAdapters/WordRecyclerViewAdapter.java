package com.example.shamsulkarim.vastvocabulary.WordAdapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shamsulkarim.vastvocabulary.BeginnerWordDatabase;
import com.example.shamsulkarim.vastvocabulary.R;
import com.example.shamsulkarim.vastvocabulary.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sk on 12/30/16.
 */

public class WordRecyclerViewAdapter extends RecyclerView.Adapter<WordRecyclerViewAdapter.WordViewHolder> {
    List<Word> words = new ArrayList<>();
    List<String> beginnerFav = new ArrayList<>();
    Context context;
    BeginnerWordDatabase beginnerDatabase;

    public WordRecyclerViewAdapter(Context context, List<Word> words) {
        this.context = context;
        this.words = words;
        beginnerDatabase = new BeginnerWordDatabase(context);
        favoriteWordChecker();

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


        if(beginnerFav.get(position).equalsIgnoreCase("true")){

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

    private void favoriteWordChecker(){

        beginnerFav.clear();
        Cursor beginnerRes = beginnerDatabase.getData();


            while (beginnerRes.moveToNext()){

                beginnerFav.add(beginnerRes.getString(2));

            }


    }



    @Override
    public int getItemCount() {
        return words.size();
    }

//------------------- INNER CLASS--------------------------------------------------------------

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

                    beginnerFav.set(getAdapterPosition(),"True");
                    beginnerDatabase.updateFav(position+"","True");
                    favorite.setImageResource(R.drawable.love);
                    favorite.setTag(R.drawable.love);

                }
                else {


                    beginnerFav.set(getAdapterPosition(),"False");

                    beginnerDatabase.updateFav(position+"","False");
                    favorite.setImageResource(R.drawable.nolove);
                    favorite.setTag(null);


                }




            }












//           if(view.getId() == R.id.favorite){
//
//               if(favorite.getTag() == null){
//
//                   favorite.setTag("Hello");
//
//                   if(words.get(getAdapterPosition()).level.equalsIgnoreCase("beginner")){
//
//
//                       favorite.setImageResource(R.drawable.love);
//                       beginnerDatabase.updateFav(position+"","true");
//
//                   }
//                   else if(words.get(getAdapterPosition()).level.equalsIgnoreCase("intermediate")){
//                       favorite.setImageResource(R.drawable.love);
//
//                       intermediateDatabase.updateFav(position+"","true");
//
//
//                   }
//                   else if(words.get(getAdapterPosition()).level.equalsIgnoreCase("advance")){
//
//                       favorite.setImageResource(R.drawable.love);
//                       advanceDatabase.updateFav(position+"","true");
//
//
//                   }
//
//               }else {
//
//
//
//                   favorite.setTag(null);
//
//                   if(words.get(getAdapterPosition()).level.equalsIgnoreCase("beginner")){
//
//                       favorite.setImageResource(R.drawable.nolove);
//                       beginnerDatabase.updateFav(position+"","false");
//
//                       Toast.makeText(view.getContext(),"FavWords "+beginnerFav,Toast.LENGTH_SHORT).show();
//
//                   }
//                   else if(words.get(getAdapterPosition()).level.equalsIgnoreCase("intermediate")){
//
//                       favorite.setImageResource(R.drawable.nolove);
//                       intermediateDatabase.updateFav(position+"","false");
//
//                       Toast.makeText(view.getContext(),"FavWords "+intermediateFav,Toast.LENGTH_SHORT).show();
//
//                   }
//                   else if(words.get(getAdapterPosition()).level.equalsIgnoreCase("advance")){
//
//                       favorite.setImageResource(R.drawable.nolove);
//                       advanceDatabase.updateFav(position+"","false");
//
//                       Toast.makeText(view.getContext(),"FavWords "+advanceFav,Toast.LENGTH_SHORT).show();
//
//
//                   }
//
//
//
//               }






  //         }



        }




    }

}
