package it.bancaditalia.indy.inter;

public enum Relop {
	EQUAL("=="),
	LESS_THAN("<");
	private final String name;

    private Relop(String s) {
        name = s;
    }
    public String toString() { return name; }
}
