package com.example.unofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.unofinal.backend.Data;

public class MiddleScreen extends AppCompatActivity {

    TextView player;
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle_screen);

        player = findViewById(R.id.playerText);
        data = new Data();


        player.setText("Player " + data.getCurrentPlayer() + "'s turn");


    }


    public void btnClick(View view){
        finish();
    }
}