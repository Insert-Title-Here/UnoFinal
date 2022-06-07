// This class extends the mainCard class and is responsible
// for colored action cards so that's your draw 2, skip, and reverse cards
// has two fields ability, and color
// it has only 1 constructor that takes in two parameters

package com.example.unofinal.backend;

import androidx.annotation.NonNull;

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
    @NonNull
	public String toString() {
    	return(color.name() + " " + ability.name());
    }


	// this method is to help distinguish during sub class parking
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
		return color == other.getColor() && ability == other.getAbility();
	}
}