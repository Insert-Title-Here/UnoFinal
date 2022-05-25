package com.example.unofinal.backend;

//BOT NOTES:
//Bot tried played a red skip when it shouldn't have been able to play (infinite loop)
//Bot tried played a yellow skip when it shouldn't have been able to play (infinite loop) on a red zero
//Bot dies if it plays a skip itself
//Bot couldn't play anything else than blue four when player was drawing card
//TODO: ask if connor has code to handle skip/draw2/reverse

import java.util.*;

public class Bot extends Player {
	private ArrayList<MainCard> botHand = new ArrayList<>();

	public Bot(){
		for(int i = 0; i < 7; i++){
			botHand.add(data.drawPile.pop());
		}
	}

	public void drawCards(int num) {
		for (int i = 0; i < num; i++) {
			botHand.add(Data.drawPile.pop());
		}
	}

	public boolean canMove(MainCard mostRecent, MainCard.Color color) {
		for (MainCard c : botHand) {
			if (c.getColor() == color || c.getNum() == mostRecent.getNum() && !mostRecent.hasAction()
					&& mostRecent.getAction() !=  ActionCards.Special.DRAW4 || c.hasAction()) {
				return true;
			}
		}
		return false;
	}

	public boolean canMove(MainCard.Color color) {
		for (MainCard c : botHand) {
			if (c.getColor() == color || c.hasAction()) {
				return true;
			}
		}
		return false;
	}

	public void playCard(MainCard card, Stack<MainCard> discard) { // plays the card i.e. discard
		for (int i = 0; i < botHand.size(); i++) {
			if (botHand.get(i).equals(card)) {
				botHand.remove(i);
			}
		}
		discard.push(card);
	}

	public void playCard(int index){


		MainCard temp = botHand.remove(index);

		System.out.println(temp.toString());
	}

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

	public int size(){
		return botHand.size();
	}

