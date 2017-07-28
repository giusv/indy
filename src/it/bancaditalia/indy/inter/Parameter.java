package it.bancaditalia.indy.inter;

import it.bancaditalia.indy.lexer.Word;
import it.bancaditalia.indy.symbols.Type;

public class Parameter extends Expression {

	private Word id;
	private Expression value;

	public Parameter(Word id) {
		super();
		this.id = id;
	}

	public Parameter(Parameter id, Type type) {
		super();
		this.id = id.getId();
		this.type = type;
	}

	public Parameter(Word id, Type type, Expression value) {
		super();
		this.id = id;
		this.type = type;
		this.setValue(value);
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

	public Expression getValue() {
		return value;
	}

	public void setValue(Expression value) {
		this.value = value;
	}

}
