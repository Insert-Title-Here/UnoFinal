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

public class PlayerCards extends AppCompatActivity {

    Data data = new Data();
    List<String> list;
    int listPosition;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_cards);

        ListView lv = findViewById(R.id.displayCards);
        //TextView tv = findViewById(R.id.listText);


        button = findViewById(R.id.backToPlay);
        list = new ArrayList<>();

        //List<MainCard> cardList = new ArrayList<>(haha it's too god);


        for (int i = 0; i < data.gameTest.size(); i++) {
            if(i == data.getCurrentPlayer()){
                list.add("Player " + (i + 1) + " (You) have " + data.gameTest.get(i).size() + " cards");

            }else {
                list.add("Player " + (i + 1) + " has " + data.gameTest.get(i).size() + " cards");
            }
            //cardList.add(data.game.get(0).get(i));
        }


        ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);


        lv.setAdapter(adapter);


    }

    public void changeScreen(View v){

        finish();

    }
}


