// This card manages wild cards such as draw 4s and pick color cards
// again it extends the MainCard class
// two fields action, and color
// has 1 constructors, one takes in the special

package com.example.unofinal.backend;


public class ActionCards extends MainCard {
	private Special action;
	private Color color;
	
	public ActionCards(Special action) {
		super(Color.NONE, Numbers.NONE);
		this.action = action;
	}

	// can be played regardless of color
	public Special getAction() {
		return action;
	}

	// helps with distinction between card types with respect to subclass parking
	public boolean hasAction(){
		return true;
	}


	// gets action
	public ActionCardColored.Action getAbility(){
		return ActionCardColored.Action.NONE;
	}
	
	public String toString() {
		return(action.name());
	}

	public enum Special {
		DRAW4, PICKCOLOR, NONE
	}

	// checking if matching
	public boolean equals(MainCard other) {

		// debug println
		System.out.println("Getting here to compare Draw4 and Wild");

		//System.out.println(other.toString());
		//System.out.println(this.toString());
		if (color == other.getColor() && action == other.getAction()) {
			// more debug
			System.out.println("Stuff");
			return true;
		} else {
			// final debug statement
			System.out.println("Isn't actually removing");
			return false;
		}
	}

	// subclass parking method
	public boolean hasColoredAction(){
		return false;
	}
}
