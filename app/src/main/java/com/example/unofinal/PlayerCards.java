package com.example.unofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.unofinal.backend.Data;

import java.util.ArrayList;
import java.util.List;

/* The PlayerCards screen is the screen that is navigated to during the game
to check how many cards each player (including the current player) has. It
displays a list to the user that shows how many cards everyone playing has.
 */
public class PlayerCards extends AppCompatActivity {

    //Data storage object, list to show players' cards and button to navigate back
    Data data = new Data();
    List<String> list;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_cards);

        //Initializing list, button and listView
        ListView lv = findViewById(R.id.displayCards);
        button = findViewById(R.id.backToPlay);
        list = new ArrayList<>();



        //Adding all the players and their cards to the list (adds "(You)" if the player shown is you)
        for (int i = 0; i < data.game.size(); i++) {
            if(i == data.getCurrentPlayer()){
                list.add("Player " + (i + 1) + " (You) have " + data.game.get(i).size() + " cards");

            }else {
                list.add("Player " + (i + 1) + " has " + data.game.get(i).size() + " cards");
            }
        }


        //Arrayadapter transfers the elements of the list to be displayed on the listView
        ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);


    }

    //Navigates back to previous activity
    public void changeScreen(View v){

        finish();

    }
}


