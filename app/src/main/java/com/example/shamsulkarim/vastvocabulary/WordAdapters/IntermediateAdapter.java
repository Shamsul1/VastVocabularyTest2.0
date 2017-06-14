package com.example.shamsulkarim.vastvocabulary.WordAdapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shamsulkarim.vastvocabulary.IntermediatewordDatabase;
import com.example.shamsulkarim.vastvocabulary.MainActivity;
import com.example.shamsulkarim.vastvocabulary.R;
import com.example.shamsulkarim.vastvocabulary.SplashScreen;
import com.example.shamsulkarim.vastvocabulary.Word;
import com.google.android.gms.ads.NativeExpressAdView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sk on 1/3/17.
 */

public class IntermediateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<String> isFav = new ArrayList<>();
    List<Object> words = new ArrayList<>();
    Context context;
    IntermediatewordDatabase db;
    final static int WORD_VIEW_TYPE = 0;
    final static int AD_VIEW_TYPE = 1;



    public IntermediateAdapter(Context context, List<Object> words) {
        this.context = context;
        this.words = words;
        db = new IntermediatewordDatabase(context);
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType){

            case WORD_VIEW_TYPE:
            if(SplashScreen.languageId == 0){
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_language,parent,false);
            }else {

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.second_language,parent,false);
            }

            WordViewHolder viewHolder = new WordViewHolder(view);
            return viewHolder;


            case AD_VIEW_TYPE:
                default:

                    View ad = LayoutInflater.from(parent.getContext()).inflate(R.layout.native_ad_layout,parent,false);
                    return new NativeExpressAdViewHolder(ad);


        }

    }




    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int viewType = getItemViewType(position);



        switch (viewType){

            case WORD_VIEW_TYPE:

                Word word = (Word) words.get(position);
                WordViewHolder wordViewHolder = (WordViewHolder)holder;


                if(word.getFavNum() == 1){

                    wordViewHolder.favorite.setImageResource(R.drawable.favorite_card_view);

                }else {

                    wordViewHolder.favorite.setImageResource(R.drawable.ic_favorite_border);

                }

                if(SplashScreen.languageId == 1){


                    wordViewHolder.secondLanguage.setText(SplashScreen.languageName[1]);
                }

                if(SplashScreen.languageId == 2){


                    wordViewHolder.secondLanguage.setText(SplashScreen.languageName[2]);
                }
                if(SplashScreen.languageId == 3){


                    wordViewHolder.secondLanguage.setText(SplashScreen.languageName[3]);
                }

                if(SplashScreen.languageId>= 1){

                    wordViewHolder.translationView.setText(word.getTranslation());
                    wordViewHolder.secondTranslation.setText(word.getExtra());
                }else{

                    wordViewHolder.translationView.setText(word.getTranslation());
                }





                wordViewHolder.translationView.setText(word.getTranslation());


                wordViewHolder.wordView.setText(word.getWord());

                wordViewHolder.grammarView.setText(word.getGrammar());
                wordViewHolder.pronunciationView.setText(word.getPronun());
                wordViewHolder.exampleView1.setText(word.getExample1());
                break;


            case AD_VIEW_TYPE:

                default:

                NativeExpressAdViewHolder nativeExpressHolder =
                        (NativeExpressAdViewHolder) holder;
                NativeExpressAdView adView =
                        (NativeExpressAdView) words.get(position);
                ViewGroup adCardView = (ViewGroup) nativeExpressHolder.itemView;

                if (adCardView.getChildCount() > 0) {
                    adCardView.removeAllViews();
                }
                if (adView.getParent() != null) {
                    ((ViewGroup) adView.getParent()).removeView(adView);
                }

                // Add the Native Express ad to the native express ad view.
                adCardView.addView(adView);


        }




    }


    public int getItemViewType(int position) {

        if(MainActivity.connected){
            return (position %12 == 0)? AD_VIEW_TYPE: WORD_VIEW_TYPE;
        }else {


            return WORD_VIEW_TYPE;
        }

    }
    @Override
    public int getItemCount() {
        return words.size();
    }



    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView wordView,translationView, grammarView, pronunciationView, exampleView1, secondTranslation, secondLanguage, englishLanguage;
        ImageView favorite;


        public WordViewHolder(View itemView) {
            super(itemView);
            Typeface ABeeZee = Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/ABeeZee-Regular.ttf");
            Typeface ABeeZeeItalic  = Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/ABeeZee-Italic.ttf");


            englishLanguage = (TextView)itemView.findViewById(R.id.card_language);

            wordView = (TextView)itemView.findViewById(R.id.favorite_card_word);
            translationView = (TextView)itemView.findViewById(R.id.favorite_card_translation);
            grammarView = (TextView)itemView.findViewById(R.id.card_grammar);
            pronunciationView = (TextView)itemView.findViewById(R.id.card_pronunciation);
            exampleView1 = (TextView)itemView.findViewById(R.id.card_example1);

            secondLanguage = (TextView)itemView.findViewById(R.id.card_language_extra);
            secondTranslation = (TextView)itemView.findViewById(R.id.card_translation_extra);

            wordView.setTypeface(ABeeZee);
            translationView.setTypeface(ABeeZee);
            grammarView.setTypeface(ABeeZee);
            pronunciationView.setTypeface(ABeeZee);
            exampleView1.setTypeface(ABeeZee);

            if(SplashScreen.languageId == 1){
                secondLanguage.setTypeface(ABeeZeeItalic);
                englishLanguage.setTypeface(ABeeZeeItalic);
                secondTranslation.setTypeface(ABeeZee);
            }

            favorite = (ImageView)itemView.findViewById(R.id.favorite);
            favorite.setTag(null);

            favorite.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            int position = 1+getAdapterPosition();
            Word word = (Word) words.get(getAdapterPosition());
            int wordPos = 1+word.getNumber();
            int listPos = word.getNumber();






            if(view.getId() == R.id.favorite){

                if(favorite.getTag() == null){

                    isFav.set(listPos,"True");
                    db.updateFav(wordPos+"","True");
                    favorite.setImageResource(R.drawable.favorite_card_view);
                    favorite.setTag(R.drawable.favorite_card_view);
                    SplashScreen.favoriteCount++;
                    SplashScreen.sp.edit().putInt("favoriteCountProfile",SplashScreen.favoriteCount).apply();
                }
                else {

                    if(SplashScreen.favoriteCount > 0){

                        SplashScreen.favoriteCount--;
                        SplashScreen.sp.edit().putInt("favoriteCountProfile",SplashScreen.favoriteCount).apply();

                    }
                    isFav.set(listPos,"False");

                    db.updateFav(wordPos+"","False");
                    favorite.setImageResource(R.drawable.ic_favorite_border);
                    favorite.setTag(null);


                }




            }




        }
    }


    public class NativeExpressAdViewHolder extends RecyclerView.ViewHolder{


        public NativeExpressAdViewHolder(View itemView) {
            super(itemView);
        }
    }


}
