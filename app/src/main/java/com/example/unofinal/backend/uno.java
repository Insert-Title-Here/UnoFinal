package com.example.unofinal.backend;

import com.example.unofinal.backend.ActionCardColored;
import com.example.unofinal.backend.ActionCards;

import java.util.*;

public class uno {
    public static void main(String[] args) {
        MainCard[] deck = new MainCard[108]; // 108 cards in a uno deck
        ArrayList<ArrayList<MainCard>> game = new ArrayList<ArrayList<MainCard>>();
        buildDeck(deck); // sets up deck with all the cards
        Stack<MainCard> drawPile = new  Stack<>(); // intialize a drawpile
        Stack<MainCard> discard = new Stack<>(); // intialize a discard pile
        shuffleDeck(deck); // shuffles deck to randomnize cards
        setUpGame(deck, drawPile, game); // sets up the game, and fills the drawpile
        discard.push(drawPile.pop()); // finishes game setup by placing the starting card
        MainCard topOfDiscard = discard.peek(); // for reference and parameter
        ArrayList<MainCard> currentHand = game.get(0);
        int next = 1;
        boolean isInProgress = gameOver(game); // flag
		// manages turn order of the players and bots
        while (isInProgress) {
        	isInProgress = gameOver(game);
        	if (canPlayCard(currentHand, topOfDiscard)) {
        		// player action
        	} else {
        		drawCards(1, currentHand, drawPile);
        	}
        	if (next + 1 == game.size()) {
        		next = 0;
        	} else {
        		next++;
        	}
        	currentHand = game.get(next);
        }
        

    }

    // using a amazing triple nested for-loop to set up the uno deck
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

		// setup for the following for-loop
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

			// adds all the regular cards
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
			// adds all the action cards of various colours
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
		// adds wild cards
    	for (int g = 100; g < 108; g++) {
    		if (g <= 103) {
    			special = ActionCards.Special.DRAW4;
    		} else {
    			special = ActionCards.Special.PICKCOLOR;
    		}
    		arr[g] = new ActionCards(special);
    	}
    	
    }


    // takes a deck and shuffles it
    public static void shuffleDeck(MainCard[] arr) {
    	
    	List<MainCard> temp = Arrays.asList(arr);
    	Collections.shuffle(temp);
    	temp.toArray(arr);
    	
    }

    // adds all the cards into a draw pile and sets up hands
    public static void setUpGame(MainCard[] arr, Stack<MainCard> draw, ArrayList<ArrayList<MainCard>> hands) {
    	for (int i = 0; i < 108; i ++) {
    		draw.push(arr[i]);
    	}
    	for (int i = 0; i < 2; i++) {
    		for (int j = 0; j < 7; j++) {
    			hands.get(i).add(draw.pop());
    		}
    	}
    }

    // redundant method
    public static void drawCards(int amount, ArrayList<MainCard> recipient, Stack<MainCard> drawPile) {
    	for (int i = 0; i < amount; i++) {
    		recipient.add(drawPile.pop());
    	}
    }

    // obsolete
    public static boolean gameOver(ArrayList<ArrayList<MainCard>> arr) {
    	for (ArrayList<MainCard> a : arr) {
    		if (a.size() == 0) {
    			return true;
    		} 
    	}
    	return false;
    }

    // redundant and useless
    public static boolean canPlayCard(ArrayList<MainCard> hand, MainCard topOfDiscard) {
    	for (MainCard c : hand) {
    		if (c.matches(topOfDiscard)) {
    			return true;
    		}
    	}
    	return false;
    }

    // redudant and useless
    public static void playCard(ArrayList<MainCard> hand, MainCard card, Stack<MainCard> discard) {
    	for (int i = 0; i < hand.size(); i++) {
    		if (hand.get(i) == card) {
    			hand.remove(i);
    		}
    	}
    	discard.push(card);
    }
    
    
}
