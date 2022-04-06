package com.example.unofinal.backend;

import java.util.*;

public class Player {
	private ArrayList<MainCard> hand = new ArrayList<>();
	//private int playerNumber;
	Data data = new Data();

	// draws 7 cards from the top of the deck into the players hand
	public Player(Stack<MainCard> drawPile) {
		for (int i = 0; i < 7; i++) {
			hand.add(drawPile.pop());
		}
	}

	public Player(){
		for(int i = 0; i < 7; i++){
			hand.add(data.drawPile.pop());
		}
	}

	/*
	public player(int number){
		for(int i = 0; i < 7; i++){
			hand.add(data.drawPile.pop());
		}

		playerNumber = number;
	}

	 */
	
	// used to display data to other players (bots later take it into consideration)
	public int handSize() {
		return hand.size();
	}

	// method to add cards to the hand
	public void drawCards(int num) {
		for (int i = 0; i < num; i++) {
			hand.add(data.drawPile.pop());
		}
	}

	// checks if a move is possible with the current hand
	public boolean canMove(MainCard.Color color, MainCard mostRecent) {
		for (MainCard c : hand) {
    		if (c.matches(mostRecent) || c.getColor() == color) {
    			return true;
    		}
    	}
    	return false;
	}

	// removes card from hand and adds it to discard
	public void playCard(MainCard card, Stack<MainCard> discard) { // plays the card i.e. discard
    	for (int i = 0; i < hand.size(); i++) {
    		if (hand.get(i) == card) {
    			hand.remove(i);
    		}
    	}
    	discard.push(card);
    }

    // prints out hand
	public void printHand(){
		for(MainCard i: hand){
			System.out.println(i.toString());
		}
	}

	// getter for specific card indexes
	public MainCard getIndex(int i){
		return hand.get(i);
	}

	// removes cards from a hand
	public void remove(int i){
		System.out.println(hand.get(i));
		hand.remove(i);
	}

	public int size(){
		return hand.size();
	}

}
