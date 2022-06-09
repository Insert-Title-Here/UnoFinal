package com.example.unofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.unofinal.backend.Data;

public class MiddleScreen extends AppCompatActivity {

    TextView player;
    TextView previousamtCards;
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle_screen);
        data = new Data();


        player = findViewById(R.id.textView2);
        previousamtCards = findViewById(R.id.amtCards);

        String text = "Player " + (data.getCurrentPlayer() + 1) + "'s Turn";


        player.setText(text);
        previousamtCards.setText("The Previous Player has " + data.gameTest.get(data.getPreviousPlayer()).size() + " cards");
        //player.setAlpha(1.0f);
        System.out.println("The Player: " + data.getCurrentPlayer());


    }


    public void btnClick(View view){

        finish();
        data.change = true;
    }
}