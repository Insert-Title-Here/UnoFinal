package com.example.unofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.unofinal.backend.Data;

public class MiddleScreen extends AppCompatActivity {

    TextView playerText;
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle_screen);

        playerText = findViewById(R.id.currentPlayer);
        data = new Data();


        playerText.setText("Player turn");
        playerText.setAlpha(1.0f);
        System.out.println("The Player: " + data.getCurrentPlayer());


    }


    public void btnClick(View view){

        finish();
        data.change = true;
    }
}