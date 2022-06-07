package com.example.unofinal.backend;

//BOT NOTES:
//Bot tried played a red skip when it shouldn't have been able to play (infinite loop)
//Bot tried played a yellow skip when it shouldn't have been able to play (infinite loop) on a red zero
//Bot dies if it plays a skip itself
//Bot couldn't play anything else than blue four when player was drawing card

import java.util.*;

public class Bot extends Player {

	private ArrayList<MainCard> botHand = new ArrayList<>();
	int regBlue = cardAmount(MainCard.Color.BLUE); // amount of numerical/action blue cards
	int regRed = cardAmount(MainCard.Color.RED); // amount of numerical/action red cards
	int regGreen = cardAmount(MainCard.Color.GREEN); // amount of numerical/action Green cards
	int regYellow = cardAmount(MainCard.Color.YELLOW); // amount of numerical/action Yellow cards
	int draw2 = cardAmount(ActionCardColored.Action.DRAW2); // amount of draw2 cards in hand
	int reverse = cardAmount(ActionCardColored.Action.REVERSE); // amount of reverse cards in hand
	int skip = cardAmount(ActionCardColored.Action.SKIP); // amount of skips in hand
	int draw4 = cardAmount(ActionCards.Special.DRAW4); // amount of draw4 cards in hand
	int wild = cardAmount(ActionCards.Special.PICKCOLOR); // number of color pick cards in hand

	public Bot(){
		for(int i = 0; i < 7; i++){
			botHand.add(Data.drawPile.pop());
		}
	}



	// adds cards to the bots hand
	public void drawCards(int num) {

		System.out.println("Drew a card");
		for (int i = 0; i < num; i++) {
			botHand.add(Data.drawPile.pop());
		}
	}

	// checks if the bot can make a move, for use with move method
	public boolean canMove(MainCard mostRecent, MainCard.Color color) {
		for (MainCard c : botHand) {
			if (c.getColor() == color || c.getNum() == mostRecent.getNum() && !mostRecent.hasAction()
					&& mostRecent.getAction() !=  ActionCards.Special.DRAW4 || c.hasAction()) {
				return true;
			}
		}
		return false;
	}

	// alternative can move method using only color
	public boolean canMove(MainCard.Color color) {
		for (MainCard c : botHand) {
			if (c.getColor() == color || c.hasAction()) {
				return true;
			}
		}
		return false;
	}

	// removes card from hand places it in discard
	public void playCard(MainCard card, Stack<MainCard> discard) { // plays the card i.e. discard
		int index = -1;
		for (int i = 0; i < botHand.size(); i++) {
			if (botHand.get(i).equals(card)) {
				index = i;
			}
		}
		botHand.remove(index);
		Data.discard.push(card);
	}

	// play card based off card index
	public void playCard(int index){


		MainCard temp = botHand.remove(index);

		System.out.println(temp.toString());
	}

	// prints out bot hand
	public void printHand(){
		for(MainCard i : botHand){
			System.out.println(i.toString());
		}
	}

	// getter for specific card indexes
	public MainCard getIndex(int i){
		return botHand.get(i);
	}

	// removes cards from a hand
	public void remove(int i){
		System.out.println(botHand.get(i));
		botHand.remove(i);
	}

	// finds card with matching number in the bot hand
	public MainCard findNumCard(MainCard card) {
		MainCard save = null;
		for (MainCard c : botHand) {
			if (c.getNum() == card.getNum()) {
				save = c;
			}
		}
		return save;
	}

	// finds skip cards in bot hand
	public MainCard findCardSkip() {
		MainCard save = null;
		for (MainCard c : botHand) {
			if (c.getAbility() == ActionCardColored.Action.SKIP) {
				save = c;
			}
		}
		return save;
	}

	// finds draw2 cards in bot hand
	public MainCard findCardDraw2() {
		MainCard save = null;
		for (MainCard c : botHand) {
			if (c.getAbility() == ActionCardColored.Action.DRAW2) {
				save = c;
			}
		}
		return save;
	}

	// finds reverse cards in bot hand
	public MainCard findCardReverse() {
		MainCard save = null;
		for (MainCard c : botHand) {
			if (c.getAbility() == ActionCardColored.Action.REVERSE) {
				save = c;
			}
		}
		return save;
	}

