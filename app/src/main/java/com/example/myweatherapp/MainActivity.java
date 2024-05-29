//package com.example.myweatherapp;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//import org.json.JSONObject;
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//public class MainActivity extends AppCompatActivity {
//    private TextView cityName;
//    private Button search;
//    private TextView show;
//    private String apiKey = "3b592f8133f4f354330fdb58decd3393";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        cityName = findViewById(R.id.cityName);
//        search = findViewById(R.id.search);
//        show = findViewById(R.id.weather);
//
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String city = cityName.getText().toString().trim();
//                if (!city.isEmpty()) {
//                    fetchWeather(city);
//                } else {
//                    Toast.makeText(MainActivity.this, "Enter City", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    private void fetchWeather(String city) {
//        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
//        new GetWeatherTask().execute(url);
//    }
//
//    private class GetWeatherTask extends android.os.AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... urls) {
//            StringBuilder result = new StringBuilder();
//            try {
//                URL url = new URL(urls[0]);
//                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.connect();
//
//                InputStream inputStream = urlConnection.getInputStream();
//                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    result.append(line).append("\n");
//                }
//                reader.close();
//                inputStream.close();
//                urlConnection.disconnect();
//                return result.toString();
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            if (result != null) {
//                try {
//                    JSONObject jsonObject = new JSONObject(result);
//                    JSONObject main = jsonObject.getJSONObject("main");
//                    @SuppressLint("DefaultLocale") String weatherInfo = String.format(
//                            "Temperature : %.2f\nFeels Like : %.2f\nTemperature Max : %.2f\nTemperature Min : %.2f\nPressure : %d\nHumidity : %d",
//                            main.getDouble("temp"),
//                            main.getDouble("feels_like"),
//                            main.getDouble("temp_max"),
//                            main.getDouble("temp_min"),
//                            main.getInt("pressure"),
//                            main.getInt("humidity")
//                    );
//                    show.setText(weatherInfo);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    show.setText("Error parsing weather data.");
//                }
//            } else {
//                show.setText("Cannot find weather data.");
//            }
//        }
//    }
//}


package com.example.myweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView cityName;
    private Button search;
    private TextView show;
    private String apiKey = "3b592f8133f4f354330fdb58decd3393";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName = findViewById(R.id.cityName);
        search = findViewById(R.id.search);
        show = findViewById(R.id.weather);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = cityName.getText().toString().trim();
                if (!city.isEmpty()) {
                    fetchWeather(city);
                } else {
                    Toast.makeText(MainActivity.this, "Enter City", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchWeather(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
        new GetWeatherTask().execute(url);
    }

    private class GetWeatherTask extends android.os.AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line).append("\n");
                }
                reader.close();
                inputStream.close();
                urlConnection.disconnect();
                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject main = jsonObject.getJSONObject("main");
                    @SuppressLint("DefaultLocale") String weatherInfo = String.format(
                            "Temperature : %.2f°C\nFeels Like : %.2f°C\nTemperature Max : %.2f°C\nTemperature Min : %.2f°C\nPressure : %d hPa\nHumidity : %d%%",
                            main.getDouble("temp") - 273.15,
                            main.getDouble("feels_like") - 273.15,
                            main.getDouble("temp_max") - 273.15,
                            main.getDouble("temp_min") - 273.15,
                            main.getInt("pressure"),
                            main.getInt("humidity")
                    );
                    show.setText(weatherInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                    show.setText("Error parsing weather data.");
                }
            } else {
                show.setText("Cannot find weather data.");
            }
        }
    }
}
