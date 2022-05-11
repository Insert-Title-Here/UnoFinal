package com.example.unofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class CardTestHorizontal extends AppCompatActivity {

    Data data = new Data();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_test_horizontal);

        ImageView image = findViewById(R.id.discard);
        LinearLayout layout = (LinearLayout) findViewById(R.id.handlayout);

        //TODO: remove this later
        data.players = 2;
        newCardImplementation();


        System.out.println(data.gameTest);


        for (int i = 0; i < 7; i++) {
            filllayout(layout, new ImageButton(getApplicationContext()), data.getImage(data.gameTest.get(data.getCurrentPlayer()).getIndex(i).toString()));
        }

        //getViewInLayout(layout, 1).setScaleType(ImageView.ScaleType.FIT_XY);

        for (int i = 0; i < 7; i++) {
            ImageButton button = getViewInLayout(layout, i);
            button.setScaleType(ImageView.ScaleType.FIT_XY);


        }




        System.out.println("PreviousCard: " + data.previousCard.toString());

        image.setImageResource(data.getImage(data.previousCard.toString()));


        //image.setImageResource(R.drawable.drawfour);
        //toast.setView(view1);
        //toast.setDuration(Toast.LENGTH_LONG);
        //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);


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
        data.previousCard = data.discard.push(data.drawPile.pop());

        while (data.previousCard.getNum() == MainCard.Numbers.NONE) {
            data.previousCard = data.discard.push(data.drawPile.pop());

        }
        //MainCard topOfDiscard = data.discard.peek();
        System.out.println(data.previousCard);
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


}