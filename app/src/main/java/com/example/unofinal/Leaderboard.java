package com.example.unofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.unofinal.backend.Data;

public class Leaderboard extends AppCompatActivity {

    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        data = new Data();



        data.backgroundMusic.stop();
        data.backgroundMusic = MediaPlayer.create(Leaderboard.this, R.raw.win);
        data.backgroundMusic.start();
    }
}