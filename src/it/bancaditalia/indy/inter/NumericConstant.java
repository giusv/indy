package it.bancaditalia.indy.inter;

public class NumericConstant extends Constant {

	private Integer value;

	public NumericConstant(Integer value) {
		super();
		this.value = value;
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
