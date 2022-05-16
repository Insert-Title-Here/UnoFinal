package com.example.unofinal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unofinal.backend.ActionCardColored;
import com.example.unofinal.backend.ActionCards;
import com.example.unofinal.backend.Bot;
import com.example.unofinal.backend.Data;
import com.example.unofinal.backend.MainCard;
import com.example.unofinal.backend.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;


//TODO: add way to check card if valid playable card (check logic bug of skip/reverse for 2 players)
//TODO: if there are two of the same cards then might remove wrong one
//TODO: fix thread stuff

public class CardTestHorizontal extends AppCompatActivity {

    Data data = new Data();
    TextView player;
    LinearLayout layout;
    Data.SwitchPlayer nextType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //System.out.println("First");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_test_horizontal);

        ImageView image = findViewById(R.id.discard);
        layout = (LinearLayout) findViewById(R.id.handlayout);
        player = findViewById(R.id.playerNum);
        nextType = Data.SwitchPlayer.NORMAL;
        data.change = false;

        if(!data.initialized) {
            data.players = 2;
            newCardImplementation();
        }

        player.setText("Player " + data.currentPlayer);

        if(data.getCurrentPlayer() == 1){
            player.setTextColor(Color.RED);
        }else{
            player.setTextColor(Color.YELLOW);
        }




        //System.out.println(data.gameTest);


        for (int i = 0; i < data.gameTest.get(data.getCurrentPlayer()).size(); i++) {
            filllayout(layout, new ImageButton(getApplicationContext()), data.getImage(data.gameTest.get(data.getCurrentPlayer()).getIndex(i).toString()));
            //System.out.println("Card: " + data.gameTest.get(data.getCurrentPlayer()).getIndex(i).toString());

        }

        //getViewInLayout(layout, 1).setScaleType(ImageView.ScaleType.FIT_XY);

        for (int i = 0; i < data.gameTest.get(data.getCurrentPlayer()).size(); i++) {
            ImageButton button = getViewInLayout(layout, i);
            button.setScaleType(ImageView.ScaleType.FIT_XY);
            button.getLayoutParams().width = 300;
            button.setTag(data.gameTest.get(data.getCurrentPlayer()).getIndex(i).toString());
            cardClick(button);



        }




        //System.out.println("PreviousCard:  " + data.previousCard);

        image.setImageResource(data.getImage(data.discard.peek().toString()));


        //image.setImageResource(R.drawable.drawfour);
        //toast.setView(view1);
        //toast.setDuration(Toast.LENGTH_LONG);
        //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

