package com.example.unofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.unofinal.backend.ActionCardColored;
import com.example.unofinal.backend.ActionCards;
import com.example.unofinal.backend.Bot;
import com.example.unofinal.backend.Data;
import com.example.unofinal.backend.MainCard;
import com.example.unofinal.backend.Player;

import java.util.*;



public class CardTestHorizontal extends AppCompatActivity {

    //Data file
    Data data = new Data();

    //Name of which player is currently taking there turn
    TextView player;

    //Storage container of cards at bottom of screen
    LinearLayout layout;

    //Deciding whether to skip or go to next player normally
    Data.SwitchPlayer nextType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Creating the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_test_horizontal);


        //Initializing all of the views/layouts
        ImageView image = findViewById(R.id.discard);
        layout = (LinearLayout) findViewById(R.id.handlayout);
        player = findViewById(R.id.playerNum);

        //Setting default switching player type
        nextType = Data.SwitchPlayer.NORMAL;

        //Volatile boolean to decide whether to reload screen
        data.change = false;
        data.midScreen = false;


        //If first time activity is loaded
        if (!data.initialized) {

            //Initialize all the cards for each player
            Intent intent = getIntent();
            data.players = Integer.parseInt(intent.getStringExtra("Amt Players"));
            newCardImplementation();
        }

        //TODO: Add reshuffle draw pile if its 0 (using discard)
        if (data.drawPile.size() == 0) {
            MainCard save = data.discard.pop();
            for (int i = 0; i < data.discard.size(); i++) {
                data.drawPile.push(data.discard.pop());
            }
            Collections.shuffle(data.drawPile);
        }


        //Show which player is the current player
        player.setText("Player " + data.currentPlayer);

        //Changing text color between players
        if (data.getCurrentPlayer() == 1) {
            player.setTextColor(Color.RED);
        } else {
            player.setTextColor(Color.YELLOW);
        }

        //If the current object is a player and not a bot
        if(!(data.gameTest.get(data.getCurrentPlayer()).isBot())) {

            System.out.println("Player");



            //Add all the imagebuttons to the linear layout
            for (int i = 0; i < data.gameTest.get(data.getCurrentPlayer()).size(); i++) {
                filllayout(layout, new ImageButton(getApplicationContext()), data.getImage(data.gameTest.get(data.getCurrentPlayer()).getIndex(i).toString()));
                //System.out.println("Card: " + data.gameTest.get(data.getCurrentPlayer()).getIndex(i).toString());

            }

            //Adjusting all imagebuttons and setting button listener
            for (int i = 0; i < data.gameTest.get(data.getCurrentPlayer()).size(); i++) {
                ImageButton button = getViewInLayout(layout, i);
                button.setScaleType(ImageView.ScaleType.FIT_XY);
                button.getLayoutParams().width = 300;
                button.setTag(data.gameTest.get(data.getCurrentPlayer()).getIndex(i).toString());
                cardClick(button);


            }

            //Checking which card to put on the top of the discard
            if (data.discard.peek().hasAction() || data.discard.peek().getNum() != null) {
                image.setImageResource(data.getImage(data.discard.peek().toString()));
            } else {

                //If card has an action or doesn't have a number than set previous card as discard image
                MainCard tempCard = data.discard.pop();
                MainCard neededCard = data.discard.peek();

                data.discard.push(tempCard);

                image.setImageResource(data.getImage(neededCard.toString()));

                System.out.println("Top Card: " + data.discard.peek().toString());
            }




            //Set initialization to true so that no initialization will occur again
            if (!data.initialized) {
                data.initialized = true;
            }


        //If a bot is included
        }else{

            System.out.println("Bot Hand");

            data.gameTest.get(data.getCurrentPlayer()).printHand();


            //If there is no skip, or reverse used
            if(!(data.discard.peek().getAbility() == ActionCardColored.Action.SKIP || data.discard.peek().getAbility() == ActionCardColored.Action.REVERSE)){

                //play a card
                data.gameTest.get(data.getCurrentPlayer()).move(data.discard.peek().getColor(), data.discard.peek());
                //MainCard temp = data.gameTest.get(data.getCurrentPlayer()).move(data.discard.peek().getColor(), data.discard.peek());
                //data.discard.push(temp);


                //Can't currently move if skip or reverse is played
            }else {
                System.out.println("Can't Move: " + data.discard.peek().toString());
            }

            switchScreens();

            finish();
            startActivity(getIntent());






            System.out.println("Bot Played a " + data.discard.peek().toString());
        }


    }

    //Accessing the imagebutton in the linear layout
    public ImageButton getViewInLayout(LinearLayout layout, int position) {

        //int count = layout.getChildCount();
        ImageButton v = null;


        for (int i = 0; i <= position; i++) {
            v = (ImageButton) layout.getChildAt(i);
            //do something with your child element
        }



        return v;

    }

    //Populate the linear layout with imagebutton
    public void filllayout(LinearLayout layout, ImageButton button, int image) {
        button.setImageResource(image);

        layout.addView(button);

    }

    private void newCardImplementation() {
        buildDeck(data.deck);
        shuffleDeck(data.deck);
        newGameInit();

        //First Card
        data.discard.push(data.drawPile.pop());

        //Checking whether top card doesn't have a number the rechoosing top card
        while (data.discard.peek().getNum() == MainCard.Numbers.NONE) {
            //data.previousCard = data.discard.push(data.drawPile.pop());
            data.discard.push(data.drawPile.pop());

        }

    }


    private void newGameInit() {
        //Creating the drawpile with 108 cards
        for (int i = 0; i < 108; i++) {
            data.drawPile.push(data.deck[i]);
        }


        //Creating the creating players
        for (int i = 0; i < data.players; i++) {
            data.gameTest.add(new Player());
        }

        //If only one player then add a bot
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

    //Shuffles the deck (drawpile)
    public static void shuffleDeck(MainCard[] arr) {

        List<MainCard> temp = Arrays.asList(arr);
        Collections.shuffle(temp);
        temp.toArray(arr);

    }

    //Drawing a card
    public void draw(View view){

        //If actually drawing a card (first press)
        System.out.println("ReloadAmt: " + data.reloadAmt);
        if(data.reloadAmt == 0) {

            //Actually draw card
            data.gameTest.get(data.getCurrentPlayer()).drawCards(1);

            System.out.println("PreviousCard:  " + data.previousCard);

            /*
            //Reload screen and change reload amt
            Intent intent = new Intent(CardTestHorizontal.this, MiddleScreen.class);
            startActivity(intent);

             */

            finish();
            startActivity(getIntent());
            data.reloadAmt++;

            //If switching screen (2nd press)
        }else{
            //Change current player and reload screen
            switchScreens(Data.SwitchPlayer.NORMAL);

            /*
            Intent intent = new Intent(CardTestHorizontal.this, MiddleScreen.class);
            startActivity(intent);

             */

            ScreenThread runnable = new ScreenThread();
            runnable.start();

            data.change = true;


            //finish();
            //startActivity(getIntent());
        }


        System.out.println("DrawPile Size: " + data.drawPile.size());


    }


    //TODO: fix skip, reverse, draw2, draw4
    //This is only for 2 players currently, need to make the reverse dynamic later
    private void switchScreens(){


        //Handling the Skip Action without draw4
        if(!(data.discard.peek().getAction() == ActionCards.Special.DRAW4)) {

            //If skip draw2 or reverse
            if (data.discard.peek().getAbility() == ActionCardColored.Action.SKIP || data.discard.peek().getAbility() == ActionCardColored.Action.DRAW2 || data.discard.peek().getAbility() == ActionCardColored.Action.REVERSE) {

                //If it is a draw two then next player gets 2 cards
                if(data.discard.peek().getAbility() == ActionCardColored.Action.DRAW2){
                    data.gameTest.get(data.getNextPlayer()).drawCards(2);
                }

                //Change player type is set to skip

                if(data.gameTest.size() > 2 && data.discard.peek().getAbility() == ActionCardColored.Action.REVERSE){
                    data.reverse = !data.reverse;
                }else{
                    nextType = Data.SwitchPlayer.SKIP;
                }

                //If it is not skip draw2 or reverse
            } else {

                //If it is a wild
                if(data.discard.peek().getAction() == ActionCards.Special.PICKCOLOR){

                    //Switch to color change screen (to choose new color)
                    System.out.println("WILD Testing");
                    Intent newIntent = new Intent(CardTestHorizontal.this, ColorChange.class);
                    startActivity(newIntent);
                }

                //change player type is normal
                nextType = Data.SwitchPlayer.NORMAL;

            }

            System.out.println("CurrentPlayer: " + data.currentPlayer);

            //If it is a draw4
        }else{

            //Next player draws 4 cards
            data.gameTest.get(data.getNextPlayer()).drawCards(4);

            //change player type is skip
            nextType = Data.SwitchPlayer.SKIP;

            System.out.println("Draw4 Testing");

            //Switch to color change screen (to chooose new color)
            Intent newIntent = new Intent(CardTestHorizontal.this, ColorChange.class);
            startActivity(newIntent);

            System.out.println("new intent");
        }

        //decides what type of navigation to use for changing players
        data.nextPlayer(nextType);



        //Reset the draw amt so that drawing will be able to occur again for the next player
        data.reloadAmt = 0;
    }

    //Overriden method so that we can use special navigation when needed
    public void switchScreens(Data.SwitchPlayer next){
        data.nextPlayer(next);
        data.reloadAmt = 0;
    }


    //Button listener for the image buttons
    public void cardClick(ImageButton button){
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Access the tag of the current tag and initialize a temp card
                String card = (String)button.getTag();
                MainCard tempCard = null;


                //Separating cards into sections and then setting temp card as the card that was clicked
                if(card.contains("PICKCOLOR") || card.contains("DRAW4")){
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
                    if(card.substring(0, card.indexOf(" ")).equals("RED")){
                        if(card.contains("ZERO")){
                            tempCard = new MainCard(MainCard.Color.RED, MainCard.Numbers.ZERO);
                        }else if(card.contains("ONE")){
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
                        if(card.contains("ZERO")){
                            tempCard = new MainCard(MainCard.Color.BLUE, MainCard.Numbers.ZERO);
                        }else if(card.contains("ONE")){
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
                        if(card.contains("ZERO")){
                            tempCard = new MainCard(MainCard.Color.GREEN, MainCard.Numbers.ZERO);
                        }else if(card.contains("ONE")){
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
                        if(card.contains("ZERO")){
                            tempCard = new MainCard(MainCard.Color.YELLOW, MainCard.Numbers.ZERO);
                        }else if(card.contains("ONE")){
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

                //Check if the clicked card is actually playable
                if(data.gameTest.get(data.getCurrentPlayer()).canMove(tempCard)){

                    //Play the card
                    data.gameTest.get(data.getCurrentPlayer()).playCard(tempCard, data.discard);


                    //If there are no cards in the players hand after playing the card then go to the leaderboard (win screen)
                    if(data.gameTest.get(data.getCurrentPlayer()).size() == 0){
                        Intent intent = new Intent(CardTestHorizontal.this, Leaderboard.class);
                        startActivity(intent);


                    }else {

                        //Otherwise switch to the next player
                        switchScreens();




                        //Special case for draw4 and wild so that navigation doesnt occur at the same time
                        if (!(tempCard.getAction() == ActionCards.Special.DRAW4 || tempCard.getAction() == ActionCards.Special.PICKCOLOR)) {
                            data.change = true;
                        }

                        /*

                        Intent intent = new Intent(CardTestHorizontal.this, MiddleScreen.class);
                        startActivity(intent);
                         */



                        //MidThread middle = new MidThread();
                        //middle.start();




                        //Starting a special navigation thread
                        ScreenThread runnable = new ScreenThread();
                        runnable.start();


                        MediaPlayer backgroundMusic = MediaPlayer.create(CardTestHorizontal.this, R.raw.cardplaced);
                        backgroundMusic.start();




                    }


                    //Otherwise if it didn't move (just letting me know if it didn't work)
                }else{
                    System.out.println("Didn't move");
                }
            }
        });
    }

    //Thread to make sure that navigation doesn't occur at the same time based on change player and
    //color change screen for draw4 / wild
    class ScreenThread extends Thread {

        @Override
        public void run() {
            while(!data.change){

            }

            Intent intent = new Intent(CardTestHorizontal.this, MiddleScreen.class);
            startActivity(intent);

            data.change = false;


            while(!data.change){

            }

            finish();
            startActivity(getIntent());

            data.change = false;
        }
    }


    /*class MidThread extends Thread {

        @Override
        public void run() {
            while(!data.midScreen){
                System.out.println("Getting here to mid");

            }

            Intent intent = new Intent(CardTestHorizontal.this, MiddleScreen.class);
            startActivity(intent);


            data.midScreen = false;
        }
    }

     */

}

