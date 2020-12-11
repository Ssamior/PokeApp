package com.example.pokeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    class AsyncJSONData extends AsyncTask<String, Void, JSONObject> {

        private String log;

        @Override
        protected JSONObject doInBackground(String... strUrl) {
            URL url = null;
            try {
                url = new URL(strUrl[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    String s = readStream(in);
                    this.log = s;
                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    class AsyncBitmapDownloader extends AsyncTask<String, Void, Bitmap> {

            Bitmap image;

            @Override
            protected Bitmap doInBackground(String... strUrl) {
                URL url = null;
                try {
                    url = new URL(strUrl[0]);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    this.image = BitmapFactory.decodeStream(in);
                    //Log.i("ima","image ok");

                } catch (IOException e) {
                    Log.i("im", "image pas ok");
                    e.printStackTrace();
                }
                return null;
            }

        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnRandom = findViewById(R.id.button);
        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent showInfo = new Intent(getApplicationContext(), PokemonInfo.class);
                    Bundle b = new Bundle();
                    int idRandom = 1 + (int) (Math.random() * 151);
                    b.putInt("id", idRandom);
                    showInfo.putExtras(b);
                    startActivity(showInfo);
                }
        });

        Button btnFav = findViewById(R.id.button2);
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent favorites = new Intent(getApplicationContext(), Favoris.class);
                startActivity(favorites);
            }
        });


    }

    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }

}