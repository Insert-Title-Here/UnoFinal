package com.example.unofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.unofinal.backend.ActionCardColored;
import com.example.unofinal.backend.ActionCards;
import com.example.unofinal.backend.Data;
import com.example.unofinal.backend.MainCard;
import com.example.unofinal.backend.Bot;
import com.example.unofinal.backend.Player;


import java.util.*;






public class Play  extends AppCompatActivity {


    Data data = new Data();
    TextView playerView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        //System.out.println("sldkfjldf");

        //cardImplementation();

        Intent intent = getIntent();
        data.players = Integer.parseInt(intent.getStringExtra("Amt Players"));

        if(data.players > 5){
            data.players = 5;
        }

        if(data.players < 1){
            data.players = 1;
        }

        playerView = findViewById(R.id.player);


        newCardImplementation();


        //System.out.println(data.gameTest);


        //writeToFile(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        playerView.setText("" + data.currentPlayer);


    }


    /*private void writeToFile(Context context) {
        String data = "Hello";
        try {
            FileOutputStream outputStreamWriter = openFileOutput("data.txt", MODE_PRIVATE);
            //outputStreamWriter.write("Draw Pile");
            outputStreamWriter.write(data.getBytes());


            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

     */

    private void gameInit(){
        for(int i = 0; i < 2; i++){
            data.game.add(i, new ArrayList<>());
        }
    }

    public void backBtnClick(View view){
        finish();
    }

    public void imgBtnClick(View view){
        Intent intent = new Intent(Play.this, CardActivity.class);
        startActivity(intent);

    }

    private void newCardImplementation() {
        buildDeck(data.deck);
        shuffleDeck(data.deck);
        newGameInit();
        //setUpGame(data.deck, data.drawPile, data.game);

        //First Card
        data.previousCard = data.discard.push(data.drawPile.pop());

        while(data.previousCard.getNum() == MainCard.Numbers.NONE){
            data.previousCard = data.discard.push(data.drawPile.pop());

        }
        //MainCard topOfDiscard = data.discard.peek();
        System.out.println(data.previousCard);
        //ArrayList<MainCard> currentHand = data.game.get(0);

    }

    private void newGameInit(){
        for (int i = 0; i < 108; i ++) {
            data.drawPile.push(data.deck[i]);
        }


        for(int i = 0; i < data.players; i++){
            data.gameTest.add(new Player());
        }

        if(data.players == 1){
            data.gameTest.add(new Bot());
        }

    }

    /*public static void newSetUpGame(MainCard[] arr, Stack<MainCard> draw, ArrayList<ArrayList<MainCard>> hands) {



        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 7; j++) {
                hands.get(i).add(draw.pop());
            }
        }
    }

     */



    private void cardImplementation() {


        buildDeck(data.deck);
        shuffleDeck(data.deck);
        gameInit();
        setUpGame(data.deck, data.drawPile, data.game);
        data.discard.push(data.drawPile.pop());
        MainCard topOfDiscard = data.discard.peek();
        ArrayList<MainCard> currentHand = data.game.get(0);

        /*
        int next = 1;
        boolean isInProgress = gameOver(game);
        while (isInProgress) {
            isInProgress = gameOver(game);
            if (canPlayCard(currentHand, topOfDiscard)) {
                // player action
            } else {
                drawCards(1, currentHand, drawPile);
            }
            if (next + 1 == game.size()) {
                next = 0;
            } else {
                next++;
            }
            currentHand = game.get(next);
        }

         */


    }

    public static void buildDeck(MainCard[] arr) {
        // every color has 1 0 and 2 of each number 1-9
        // every color has two of each action card (6)
        // they're are 8 wild cards 4 color pickers, and 4 +4's
        // [0] - [18] red
        // [19] - [37] blue
        // [38] - [56] green
        // [57] - [75] yellow
        // [76] - [81] red action
        // [82] - [87] blue action
        // [88] - [93] green action
        // [94] - [99] yellow action
        // [100] - [107] wild cards

        MainCard.Color card;
        MainCard.Numbers temp;
        ActionCardColored.Action ab;
        ActionCards.Special special;
        int startSequence;
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                card = MainCard.Color.RED;
                startSequence = 0;
            } else if (i == 1) {
                card = MainCard.Color.BLUE;
                startSequence = 19;
            } else if (i == 2) {
                card = MainCard.Color.GREEN;
                startSequence = 38;
            } else {
                card = MainCard.Color.YELLOW;
                startSequence = 57;
            }
            arr[startSequence] = new MainCard(card, MainCard.Numbers.ZERO);
            for (int k = 1; k <= 9; k++) {
                for (int l = 0; l < 2; l++) {
                    if (k == 1) {
                        temp = MainCard.Numbers.ONE;
                    } else if (k == 2) {
                        temp = MainCard.Numbers.TWO;
                    } else if (k == 3) {
                        temp = MainCard.Numbers.THREE;
                    } else if (k == 4) {
                        temp = MainCard.Numbers.FOUR;
                    } else if (k == 5) {
                        temp = MainCard.Numbers.FIVE;
                    } else if (k == 6) {
                        temp = MainCard.Numbers.SIX;
                    } else if (k == 7) {
                        temp = MainCard.Numbers.SEVEN;
                    } else if (k == 8) {
                        temp = MainCard.Numbers.EIGHT;
                    } else {
                        temp = MainCard.Numbers.NINE;
                    }
                    arr[(k + l) + (k - 1) + (i * 19)] = new MainCard(card, temp);
                }
            }
            for (int j = 76; j <= 81; j++) {
                if (j <= 77) {
                    ab = ActionCardColored.Action.SKIP;
                } else if (j <= 79) {
                    ab = ActionCardColored.Action.REVERSE;
                } else {
                    ab = ActionCardColored.Action.DRAW2;
                }
                arr[j + (i * 6)] = new ActionCardColored(ab, card);
            }
        }
        for (int g = 100; g < 108; g++) {
            if (g <= 103) {
                special = ActionCards.Special.DRAW4;
            } else {
                special = ActionCards.Special.PICKCOLOR;
            }
            arr[g] = new ActionCards(special);
        }

    }

    public static void shuffleDeck(MainCard[] arr) {

        List<MainCard> temp = Arrays.asList(arr);
        Collections.shuffle(temp);
        temp.toArray(arr);

    }

    public static void setUpGame(MainCard[] arr, Stack<MainCard> draw, ArrayList<ArrayList<MainCard>> hands) {
        for (int i = 0; i < 108; i ++) {
            draw.push(arr[i]);
        }



        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 7; j++) {
                hands.get(i).add(draw.pop());
            }
        }






    }

    public static void drawCards(int amount, ArrayList<MainCard> recipient, Stack<MainCard> drawPile) {
        for (int i = 0; i < amount; i++) {
            recipient.add(drawPile.pop());
        }
    }

    public static boolean gameOver(ArrayList<ArrayList<MainCard>> arr) {
        for (ArrayList<MainCard> a : arr) {
            if (a.size() == 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean canPlayCard(ArrayList<MainCard> hand, MainCard topOfDiscard) {
        for (MainCard c : hand) {
            if (c.matches(topOfDiscard)) {
                return true;
            }
        }
        return false;
    }

    public static void playCard(ArrayList<MainCard> hand, MainCard card, Stack<MainCard> discard) {
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i) == card) {
                hand.remove(i);
            }
        }
        discard.push(card);
    }


}