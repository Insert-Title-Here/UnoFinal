package com.example.unofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        }else{
            //players.setHint("Please enter the number of players");
            error.setAlpha(1.0f);


        }
    }


    public void leaderboardBtnClick(View view){
        Intent intent = new Intent(MainActivity.this, CardTestHorizontal.class);
        intent.putExtra("Amt Players", "2");

        startActivity(intent);
    }


}