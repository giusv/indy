package it.bancaditalia.indy.inter;

import java.util.List;

public class And2 implements BoolExpr {

	private List<BoolExpr> expressions;

	public And2(List<BoolExpr> expressions) {
		this.expressions = expressions;
	}

	@Override
	public boolean eval() {
		return false;
	}

	public String toString() {
		return "(and " + expressions.stream().map(e->e.toString()).reduce("",(a,b) -> a + " " + b) + ")"; 
	}

}
