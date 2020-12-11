package com.example.pokeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Favoris extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favoris);

        final String baseUrl = "https://pokeapi.co/api/v2/";

        //load data from the favorites DB
        MyDBHandler dbHandler = new MyDBHandler(getApplicationContext(), "Favoris", null, 1);
        String[][] strPokemons = dbHandler.loadHandler();
        Log.i("JFL","Nombre de favoris : "+String.valueOf(strPokemons.length));

        RecyclerView rv = findViewById(R.id.rvFav);
        rv.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, strPokemons);
        adapter.setClickListener(new RecyclerViewAdapter.MyAdapterListener() {
            @Override
            public void onItemClick(View view, int id) {
                Intent showInfo = new Intent(getApplicationContext(), PokemonInfo.class);
                Bundle b = new Bundle();
                b.putInt("id", id);
                showInfo.putExtras(b);
                startActivity(showInfo);
            }

        });
        rv.setAdapter(adapter);

    }

    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while (i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }
}
