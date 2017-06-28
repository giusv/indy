package it.bancaditalia.indy.inter;

import it.bancaditalia.indy.lexer.*;

public class Id2 implements Expr {

	private Word id;

	public Id2(Word id) {
		this.id = id;
	}

	public String toString() {
		return id.toString();
	}

	@Override
	public Object eval() {
		return null;
	}
}
