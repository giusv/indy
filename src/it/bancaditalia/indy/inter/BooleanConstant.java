package it.bancaditalia.indy.inter;

import it.bancaditalia.indy.symbols.Type;

public class BooleanConstant extends Constant {

	private Boolean value;

	public BooleanConstant(Boolean value) {
		super();
		this.value = value;
		this.type = Type.BOOLEAN;
	}

	public Boolean getValue() {
		return value;
	}

	public void setValue(Boolean value) {
		this.value = value;
	}

	@Override
	public String javascript() {
		return value.toString();
	}

}
