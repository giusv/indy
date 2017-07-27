package it.bancaditalia.indy.inter;

import it.bancaditalia.indy.lexer.Word;
import it.bancaditalia.indy.symbols.Type;

public class Identifier extends Expression {

	private Word id;

	public Identifier(Word id) {
		super();
		this.id = id;
	}
	
	public Identifier(Identifier id, Type type) {
		super();
		this.id = id.getId();
		this.type = type;
	}

	public Word getId() {
		return id;
	}

	public void setId(Word id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id.toString();
	}
	
	@Override
	public String javascript() {
		return id.lexeme;
	}

	
}
