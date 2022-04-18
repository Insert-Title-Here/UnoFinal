package com.example.unofinal.backend;


import java.util.*;

public class Bot extends Player {
	private ArrayList<MainCard> hand = new ArrayList<>();
	
	public Bot(Stack<MainCard> drawPile) {
		super(drawPile);
	}

	public Bot(){
		for(int i = 0; i < 7; i++){
			hand.add(data.drawPile.pop());
		}
	}
	
	// make a move method
	// check size of other people hands
	// look at their most recent played cards
	// prioritize getting rid of cards
	/*public void move(MainCard.Color color, MainCard mostRecent, Stack<MainCard> drawPile, Stack<MainCard> discard) {
		int regBlue = cardAmount(MainCard.Color.BLUE); // amount of numerical/action blue cards
		int regRed = cardAmount(MainCard.Color.RED); // amount of numerical/action red cards
		int regGreen = cardAmount(MainCard.Color.GREEN); // amount of numerical/action Green cards
		int regYellow = cardAmount(MainCard.Color.YELLOW); // amount of numerical/action Yellow cards
		int special = cardAmount(MainCard.Color.NONE); // number of special cards
		if (canMove(color, mostRecent)) { // checks if a move is possible first
			// decision making
			if (regBlue > regRed && regBlue > regGreen && regBlue > regYellow) { // largest amount of cards in hand are blue
				if (color == MainCard.Color.BLUE) { // checks if the most recent card played is blue
					int preSize = hand.size(); // useful to see if hand changed
					for (int i = 0; i < hand.size(); i++) { // plays a numbered blue card
						if (hand.get(i).getColor() == color && hand.get(i).getNum() != MainCard.Numbers.NONE) {
							playCard(hand.get(i), discard);
							i = hand.size();
						}
					}
					if (preSize == hand.size()) { // checks if the hand size changed indicating a move was made
						// plays action card if it didn't happen
						for (int i = 0; i < hand.size(); i++) {
							if (hand.get(i).getColor() == color) {
								playCard(hand.get(i), discard);
								i = hand.size();
							}
						}
						if (preSize == hand.size()) { // check again for change in hand size
							for (int i = 0; i < hand.size(); i++) { // tries to match number to get color to blue
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
								drawCards(1);
							}
							// what should the bot do if they can't play a blue num card or action card or play a card num blue to change the color to blue
							// draw cards? or special?
						}
					}
				}
			} else if (regRed > regGreen && regRed > regYellow) { // largest amount of cards in hand are red
				
			} else if (regGreen > regYellow) { // largest amount of cards in hand are Green
				
			} else { // largest amount of cards in hand are Yellow
				
			}
		} 
	}*/