	public MainCard move(MainCard.Color color, MainCard mostRecent) { // assumes not called following a skip or reverse
		// additionally after a player plays a +4 or +2 should just call bot.drawCards(2)

		System.out.print("botHand: ");

		int regBlue = cardAmount(MainCard.Color.BLUE); // amount of numerical/action blue cards
		int regRed = cardAmount(MainCard.Color.RED); // amount of numerical/action red cards
		int regGreen = cardAmount(MainCard.Color.GREEN); // amount of numerical/action Green cards
		int regYellow = cardAmount(MainCard.Color.YELLOW); // amount of numerical/action Yellow cards
		int draw2 = cardAmount(ActionCardColored.Action.DRAW2);
		int reverse = cardAmount(ActionCardColored.Action.REVERSE);
		int skip = cardAmount(ActionCardColored.Action.SKIP);
		int draw4 = cardAmount(ActionCards.Special.DRAW4);
		int wild = cardAmount(ActionCards.Special.PICKCOLOR);
		if (canMove(mostRecent, color)) { // checks if a move is possible first
			System.out.println("Can Move");
			// decision making
			// returns a 1 if a successful move was made
			if (mostRecent.getNum() == null) { // moved shit
				return move(mostRecent.getColor(), regBlue, regRed, regGreen, regYellow, draw2, reverse, skip, draw4, wild);
			} else if (mostRecent.getAbility() == ActionCardColored.Action.DRAW2) {
				drawCards(2);
			} else if (mostRecent.getColor() == MainCard.Color.BLUE) { // if most recent color is blue
				if (regBlue > 0) {
					return move(mostRecent.getColor(), regBlue, regRed, regGreen, regYellow, draw2, reverse, skip, draw4, wild);
				} else {
					MainCard temp = new MainCard();
					for (MainCard c : botHand) {
						if (c.getNum() == mostRecent.getNum()) {
							temp = c;
						}
					}
					if (temp.getNum() != null) {
						playCard(temp, data.discard);
						return temp;
					} else {
						if (skip > 0) {
							temp = new MainCard();
							for (MainCard c : botHand) {
								if (c.getAbility() == ActionCardColored.Action.SKIP) {
									temp = c;
								}
							}
							playCard(temp, data.discard);
							return temp;
						} else if (reverse > 0) {
							temp = new MainCard();
							for (MainCard c : botHand) {
								if (c.getAbility() == ActionCardColored.Action.REVERSE) {
									temp = c;
								}
							}
							playCard(temp, data.discard);
							return temp;
						} else if (draw2 > 1) {
							temp = new MainCard();
							for (MainCard c : botHand) {
								if (c.getAbility() == ActionCardColored.Action.DRAW2) {
									temp = c;
								}
							}
							playCard(temp, data.discard);
							return temp;
						} else if (draw4 > 1) {
							temp = new MainCard();
							for (MainCard c : botHand) {
								if (c.getAction() == ActionCards.Special.DRAW4) {
									temp = c;
								}
							}
							if (regBlue >= regRed && regBlue >= regYellow && regBlue >= regGreen) {
								temp.setColor(MainCard.Color.BLUE);
							} else if (regRed >= regGreen && regRed >= regYellow) {
								temp.setColor(MainCard.Color.RED);
							} else if (regGreen >= regYellow) {
								temp.setColor(MainCard.Color.GREEN);
							} else {
								temp.setColor(MainCard.Color.YELLOW);
							}
							playCard(temp, data.discard);
							return temp;
						} else if (wild > 1) {
							temp = new MainCard();
							for (MainCard c : botHand) {
								if (c.getAction() == ActionCards.Special.PICKCOLOR) {
									temp = c;
								}
							}
							if (regBlue >= regRed && regBlue >= regYellow && regBlue >= regGreen) {
								temp.setColor(MainCard.Color.BLUE);
							} else if (regRed >= regGreen && regRed >= regYellow) {
								temp.setColor(MainCard.Color.RED);
							} else if (regGreen >= regYellow) {
								temp.setColor(MainCard.Color.GREEN);
							} else {
								temp.setColor(MainCard.Color.YELLOW);
							}
							playCard(temp, data.discard);
							return temp;
						} else {
							drawCards(1);
							System.out.println(1);
							return null;
						}
					}
				}
			} else if (mostRecent.getColor() == MainCard.Color.RED) {
				if (regRed > 0) {
					return move(mostRecent.getColor(), regBlue, regRed, regGreen, regYellow, draw2, reverse, skip, draw4, wild);
				} else {
					MainCard temp = new MainCard();
					for (MainCard c : botHand) {
						if (c.getNum() == mostRecent.getNum()) {
							temp = c;
						}
					}
					if (temp.getNum() != null) {
						playCard(temp, data.discard);
						return temp;
					} else {
						if (skip > 0) {
							temp = new MainCard();
							for (MainCard c : botHand) {
								if (c.getAbility() == ActionCardColored.Action.SKIP) {
									temp = c;
								}
							}
							playCard(temp, data.discard);
							return temp;
						} else if (reverse > 0) {
							temp = new MainCard();
							for (MainCard c : botHand) {
								if (c.getAbility() == ActionCardColored.Action.REVERSE) {
									temp = c;
								}
							}
							playCard(temp, data.discard);
							return temp;
						} else if (draw2 > 1) {
							temp = new MainCard();
							for (MainCard c : botHand) {
								if (c.getAbility() == ActionCardColored.Action.DRAW2) {
									temp = c;
								}
							}
							playCard(temp, data.discard);
							return temp;
						} else if (draw4 > 1) {
							temp = new MainCard();
							for (MainCard c : botHand) {
								if (c.getAction() == ActionCards.Special.DRAW4) {
									temp = c;
								}
							}
							if (regBlue >= regRed && regBlue >= regYellow && regBlue >= regGreen) {
								temp.setColor(MainCard.Color.BLUE);
							} else if (regRed >= regGreen && regRed >= regYellow) {
								temp.setColor(MainCard.Color.RED);
							} else if (regGreen >= regYellow) {
								temp.setColor(MainCard.Color.GREEN);
							} else {
								temp.setColor(MainCard.Color.YELLOW);
							}
							playCard(temp, data.discard);
							return temp;
						} else if (wild > 1) {
							temp = new MainCard();
							for (MainCard c : botHand) {
								if (c.getAction() == ActionCards.Special.PICKCOLOR) {
									temp = c;
								}
							}
							if (regBlue >= regRed && regBlue >= regYellow && regBlue >= regGreen) {
								temp.setColor(MainCard.Color.BLUE);
							} else if (regRed >= regGreen && regRed >= regYellow) {
								temp.setColor(MainCard.Color.RED);
							} else if (regGreen >= regYellow) {
								temp.setColor(MainCard.Color.GREEN);
							} else {
								temp.setColor(MainCard.Color.YELLOW);
							}
							playCard(temp, data.discard);
							return temp;
						} else {
							drawCards(1);
							System.out.println(2);

							return null;
						}
					}
				}
			} else if (mostRecent.getColor() == MainCard.Color.GREEN) {
				if (regGreen > 0) {
					return move(mostRecent.getColor(), regBlue, regRed, regGreen, regYellow, draw2, reverse, skip, draw4, wild);
				} else {
					MainCard temp = new MainCard();
					for (MainCard c : botHand) {
						if (c.getNum() == mostRecent.getNum()) {
							temp = c;
						}
					}
					if (temp.getNum() != null) {
						playCard(temp, data.discard);
						return temp;
					} else {
						if (skip > 0) {
							temp = new MainCard();
							for (MainCard c : botHand) {
								if (c.getAbility() == ActionCardColored.Action.SKIP) {
									temp = c;
								}
							}
							playCard(temp, data.discard);
							return temp;
						} else if (reverse > 0) {
							temp = new MainCard();
							for (MainCard c : botHand) {
								if (c.getAbility() == ActionCardColored.Action.REVERSE) {
									temp = c;
								}
							}
							playCard(temp, data.discard);
							return temp;
						} else if (draw2 > 1) {
							temp = new MainCard();
							for (MainCard c : botHand) {
								if (c.getAbility() == ActionCardColored.Action.DRAW2) {
									temp = c;
								}
							}
							playCard(temp, data.discard);
							return temp;
						} else if (draw4 > 1) {
							temp = new MainCard();
							for (MainCard c : botHand) {
								if (c.getAction() == ActionCards.Special.DRAW4) {
									temp = c;
								}
							}
							if (regBlue >= regRed && regBlue >= regYellow && regBlue >= regGreen) {
								temp.setColor(MainCard.Color.BLUE);
							} else if (regRed >= regGreen && regRed >= regYellow) {
								temp.setColor(MainCard.Color.RED);
							} else if (regGreen >= regYellow) {
								temp.setColor(MainCard.Color.GREEN);
							} else {
								temp.setColor(MainCard.Color.YELLOW);
							}
							playCard(temp, data.discard);
							return temp;
						} else if (wild > 1) {
							temp = new MainCard();
							for (MainCard c : botHand) {
								if (c.getAction() == ActionCards.Special.PICKCOLOR) {
									temp = c;
								}
							}
							if (regBlue >= regRed && regBlue >= regYellow && regBlue >= regGreen) {
								temp.setColor(MainCard.Color.BLUE);
							} else if (regRed >= regGreen && regRed >= regYellow) {
								temp.setColor(MainCard.Color.RED);
							} else if (regGreen >= regYellow) {
								temp.setColor(MainCard.Color.GREEN);
							} else {
								temp.setColor(MainCard.Color.YELLOW);
							}
							playCard(temp, data.discard);
							return temp;
						} else {
							drawCards(1);
							return temp;
						}
					}
				}
			} else {
				if (regYellow > 0) {
					return move(mostRecent.getColor(), regBlue, regRed, regGreen, regYellow, draw2, reverse, skip, draw4, wild);
				} else {
					MainCard temp = new MainCard();
					for (MainCard c : botHand) {
						if (c.getNum() == mostRecent.getNum()) {
							temp = c;
						}
					}
					if (temp.getNum() != null) {
						playCard(temp, data.discard);
						return temp;
					} else {
						if (skip > 0) {
							temp = new MainCard();
							for (MainCard c : botHand) {
								if (c.getAbility() == ActionCardColored.Action.SKIP) {
									temp = c;
								}
							}
							playCard(temp, data.discard);
							return temp;
						} else if (reverse > 0) {
							temp = new MainCard();
							for (MainCard c : botHand) {
								if (c.getAbility() == ActionCardColored.Action.REVERSE) {
									temp = c;
								}
							}
							playCard(temp, data.discard);
							return temp;
						} else if (draw2 > 1) {
							temp = new MainCard();
							for (MainCard c : botHand) {
								if (c.getAbility() == ActionCardColored.Action.DRAW2) {
									temp = c;
								}
							}
							playCard(temp, data.discard);
							return temp;
						} else if (draw4 > 1) {
							temp = new MainCard();
							for (MainCard c : botHand) {
								if (c.getAction() == ActionCards.Special.DRAW4) {
									temp = c;
								}
							}
							if (regBlue >= regRed && regBlue >= regYellow && regBlue >= regGreen) {
								temp.setColor(MainCard.Color.BLUE);
							} else if (regRed >= regGreen && regRed >= regYellow) {
								temp.setColor(MainCard.Color.RED);
							} else if (regGreen >= regYellow) {
								temp.setColor(MainCard.Color.GREEN);
							} else {
								temp.setColor(MainCard.Color.YELLOW);
							}
							playCard(temp, data.discard);
							return temp;
						} else if (wild > 1) {
							temp = new MainCard();
							for (MainCard c : botHand) {
								if (c.getAction() == ActionCards.Special.PICKCOLOR) {
									temp = c;
								}
							}
							if (regBlue >= regRed && regBlue >= regYellow && regBlue >= regGreen) {
								temp.setColor(MainCard.Color.BLUE);
							} else if (regRed >= regGreen && regRed >= regYellow) {
								temp.setColor(MainCard.Color.RED);
							} else if (regGreen >= regYellow) {
								temp.setColor(MainCard.Color.GREEN);
							} else {
								temp.setColor(MainCard.Color.YELLOW);
							}
							playCard(temp, data.discard);
							return temp;
						} else {
							drawCards(1);

							//HERE
							System.out.println(3);

							return null;
						}
					}
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

	private MainCard move(MainCard.Color color, int blue, int red, int green, int yellow, int draw2, int reverse, int skip, int draw4, int wild) {
		if (canMove(color)) {
			if (color == MainCard.Color.BLUE && blue > 0) {
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
				playCard(temp, data.discard);
				return temp;
			} else if (color == MainCard.Color.RED && red > 0) {
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
				playCard(temp, data.discard);
				return temp;
			} else if (color == MainCard.Color.GREEN && green > 0) {
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
				playCard(temp, data.discard);
				return temp;
			} else if (color == MainCard.Color.YELLOW && yellow > 0) {
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
				playCard(temp, data.discard);
				return temp;
			} else if (draw2 > 1) {
				MainCard temp = new MainCard();
				for (MainCard c : botHand) {
					if (c.getAbility() == ActionCardColored.Action.DRAW2) {
						temp = c;
					}
				}
				playCard(temp, data.discard);
				return temp;
			} else if (reverse > 0) {
				MainCard temp = new MainCard();
				for (MainCard c : botHand) {
					if (c.getAbility() == ActionCardColored.Action.REVERSE) {
						temp = c;
					}
				}
				playCard(temp, data.discard);
				return temp;
			} else if (skip > 0) {
				MainCard temp = new MainCard();
				for (MainCard c : botHand) {
					if (c.getAbility() == ActionCardColored.Action.SKIP) {
						temp = c;
					}
				}
				playCard(temp, data.discard);
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




	public boolean isBot(){
		return true;
	}

	public int cardAmount(MainCard.Color color) { // returns a number, allows other players to see how many cards opponents have
		int amount = 0;
		for (MainCard c : botHand) {
			if (c.getColor() == color && c.getNum() != MainCard.Numbers.NONE) {
				amount++;
			}
		}
		return amount;
	}

	public int cardAmount(ActionCardColored.Action ability) {
		int amount = 0;
		for (MainCard c : botHand) {
			if (c.hasAction() && c.getAbility() == ability) {
				amount++;
			}
		}
		return amount;
	}

	public int cardAmount(ActionCards.Special action) {
		int amount = 0;
		for (MainCard c : botHand) {
			if (c.hasAction() && c.getAction() == action) {
				amount++;
			}
		}
		return amount;
	}


	public void printBotHand(){
		for (MainCard c : botHand) {
			System.out.println(c.toString());
		}
	}
}
