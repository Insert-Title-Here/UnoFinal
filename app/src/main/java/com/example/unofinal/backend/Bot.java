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
	// make decision based off most recent played card



	public int move(MainCard.Color color, MainCard mostRecent) {
		System.out.print("Hand: ");
		printHand();
		// I'll leave the print statements in
		// I broke my github help

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
			if (mostRecent.getAction() == ActionCards.Special.DRAW4) {
				drawCards(4);
			} else if (mostRecent.getAction() == ActionCards.Special.PICKCOLOR) {
				move(mostRecent.getColor(), regBlue, regRed, regGreen, regYellow, draw2, reverse, skip, draw4, wild);
			}

		} else {
			drawCards(1);
		}

		return 2147483647;

	}

	private void move(MainCard.Color color, int blue, int red, int green, int yellow, int draw2, int reverse, int skip, int draw4, int wild) {
		if (canMove(color)) {
			if (color == MainCard.Color.BLUE && blue > 0) {
				MainCard temp; // intialize to a blue card
				for (MainCard c : hand) {
					if (c.getColor() == MainCard.Color.BLUE) {
						// use MainCard compare to method
					}
				}
			} else if (color == MainCard.Color.RED) {

			} else if (color == MainCard.Color.GREEN) {

			} else if (color == MainCard.Color.YELLOW) {

			} else {

			}
		} else {
			drawCards(1);
		}
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
