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
	
	public String toString() {

		if(color == null && num == null){
			return "null";
		}

		if(num == null){
			return(color.name() + "");
		}
		return(color.name() + " " + num.name());
	}

	// checks if the card matches another either in color or number matching
	public boolean matches(MainCard other) {

		if(other.getColor() == Color.NONE){
			System.out.println("Also true");
			return true;
		}

		if (other.getColor() == color || other.getNum() == num) {
			System.out.println("true");
			return true;

		} else {
			System.out.println("false");
			return false;
		}
	}

	// this method is to distingush between cards with subclass parking
	public boolean hasAction(){
		return false;
	}

	public com.example.unofinal.backend.ActionCardColored.Action getAbility(){
		return ActionCardColored.Action.NONE;
	}

	public ActionCards.Special getAction(){
		return ActionCards.Special.NONE;
	}







	public enum Color {
		RED, GREEN, BLUE, YELLOW, NONE
	}

	public enum Numbers {
		ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, NONE
	}
}
