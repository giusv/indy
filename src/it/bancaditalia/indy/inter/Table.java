package it.bancaditalia.indy.inter;

public enum Table {
	SINISTRI("Sinistri"),
	VEICOLI("Veicoli");
	private final String name;

    private Table(String s) {
        name = s;
    }
    public String toString() { return name; }
}
