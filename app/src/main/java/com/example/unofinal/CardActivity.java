package com.example.unofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.unofinal.backend.Data;
import com.example.unofinal.backend.MainCard;

import java.util.*;

public class CardActivity extends AppCompatActivity {


    Data data = new Data();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        ListView lv = findViewById(R.id.cardList);
        //TextView tv = findViewById(R.id.listText);

        List<String> list = new ArrayList<>();
        //List<MainCard> cardList = new ArrayList<>();


        for(int i = 0; i < data.gameTest.get(data.currentPlayer - 1).size(); i++){
            list.add(data.gameTest.get(data.currentPlayer - 1).getIndex(i).toString());
            //cardList.add(data.game.get(0).get(i));
        }

        System.out.println("lskdjflskdjf");



        ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);


        lv.setAdapter(adapter);

        listButtonListener(lv, list);

        //System.out.println("sldflskfj");




    }

    public void backToPlay(View view){
        data.switchPlayer();
        finish();
    }




    private void listButtonListener(ListView lv, List<String> list){

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == 0){
                    System.out.println(list.get(0));
                    //tv.setText(list.get(0));

                }else if(position == 1){
                    System.out.println(list.get(1));
                    //tv.setText(list.get(1));


                }else if(position == 2){
                    System.out.println(list.get(2));
                    //tv.setText(list.get(2));


                }else if(position == 3){
                    System.out.println(list.get(3));
                    //tv.setText(list.get(3));

                }else if(position == 4){
                    System.out.println(list.get(4));
                    //tv.setText(list.get(4));

                }else if(position == 5){
                    System.out.println(list.get(5));
                    //tv.setText(list.get(5));

                }else{
                    System.out.println(list.get(6));
                    //tv.setText(list.get(6));

                }

                data.currentCard = data.gameTest.get(data.currentPlayer - 1).getIndex(position);
                data.gameTest.get(data.currentPlayer - 1).remove(position);
            }
        });
    }



}