package com.example.unofinal.backend;

// enum for special actions
enum Action {
	DRAW2, SKIP, REVERSE, NONE
}
public class ActionCardColored extends MainCard { // inherits from the Main Card class
	Action ability;
	Color color;

    public ActionCardColored(Action ability, Color color) {
    	super(color, Numbers.NONE);
    	this.color = color;
    	this.ability = ability;
    }

	// getter for ability
	public com.example.unofinal.backend.ActionCardColored.Action getAbility(){
		return ability;
	}

	// getter for action, subclass parking
	public ActionCards.Special getAction(){
		return ActionCards.Special.NONE;
	}

	// format color ability
    public String toString() {
    	return(color.name() + " " + ability.name());
    }
    
    /*public boolean matches(MainCard other) {
    	ActionCardColored temp = new ActionCardColored(other);
    	if (temp.getColor() != color) {
    		return false;
    	} else if (temp.getNum() != this.getNum()) {
    		return false;
    	} else {
    		return actionMatch(temp);
    	}
    	
    }

     */
    // according to official uno rules action cards can be played regardless of color
    public boolean actionMatch(ActionCardColored other) {
    	return true;
    }


	// this method is to help distingush during sub class parking
	public boolean hasAction(){
		return true;
	}


	public boolean hasColoredAction(){
    	return true;
	} // boolean for identification


	public enum Action {
		DRAW2, SKIP, REVERSE, NONE
	}

	public boolean equals(MainCard other) { // checks if card matches and can be played
		if (color == other.getColor() && ability == other.getAbility()) {
			return true;
		} else {
			return false;
		}
	}
}