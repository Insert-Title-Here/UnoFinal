package com.example.unofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

public class Leaderboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        //TODO: Change Preview cuz it has person saying stuff in it
        //TODO: Also figure out how to stop background audio when win audio is playing
        MediaPlayer backgroundMusic = MediaPlayer.create(Leaderboard.this, R.raw.win);
        backgroundMusic.start();
    }
}