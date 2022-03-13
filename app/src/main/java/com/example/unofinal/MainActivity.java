package com.example.unofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onBtnClick(View view){
        //Button button = findViewById(R.id.playButton);
        Intent intent = new Intent(MainActivity.this, Play.class);
        startActivity(intent);
    }

    public void leaderboardBtnClick(View view){
        Intent intent = new Intent(MainActivity.this, Leaderboard.class);
        startActivity(intent);
    }
}