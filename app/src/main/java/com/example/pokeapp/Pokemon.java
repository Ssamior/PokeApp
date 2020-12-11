package com.example.pokeapp;

import android.graphics.Bitmap;

public class Pokemon {
    //Pokemon class will be used in both tables of our DB

    //fields
    private int pokemonID;
    private String pokemonName;

    //constructors
    public Pokemon() {}
    public Pokemon(int id, String name) {
        this.pokemonID = id;
        this.pokemonName = name;
    }

    //properties
    public void setPokemonID(int id) {
        this.pokemonID = id;
    }
    public int getPokemonID() {
        return this.pokemonID;
    }
    public void setPokemonName(String name) {
        this.pokemonName = name;
    }
    public String getPokemonName() {
        return this.pokemonName;
    }
}
