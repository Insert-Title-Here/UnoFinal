package com.example.unofinal.backend;

import android.media.MediaPlayer;

import com.example.unofinal.R;

import java.util.ArrayList;
import java.util.Stack;

public class Data {

    public static Stack<MainCard> drawPile;
    public static Stack<MainCard> discard = new Stack<>();
    public static MainCard[] deck = new MainCard[108];
    public static ArrayList<ArrayList<MainCard>> game = new ArrayList<ArrayList<MainCard>>();
    public static MainCard currentCard = new MainCard();
    public static MainCard previousCard = new MainCard();


    public static ArrayList<Player> gameTest = new ArrayList<>();

    public static int players;
    public static int currentPlayer;
    public static boolean reverse;
    public static int reloadAmt;
    public static boolean initialized;
    public static volatile boolean change;
    public static volatile boolean midScreen;



    public Data(Stack<MainCard> drawPile, Stack<MainCard> discard, MainCard[] deck, ArrayList<ArrayList<MainCard>> game) {
        this.drawPile = drawPile;
        this.discard = discard;
        this.deck = deck;
        this.game = game;

        currentPlayer = 1;
        reverse = false;
        reloadAmt = 0;
        initialized = false;

    }

    public Data() {
        if (drawPile == null) {
            drawPile = new Stack<>();
        }
        if (discard == null) {
            discard = new Stack<>();
        }
        if (deck == null) {
            deck = new MainCard[108];
        }
        if (game == null) {
            game = new ArrayList<ArrayList<MainCard>>();
        }

        if (currentPlayer == 0) {
            currentPlayer = 1;
        }


    }


    public void printDeck() {
        for (MainCard i : deck) {
            System.out.println(i.toString());
        }
    }

    public void skip() {
        System.out.println(currentPlayer);
        switchPlayer();
        System.out.println(currentPlayer);
        switchPlayer();
        System.out.println(currentPlayer);
    }

    public void switchPlayer() {

        if (!reverse) {
            if (!(currentPlayer + 1 > players)) {
                currentPlayer += 1;
            } else {
                currentPlayer = 1;
            }
        } else {
            if (currentPlayer - 1 > 0) {
                currentPlayer -= 1;
            } else {
                currentPlayer = players;
            }
        }

    }

    public void nextPlayer(SwitchPlayer next){
        if(next == SwitchPlayer.SKIP){
            skip();
        }else if(next == SwitchPlayer.NORMAL){
            switchPlayer();
        }

    }

    public int getNextPlayer() {
        if (!reverse) {
            if (!(currentPlayer + 1 > players)) {
                return currentPlayer;
            } else {
                return 0;
            }
        } else {
            if (currentPlayer - 1 > 0) {
                return currentPlayer - 2;
            } else {
                return players - 1;
            }
        }
    }

    public int getPreviousPlayer(){
        if(!reverse){
            if(!(currentPlayer - 1 == 0)){
                return currentPlayer - 2;

            }else{
                return players - 1;
            }
        }else{
            if(!(currentPlayer + 1 > players)){
                return currentPlayer;
            }else{
                return 0;
            }

        }
    }

    public int getCurrentPlayer(){
        return currentPlayer - 1;
    }


