package com.example.shamsulkarim.vastvocabulary;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sk on 1/1/17.
 */

public class FavoriteRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteRecyclerViewAdapter.WordViewHolder> {


    List<Boolean> isFav = new ArrayList<>();
    List<Word> words = new ArrayList<>();
    Context context;
    AdvancedWordDatabase aDb;
    BeginnerWordDatabase bDb;
    IntermediatewordDatabase iDb;


    public FavoriteRecyclerViewAdapter(Context context, List<Word> words) {
        this.context = context;
        this.words = words;

        aDb = new AdvancedWordDatabase(context);
        bDb = new BeginnerWordDatabase(context);
        iDb = new IntermediatewordDatabase(context);


        addFav();
    }



    private void addFav(){

        for(int i = 0; i < words.size(); i++){

            isFav.add(true);



        }



    }


    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(SplashScreen.languageId >= 1){

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_favorite_two_language,parent,false);

        }else {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_favorite_one_language,parent,false);
        }



        WordViewHolder viewHolder = new WordViewHolder(view);
        return viewHolder;
    }




    public void onBindViewHolder(WordViewHolder holder, int position) {

        int pos = position;

        Word word = words.get(pos);

        if(isFav.get(pos) == true){


            holder.favorite.setImageResource(R.drawable.favorite_card_view);
        }else {
         holder.favorite.setImageResource(R.drawable.ic_favorite_border);


        }

        if(SplashScreen.languageId == 1){


            holder.secondLanguageName.setText(SplashScreen.languageName[1]);
        }

        if(SplashScreen.languageId == 2){


            holder.secondLanguageName.setText(SplashScreen.languageName[2]);
        }

        if(SplashScreen.languageId == 3){


            holder.secondLanguageName.setText(SplashScreen.languageName[3]);
        }




        if(SplashScreen.languageId >= 1){

            holder.translationView.setText(word.getTranslation());
            holder.secondTranslation.setText(word.getExtra());


        }else {

            holder.translationView.setText(word.getTranslation());
        }

        holder.wordView.setText(word.getWord());

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

        TextView wordView,translationView, grammarView, pronunciationView, exampleView1,exampleView2,exampleView3,secondLanguageName,secondTranslation, englishLanguage;
        ImageView favorite;


        public WordViewHolder(View itemView) {
            super(itemView);
            Typeface ABeeZee = Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/ABeeZee-Regular.ttf");
            Typeface ABeeZeeItalic  = Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/ABeeZee-Italic.ttf");

            englishLanguage = (TextView)itemView.findViewById(R.id.favorite_second_language);
            secondLanguageName = (TextView)itemView.findViewById(R.id.favorite_second_language2);
            secondTranslation = (TextView)itemView.findViewById(R.id.favorite_second_translation);
            wordView = (TextView)itemView.findViewById(R.id.favorite_card_word);
            translationView = (TextView)itemView.findViewById(R.id.favorite_card_translation);
            grammarView = (TextView)itemView.findViewById(R.id.favorite_card_grammar);
            pronunciationView = (TextView)itemView.findViewById(R.id.favorite_card_pronunciation);
            exampleView1 = (TextView)itemView.findViewById(R.id.favorite_card_example1);
            exampleView2 = (TextView)itemView.findViewById(R.id.favorite_card_example2);
            exampleView3 = (TextView)itemView.findViewById(R.id.favorite_card_example3);

            wordView.setTypeface(ABeeZee);
            translationView.setTypeface(ABeeZee);
            grammarView.setTypeface(ABeeZee);
            pronunciationView.setTypeface(ABeeZee);
            exampleView1.setTypeface(ABeeZee);


            exampleView2.setTypeface(ABeeZee);
            exampleView3.setTypeface(ABeeZee);

            if(SplashScreen.languageId == 1){
                secondLanguageName.setTypeface(ABeeZeeItalic);
                englishLanguage.setTypeface(ABeeZeeItalic);
                secondTranslation.setTypeface(ABeeZee);
            }

            favorite = (ImageView)itemView.findViewById(R.id.favorite_favorite);
            favorite.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {




            Toast.makeText(view.getContext(),"level: "+words.get(getAdapterPosition()).level+ "DatabasePosition: "+words.get(getAdapterPosition()).databasePosition,Toast.LENGTH_SHORT).show();

            if(view.getId() == R.id.favorite_favorite){


                if(favorite.getTag() == null){

                    favorite.setImageResource(R.drawable.ic_favorite_border);
                    favorite.setTag(R.drawable.ic_favorite_border);
                    isFav.set(getAdapterPosition(),false);


                    if(SplashScreen.favoriteCount > 0){

                        SplashScreen.favoriteCount--;
                        SplashScreen.sp.edit().putInt("favoriteCountProfile",SplashScreen.favoriteCount).apply();

                    }

                    if(words.get(getAdapterPosition()).level.equalsIgnoreCase("beginner")){

                        bDb.updateFav(words.get(getAdapterPosition()).databasePosition+"","False");

                    }

                    if(words.get(getAdapterPosition()).level.equalsIgnoreCase("intermediate")){
                        iDb.updateFav(words.get(getAdapterPosition()).databasePosition+"","False");

                    }

                    if(words.get(getAdapterPosition()).level.equalsIgnoreCase("advance")){

                        aDb.updateFav(words.get(getAdapterPosition()).databasePosition+"","False");


                    }





                }else {
                    SplashScreen.favoriteCount++;
                    SplashScreen.sp.edit().putInt("favoriteCountProfile",SplashScreen.favoriteCount).apply();


                    isFav.set(getAdapterPosition(),true);
                    favorite.setImageResource(R.drawable.favorite_card_view);
                    favorite.setTag(null);

                    if(words.get(getAdapterPosition()).level.equalsIgnoreCase("beginner")){

                        bDb.updateFav(words.get(getAdapterPosition()).databasePosition+"","True");
                    }

                    if(words.get(getAdapterPosition()).level.equalsIgnoreCase("interediate")){
                        iDb.updateFav(words.get(getAdapterPosition()).databasePosition+"","True");

                    }

                    if(words.get(getAdapterPosition()).level.equalsIgnoreCase("advance")){

                        aDb.updateFav(words.get(getAdapterPosition()).databasePosition+"","True");


                    }

                }




            }
        }
    }





}
