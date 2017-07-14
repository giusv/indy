package it.bancaditalia.indy.lexer;

public class DFA {
	static State transition[][] = {
		    //  A               B               C
		    {
		        State.Initial,  State.Final,    State.Error
		    }, {
		        State.Final,    State.Initial,  State.Error
		    }
		};
	
	public State move(State s, Symbol c) {
		return transition[s.ordinal()][c.ordinal()];
	}
}
