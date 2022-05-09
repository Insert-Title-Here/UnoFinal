package com.example.unofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.DragAndDropPermissions;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.unofinal.backend.ActionCardColored;
import com.example.unofinal.backend.ActionCards;
import com.example.unofinal.backend.CardAdapter;
import com.example.unofinal.backend.Data;
import com.example.unofinal.backend.MainCard;
import com.example.unofinal.backend.SimpleCard;

import java.util.*;

public class CardActivityTest extends AppCompatActivity {


    Data data = new Data();
    List<String> list;
    ArrayList<SimpleCard> cardList;
    int listPosition;
    Button button;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        lv = findViewById(R.id.cardList);
        //TextView tv = findViewById(R.id.listText);
        if(!(data.gameTest.get(data.getCurrentPlayer()).isBot())) {


            button = findViewById(R.id.draw);
            list = new ArrayList<>();
            cardList = new ArrayList<>();


            //List<MainCard> cardList = new ArrayList<>(haha it's too god);


            for (int i = 0; i < data.gameTest.get(data.getCurrentPlayer()).size(); i++) {
                //list.add(data.gameTest.get(data.getCurrentPlayer()).getIndex(i).toString());
                cardList.add(new SimpleCard(data.gameTest.get(data.getCurrentPlayer()).getIndex(i).toString()));
                //cardList.add(data.game.get(0).get(i));
            }
            //System.out.println("Hi");

            /*
            for(SimpleCard i: cardList){
                System.out.println("Num: " + i.getName());
            }

             */


            //ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
            CardAdapter cardAdapter = new CardAdapter(this, R.layout.list_row, cardList);

            lv.setAdapter(cardAdapter);



            //listButtonListener(lv, list);
        }else{
            botPlay();
        }





    }

    @Override
    protected void onResume() {
        super.onResume();
        if(data.reloadAmt == 1){
            button.setText("Next Player");
        }




    }

    public void botPlay(){
        int intCard = data.gameTest.get(data.getCurrentPlayer()).move(data.previousCard.getColor(), data.previousCard);
        System.out.println(intCard);

        data.currentCard = data.gameTest.get(data.getCurrentPlayer()).getIndex(intCard);
        //System.out.println("Current Card: " + data.currentCard.toString());

        //System.out.println("botCurrentCard: " + data.currentCard);

        if (data.previousCard.matches(data.currentCard) || actionMatches()) {

            //list.remove(listPosition);

            if (data.gameTest.get(data.getCurrentPlayer()).size() == 0) {
                System.out.println("Bot win!!!");
                Intent intent = new Intent(CardActivityTest.this, Leaderboard.class);
                startActivity(intent);
            }

            if (data.currentCard.hasAction()) {
                completeAction();
            }

            data.gameTest.get(data.getCurrentPlayer()).remove(intCard);
            data.previousCard = data.currentCard;
            data.discard.push(data.previousCard);
            data.currentCard = null;


            if (data.previousCard.getAbility() != ActionCardColored.Action.REVERSE) {
                switchScreens();
            }
        }

    }

    public void draw(View view){
        if(data.reloadAmt == 0) {
            data.gameTest.get(data.getCurrentPlayer()).drawCards(1);
            finish();
            startActivity(getIntent());
            data.reloadAmt++;
        }else{
            switchScreens();
        }

        //switchScreens();

    }

    public void backToPlay(View view){

        if(!(data.gameTest.get(data.getCurrentPlayer()).isBot())){
            data.currentCard = data.gameTest.get(data.getCurrentPlayer()).getIndex(listPosition);
            System.out.println("currentCard: " + data.currentCard);

            if (data.previousCard.matches(data.currentCard) || actionMatches()) {

                list.remove(listPosition);

                if (list.size() == 0) {
                    System.out.println("You win!!!");
                    Intent intent = new Intent(CardActivityTest.this, Leaderboard.class);
                    startActivity(intent);
                }

                if (data.currentCard.hasAction()) {
                    completeAction();
                }


                data.gameTest.get(data.getCurrentPlayer()).remove(listPosition);
                data.previousCard = data.currentCard;
                data.discard.push(data.previousCard);
                data.currentCard = null;


                if (data.previousCard.getAbility() != ActionCardColored.Action.REVERSE) {
                    switchScreens();
                }
            }
        }else{

        }

    }

    private void switchScreens(){
        //Handling the Skip Action
        if(!(data.previousCard.getAction() == ActionCards.Special.DRAW4)) {
            if (data.previousCard.getAbility() == ActionCardColored.Action.SKIP || data.previousCard.getAbility() == ActionCardColored.Action.DRAW2) {
                data.skip();
            } else {
                data.switchPlayer();
            }
        }

        data.reloadAmt = 0;
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

                listPosition = position;


            }
        });
    }


    public boolean actionMatches(){
        if(data.previousCard.hasAction() && data.currentCard.hasAction()){
            if(data.previousCard.getAbility() == data.currentCard.getAbility()){
                return true;
            }
        }
        return false;
    }


    public void completeAction(){


        //Bot Version
        if(data.gameTest.get(data.getCurrentPlayer()).isBot()){
            if (data.currentCard.getAbility() == ActionCardColored.Action.DRAW2) {


                data.gameTest.get(data.getNextPlayer()).drawCards(2);

            } else if (data.currentCard.getAbility() == ActionCardColored.Action.REVERSE) {
                if (data.players == 2) {
                    System.out.println("getting here");
                    data.skip();
                    data.reloadAmt = 0;
                    finish();

                } else {

                    data.reverse = true;

                }

            } else if (data.currentCard.getAction() == ActionCards.Special.DRAW4) {
                data.gameTest.get(data.getNextPlayer()).drawCards(4);
                //Intent intent = new Intent(CardActivity.this, ColorChange.class);
                //startActivity(intent);

                data.previousCard = new MainCard(data.gameTest.get(data.getCurrentPlayer()).chooseColor(), null);





            } else if (data.currentCard.getAction() == ActionCards.Special.PICKCOLOR) {
                //Intent intent = new Intent(CardActivity.this, ColorChange.class);
                //startActivity(intent);
                data.previousCard = new MainCard(data.gameTest.get(data.getCurrentPlayer()).chooseColor(), null);


            }


        }

        //Player Version
        else {


            if (data.currentCard.getAbility() == ActionCardColored.Action.DRAW2) {


                data.gameTest.get(data.getNextPlayer()).drawCards(2);

            } else if (data.currentCard.getAbility() == ActionCardColored.Action.REVERSE) {
                if (data.players == 2) {
                    System.out.println("getting here");
                    data.skip();
                    data.reloadAmt = 0;
                    finish();

                } else {

                    data.reverse = true;

                }

            } else if (data.currentCard.getAction() == ActionCards.Special.DRAW4) {
                data.gameTest.get(data.getNextPlayer()).drawCards(4);
                Intent intent = new Intent(CardActivityTest.this, ColorChange.class);
                startActivity(intent);


            } else if (data.currentCard.getAction() == ActionCards.Special.PICKCOLOR) {
                Intent intent = new Intent(CardActivityTest.this, ColorChange.class);
                startActivity(intent);

            }

        }

    }



}