package com.beratozdemir.currencyconvertt;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView tryText;
    TextView cadText;
    TextView usdText;
    TextView jpyText;
    TextView chfText2;
    TextView metin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tryText = findViewById(R.id.tryText);
        cadText = findViewById(R.id.cadText);
        usdText = findViewById(R.id.usdText);
        jpyText = findViewById(R.id.jpyText);
        chfText2 = findViewById(R.id.chfText2);
        metin = findViewById(R.id.metin);


    }


    public void getRates(View view){

        DownloadData downloadData = new DownloadData();

        try {

            String url = "http://data.fixer.io/api/latest?access_key=389f09c06743bd1e69558e1ed6e2d30d&format=1";
            downloadData.execute(url);

        }
        catch (Exception e)
        {

        }


    }

    private class DownloadData extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... strings) {

            String result ="";
            URL url;
            HttpURLConnection httpURLConnection;

            try
            {

                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);


                int data = inputStreamReader.read();

                while (data > 0)
                {
                    char character = (char) data;
                    result += character;

                    data = inputStreamReader.read();

                }

                return result;
            }
            catch (Exception e)
            {

                return null;
            }


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


           // System.out.println("Alınan Data: " + s);
            try {

                JSONObject jsonObject = new JSONObject(s);

                String base = jsonObject.getString("base");
                //System.out.println("base: " + base);

                String rates = jsonObject.getString("rates");
               // System.out.println("rates: " + rates);

                JSONObject jsonObject1 = new JSONObject(rates);
                String turkishLira =jsonObject1.getString("TRY");
                tryText.setText("Türk Lirası: " + turkishLira);
                //System.out.println("try: " + turkishLira);

                String usd =jsonObject1.getString("USD");
                usdText.setText("Kanada Doları: " + usd);

                String cad =jsonObject1.getString("CAD");
                cadText.setText("Kanada Dolaru: " + cad);

                String chf =jsonObject1.getString("CHF");
                chfText2.setText("İsveç Frangı: " + chf);

                String jpy =jsonObject1.getString("JPY");
                jpyText.setText("Japon Yeni: " + turkishLira);

            }catch (Exception e)
            {

            }
        }
    }
}