	// finds +4 cards in bot hand
	public MainCard findCardDraw4() {
		MainCard save = null;
		for (MainCard c : botHand) {
			if (c.getAction() == ActionCards.Special.DRAW4) {
				save = c;
			}
		}
		return save;
	}

	// finds wild cards in bot hand
	public MainCard findCardWild() {
		MainCard save = null;
		for (MainCard c : botHand) {
			if (c.getAction() == ActionCards.Special.PICKCOLOR) {
				save = c;
			}
		}
		return save;
	}

	// returns size of the bot hand
	public int size(){
		return botHand.size();
	}

	// handles moves when a regular card can't be played
	public MainCard noRegMove(MainCard mostRecent) {

		MainCard temp = findNumCard(mostRecent);
		if (temp != null && temp.getNum() != null) {
			playCard(temp, Data.discard);
			return temp;
		} else {
			if (skip > 0) {

				temp = findCardSkip();
				playCard(temp, Data.discard);
				return temp;

			} else if (reverse > 0) {

				temp = findCardReverse();
				playCard(temp, Data.discard);
				return temp;

			} else if (draw2 > 1 || botHand.size() == 1) {

				temp = findCardDraw2();
				playCard(temp, Data.discard);
				return temp;

			} else if (draw4 > 1) {

				temp = findCardDraw4();
				// picks what color to switch the color too
				if (regBlue >= regRed && regBlue >= regYellow && regBlue >= regGreen) {
					temp.setColor(MainCard.Color.BLUE);
				} else if (regRed >= regGreen && regRed >= regYellow) {
					temp.setColor(MainCard.Color.RED);
				} else if (regGreen >= regYellow) {
					temp.setColor(MainCard.Color.GREEN);
				} else {
					temp.setColor(MainCard.Color.YELLOW);
				}
				playCard(temp, Data.discard);
				return temp;

			} else if (wild > 1) {

				temp = findCardWild();
				// picks what color to switch the color too
				if (regBlue >= regRed && regBlue >= regYellow && regBlue >= regGreen) {
					temp.setColor(MainCard.Color.BLUE);
				} else if (regRed >= regGreen && regRed >= regYellow) {
					temp.setColor(MainCard.Color.RED);
				} else if (regGreen >= regYellow) {
					temp.setColor(MainCard.Color.GREEN);
				} else {
					temp.setColor(MainCard.Color.YELLOW);
				}
				playCard(temp, Data.discard);
				return temp;

			}
		}
		return null;
	}

	// handles which card to play
	public MainCard move(MainCard.Color color, MainCard mostRecent) { // assumes not called following a skip or reverse
		// additionally after a player plays a +4 or +2 should just call bot.drawCards(2)



		if (canMove(mostRecent, color)) { // checks if a move is possible first
			System.out.println("Can Move");
			// decision making
			// returns a 1 if a successful move was made
			if (mostRecent.getNum() == null) { // moved shit
				return move(mostRecent.getColor());
			} else if (mostRecent.getAbility() == ActionCardColored.Action.DRAW2) {
				drawCards(2);
			} else if (mostRecent.getAbility() == ActionCardColored.Action.REVERSE) {
				return move(mostRecent.getColor());
			} else if (mostRecent.getAbility() == ActionCardColored.Action.SKIP) {
				return move(mostRecent.getColor());
			} else if (mostRecent.getColor() == MainCard.Color.BLUE) { // if most recent color is blue
				if (regBlue > 0) {
					return move(mostRecent.getColor());
				} else {
					return noRegMove(mostRecent);
				}
			} else if (mostRecent.getColor() == MainCard.Color.RED) {
				if (regRed > 0) {
					return move(mostRecent.getColor());
				} else {
					noRegMove(mostRecent);
				}
			} else if (mostRecent.getColor() == MainCard.Color.GREEN) {
				if (regGreen > 0) {
					return move(mostRecent.getColor());
				} else {
					noRegMove(mostRecent);
				}
			} else {
				if (regYellow > 0) {
					return move(mostRecent.getColor());
				} else {
					noRegMove(mostRecent);
				}

			}
		} else if (mostRecent.getAction() == ActionCards.Special.DRAW4) {
			drawCards(4);
			System.out.println(4);

			return null;
		} else { // assumes this wasn't called after a skip or reverse was played
			drawCards(1);
		}

		return new MainCard(null, null); // tell you something went wrong

	}

