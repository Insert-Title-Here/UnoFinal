package com.example.unofinal.backend;


public class ActionCards extends MainCard {
	private Special action;
	private Color color;
	
	public ActionCards(Special action) {
		super(Color.NONE, Numbers.NONE);
		this.action = action;
	}

	public ActionCards(MainCard other) {
		super(other.getColor(), other.getNum());
		this.color = other.getColor();
	}






	// can be played regardless of color
	public Special getAction() {
		return action;
	}

	// helps with distiniction between card types with respect to subclass parking
	public boolean hasAction(){
		return true;
	}


	public ActionCardColored.Action getAbility(){
		return ActionCardColored.Action.NONE;
	}
	
	public String toString() {
		return(action.name());
	}
	
	/*public boolean matches(MainCard other) {
		return true;
	}

	 */

	// use for changing the current viable color during the game
	public void changeColor(Color color) {
		this.color = color;
	}

	public enum Special {
		DRAW4, PICKCOLOR, NONE
	}
}
