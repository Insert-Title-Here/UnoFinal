package com.example.unofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

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


    public void restart(View v){
        Intent intent = new Intent(Leaderboard.this, MainActivity.class);
        startActivity(intent);
    }
}