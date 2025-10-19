package com.example.ayush;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class dashboard extends AppCompatActivity implements View.OnClickListener{
    private CardView card3,card2,card1,card4;

    private TTS tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        card3=(CardView)findViewById(R.id.card1234);
        card2=(CardView)findViewById(R.id.card12345);
        card1=(CardView)findViewById(R.id.card123456);
        card4=(CardView)findViewById(R.id.card1234567);

        card4.setOnClickListener(this);
        card3.setOnClickListener(this);
        card2.setOnClickListener(this);
        card1.setOnClickListener(this);

        tts = new TTS(this, Locale.ENGLISH);


    }

    @Override
    public void onClick(View v) {
        final FirebaseAuth mAuth;
        mAuth= FirebaseAuth.getInstance();
        switch(v.getId())
        {
            case R.id.card1234:
                Intent ii=new Intent(dashboard.this,MainActivity.class);
                tts.speak("Object detection activated. Please point the camera toward the object.");
                startActivity(ii);
                break;

            case R.id.card12345:
                Intent iz=new Intent(dashboard.this,TextToSpeech.class);
                tts.speak("Text detection started. Hold the camera steady over the text to read.");
                startActivity(iz);
                break;
            case R.id.card1234567:
                Intent izw=new Intent(dashboard.this,Home.class);
                tts.speak("Fetching weather details. Please wait a moment.");
                startActivity(izw);
                break;
            case R.id.card123456:
                Intent iz1=new Intent(dashboard.this,Userlogin.class);
                iz1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear all activities on top of the login activity
                iz1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Start a new task
                FirebaseAuth.getInstance().signOut();
                tts.speak("You have been logged out successfully.");
                startActivity(iz1);
                finish();
                break;
        }

    }
}

