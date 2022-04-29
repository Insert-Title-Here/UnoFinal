package com.example.unofinal.backend;


import java.util.*;

public class Bot extends Player {
	private ArrayList<MainCard> hand = new ArrayList<>();
	
	public Bot(Stack<MainCard> drawPile) {
		super(drawPile);
		for(int i = 0; i < 7; i++){
			hand.add(drawPile.pop());
		}
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
			// returns a 0 if cards were drawn, -1 if no move, other wise it returns the index of the card
			if (mostRecent.getAction() == ActionCards.Special.DRAW4) {
				drawCards(4);
				return -1;
			} else if (mostRecent.getAction() == ActionCards.Special.PICKCOLOR) {
				return move(mostRecent.getColor(), regBlue, regRed, regGreen, regYellow, draw2, reverse, skip, draw4, wild);
			} else if (mostRecent.getAbility() == ActionCardColored.Action.DRAW2) {
				drawCards(2);
				return -1;
			} else if (mostRecent.getColor() == MainCard.Color.BLUE) { // if most recent color is blue
				if (regBlue > 0) {
					return move(mostRecent.getColor(), regBlue, regRed, regGreen, regYellow, draw2, reverse, skip, draw4, wild);
				} else {
					MainCard temp = new MainCard();
					for (MainCard c : hand) {
						if (c.getNum() == mostRecent.getNum()) {
							temp = c;
						}
					}
					if (temp.getNum() != null) {
						playCard(temp, data.discard);
						return hand.indexOf(temp);
					} else {
						if (skip > 0) {
							temp = new MainCard();
							for (MainCard c : hand) {
								if (c.getAbility() == ActionCardColored.Action.SKIP) {
									temp = c;
								}
							}
							playCard(temp, data.discard);
							return hand.indexOf(temp);
						} else if (reverse > 0) {
							temp = new MainCard();
							for (MainCard c : hand) {
								if (c.getAbility() == ActionCardColored.Action.REVERSE) {
									temp = c;
								}
							}
							playCard(temp, data.discard);
							return hand.indexOf(temp);
						} else if (draw2 > 1) {
							temp = new MainCard();
							for (MainCard c : hand) {
								if (c.getAbility() == ActionCardColored.Action.DRAW2) {
									temp = c;
								}
							}
							playCard(temp, data.discard);
							return hand.indexOf(temp);
						} else if (draw4 > 1) {
							temp = new MainCard();
							for (MainCard c : hand) {
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
							return hand.indexOf(temp);
						} else if (wild > 1) {
							temp = new MainCard();
							for (MainCard c : hand) {
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
							return hand.indexOf(temp);
						} else {
							drawCards(1);
							return -1;
						}
					}
				}
			} else if (mostRecent.getColor() == MainCard.Color.RED) {
				if (regRed > 0) {
					return move(mostRecent.getColor(), regBlue, regRed, regGreen, regYellow, draw2, reverse, skip, draw4, wild);
				} else {
					MainCard temp = new MainCard();
					for (MainCard c : hand) {
						if (c.getNum() == mostRecent.getNum()) {
							temp = c;
						}
					}
					if (temp.getNum() != null) {
						playCard(temp, data.discard);
						return hand.indexOf(temp);
					} else {
						if (skip > 0) {
							temp = new MainCard();
							for (MainCard c : hand) {
								if (c.getAbility() == ActionCardColored.Action.SKIP) {
									temp = c;
								}
							}
							playCard(temp, data.discard);
							return hand.indexOf(temp);
						} else if (reverse > 0) {
							temp = new MainCard();
							for (MainCard c : hand) {
								if (c.getAbility() == ActionCardColored.Action.REVERSE) {
									temp = c;
								}
							}
							playCard(temp, data.discard);
							return hand.indexOf(temp);
						} else if (draw2 > 1) {
							temp = new MainCard();
							for (MainCard c : hand) {
								if (c.getAbility() == ActionCardColored.Action.DRAW2) {
									temp = c;
								}
							}
							playCard(temp, data.discard);
							return hand.indexOf(temp);
						} else if (draw4 > 1) {
							temp = new MainCard();
							for (MainCard c : hand) {
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
							return hand.indexOf(temp);
						} else if (wild > 1) {
							temp = new MainCard();
							for (MainCard c : hand) {
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
							return hand.indexOf(temp);
						} else {
							drawCards(1);
							return -1;
						}
					}
				}
			} else if (mostRecent.getColor() == MainCard.Color.GREEN) {
				if (regGreen > 0) {
					return move(mostRecent.getColor(), regBlue, regRed, regGreen, regYellow, draw2, reverse, skip, draw4, wild);
				} else {
					MainCard temp = new MainCard();
					for (MainCard c : hand) {
						if (c.getNum() == mostRecent.getNum()) {
							temp = c;
						}
					}
					if (temp.getNum() != null) {
						playCard(temp, data.discard);
						return hand.indexOf(temp);
					} else {
						if (skip > 0) {
							temp = new MainCard();
							for (MainCard c : hand) {
								if (c.getAbility() == ActionCardColored.Action.SKIP) {
									temp = c;
								}
							}
							playCard(temp, data.discard);
							return hand.indexOf(temp);
						} else if (reverse > 0) {
							temp = new MainCard();
							for (MainCard c : hand) {
								if (c.getAbility() == ActionCardColored.Action.REVERSE) {
									temp = c;
								}
							}
							playCard(temp, data.discard);
							return hand.indexOf(temp);
						} else if (draw2 > 1) {
							temp = new MainCard();
							for (MainCard c : hand) {
								if (c.getAbility() == ActionCardColored.Action.DRAW2) {
									temp = c;
								}
							}
							playCard(temp, data.discard);
							return hand.indexOf(temp);
						} else if (draw4 > 1) {
							temp = new MainCard();
							for (MainCard c : hand) {
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
							return hand.indexOf(temp);
						} else if (wild > 1) {
							temp = new MainCard();
							for (MainCard c : hand) {
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
							return hand.indexOf(temp);
						} else {
							drawCards(1);
							return -1;
						}
					}
				}
			} else {
				if (regYellow > 0) {
					return move(mostRecent.getColor(), regBlue, regRed, regGreen, regYellow, draw2, reverse, skip, draw4, wild);
				} else {
					MainCard temp = new MainCard();
					for (MainCard c : hand) {
						if (c.getNum() == mostRecent.getNum()) {
							temp = c;
						}
					}
					if (temp.getNum() != null) {
						playCard(temp, data.discard);
						return hand.indexOf(temp);
					} else {
						if (skip > 0) {
							temp = new MainCard();
							for (MainCard c : hand) {
								if (c.getAbility() == ActionCardColored.Action.SKIP) {
									temp = c;
								}
							}
							playCard(temp, data.discard);
							return hand.indexOf(temp);
						} else if (reverse > 0) {
							temp = new MainCard();
							for (MainCard c : hand) {
								if (c.getAbility() == ActionCardColored.Action.REVERSE) {
									temp = c;
								}
							}
							playCard(temp, data.discard);
							return 1;
						} else if (draw2 > 1) {
							temp = new MainCard();
							for (MainCard c : hand) {
								if (c.getAbility() == ActionCardColored.Action.DRAW2) {
									temp = c;
								}
							}
							playCard(temp, data.discard);
							return hand.indexOf(temp);
						} else if (draw4 > 1) {
							temp = new MainCard();
							for (MainCard c : hand) {
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
							return hand.indexOf(temp);
						} else if (wild > 1) {
							temp = new MainCard();
							for (MainCard c : hand) {
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
							return hand.indexOf(temp);
						} else {
							drawCards(1);
							return -1;
						}
					}
				}
			}
		} else { // assumes this wasn't called after a skip or reverse was played
			drawCards(1);
			return -1;
		}

		// return 2147483647; // tell you something went wrong

	}

	private int move(MainCard.Color color, int blue, int red, int green, int yellow, int draw2, int reverse, int skip, int draw4, int wild) {
		if (canMove(color)) {
			if (color == MainCard.Color.BLUE && blue > 0) {
				MainCard temp = new MainCard(); // initialize to a blue card
				for (int i = 0; i < hand.size(); i++) {
					if (hand.get(i).getColor() == MainCard.Color.BLUE) {
						temp = hand.get(i);
						i = hand.size();
					}
				}
				for (MainCard c : hand) {
					if (c.getColor() == MainCard.Color.BLUE) {
						temp = c.largerNum(temp);
					}
				}
				playCard(temp, data.discard);
				return hand.indexOf(temp);
			} else if (color == MainCard.Color.RED && red > 0) {
				MainCard temp = new MainCard(); // initialize to a blue card
				for (int i = 0; i < hand.size(); i++) {
					if (hand.get(i).getColor() == MainCard.Color.RED) {
						temp = hand.get(i);
						i = hand.size();
					}
				}
				for (MainCard c : hand) {
					if (c.getColor() == MainCard.Color.RED) {
						temp = c.largerNum(temp);
					}
				}
				playCard(temp, data.discard);
				return hand.indexOf(temp);
			} else if (color == MainCard.Color.GREEN && green > 0) {
				MainCard temp = new MainCard(); // initialize to a blue card
				for (int i = 0; i < hand.size(); i++) {
					if (hand.get(i).getColor() == MainCard.Color.GREEN) {
						temp = hand.get(i);
						i = hand.size();
					}
				}
				for (MainCard c : hand) {
					if (c.getColor() == MainCard.Color.GREEN) {
						temp = c.largerNum(temp);
					}
				}
				playCard(temp, data.discard);
				return hand.indexOf(temp);
			} else if (color == MainCard.Color.YELLOW && yellow > 0) {
				MainCard temp = new MainCard(); // initialize to a blue card
				for (int i = 0; i < hand.size(); i++) {
					if (hand.get(i).getColor() == MainCard.Color.YELLOW) {
						temp = hand.get(i);
						i = hand.size();
					}
				}
				for (MainCard c : hand) {
					if (c.getColor() == MainCard.Color.YELLOW) {
						temp = c.largerNum(temp);
					}
				}
				playCard(temp, data.discard);
				return hand.indexOf(temp);
			} else if (draw2 > 1) {
				MainCard temp = new MainCard();
				for (MainCard c : hand) {
					if (c.getAbility() == ActionCardColored.Action.DRAW2) {
						temp = c;
					}
				}
				playCard(temp, data.discard);
				return hand.indexOf(temp);
			} else if (reverse > 0) {
				MainCard temp = new MainCard();
				for (MainCard c : hand) {
					if (c.getAbility() == ActionCardColored.Action.REVERSE) {
						temp = c;
					}
				}
				playCard(temp, data.discard);
				return hand.indexOf(temp);
			} else if (skip > 0) {
				MainCard temp = new MainCard();
				for (MainCard c : hand) {
					if (c.getAbility() == ActionCardColored.Action.SKIP) {
						temp = c;
					}
				}
				playCard(temp, data.discard);
				return hand.indexOf(temp);
			} else {
				drawCards(1);
				return -1;
			}
		} else {
			drawCards(1);
			return -1;
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
