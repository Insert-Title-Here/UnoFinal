package com.example.unofinal.backend;

import java.util.*;

public class player {
	private ArrayList<MainCard> hand = new ArrayList<>();
	Data data = new Data();
	
	public player(Stack<MainCard> drawPile) {
		for (int i = 0; i < 7; i++) {
			hand.add(drawPile.pop());
		}
	}

	public player(){
		for(int i = 0; i < 7; i++){
			hand.add(data.drawPile.pop());
		}
	}
	
	
	public int handSize() {
		return hand.size();
	}
	
	public void DrawCards(int num, Stack<MainCard> drawPile) {
		for (int i = 0; i < num; i++) {
			hand.add(drawPile.pop());
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

}
