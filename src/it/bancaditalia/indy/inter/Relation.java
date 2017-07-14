package it.bancaditalia.indy.inter;

public class Relation extends BooleanExpression {

	private Relop r;
	private Expression op1, op2;

	public Relation(Relop r, Expression op1, Expression op2) {
		super();
		this.r = r;
		this.op1 = op1;
		this.op2 = op2;
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
		return "(" + op1.javascript() + " " + r.toString() + " "
				+ op2.javascript() + ")";
	}

}
