package com.example.pokeapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PokemonInfo extends Activity {

    private Bundle b;
    String[][] pokemonsFR = {{"0","null"},{"1","Bulbizarre"},{"2","Herbizarre"},{"3","Florizarre"},{"4","Salamèche"},{"5","Reptincel"},{"6","Dracaufeu"},{"7","Carapuce"},{"8","Carabaffe"},{"9","Tortank"},{"10","Chenipan"},{"11","Chrysacier"},{"12","Papilusion"},{"13","Aspicot"},{"14","Coconfort"},{"15","Dardargnan"},{"16","Roucool"},{"17","Roucoups"},{"18","Roucarnage"},{"19","Rattata"},{"20","Rattatac"},{"21","Piafabec"},{"22","Rapasdepic"},{"23","Abo"},{"24","Arbok"},{"25","Pikachu"},{"26","Raichu"},{"27","Sabelette"},{"28","Sablaireau"},{"29","Nidoran♀"},{"30","Nidorina"},{"31","Nidoqueen"},{"32","Nidoran♂"},{"33","Nidorino"},{"34","Nidoking"},{"35","Mélofée"},{"36","Mélodelfe"},{"37","Goupix"},{"38","Feunard"},{"39","Rondoudou"},{"40","Grodoudou"},{"41","Nosferapti"},{"42","Nosferalto"},{"43","Mystherbe"},{"44","Ortide"},{"45","Rafflesia"},{"46","Paras"},{"47","Parasect"},{"48","Mimitoss"},{"49","Aéromite"},{"50","Taupiqueur"},{"51","Triopikeur"},{"52","Miaouss"},{"53","Persian"},{"54","Psykokwak"},{"55","Akwakwak"},{"56","Férosinge"},{"57","Colossinge"},{"58","Caninos"},{"59","Arcanin"},{"60","Ptitard"},{"61","Têtarte"},{"62","Tartard"},{"63","Abra"},{"64","Kadabra"},{"65","Alakazam"},{"66","Machoc"},{"67","Machopeur"},{"68","Mackogneur"},{"69","Chétiflor"},{"70","Boustiflor"},{"71","Empiflor"},{"72","Tentacool"},{"73","Tentacruel"},{"74","Racaillou"},{"75","Gravalanch"},{"76","Grolem"},{"77","Ponyta"},{"78","Galopa"},{"79","Ramoloss"},{"80","Flagadoss"},{"81","Magnéti"},{"82","Magnéton"},{"83","Canarticho"},{"84","Doduo"},{"85","Dodrio"},{"86","Otaria"},{"87","Lamantine"},{"88","Tadmorv"},{"89","Grotadmorv"},{"90","Kokiyas"},{"91","Crustabri"},{"92","Fantominus"},{"93","Spectrum"},{"94","Ectoplasma"},{"95","Onix"},{"96","Soporifik"},{"97","Hypnomade"},{"98","Krabby"},{"99","Krabboss"},{"100","Voltorbe"},{"101","Électrode"},{"102","Noeunoeuf"},{"103","Noadkoko"},{"104","Osselait"},{"105","Ossatueur"},{"106","Kicklee"},{"107","Tygnon"},{"108","Excelangue"},{"109","Smogo"},{"110","Smogogo"},{"111","Rhinocorne"},{"112","Rhinoféros"},{"113","Leveinard"},{"114","Saquedeneu"},{"115","Kangourex"},{"116","Hypotrempe"},{"117","Hypocéan"},{"118","Poissirène"},{"119","Poissoroy"},{"120","Stari"},{"121","Staross"},{"122","M. Mime"},{"123","Insécateur"},{"124","Lippoutou"},{"125","Élektek"},{"126","Magmar"},{"127","Scarabrute"},{"128","Tauros"},{"129","Magicarpe"},{"130","Léviator"},{"131","Lokhlass"},{"132","Métamorph"},{"133","Évoli"},{"134","Aquali"},{"135","Voltali"},{"136","Pyroli"},{"137","Porygon"},{"138","Amonita"},{"139","Amonistar"},{"140","Kabuto"},{"141","Kabutops"},{"142","Ptéra"},{"143","Ronflex"},{"144","Artikodin"},{"145","Électhor"},{"146","Sulfura"},{"147","Minidraco"},{"148","Draco"},{"149","Dracolosse"},{"150","Mewtwo"},{"151","Mew"}};


    @SuppressLint("StaticFieldLeak")
    //Main class : update GUI
    class AsyncInfoPokemon extends AsyncTask<String, Void, JSONObject> {


        private String log;
        Map<String,Integer> pokemonIdToFr = new HashMap<String,Integer>();


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
                    Log.i("JFL",s);


                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            //Collection of needed informations
            JSONObject jsonResult = null;
            String name = "null";
            String urlImage = "";



            try {
                jsonResult = new JSONObject(log);
            } catch (JSONException e) { e.printStackTrace(); }

            //Get id passed in parameter and get FR pokemon name
            int id = b.getInt("id");
            name = pokemonsFR[id][1];
            Log.i("JFL",name+id);

            try {
                urlImage = jsonResult != null ? jsonResult.getJSONObject("sprites").getString("front_default") : null;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Modification UI
            String finalName = name.substring(0, 1).toUpperCase() + name.substring(1); //Putting first letter in caps
            String finalUrlImage = urlImage;
            runOnUiThread(new Runnable() {
                @Override
                //Set pokemon name and image
                public void run() {
                    TextView txt = findViewById(R.id.namePokemon);
                    txt.setText(finalName);
                    new AsyncBitmapDownloader().execute(finalUrlImage);
                }
            });

        }
    }

    @SuppressLint("StaticFieldLeak")
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

                //After getting the image as bitmap, update the GUI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageView imView = findViewById(R.id.imgPokemon);
                        imView.setImageBitmap(image);
                    }
                });

            } catch (IOException e) {
                //Log.i("im","image pas ok");
                e.printStackTrace();
            }
            return null;
        }

    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_info);
        Log.i("JFL", "Fiche initialisee");

        final String baseUrl = "https://pokeapi.co/api/v2/";

        //Get id passed in parameter
        this.b = getIntent().getExtras();
        int id = 0;
        if (b!=null) {
            id = b.getInt("id");
        }
        new AsyncInfoPokemon().execute(baseUrl+"pokemon/"+id);
        Button btnAddFav = findViewById(R.id.button3);
        //need a final id to use in inner class
        final int finalId = id;
        btnAddFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDBHandler dbHandler = new MyDBHandler(getApplicationContext(), "Favoris", null, 1);
                String name = pokemonsFR[finalId][1];
                Pokemon pokemon = new Pokemon(finalId, name);
                dbHandler.addHandler(pokemon);
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
