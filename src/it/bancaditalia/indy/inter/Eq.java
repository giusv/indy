package it.bancaditalia.indy.inter;

import java.util.List;

public class Eq implements BoolExpr {

	private Expr lhs;
	private Expr rhs;
	

	public Eq(Expr lhs, Expr rhs) {
		super();
		this.lhs = lhs;
		this.rhs = rhs;
	}


	@Override
	public boolean eval() {
		return false;
	}

	public String toString() {
		return "(eq " + lhs.toString() + " " + rhs.toString() + ")"; 
	}

}
