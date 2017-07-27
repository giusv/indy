package it.bancaditalia.indy.inter;

import it.bancaditalia.indy.lexer.Lexer;
import it.bancaditalia.indy.symbols.Type;

public abstract class Expression implements Attribute {
	private int lexline;

	public Expression() {
		super();
		this.setLexline(Lexer.line);
	}

	protected Type type;

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getLexline() {
		return lexline;
	}

	public void setLexline(int lexline) {
		this.lexline = lexline;
	}

	void error(String s) throws TypeException {
		throw new TypeException("near line " + lexline + ": " + s);
	}
}
