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

/* The Card screen is the main gameplay screen that is navigated to during the start
of the game. It displays your hand, the previous card played and the draw pile. You can
navigated to multiple other screens from here to access other things (such as the help screen
or the PlayerCards screen)
 */
public class Card extends AppCompatActivity {

    //Data file
    Data data = new Data();

    //Player that is currently taking there turn (displayed)
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


        //Initializing all of the views/layouts and data file
        ImageView image = findViewById(R.id.discard);
        layout = (LinearLayout) findViewById(R.id.handlayout);
        player = (TextView) findViewById(R.id.playerNum);


        //Setting default switching player type
        nextType = Data.SwitchPlayer.NORMAL;

        //Volatile boolean to decide whether to reload screen
        data.change = false;


        //If first time activity is loaded
        if (!data.initialized) {

            //Getting amtPlayers from MainActivity and using it to determine how many players are playing
            //Has a max (6) and a min (1)
            Intent intent = getIntent();

            if (Integer.parseInt(intent.getStringExtra("Amt Players")) < 6 && Integer.parseInt(intent.getStringExtra("Amt Players")) >= 1) {
                data.players = Integer.parseInt(intent.getStringExtra("Amt Players"));
            } else if (Integer.parseInt(intent.getStringExtra("Amt Players")) == 0) {
                data.players = 2;
            } else {
                data.players = 5;
            }


            //Initialize all the cards for each player
            newCardImplementation();


        }


        //If there aren't cards in drawPile the take discard and reshuffle it as the draw pile
        if (data.drawPile.size() == 0) {
            MainCard save = data.discard.pop();
            for (int i = 0; i < data.discard.size(); i++) {
                data.drawPile.push(data.discard.pop());
            }
            Collections.shuffle(data.drawPile);
            data.discard.push(save);
        }


        //Show which player is the current player
        player.setText("Player " + (data.getCurrentPlayer() + 1));

        //Changing text color to reflect the color of the current card on top of the discard
        if (data.discard.peek().getColor() == MainCard.Color.RED) {
            player.setTextColor(Color.RED);
        } else if (data.discard.peek().getColor() == MainCard.Color.BLUE) {
            player.setTextColor(Color.parseColor("#04ABEE"));

        } else if (data.discard.peek().getColor() == MainCard.Color.YELLOW) {
            player.setTextColor(Color.parseColor("#FDD500"));

        } else if (data.discard.peek().getColor() == MainCard.Color.GREEN) {
            player.setTextColor(Color.GREEN);

        } else {
            player.setTextColor(Color.BLACK);
        }

