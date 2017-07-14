package it.bancaditalia.indy.inter;

public class Binding {

	private Identifier id;
	private Expression exp;

	public Binding(Identifier id, Expression exp) {
		super();
		this.id = id;
		this.exp = exp;
	}

	public Identifier getId() {
		return id;
	}

	public void setId(Identifier id) {
		this.id = id;
	}

	public Expression getExp() {
		return exp;
	}

	public void setExp(Expression exp) {
		this.exp = exp;
	}

}
