package com.example.unofinal.backend;

import java.util.*;

public class Player {
	private ArrayList<MainCard> hand = new ArrayList<>();
	//private int playerNumber;
	Data data = new Data();
	
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
	
	
	public int handSize() {
		return hand.size();
	}
	
	public void drawCards(int num) {
		for (int i = 0; i < num; i++) {
			hand.add(data.drawPile.pop());
		}
	}
	
	public boolean canMove(MainCard.Color color, MainCard mostRecent) {
		for (MainCard c : hand) {
    		if (c.matches(mostRecent) || c.getColor() == color) {
    			return true;
    		}
    	}
    	return false;
	}
	
	public void playCard(MainCard card, Stack<MainCard> discard) { // plays the card i.e. discard
    	for (int i = 0; i < hand.size(); i++) {
    		if (hand.get(i) == card) {
    			hand.remove(i);
    		}
    	}
    	discard.push(card);
    }

	public void printHand(){
		for(MainCard i: hand){
			System.out.println(i.toString());
		}
	}

	public MainCard getIndex(int i){
		return hand.get(i);
	}

	public void remove(int i){
		System.out.println(hand.get(i));
		hand.remove(i);
	}

	public int size(){
		return hand.size();
	}

}
