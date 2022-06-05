package com.example.unofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.example.unofinal.backend.Data;


public class Win extends AppCompatActivity {

    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        data = new Data();

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }




        data.backgroundMusic.stop();
        data.backgroundMusic = MediaPlayer.create(Win.this, R.raw.win);
        data.backgroundMusic.start();
    }


    public void restart(View v){

        //TODO:later check if too many intents running at once
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        data.backgroundMusic.stop();

        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //intent.putExtra("EXIT", true);

        data.reset();
        data.played = true;

        startActivity(intent);




        //finishAffinity();


    }


}