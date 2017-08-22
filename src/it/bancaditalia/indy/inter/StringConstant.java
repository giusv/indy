package it.bancaditalia.indy.inter;

import it.bancaditalia.indy.symbols.Type;

public class StringConstant extends Constant {

	private String value;

	public StringConstant(String value) {
		super();
		this.value = value;
		this.type = Type.STRING;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String javascript() {
		return "\"" + value + "\"";
	}

	@Override
	public String sql() {
		return javascript();
	}

}
