package it.bancaditalia.indy.inter;

public enum Relop implements Attribute {
	EQUAL("=="), LESS_THAN("<");
	private final String name;

	private Relop(String s) {
		name = s;
	}

	// public String javascript() { return name; }
	@Override
	public String sql() {
		return name().toLowerCase();
	}

	@Override
	public String javascript() {
		return name;
	}

}
