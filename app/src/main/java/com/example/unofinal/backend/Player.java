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

	public int move(MainCard.Color color, MainCard mostRecent) {
		return -1;
	}


	public boolean isBot(){
		return false;
	}


	public MainCard.Color chooseColor(){
		int red = 0;
		int blue = 0;
		int green = 0;
		int yellow = 0;

		for(MainCard i: hand){
			if(i.getColor() == MainCard.Color.RED){
				red++;
			}else if(i.getColor() == MainCard.Color.BLUE){
				blue++;
			}else if(i.getColor() == MainCard.Color.GREEN){
				green++;
			}else if(i.getColor() == MainCard.Color.YELLOW) {
				yellow++;
			}
		}

		if(red >= blue && red >= green && red >= yellow){
			return MainCard.Color.RED;
		}else if(blue >= green && blue >= yellow && blue > red){
			return MainCard.Color.BLUE;
		}else if(green >= yellow && green >= blue && green >= red){
			return MainCard.Color.GREEN;
		}else{
			return MainCard.Color.YELLOW;
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
	public boolean canMove(MainCard mostRecent, MainCard.Color color) {
		for (MainCard c : hand) {
			if (c.getColor() == color || c.getNum() == mostRecent.getNum() && !mostRecent.hasAction()
					&& mostRecent.getAction() !=  ActionCards.Special.DRAW4 || c.hasAction()) {
				return true;
			}
		}
		return false;
	}


	public boolean canMove(MainCard.Color color) {
		for (MainCard c : hand) {
			if (c.getColor() == color || c.hasAction()) {
				return true;
			}
		}
		return false;
	}



	// removes card from hand and adds it to discard
	public void playCard(MainCard card, Stack<MainCard> discard) { // plays the card i.e. discard
    	for (int i = 0; i < hand.size(); i++) {
    		if (hand.get(i).equals(card)) {
    			hand.remove(i);
    		}
    	}

    	data.previousCard = card;
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