        //If the current object is a player and not a bot
        if (!(data.game.get(data.getCurrentPlayer()).isBot())) {


            //Add all the imagebuttons to the linear layout
            for (int i = 0; i < data.game.get(data.getCurrentPlayer()).size(); i++) {
                filllayout(layout, new ImageButton(getApplicationContext()), data.getImage(data.game.get(data.getCurrentPlayer()).getIndex(i).toString()));

            }

            //Adjusting all imagebuttons and setting button listener
            for (int i = 0; i < data.game.get(data.getCurrentPlayer()).size(); i++) {
                ImageButton button = getViewInLayout(layout, i);
                button.setScaleType(ImageView.ScaleType.FIT_XY);
                button.getLayoutParams().width = 300;
                button.setTag(data.game.get(data.getCurrentPlayer()).getIndex(i).toString());
                cardClick(button);


            }

            //Checking which card to put on the top of the discard
            if (data.discard.peek().hasAction() || data.discard.peek().getNum() != null) {
                image.setImageResource(data.getImage(data.discard.peek().toString()));
            } else {

                //If card has an action or doesn't have a number than set previous card as discard image
                //TODO: fix draw pile bug/empty stack exception
                MainCard tempCard = data.discard.pop();
                MainCard neededCard = data.discard.peek();

                data.discard.push(tempCard);
                image.setImageResource(data.getImage(neededCard.toString()));
            }


            //Set initialization to true so that no initialization will occur again
            if (!data.initialized) {
                data.initialized = true;
            }


            //If a bot is included
        } else {

            //TODO: remove this
            data.game.get(data.getCurrentPlayer()).printHand();


            //bot plays a card
            data.game.get(data.getCurrentPlayer()).move(data.discard.peek().getColor(), data.discard.peek());

            //Navigate to next player and reload activity
            switchScreens();
            finish();
            startActivity(getIntent());


            //TODO: remove this
            System.out.println("Bot Played a " + data.discard.peek().toString());
        }
    }

    //Accessing specific imagebutton in the linear layout
    public ImageButton getViewInLayout(LinearLayout layout, int position) {

        ImageButton v = null;


        for (int i = 0; i <= position; i++) {
            v = (ImageButton) layout.getChildAt(i);
        }


        return v;

    }

    //Populate the linear layout with imagebutton
    public void filllayout(LinearLayout layout, ImageButton button, int image) {
        button.setImageResource(image);

        layout.addView(button);

    }

    //Setting up game
    private void newCardImplementation() {
        buildDeck(data.deck);
        shuffleDeck(data.deck);
        newGameInit();

        //First Card
        data.discard.push(data.drawPile.pop());

        //Checking whether top card doesn't have a number the rechoosing top card
        while (data.discard.peek().getNum() == MainCard.Numbers.NONE) {
            data.discard.push(data.drawPile.pop());

        }

    }


    private void newGameInit() {
        //Creating the drawpile with 108 cards
        for (int i = 0; i < 108; i++) {
            data.drawPile.push(data.deck[i]);
        }

        System.out.println("Got here");


        //Creating the creating players
        for (int i = 0; i < data.players; i++) {

            data.game.add(new Player());

        }

        //If bot check box was selected than add a bot
        if (data.bot == true) {

            data.game.add(new Bot());

            //Set new amt of player including bot
            data.players = data.game.size();

        }

    }


    //TODO: Connor comment this
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
    public void draw(View view) {

        //If actually drawing a card (first press)
        System.out.println("ReloadAmt: " + data.reloadAmt);
        if (data.reloadAmt == 0) {

            //Actually draw card
            data.game.get(data.getCurrentPlayer()).drawCards(1);


            finish();
            startActivity(getIntent());
            data.reloadAmt++;

            //If switching screen (2nd press)
        } else {
            //Change current player and reload screen
            switchScreens(Data.SwitchPlayer.NORMAL);


            //Navigation thread to help navigation run smoothly
            ScreenThread runnable = new ScreenThread();
            runnable.start();

            data.change = true;


        }

        //TODO: remove this
        System.out.println("DrawPile Size: " + data.drawPile.size());


    }


    //Determines which type of navigation will occur
    private void switchScreens() {


        //Handling the Skip Action without draw4
        if (!(data.discard.peek().getAction() == ActionCards.Special.DRAW4)) {

            //If skip draw2 or reverse
            if (data.discard.peek().getAbility() == ActionCardColored.Action.SKIP || data.discard.peek().getAbility() == ActionCardColored.Action.DRAW2 || data.discard.peek().getAbility() == ActionCardColored.Action.REVERSE) {

                //If it is a draw two then next player gets 2 cards
                if (data.discard.peek().getAbility() == ActionCardColored.Action.DRAW2) {
                    data.game.get(data.getNextPlayer()).drawCards(2);
                }

                //Change player type is set to skip

                if (data.game.size() > 2 && data.discard.peek().getAbility() == ActionCardColored.Action.REVERSE) {
                    data.reverse = !data.reverse;
                } else {
                    nextType = Data.SwitchPlayer.SKIP;
                }

                //If it is not skip draw2 or reverse
            } else {

                //If it is a wild
                if (data.discard.peek().getAction() == ActionCards.Special.PICKCOLOR) {

                    //Switch to color change screen (to choose new color)
                    System.out.println("WILD Testing");
                    Intent newIntent = new Intent(Card.this, ColorChange.class);
                    startActivity(newIntent);
                }

                //change player type is normal
                nextType = Data.SwitchPlayer.NORMAL;

            }

            System.out.println("CurrentPlayer: " + data.currentPlayer);

            //If it is a draw4
        } else {

            //Next player draws 4 cards
            data.game.get(data.getNextPlayer()).drawCards(4);

            //change player type is skip
            nextType = Data.SwitchPlayer.SKIP;

            System.out.println("Draw4 Testing");

            //Switch to color change screen (to chooose new color)
            Intent newIntent = new Intent(Card.this, ColorChange.class);
            startActivity(newIntent);

            System.out.println("new intent");
        }

        //decides what type of navigation to use for changing players
        data.nextPlayer(nextType);


        //Reset the draw amt so that drawing will be able to occur again for the next player
        data.reloadAmt = 0;
    }

    //Overriden method so that we can use special navigation when needed
    public void switchScreens(Data.SwitchPlayer next) {
        data.nextPlayer(next);
        data.reloadAmt = 0;
    }


    //Separating cards into sections and then setting temp card as the card that was clicked
    //Creating temp version of card that was pressed
    public MainCard getCardFromImage(String card) {
        if (card.contains("PICKCOLOR") || card.contains("DRAW4")) {
            if (card.contains("PICKCOLOR")) {
                return new ActionCards(ActionCards.Special.PICKCOLOR);
            } else {
                return new ActionCards(ActionCards.Special.DRAW4);
            }
        } else if (card.contains("REVERSE") || card.contains("SKIP") || card.contains("DRAW2")) {
            System.out.println(card + " is somewhat special");

            if (card.substring(0, card.indexOf(" ")).equals("RED")) {
                if (card.contains("REVERSE")) {
                    return new ActionCardColored(ActionCardColored.Action.REVERSE, MainCard.Color.RED);
                } else if (card.contains("SKIP")) {
                    return new ActionCardColored(ActionCardColored.Action.SKIP, MainCard.Color.RED);
                } else {
                    return new ActionCardColored(ActionCardColored.Action.DRAW2, MainCard.Color.RED);
                }

            } else if (card.substring(0, card.indexOf(" ")).equals("BLUE")) {
                if (card.contains("REVERSE")) {
                    return new ActionCardColored(ActionCardColored.Action.REVERSE, MainCard.Color.BLUE);
                } else if (card.contains("SKIP")) {
                    return new ActionCardColored(ActionCardColored.Action.SKIP, MainCard.Color.BLUE);
                } else {
                    return new ActionCardColored(ActionCardColored.Action.DRAW2, MainCard.Color.BLUE);
                }
            } else if (card.substring(0, card.indexOf(" ")).equals("GREEN")) {
                if (card.contains("REVERSE")) {
                    return new ActionCardColored(ActionCardColored.Action.REVERSE, MainCard.Color.GREEN);
                } else if (card.contains("SKIP")) {
                    return new ActionCardColored(ActionCardColored.Action.SKIP, MainCard.Color.GREEN);
                } else {
                    return new ActionCardColored(ActionCardColored.Action.DRAW2, MainCard.Color.GREEN);
                }
            } else {

                if (card.contains("REVERSE")) {
                    return new ActionCardColored(ActionCardColored.Action.REVERSE, MainCard.Color.YELLOW);
                } else if (card.contains("SKIP")) {
                    return new ActionCardColored(ActionCardColored.Action.SKIP, MainCard.Color.YELLOW);
                } else {
                    return new ActionCardColored(ActionCardColored.Action.DRAW2, MainCard.Color.YELLOW);
                }
            }
        } else {
            if (card.substring(0, card.indexOf(" ")).equals("RED")) {
                if (card.contains("ZERO")) {
                    return new MainCard(MainCard.Color.RED, MainCard.Numbers.ZERO);
                } else if (card.contains("ONE")) {
                    return new MainCard(MainCard.Color.RED, MainCard.Numbers.ONE);
                } else if (card.contains("TWO")) {
                    return new MainCard(MainCard.Color.RED, MainCard.Numbers.TWO);
                } else if (card.contains("THREE")) {
                    return new MainCard(MainCard.Color.RED, MainCard.Numbers.THREE);
                } else if (card.contains("FOUR")) {
                    return new MainCard(MainCard.Color.RED, MainCard.Numbers.FOUR);
                } else if (card.contains("FIVE")) {
                    return new MainCard(MainCard.Color.RED, MainCard.Numbers.FIVE);
                } else if (card.contains("SIX")) {
                    return new MainCard(MainCard.Color.RED, MainCard.Numbers.SIX);
                } else if (card.contains("SEVEN")) {
                    return new MainCard(MainCard.Color.RED, MainCard.Numbers.SEVEN);
                } else if (card.contains("EIGHT")) {
                    return new MainCard(MainCard.Color.RED, MainCard.Numbers.EIGHT);
                } else if (card.contains("NINE")) {
                    return new MainCard(MainCard.Color.RED, MainCard.Numbers.NINE);
                }
            } else if (card.substring(0, card.indexOf(" ")).equals("BLUE")) {
                if (card.contains("ZERO")) {
                    return new MainCard(MainCard.Color.BLUE, MainCard.Numbers.ZERO);
                } else if (card.contains("ONE")) {
                    return new MainCard(MainCard.Color.BLUE, MainCard.Numbers.ONE);
                } else if (card.contains("TWO")) {
                    return new MainCard(MainCard.Color.BLUE, MainCard.Numbers.TWO);
                } else if (card.contains("THREE")) {
                    return new MainCard(MainCard.Color.BLUE, MainCard.Numbers.THREE);
                } else if (card.contains("FOUR")) {
                    return new MainCard(MainCard.Color.BLUE, MainCard.Numbers.FOUR);
                } else if (card.contains("FIVE")) {
                    return new MainCard(MainCard.Color.BLUE, MainCard.Numbers.FIVE);
                } else if (card.contains("SIX")) {
                    return new MainCard(MainCard.Color.BLUE, MainCard.Numbers.SIX);
                } else if (card.contains("SEVEN")) {
                    return new MainCard(MainCard.Color.BLUE, MainCard.Numbers.SEVEN);
                } else if (card.contains("EIGHT")) {
                    return new MainCard(MainCard.Color.BLUE, MainCard.Numbers.EIGHT);
                } else if (card.contains("NINE")) {
                    return new MainCard(MainCard.Color.BLUE, MainCard.Numbers.NINE);
                }
            } else if (card.substring(0, card.indexOf(" ")).equals("GREEN")) {
                if (card.contains("ZERO")) {
                    return new MainCard(MainCard.Color.GREEN, MainCard.Numbers.ZERO);
                } else if (card.contains("ONE")) {
                    return new MainCard(MainCard.Color.GREEN, MainCard.Numbers.ONE);
                } else if (card.contains("TWO")) {
                    return new MainCard(MainCard.Color.GREEN, MainCard.Numbers.TWO);
                } else if (card.contains("THREE")) {
                    return new MainCard(MainCard.Color.GREEN, MainCard.Numbers.THREE);
                } else if (card.contains("FOUR")) {
                    return new MainCard(MainCard.Color.GREEN, MainCard.Numbers.FOUR);
                } else if (card.contains("FIVE")) {
                    return new MainCard(MainCard.Color.GREEN, MainCard.Numbers.FIVE);
                } else if (card.contains("SIX")) {
                    return new MainCard(MainCard.Color.GREEN, MainCard.Numbers.SIX);
                } else if (card.contains("SEVEN")) {
                    return new MainCard(MainCard.Color.GREEN, MainCard.Numbers.SEVEN);
                } else if (card.contains("EIGHT")) {
                    return new MainCard(MainCard.Color.GREEN, MainCard.Numbers.EIGHT);
                } else if (card.contains("NINE")) {
                    return new MainCard(MainCard.Color.GREEN, MainCard.Numbers.NINE);
                }
            } else {
                if (card.contains("ZERO")) {
                    return new MainCard(MainCard.Color.YELLOW, MainCard.Numbers.ZERO);
                } else if (card.contains("ONE")) {
                    return new MainCard(MainCard.Color.YELLOW, MainCard.Numbers.ONE);
                } else if (card.contains("TWO")) {
                    return new MainCard(MainCard.Color.YELLOW, MainCard.Numbers.TWO);
                } else if (card.contains("THREE")) {
                    return new MainCard(MainCard.Color.YELLOW, MainCard.Numbers.THREE);
                } else if (card.contains("FOUR")) {
                    return new MainCard(MainCard.Color.YELLOW, MainCard.Numbers.FOUR);
                } else if (card.contains("FIVE")) {
                    return new MainCard(MainCard.Color.YELLOW, MainCard.Numbers.FIVE);
                } else if (card.contains("SIX")) {
                    return new MainCard(MainCard.Color.YELLOW, MainCard.Numbers.SIX);
                } else if (card.contains("SEVEN")) {
                    return new MainCard(MainCard.Color.YELLOW, MainCard.Numbers.SEVEN);
                } else if (card.contains("EIGHT")) {
                    return new MainCard(MainCard.Color.YELLOW, MainCard.Numbers.EIGHT);
                } else if (card.contains("NINE")) {
                    return new MainCard(MainCard.Color.YELLOW, MainCard.Numbers.NINE);
                }
            }

        }

        return null;
    }


    //Button listener for the image buttons
    public void cardClick(ImageButton button) {
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Access the tag of the current tag and initialize a temp card
                String card = (String) button.getTag();
                MainCard tempCard = null;

                //Getting which card was clicked
                tempCard = getCardFromImage(card);


                //Check if the clicked card is actually playable
                if (data.game.get(data.getCurrentPlayer()).canMove(tempCard)) {

                    //Play the card
                    data.game.get(data.getCurrentPlayer()).playCard(tempCard, data.discard);


                    //If there are no cards in the players hand after playing the card then go to the leaderboard (win screen)
                    if (data.game.get(data.getCurrentPlayer()).size() == 0) {
                        Intent intent = new Intent(Card.this, Win.class);
                        startActivity(intent);


                    } else {

                        //Otherwise switch to the next player
                        switchScreens();


                        //Special case for draw4 and wild so that navigation doesnt occur at the same time
                        if (!(tempCard.getAction() == ActionCards.Special.DRAW4 || tempCard.getAction() == ActionCards.Special.PICKCOLOR)) {
                            data.change = true;
                        }


                        //Starting a special navigation thread
                        ScreenThread runnable = new ScreenThread();
                        runnable.start();


                        //Sound effect for placing a card
                        MediaPlayer backgroundMusic = MediaPlayer.create(Card.this, R.raw.cardplaced);
                        backgroundMusic.start();


                    }


                }
            }
        });
    }

    //Thread to make sure that navigation doesn't occur at the same time based on change player and
    //color change screen for draw4 / wild
    class ScreenThread extends Thread {

        @Override
        public void run() {

            //Volatile boolean to determine when to switch screen
            while (!data.change) {

            }

            //Navigates to middlescreen
            Intent intent = new Intent(Card.this, MiddleScreen.class);
            startActivity(intent);

            //Stops navigation past middlescreen
            data.change = false;


            //Volatile boolean to determine when to switch screen
            while (!data.change) {

            }

            //Reloads activity
            finish();
            startActivity(getIntent());

            //Stops navigation
            data.change = false;
        }
    }


    //Navigation to playerCard screen (to view how many cards each player has)
    public void playerCards(View v) {
        Intent intent = new Intent(Card.this, PlayerCards.class);
        startActivity(intent);

    }

    //Navigation to help menu
    public void help(View v) {
        Intent intent = new Intent(Card.this, Help.class);
        startActivity(intent);

    }

}

