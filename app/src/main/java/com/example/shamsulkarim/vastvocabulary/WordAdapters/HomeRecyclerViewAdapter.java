package com.example.shamsulkarim.vastvocabulary.WordAdapters;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shamsulkarim.vastvocabulary.R;

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



    class HomeViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView text;

        public HomeViewHolder(View itemView) {
            super(itemView);

            image =(ImageView) itemView.findViewById(R.id.home_planet);
            text = (TextView) itemView.findViewById(R.id.home_level_text);
        }


    }
}
