package com.example.unofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.unofinal.backend.Data;


/* The MiddleScreen screen is the screen that is navigated to during the game
in between each players turn. It acts as a failsafe so that each player wont see
the next players cards when they end their turn (mostly an honor system though)
 */
public class MiddleScreen extends AppCompatActivity {

    //Data storage object and textView for showing whose turn it is
    TextView player;
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle_screen);

        //Initializing data object and textView
        data = new Data();
        player = findViewById(R.id.textView2);

        //Getting which player's turn it is and setting it as the textView's text
        String text = "Player " + (data.getCurrentPlayer() + 1) + "'s Turn";
        player.setText(text);

    }

    //Navigates back to previous activity and allows for navigation to continue
    public void btnClick(View view){

        finish();
        data.change = true;
    }
}