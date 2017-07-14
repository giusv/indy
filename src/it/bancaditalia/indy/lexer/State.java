package it.bancaditalia.indy.lexer;

public enum State {
    Initial( false ),
    Final( true ),
    Error( false );
    static public final Integer length = 1 + Error.ordinal();

    final boolean accepting;

    State( boolean accepting ) {
        this.accepting = accepting;
    }
}