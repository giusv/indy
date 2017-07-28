package it.bancaditalia.indy.inter;

public class Binding {

	private Identifier id;
	private Expression exp;

	public Binding(Identifier id, Expression exp) throws TypeException {
		super();
		System.out.println("in Binding: " + id.type + " " + exp.type);
		if(id.type != null && id.type != exp.type)
			id.error("Tipi non corrispondenti nel legame per " + id.getId() + ", specificato " 
					+ id.type + ", trovato " + exp.type);
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
