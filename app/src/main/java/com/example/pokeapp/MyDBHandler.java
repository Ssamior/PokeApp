package com.example.pokeapp;

import android.content.Context;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper {
    //informations
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "pokemonDB.db";
    private static final String TABLE_NAME = "Pokemon";
    private static final String COLUMN_ID = "PokemonID";
    private static final String COLUMN_NAME = "PokemonName";
    private static final String COLUMN_IMG = "PokemonImg";
    //initialize
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_NAME + " TEXT )";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public String[][] loadHandler() {
        ArrayList<String[]> result = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result0 = cursor.getInt(0);
            String result1 = cursor.getString(1);
            String[] res = {String.valueOf(result0),result1};
            result.add(res);
        }
        String[][] simpleArray = new String[ result.size() ][];
        result.toArray( simpleArray );
        cursor.close();
        db.close();
        return simpleArray;
    }
    public void addHandler(Pokemon pokemon) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, pokemon.getPokemonID());
        values.put(COLUMN_NAME, pokemon.getPokemonName());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public Pokemon findHandler(String pokemonName) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " = '" + pokemonName + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Pokemon pokemon = new Pokemon();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            pokemon.setPokemonID(Integer.parseInt(cursor.getString(0)));
            pokemon.setPokemonName(cursor.getString(1));
            cursor.close();
        } else {
            pokemon = null;
        }
        db.close();
        return pokemon;
    }
    public boolean deleteHandler(int id) {
        boolean result = false;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + String.valueOf(id) + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Pokemon pokemon = new Pokemon();
        if (cursor.moveToFirst()) {
            pokemon.setPokemonID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[] {
                    String.valueOf(pokemon.getPokemonID())
            });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
    public boolean updateHandler(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COLUMN_ID, id);
        args.put(COLUMN_NAME, name);
        return db.update(TABLE_NAME, args, COLUMN_ID + " = " + id, null) > 0;
    }


}
