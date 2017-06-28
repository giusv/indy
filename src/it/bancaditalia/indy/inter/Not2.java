package it.bancaditalia.indy.inter;

public class Not2 implements BoolExpr {

	private BoolExpr expression;
	public Not2(BoolExpr expression) {
		this.expression = expression;
	}
	@Override
	public boolean eval() {
		return false;
	}

}
