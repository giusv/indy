package it.bancaditalia.indy.inter;

import java.util.List;

public class Or2 implements BoolExpr {

	private List<BoolExpr> expressions;
	public Or2(List<BoolExpr> expressions) {
		this.expressions = expressions;
	}
	@Override
	public boolean eval() {
		return false;
	}

}
