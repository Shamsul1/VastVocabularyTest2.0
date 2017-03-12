package com.example.shamsulkarim.vastvocabulary.WordAdapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shamsulkarim.vastvocabulary.R;
import com.example.shamsulkarim.vastvocabulary.SplashScreen;
import com.example.shamsulkarim.vastvocabulary.StartTrainingActivity;

import java.util.List;

/**
 * Created by karim on 2/25/17.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.HomeViewHolder>{

    int[] images = {R.drawable.advance_planet_home,R.drawable.intermediate_planet_home,R.drawable.beginner_planet_home};
    String[] text = {"Advance","Intermediate","Beginner"};

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycler_view,parent,false);

        HomeViewHolder viewHolder = new HomeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {

        holder.image.setImageResource(images[position]);
        holder.text.setText(text[position]);

    }


    @Override
    public int getItemCount() {
        return images.length;
    }



    class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image;
        TextView text;
        Context ctx;

        public HomeViewHolder(View itemView) {
            super(itemView);

            ctx = itemView.getContext();
            image =(ImageView) itemView.findViewById(R.id.home_planet);
            text = (TextView) itemView.findViewById(R.id.home_level_text);
            image.setOnClickListener(this);
            text.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

            if(view.getId() == image.getId() || view.getId() == text.getId()){


                if(getAdapterPosition() == 0){

                    SplashScreen.sp.edit().putString("level","advance").apply();
                    ctx.startActivity(new Intent(ctx, StartTrainingActivity.class));

                }
                if(getAdapterPosition() == 1){

                    SplashScreen.sp.edit().putString("level","intermediate").apply();
                    ctx.startActivity(new Intent(ctx, StartTrainingActivity.class));

                }
                if(getAdapterPosition() == 2){

                    SplashScreen.sp.edit().putString("level","beginner").apply();
                    ctx.startActivity(new Intent(ctx, StartTrainingActivity.class));

                }
            }
        }
    }
}
