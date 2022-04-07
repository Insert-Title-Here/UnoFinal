package com.example.unofinal.backend;

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


    public Data(Stack<MainCard> drawPile, Stack<MainCard> discard, MainCard[] deck, ArrayList<ArrayList<MainCard>> game) {
        this.drawPile = drawPile;
        this.discard = discard;
        this.deck = deck;
        this.game = game;

        currentPlayer = 1;
        reverse = false;
        reloadAmt = 0;

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
        switchPlayer();
        System.out.println(1);
        switchPlayer();
        System.out.println(2);
    }

    public void switchPlayer() {
        System.out.print(currentPlayer + " to ");

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

        System.out.println(currentPlayer);
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

    public int getCurrentPlayer(){
        return currentPlayer - 1;
    }



}