        if(!data.initialized){
            data.initialized = true;
        }

    }


    public ImageButton getViewInLayout(LinearLayout layout, int position) {

        //int count = layout.getChildCount();
        ImageButton v = null;


        for (int i = 0; i <= position; i++) {
            v = (ImageButton) layout.getChildAt(i);
            //do something with your child element
        }


        //v = (ImageButton) layout.getChildAt(1);

        return v;

    }


    public void filllayout(LinearLayout layout, ImageButton button, int image) {
        button.setImageResource(image);

        layout.addView(button);

    }

    //TODO: remove everything past here later
    private void newCardImplementation() {
        buildDeck(data.deck);
        shuffleDeck(data.deck);
        newGameInit();
        //setUpGame(data.deck, data.drawPile, data.game);

        //First Card
        //data.previousCard = data.discard.push(data.drawPile.pop());
        data.discard.push(data.drawPile.pop());

        while (data.discard.peek().getNum() == MainCard.Numbers.NONE) {
            //data.previousCard = data.discard.push(data.drawPile.pop());
            data.discard.push(data.drawPile.pop());

        }
        //MainCard topOfDiscard = data.discard.peek();
        //System.out.println(data.discard.peek());
        //ArrayList<MainCard> currentHand = data.game.get(0);

    }

    private void newGameInit() {
        for (int i = 0; i < 108; i++) {
            data.drawPile.push(data.deck[i]);
        }


        for (int i = 0; i < data.players; i++) {
            data.gameTest.add(new Player());
        }

        if (data.players == 1) {
            data.gameTest.add(new Bot());
            data.players = 2;
            System.out.println("has bot");
        }

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

    public void draw(View view){
        if(data.reloadAmt == 0) {
            data.gameTest.get(data.getCurrentPlayer()).drawCards(1);

            System.out.println("PreviousCard:  " + data.previousCard);

            finish();
            startActivity(getIntent());
            data.reloadAmt++;
        }else{
            switchScreens(Data.SwitchPlayer.NORMAL);
            finish();
            startActivity(getIntent());
        }


    }


    //TODO: fix skip, reverse, draw2, draw4
    //This is only for 2 players currently, need to make the reverse dynamic later
    private void switchScreens(){
        //Handling the Skip Action
        if(!(data.discard.peek().getAction() == ActionCards.Special.DRAW4)) {
            if (data.discard.peek().getAbility() == ActionCardColored.Action.SKIP || data.discard.peek().getAbility() == ActionCardColored.Action.DRAW2 || data.discard.peek().getAbility() == ActionCardColored.Action.REVERSE) {

                if(data.discard.peek().getAbility() == ActionCardColored.Action.DRAW2){
                    data.gameTest.get(data.getNextPlayer()).drawCards(2);
                }

                //data.skip();
                nextType = Data.SwitchPlayer.SKIP;
            } else {
                //data.switchPlayer();

                if(data.discard.peek().getAction() == ActionCards.Special.PICKCOLOR){

                    System.out.println("WILD Testing");
                    Intent newIntent = new Intent(CardTestHorizontal.this, ColorChange.class);
                    startActivity(newIntent);
                }
                nextType = Data.SwitchPlayer.NORMAL;

            }

            System.out.println("CurrentPlayer: " + data.currentPlayer);
        }else{
            data.gameTest.get(data.getNextPlayer()).drawCards(4);
            //data.skip();

            nextType = Data.SwitchPlayer.SKIP;

            System.out.println("Draw4 Testing");

            Intent newIntent = new Intent(CardTestHorizontal.this, ColorChange.class);
            startActivity(newIntent);

            System.out.println("new intent");
        }

        data.nextPlayer(nextType);




        data.reloadAmt = 0;
        //finish();
    }

    public void switchScreens(Data.SwitchPlayer next){
        data.nextPlayer(next);
    }


    public void cardClick(ImageButton button){
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                //System.out.println("Old Hand:");
                //data.gameTest.get(data.getCurrentPlayer()).printHand();
                //System.out.println();

                String card = (String)button.getTag();

                MainCard tempCard = null;

                if(card.contains("PICKCOLOR") || card.contains("DRAW4")){
                    System.out.println(card + " is really special");

                    if(card.contains("PICKCOLOR")){
                        tempCard = new ActionCards(ActionCards.Special.PICKCOLOR);
                    }else {
                        tempCard = new ActionCards(ActionCards.Special.DRAW4);
                    }

                }else if(card.contains("REVERSE") || card.contains("SKIP") || card.contains("DRAW2")){
                    System.out.println(card + " is somewhat special");

                    if(card.substring(0, card.indexOf(" ")).equals("RED")){
                        if(card.contains("REVERSE")){
                            tempCard = new ActionCardColored(ActionCardColored.Action.REVERSE, MainCard.Color.RED);

                        }else if(card.contains("SKIP")){
                            tempCard = new ActionCardColored(ActionCardColored.Action.SKIP, MainCard.Color.RED);


                        }else{
                            tempCard = new ActionCardColored(ActionCardColored.Action.DRAW2, MainCard.Color.RED);


                        }


                    }else if(card.substring(0, card.indexOf(" ")).equals("BLUE")){

                        if(card.contains("REVERSE")){
                            tempCard = new ActionCardColored(ActionCardColored.Action.REVERSE, MainCard.Color.BLUE);


                        }else if(card.contains("SKIP")){
                            tempCard = new ActionCardColored(ActionCardColored.Action.SKIP, MainCard.Color.BLUE);


                        }else{
                            tempCard = new ActionCardColored(ActionCardColored.Action.DRAW2, MainCard.Color.BLUE);


                        }


                    }else if(card.substring(0, card.indexOf(" ")).equals("GREEN")){

                        if(card.contains("REVERSE")){
                            tempCard = new ActionCardColored(ActionCardColored.Action.REVERSE, MainCard.Color.GREEN);


                        }else if(card.contains("SKIP")){
                            tempCard = new ActionCardColored(ActionCardColored.Action.SKIP, MainCard.Color.GREEN);


                        }else{
                            tempCard = new ActionCardColored(ActionCardColored.Action.DRAW2, MainCard.Color.GREEN);


                        }



                    }else{

                        if(card.contains("REVERSE")){
                            tempCard = new ActionCardColored(ActionCardColored.Action.REVERSE, MainCard.Color.YELLOW);


                        }else if(card.contains("SKIP")){
                            tempCard = new ActionCardColored(ActionCardColored.Action.SKIP, MainCard.Color.YELLOW);


                        }else{
                            tempCard = new ActionCardColored(ActionCardColored.Action.DRAW2, MainCard.Color.YELLOW);


                        }



                    }

                }else{
                    System.out.println(card + " is not special");

                    if(card.substring(0, card.indexOf(" ")).equals("RED")){
                        if(card.contains("ONE")){
                            tempCard = new MainCard(MainCard.Color.RED, MainCard.Numbers.ONE);
                        }else if(card.contains("TWO")){
                            tempCard = new MainCard(MainCard.Color.RED, MainCard.Numbers.TWO);

                        }else if(card.contains("THREE")){
                            tempCard = new MainCard(MainCard.Color.RED, MainCard.Numbers.THREE);

                        }else if(card.contains("FOUR")){
                            tempCard = new MainCard(MainCard.Color.RED, MainCard.Numbers.FOUR);

                        }else if(card.contains("FIVE")){
                            tempCard = new MainCard(MainCard.Color.RED, MainCard.Numbers.FIVE);

                        }else if(card.contains("SIX")){
                            tempCard = new MainCard(MainCard.Color.RED, MainCard.Numbers.SIX);

                        }else if(card.contains("SEVEN")){
                            tempCard = new MainCard(MainCard.Color.RED, MainCard.Numbers.SEVEN);

                        }else if(card.contains("EIGHT")){
                            tempCard = new MainCard(MainCard.Color.RED, MainCard.Numbers.EIGHT);

                        }else if(card.contains("NINE")){
                            tempCard = new MainCard(MainCard.Color.RED, MainCard.Numbers.NINE);

                        }



                    }else if(card.substring(0, card.indexOf(" ")).equals("BLUE")){
                        if(card.contains("ONE")){
                            tempCard = new MainCard(MainCard.Color.BLUE, MainCard.Numbers.ONE);
                        }else if(card.contains("TWO")){
                            tempCard = new MainCard(MainCard.Color.BLUE, MainCard.Numbers.TWO);

                        }else if(card.contains("THREE")){
                            tempCard = new MainCard(MainCard.Color.BLUE, MainCard.Numbers.THREE);

                        }else if(card.contains("FOUR")){
                            tempCard = new MainCard(MainCard.Color.BLUE, MainCard.Numbers.FOUR);

                        }else if(card.contains("FIVE")){
                            tempCard = new MainCard(MainCard.Color.BLUE, MainCard.Numbers.FIVE);

                        }else if(card.contains("SIX")){
                            tempCard = new MainCard(MainCard.Color.BLUE, MainCard.Numbers.SIX);

                        }else if(card.contains("SEVEN")){
                            tempCard = new MainCard(MainCard.Color.BLUE, MainCard.Numbers.SEVEN);

                        }else if(card.contains("EIGHT")){
                            tempCard = new MainCard(MainCard.Color.BLUE, MainCard.Numbers.EIGHT);

                        }else if(card.contains("NINE")){
                            tempCard = new MainCard(MainCard.Color.BLUE, MainCard.Numbers.NINE);

                        }



                    }else if(card.substring(0, card.indexOf(" ")).equals("GREEN")){
                        if(card.contains("ONE")){
                            tempCard = new MainCard(MainCard.Color.GREEN, MainCard.Numbers.ONE);
                        }else if(card.contains("TWO")){
                            tempCard = new MainCard(MainCard.Color.GREEN, MainCard.Numbers.TWO);

                        }else if(card.contains("THREE")){
                            tempCard = new MainCard(MainCard.Color.GREEN, MainCard.Numbers.THREE);

                        }else if(card.contains("FOUR")){
                            tempCard = new MainCard(MainCard.Color.GREEN, MainCard.Numbers.FOUR);

                        }else if(card.contains("FIVE")){
                            tempCard = new MainCard(MainCard.Color.GREEN, MainCard.Numbers.FIVE);

                        }else if(card.contains("SIX")){
                            tempCard = new MainCard(MainCard.Color.GREEN, MainCard.Numbers.SIX);

                        }else if(card.contains("SEVEN")){
                            tempCard = new MainCard(MainCard.Color.GREEN, MainCard.Numbers.SEVEN);

                        }else if(card.contains("EIGHT")){
                            tempCard = new MainCard(MainCard.Color.GREEN, MainCard.Numbers.EIGHT);

                        }else if(card.contains("NINE")){
                            tempCard = new MainCard(MainCard.Color.GREEN, MainCard.Numbers.NINE);

                        }




                    }else{
                        if(card.contains("ONE")){
                            tempCard = new MainCard(MainCard.Color.YELLOW, MainCard.Numbers.ONE);
                        }else if(card.contains("TWO")){
                            tempCard = new MainCard(MainCard.Color.YELLOW, MainCard.Numbers.TWO);

                        }else if(card.contains("THREE")){
                            tempCard = new MainCard(MainCard.Color.YELLOW, MainCard.Numbers.THREE);

                        }else if(card.contains("FOUR")){
                            tempCard = new MainCard(MainCard.Color.YELLOW, MainCard.Numbers.FOUR);

                        }else if(card.contains("FIVE")){
                            tempCard = new MainCard(MainCard.Color.YELLOW, MainCard.Numbers.FIVE);

                        }else if(card.contains("SIX")){
                            tempCard = new MainCard(MainCard.Color.YELLOW, MainCard.Numbers.SIX);

                        }else if(card.contains("SEVEN")){
                            tempCard = new MainCard(MainCard.Color.YELLOW, MainCard.Numbers.SEVEN);

                        }else if(card.contains("EIGHT")){
                            tempCard = new MainCard(MainCard.Color.YELLOW, MainCard.Numbers.EIGHT);

                        }else if(card.contains("NINE")){
                            tempCard = new MainCard(MainCard.Color.YELLOW, MainCard.Numbers.NINE);

                        }



                    }

                }


                //System.out.println(tempCard.toString());


                if(data.gameTest.get(data.getCurrentPlayer()).canMove(tempCard)){
                    data.gameTest.get(data.getCurrentPlayer()).playCard(tempCard, data.discard);
                    System.out.println("Moved");

                    switchScreens();


                    //if(!(tempCard.getAction() == ActionCards.Special.DRAW4 || tempCard.getAction() == ActionCards.Special.PICKCOLOR)) {

                    //}

                    ScreenThread runnable = new ScreenThread();
                    runnable.start();


                    //runnable.stop();

                }else{
                    System.out.println("Didn't move");
                }





                //System.out.println("New Hand:");

                //data.gameTest.get(data.getCurrentPlayer()).printHand();




                //Intent intent = new Intent(CardTestHorizontal.this, Leaderboard.class);
                //startActivity(intent);








            }
        });
    }

    class ScreenThread extends Thread {

        @Override
        public void run() {
            while(!data.change){

            }
            finish();
            startActivity(getIntent());

            data.change = false;


        }
    }





}

