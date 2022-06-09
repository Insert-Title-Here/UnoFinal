// This class is for the regular cards
// that are just a number + color so a Blue 3
// it has just 2 fields, a color enum, and a num enum
// it has 2 constructors one takes in two parameters for
// both color and num, the other creates a null card



package com.example.unofinal.backend;


public class MainCard {
	private Color color;
	private Numbers num;
	
	public MainCard(Color color, Numbers num) {
		this.color = color;
		this.num = num;
	}

	public MainCard(){
		color = null;
		num = null;
	}

	// setter for color of the card
	public void setColor(Color color){
		this.color = color;
	}

	// setter for the cards number
	public void setNum(Numbers num){
		this.num = num;
	}

	// getter for the cards color
	public Color getColor() {
		return color;
	}

	// getters for the cards number
	public Numbers getNum() {
		return num;
	}

	// format color number
	public String toString() {

		if(color == null && num == null){
			return "null";
		}

		if(num == null){
			return(color.name() + "");
		}
		return(color.name() + " " + num.name());
	}

	// checks if a card is an exact match
	public boolean equals(MainCard other) {
		if (color == other.getColor() && num == other.getNum()) {
			return true;
		} else {
			return false;
		}
	}

	// checks if the card matches another either in color or number matching
	public boolean matches(MainCard other) {

		if(other.getColor() == Color.NONE){
			return true;
		}

		if (other.getColor() == color || other.getNum() == num) {
			return true;

		} else {
			return false;
		}
	}

	// this method is to distingush between cards with subclass parking
	public boolean hasAction(){
		return false;
	}

	// subclass parking
	public boolean hasColoredAction(){
		return false;
	}

	// getter for ability for subclass parking and bot movement
	public com.example.unofinal.backend.ActionCardColored.Action getAbility() {
		return ActionCardColored.Action.NONE;
	}

	// getter for cards Special ability for subclass parking
	public ActionCards.Special getAction(){
		return ActionCards.Special.NONE;
	}

	// used for card comparisons
	public MainCard largerNum(MainCard other) {
		int thisNum;
		int otherNum;
		if (this.num == Numbers.ZERO) {
			thisNum = 0;
		} else if (this.num == Numbers.ONE) {
			thisNum = 1;
		} else if (this.num == Numbers.TWO) {
			thisNum = 2;
		} else if (this.num == Numbers.THREE) {
			thisNum = 3;
		} else if (this.num == Numbers.FOUR) {
			thisNum = 4;
		} else if (this.num == Numbers.FIVE) {
			thisNum = 5;
		} else if (this.num == Numbers.SIX) {
			thisNum = 6;
		} else if (this.num == Numbers.SEVEN) {
			thisNum = 7;
		} else if (this.num == Numbers.EIGHT) {
			thisNum = 8;
		} else if (this.num == Numbers.NINE) {
			thisNum = 9;
		} else {
			thisNum = -1;
		}

		if (other.num == Numbers.ZERO) {
			otherNum = 0;
		} else if (other.num == Numbers.ONE) {
			otherNum = 1;
		} else if (other.num == Numbers.TWO) {
			otherNum = 2;
		} else if (other.num == Numbers.THREE) {
			otherNum = 3;
		} else if (other.num == Numbers.FOUR) {
			otherNum = 4;
		} else if (other.num == Numbers.FIVE) {
			otherNum = 5;
		} else if (other.num == Numbers.SIX) {
			otherNum = 6;
		} else if (other.num == Numbers.SEVEN) {
			otherNum = 7;
		} else if (other.num == Numbers.EIGHT) {
			otherNum = 8;
		} else if (other.num == Numbers.NINE) {
			otherNum = 9;
		} else {
			otherNum = -1;
		}

		if (thisNum >= otherNum) {
			return this;
		} else {
			return other;
		}
	}







	public enum Color {
		RED, GREEN, BLUE, YELLOW, NONE
	}

	public enum Numbers {
		ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, NONE
	}
}
