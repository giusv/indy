package it.bancaditalia.indy.inter;

public class Minus extends ArithmeticExpression {

	private Expression op1, op2;

	public Minus(Expression op1, Expression op2) {
		super();
		this.op1 = op1;
		this.op2 = op2;
	}

	public Expression getOp1() {
		return op1;
	}

	public void setOp1(Expression op1) {
		this.op1 = op1;
	}

	@Override
	public String javascript() {
		return "(" + op1.javascript() + " - " + op2.javascript() + ")";
	}

}
