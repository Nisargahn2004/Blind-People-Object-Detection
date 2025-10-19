package com.example.ayush;

import android.location.LocationManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class Home extends AppCompatActivity implements OnInitListener {

    private TextView weatherTextView;
    private Button speakButton;
    private TextToSpeech textToSpeech;
    private RequestQueue requestQueue;
    private final String API_KEY = "dab3af44de7d24ae7ff86549334e45bd";
    private final String API_URL = "https://api.openweathermap.org/data/2.5/weather?q=mandya&units=metric&appid=" + API_KEY;
    private TTS tts;

    String locationName = LocationManager.GPS_PROVIDER;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        weatherTextView = findViewById(R.id.weatherTextView);

        requestQueue = Volley.newRequestQueue(this);
        textToSpeech = new TextToSpeech(this, this);

        tts = new TTS(this, Locale.ENGLISH);

        fetchWeatherData();
    }

    private void fetchWeatherData() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, API_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject mainObject = response.getJSONObject("main");
                            String locationName = response.getString("name");
                            double temp = mainObject.getDouble("temp");
                            String weatherDescription = response.getJSONArray("weather")
                                    .getJSONObject(0).getString("description");

                            String weather = "Weather in " + locationName + ": The current weather is " + temp + "°C and the weather is " + weatherDescription + ".";
                            weatherTextView.setText(weather);
                            textToSpeech.speak(weather, TextToSpeech.QUEUE_FLUSH, null, null);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        weatherTextView.setText("Error fetching weather data");
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.getDefault());
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                speakButton.setEnabled(false);
            }
        } else {
            speakButton.setEnabled(false);
        }
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