	public int move(MainCard.Color color, MainCard mostRecent) {
		System.out.print("Hand: ");
		printHand();


		int regBlue = cardAmount(MainCard.Color.BLUE); // amount of numerical/action blue cards
		int regRed = cardAmount(MainCard.Color.RED); // amount of numerical/action red cards
		int regGreen = cardAmount(MainCard.Color.GREEN); // amount of numerical/action Green cards
		int regYellow = cardAmount(MainCard.Color.YELLOW); // amount of numerical/action Yellow cards
		int draw2 = cardAmount(com.example.unofinal.backend.ActionCardColored.Action.DRAW2);
		int reverse = cardAmount(com.example.unofinal.backend.ActionCardColored.Action.REVERSE);
		int skip = cardAmount(com.example.unofinal.backend.ActionCardColored.Action.SKIP);
		int draw4 = cardAmount(ActionCards.Special.DRAW4);
		int wild = cardAmount(ActionCards.Special.PICKCOLOR);
		if (canMove(color, mostRecent)) { // checks if a move is possible first

			System.out.println("Can Move");
			// decision making
			if (regBlue > regRed && regBlue > regGreen && regBlue > regYellow) { // largest amount of cards in hand are blue
				if (color == MainCard.Color.BLUE) { // checks if the most recent card played is blue
					int preSize = hand.size(); // useful to see if hand changed
					for (int i = 0; i < hand.size(); i++) { // plays a numbered blue card
						if (hand.get(i).getColor() == color && hand.get(i).getNum() != MainCard.Numbers.NONE) {
							//playCard(hand.get(i), discard);
							return i;
							//i = hand.size();
						}
					}
					if (preSize == hand.size()) { // checks if the hand size changed indicating a move was made
						// plays action card if it didn't happen
						for (int i = 0; i < hand.size(); i++) {
							if (hand.get(i).getColor() == color) {
								//playCard(hand.get(i), discard);
								return i;

								//i = hand.size();
							}
						}
						if (preSize == hand.size()) { // check again for change in hand size
							for (int i = 0; i < hand.size(); i++) { // tries to match number to get color to blue
								if (hand.get(i).getNum() == mostRecent.getNum() && mostRecent.getNum() != MainCard.Numbers.NONE) {
									//playCard(hand.get(i), discard);
									return i;

									//i = hand.size();
								}
							}
						} else {
							if (regBlue == 0 && regRed != 0 && regYellow == 0 && regGreen == 0 && special != 0) {
								for (int i = 0; i < hand.size(); i++) {
									if (hand.get(i).getColor() == MainCard.Color.NONE && hand.get(i).getNum() == MainCard.Numbers.NONE) {
										ActionCards temp = (ActionCards) hand.get(i);
										temp.changeColor(MainCard.Color.RED);
										//playCard(temp, discard);
										return i;

									}
								}
							} else if (regYellow != 0 && regGreen == 0 && special != 0) {
								for (int i = 0; i < hand.size(); i++) {
									if (hand.get(i).getColor() == MainCard.Color.NONE && hand.get(i).getNum() == MainCard.Numbers.NONE) {
										ActionCards temp = (ActionCards) hand.get(i);
										temp.changeColor(MainCard.Color.YELLOW);
										//playCard(temp, discard);
										return i;

									}
								}
							} else if (regGreen != 0 && special != 0) {
								for (int i = 0; i < hand.size(); i++) {
									if (hand.get(i).getColor() == MainCard.Color.NONE && hand.get(i).getNum() == MainCard.Numbers.NONE) {
										ActionCards temp = (ActionCards) hand.get(i);
										temp.changeColor(MainCard.Color.GREEN);
										//playCard(temp, discard);
										return i;

									}
								}
							} else {
								drawCards(1);
							}
							// what should the bot do if they can't play a blue num card or action card or play a card num blue to change the color to blue
							// draw cards? or special?
						}
					}
				}
			} else if (regRed > regGreen && regRed > regYellow) { // largest amount of cards in hand are red

			} else if (regGreen > regYellow) { // largest amount of cards in hand are Green

			} /*else { // largest amount of cards in hand are Yellow

			}*/

		}

		return 2147483647;

	}

	public void remove(int i){
		hand.remove(i);
	}



	public boolean isBot(){
		return true;
	}
	
	public int cardAmount(MainCard.Color color) { // returns a number, allows other players to see how many cards opponents have
		int amount = 0;
		for (MainCard c : hand) {
			if (c.getColor() == color && c.getNum() != MainCard.Numbers.NONE) {
				amount++;
			}
		}
		return amount;
	}

    public int cardAmount(com.example.unofinal.backend.ActionCardColored.Action ability) {
        int amount = 0;
        for (MainCard c : hand) {
            if (c.hasAction() && c.getAbility() == ability) {
                amount++;
            }
        }
        return amount;
    }

    public int cardAmount(com.example.unofinal.backend.ActionCards.Special action) {
		int amount = 0;
		for (MainCard c : hand) {
			if (c.hasAction() && c.getAction() == action) {
				amount++;
			}
		}
		return amount;
	}


    public void printHand(){
		System.out.println(hand);
	}

}
