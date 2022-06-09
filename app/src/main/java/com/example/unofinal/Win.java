package com.example.unofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.unofinal.backend.Data;

/* The wins screen is the screen that is navigated to after one of the
players wins the current UNO game. It features of images and a change in music.
It is meant to be the final screen of the game.
 */
public class Win extends AppCompatActivity {

    //Data storage class
    Data data;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        //Initializing data object to get access to MediaPlayer and textView
        data = new Data();
        text = findViewById(R.id.winText);

        //Stops music and plays winning music
        data.backgroundMusic.stop();
        data.backgroundMusic = MediaPlayer.create(Win.this, R.raw.win);
        data.backgroundMusic.start();


        //Setting text color to yellow
        text.setTextColor(Color.parseColor("#FDD500"));
    }

    //Restarts app by navigating back to MainActivity (launching activity)
    public void restart(View v){

        //TODO:later check if too many intents running at once
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        //Stops music and resets stored data
        data.backgroundMusic.stop();
        data.reset();

        //Navigates back to MainActivity
        startActivity(intent);

    }


}