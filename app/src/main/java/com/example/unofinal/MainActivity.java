package com.example.unofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView error;

    //TODO: Fix music stuff (doesn't play sometimes)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MediaPlayer backgroundMusic = MediaPlayer.create(MainActivity.this, R.raw.sleepycat);
        backgroundMusic.start();


        setContentView(R.layout.activity_main);

        error = findViewById(R.id.errorMessage);

        error.setAlpha(0.0f);


    }

    public void onBtnClick(View view){
        //Button button = findViewById(R.id.playButton);
        EditText players = findViewById(R.id.playerNumber);

        String amtPlayers = players.getText().toString();




        if(!(players.getText().toString().isEmpty())) {
            Intent intent = new Intent(MainActivity.this, CardTestHorizontal.class);
            intent.putExtra("Amt Players", amtPlayers);
            startActivity(intent);

            MediaPlayer backgroundMusic = MediaPlayer.create(MainActivity.this, R.raw.cardshuffle);
            backgroundMusic.start();
        }else{
            //players.setHint("Please enter the number of players");
            error.setAlpha(1.0f);


        }
    }


    public void leaderboardBtnClick(View view){
        Intent intent = new Intent(MainActivity.this, CardTestHorizontal.class);
        intent.putExtra("Amt Players", "2");

        MediaPlayer backgroundMusic = MediaPlayer.create(MainActivity.this, R.raw.cardshuffle);
        backgroundMusic.start();

        startActivity(intent);
    }


}