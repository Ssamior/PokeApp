package com.example.pokeapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//------------
//I used sample code from https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example and customized it for pokemon cards
//------------


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private String[][] mData;
    private LayoutInflater mInflater;
    private MyAdapterListener onClickListener;

    //Constructor
    RecyclerViewAdapter(Context context, String[][] data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_card, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String pokemonId = mData[position][0];
        String pokemonName = mData[position][1];
        String txt = pokemonId + " " + pokemonName;
        holder.myTextView.setText(txt);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.length;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        Button myButton;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.rvPokemonName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onClickListener != null) onClickListener.onItemClick(view, Integer.parseInt(mData[getAdapterPosition()][0]));
        }
    }

    // convenience method for getting data at click position
    String getItem(int position) {
        return mData[position][0];
    }

    // allows clicks events to be caught
    void setClickListener(MyAdapterListener itemClickListener) {
        this.onClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface MyAdapterListener {
        void onItemClick(View view, int id);
    }
}