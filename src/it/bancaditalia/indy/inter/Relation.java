package it.bancaditalia.indy.inter;

import it.bancaditalia.indy.symbols.Type;

public class Relation extends BooleanExpression {

	private Relop r;
	private Expression op1, op2;

	public Relation(Relop r, Expression op1, Expression op2) throws TypeException {
		super();
		this.r = r;
		this.op1 = op1;
		this.op2 = op2;
		
		if (op1.type != op2.type)
			error("I due membri di " + r.toString() + " non hanno lo stesso tipo: " + op1.type + " != " + op2.type);
		this.type = Type.BOOLEAN;
	}

	public Expression getOp1() {
		return op1;
	}

	public void setOp1(Expression op1) {
		this.op1 = op1;
	}

	public Expression getOp2() {
		return op2;
	}

	public void setOp2(Expression op2) {
		this.op2 = op2;
	}

	public Relop getR() {
		return r;
	}

	public void setR(Relop r) {
		this.r = r;
	}

	@Override
	public String javascript() {
		return "(" + op1.javascript() + " " + r.javascript() + " "
				+ op2.javascript() + ")";
	}
	@Override
	public String sql() {
		return r.sql() +"(" + op1.sql() + ", " + op2.sql() + ")";
	}
}
