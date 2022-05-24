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
        data = new Data();


        player = findViewById(R.id.textView2);

        String text = "Player " + (data.getCurrentPlayer() + 1) + "'s Turn";


        player.setText(text);
        //player.setAlpha(1.0f);
        System.out.println("The Player: " + data.getCurrentPlayer());


    }


    public void btnClick(View view){

        finish();
        data.change = true;
    }
}