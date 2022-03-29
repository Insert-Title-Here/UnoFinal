package com.example.unofinal.backend;


import java.util.*;

public class bot  extends player {
	private ArrayList<MainCard> hand;
	
	public bot (Stack<MainCard> drawPile) {
		super(drawPile);
	}
	
	// make a move method
	// check size of other people hands
	// look at their most recent played cards
	// prioritize getting rid of cards
	public void move(MainCard.Color color, MainCard mostRecent, Stack<MainCard> drawPile, Stack<MainCard> discard) {
		int regBlue = cardAmount(MainCard.Color.BLUE);
		int regRed = cardAmount(MainCard.Color.RED);
		int regGreen = cardAmount(MainCard.Color.GREEN);
		int regYellow = cardAmount(MainCard.Color.YELLOW);
		int special = cardAmount(MainCard.Color.NONE);
		if (canMove(color, mostRecent)) {
			// decision making
			if (regBlue > regRed && regBlue > regGreen && regBlue > regYellow) {
				if (color == MainCard.Color.BLUE) {
					int preSize = hand.size();
					for (int i = 0; i < hand.size(); i++) {
						if (hand.get(i).getColor() == color && hand.get(i).getNum() != MainCard.Numbers.NONE) {
							playCard(hand.get(i), discard);
							i = hand.size();
						}
					}
					if (preSize == hand.size()) {
						for (int i = 0; i < hand.size(); i++) {
							if (hand.get(i).getColor() == color) {
								playCard(hand.get(i), discard);
								i = hand.size();
							}
						}
						if (preSize == hand.size()) {
							for (int i = 0; i < hand.size(); i++) {
								if (hand.get(i).getNum() == mostRecent.getNum() && mostRecent.getNum() != MainCard.Numbers.NONE) {
									playCard(hand.get(i), discard);
									i = hand.size();
								}
							}
						} else {
							if (regBlue == 0 && regRed != 0 && regYellow == 0 && regGreen == 0 && special != 0) {
								for (int i = 0; i < hand.size(); i++) {
									if (hand.get(i).getColor() == MainCard.Color.NONE && hand.get(i).getNum() == MainCard.Numbers.NONE) {
										ActionCards temp = (ActionCards) hand.get(i);
										temp.changeColor(MainCard.Color.RED);
										playCard(temp, discard);
									}
								}
							} else if (regYellow != 0 && regGreen == 0 && special != 0) {
								for (int i = 0; i < hand.size(); i++) {
									if (hand.get(i).getColor() == MainCard.Color.NONE && hand.get(i).getNum() == MainCard.Numbers.NONE) {
										ActionCards temp = (ActionCards) hand.get(i);
										temp.changeColor(MainCard.Color.YELLOW);
										playCard(temp, discard);
									}
								}
							} else if (regGreen != 0 && special != 0) {
								for (int i = 0; i < hand.size(); i++) {
									if (hand.get(i).getColor() == MainCard.Color.NONE && hand.get(i).getNum() == MainCard.Numbers.NONE) {
										ActionCards temp = (ActionCards) hand.get(i);
										temp.changeColor(MainCard.Color.GREEN);
										playCard(temp, discard);
									}
								}
							} else {
								DrawCards(1, drawPile);
							}
							// what should the bot do if they can't play a blue num card or action card or play a card num blue to change the color to blue
							// draw cards? or special?
						}
					}
				}
			} else if (regRed > regGreen && regRed > regYellow) {
				
			} else if (regGreen > regYellow) {
				
			} else {
				
			}
		} 
	}
	
	public int cardAmount(MainCard.Color color) {
		int amount = 0;
		for (MainCard c : hand) {
			if (c.getColor() == color && c.getNum() != MainCard.Numbers.NONE) {
				amount++;
			}
		}
		return amount;
	}

}
