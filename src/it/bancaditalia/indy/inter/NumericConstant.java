package it.bancaditalia.indy.inter;

import it.bancaditalia.indy.symbols.Type;

public class NumericConstant extends Constant {

	private Integer value;

	public NumericConstant(Integer value) {
		super();
		this.value = value;
		this.type = Type.NUMBER;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public String javascript() {
		return value.toString();
	}

}
