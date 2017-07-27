package it.bancaditalia.indy.inter;

import it.bancaditalia.indy.symbols.Type;

public class Times extends ArithmeticExpression {

	private Expression op1, op2;

	public Times(Expression op1, Expression op2) throws TypeException {
		super();
		this.op1 = op1;
		this.op2 = op2;
		
		if (op1.type != Type.NUMBER)
			error("Primo operando in * non ha tipo NUMBER , ma " + op1.type);
		if (op2.type != Type.NUMBER)
			error("Secondo operando in * non ha tipo NUMBER , ma " + op2.type);
		this.type = Type.NUMBER;
	}

	public Expression getOp1() {
		return op1;
	}

	public void setOp1(Expression op1) {
		this.op1 = op1;
	}

	@Override
	public String javascript() {
		return "(" + op1.javascript() + " * " + op2.javascript() + ")";
	}

}
