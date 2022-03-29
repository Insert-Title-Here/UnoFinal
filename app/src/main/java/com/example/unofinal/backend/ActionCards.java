package com.example.unofinal.backend;


public class ActionCards extends MainCard {
	private Special action;
	private Color color;
	
	public ActionCards(Special action) {
		super(Color.NONE, Numbers.NONE);
		this.action = action;
	}
	
	public Special getAction() {
		return action;
	}
	
	public String toString() {
		return(action.name());
	}
	
	public boolean matches(MainCard other) {
		return true;
	}

	public void changeColor(Color color) {
		this.color = color;
	}

	public enum Special {
		DRAW4, PICKCOLOR
	}
}