	// handles which card to play when the most recent is a regular card
	private MainCard move(MainCard.Color color) {
		if (canMove(color)) {
			if (color == MainCard.Color.BLUE && regBlue > 0) {
				MainCard temp = new MainCard(); // initialize to a blue card
				for (int i = 0; i < botHand.size(); i++) {
					if (botHand.get(i).getColor() == MainCard.Color.BLUE) {
						temp = botHand.get(i);
						i = botHand.size();
					}
				}
				for (MainCard c : botHand) {
					if (c.getColor() == MainCard.Color.BLUE) {
						temp = c.largerNum(temp);
					}
				}
				playCard(temp, Data.discard);
				return temp;
			} else if (color == MainCard.Color.RED && regRed > 0) {
				MainCard temp = new MainCard(); // initialize to a blue card
				for (int i = 0; i < botHand.size(); i++) {
					if (botHand.get(i).getColor() == MainCard.Color.RED) {
						temp = botHand.get(i);
						i = botHand.size();
					}
				}
				for (MainCard c : botHand) {
					if (c.getColor() == MainCard.Color.RED) {
						temp = c.largerNum(temp);
					}
				}
				playCard(temp, Data.discard);
				return temp;
			} else if (color == MainCard.Color.GREEN && regGreen > 0) {
				MainCard temp = new MainCard(); // initialize to a blue card
				for (int i = 0; i < botHand.size(); i++) {
					if (botHand.get(i).getColor() == MainCard.Color.GREEN) {
						temp = botHand.get(i);
						i = botHand.size();
					}
				}
				for (MainCard c : botHand) {
					if (c.getColor() == MainCard.Color.GREEN) {
						temp = c.largerNum(temp);
					}
				}
				playCard(temp, Data.discard);
				return temp;
			} else if (color == MainCard.Color.YELLOW && regYellow > 0) {
				MainCard temp = new MainCard(); // initialize to a blue card
				for (int i = 0; i < botHand.size(); i++) {
					if (botHand.get(i).getColor() == MainCard.Color.YELLOW) {
						temp = botHand.get(i);
						i = botHand.size();
					}
				}
				for (MainCard c : botHand) {
					if (c.getColor() == MainCard.Color.YELLOW) {
						temp = c.largerNum(temp);
					}
				}
				playCard(temp, Data.discard);
				return temp;
			} else if (draw2 > 1) {
				MainCard temp = new MainCard();
				for (MainCard c : botHand) {
					if (c.getAbility() == ActionCardColored.Action.DRAW2) {
						temp = c;
					}
				}
				playCard(temp, Data.discard);
				return temp;
			} else if (reverse > 0) {
				MainCard temp = new MainCard();
				for (MainCard c : botHand) {
					if (c.getAbility() == ActionCardColored.Action.REVERSE) {
						temp = c;
					}
				}
				playCard(temp, Data.discard);
				return temp;
			} else if (skip > 0) {
				MainCard temp = new MainCard();
				for (MainCard c : botHand) {
					if (c.getAbility() == ActionCardColored.Action.SKIP) {
						temp = c;
					}
				}
				playCard(temp, Data.discard);
				return temp;
			} else {
				drawCards(1);
				System.out.println(5);

				return null;
			}
		} else {
			drawCards(1);
			System.out.println(6);

			return null;
		}
	}

	// subclass parking boolean
	public boolean isBot(){
		return true;
	}

	// returns amount of cards of a certain color excluding action cards
	public int cardAmount(MainCard.Color color) { // returns a number, allows other players to see how many cards opponents have
		int amount = 0;
		for (MainCard c : botHand) {
			if (c.getColor() == color && c.getNum() != MainCard.Numbers.NONE) {
				amount++;
			}
		}
		return amount;
	}

	// returns amount of action cards
	public int cardAmount(ActionCardColored.Action ability) {
		int amount = 0;
		for (MainCard c : botHand) {
			if (c.hasAction() && c.getAbility() == ability) {
				amount++;
			}
		}
		return amount;
	}

	// returns the amount of special cards
	public int cardAmount(ActionCards.Special action) {
		int amount = 0;
		for (MainCard c : botHand) {
			if (c.hasAction() && c.getAction() == action) {
				amount++;
			}
		}
		return amount;
	}

}