    public int getImage(String name) {
        if (name.contains("RED")) {
            if (name.contains("ZERO")) {
                return R.drawable.redzero;
            } else if (name.contains("ONE")) {
                return R.drawable.redone;
            } else if (name.contains("TWO")) {
                return R.drawable.redtwo;
            } else if (name.contains("THREE")) {
                return R.drawable.redthree;
            } else if (name.contains("FOUR")) {
                return R.drawable.redfour;
            } else if (name.contains("FIVE")) {
                return R.drawable.redfive;
            } else if (name.contains("SIX")) {
                return R.drawable.redsix;
            } else if (name.contains("SEVEN")) {
                return R.drawable.redseven;
            } else if (name.contains("EIGHT")) {
                return R.drawable.redeight;
            } else if (name.contains("NINE")) {
                return R.drawable.rednine;
            } else if (name.contains("REVERSE")) {
                return R.drawable.redreverse;
            } else if (name.contains("DRAW2")) {
                return R.drawable.reddrawtwo;
            } else if (name.contains("SKIP")) {
                return R.drawable.redskip;
            }

        } else if (name.contains("BLUE")) {
            if (name.contains("ZERO")) {
                return R.drawable.bluezero;
            } else if (name.contains("ONE")) {
                return R.drawable.blueone;
            } else if (name.contains("TWO")) {
                return R.drawable.bluetwo;
            } else if (name.contains("THREE")) {
                return R.drawable.bluethree;
            } else if (name.contains("FOUR")) {
                return R.drawable.bluefour;
            } else if (name.contains("FIVE")) {
                return R.drawable.bluefive;
            } else if (name.contains("SIX")) {
                return R.drawable.bluesix;
            } else if (name.contains("SEVEN")) {
                return R.drawable.blueseven;
            } else if (name.contains("EIGHT")) {
                return R.drawable.blueeight;
            } else if (name.contains("NINE")) {
                return R.drawable.bluenine;
            } else if (name.contains("REVERSE")) {
                return R.drawable.bluereverse;
            } else if (name.contains("DRAW2")) {
                return R.drawable.bluedrawtwo;
            } else if (name.contains("SKIP")) {
                return R.drawable.blueskip;
            }
        } else if (name.contains("GREEN")) {
            if (name.contains("ZERO")) {
                return R.drawable.greenzero;
            } else if (name.contains("ONE")) {
                return R.drawable.greenone;
            } else if (name.contains("TWO")) {
                return R.drawable.greentwo;
            } else if (name.contains("THREE")) {
                return R.drawable.greenthree;
            } else if (name.contains("FOUR")) {
                return R.drawable.greenfour;
            } else if (name.contains("FIVE")) {
                return R.drawable.greenfive;
            } else if (name.contains("SIX")) {
                return R.drawable.greensix;
            } else if (name.contains("SEVEN")) {
                return R.drawable.greenseven;
            } else if (name.contains("EIGHT")) {
                return R.drawable.greeneight;
            } else if (name.contains("NINE")) {
                return R.drawable.greennine;
            } else if (name.contains("REVERSE")) {
                return R.drawable.greenreverse;
            } else if (name.contains("DRAW2")) {
                return R.drawable.greendrawtwo;
            } else if (name.contains("SKIP")) {
                return R.drawable.greenskip;
            }
        } else if (name.contains("YELLOW")) {
            if (name.contains("ZERO")) {
                return R.drawable.yellowzero;
            } else if (name.contains("ONE")) {
                return R.drawable.yellowone;
            } else if (name.contains("TWO")) {
                return R.drawable.yellowtwo;
            } else if (name.contains("THREE")) {
                return R.drawable.yellowthree;
            } else if (name.contains("FOUR")) {
                return R.drawable.yellowfour;
            } else if (name.contains("FIVE")) {
                return R.drawable.yellowfive;
            } else if (name.contains("SIX")) {
                return R.drawable.yellowsix;
            } else if (name.contains("SEVEN")) {
                return R.drawable.yellowseven;
            } else if (name.contains("EIGHT")) {
                return R.drawable.yelloweight;
            } else if (name.contains("NINE")) {
                return R.drawable.yellownine;
            } else if (name.contains("REVERSE")) {
                return R.drawable.yellowreverse;
            } else if (name.contains("DRAW2")) {
                return R.drawable.yellowdrawtwo;
            } else if (name.contains("SKIP")) {
                return R.drawable.yellowskip;
            }

            //Wild and Draw 4
        } else {
            if (name.contains("4")) {
                return R.drawable.drawfour;
            } else {
                return R.drawable.wild;
            }

        }

        return 0;

    }

    public enum SwitchPlayer {
        SKIP, NORMAL
    }



}



