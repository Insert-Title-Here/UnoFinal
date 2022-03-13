package com.example.unofinal.backend;


public class ActionCards extends MainCard {
	private Special action;
	
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

	public enum Special {
		DRAW4, PICKCOLOR
	}
}
