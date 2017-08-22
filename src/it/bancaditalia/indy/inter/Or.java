package it.bancaditalia.indy.inter;

import it.bancaditalia.indy.symbols.Type;

public class Or extends BooleanExpression {

	private Expression op1, op2;

	public Or(Expression op1, Expression op2) throws TypeException {
		super();
		this.op1 = op1;
		this.op2 = op2;
		
		if (op1.type != Type.BOOLEAN)
			error("Primo operando in OR non ha tipo BOOLEAN , ma " + op1.type);
		if (op2.type != Type.BOOLEAN)
			error("Secondo operando in OR non ha tipo BOOLEAN , ma " + op2.type);
		this.type = Type.BOOLEAN;
	}

	public Expression getOp1() {
		return op1;
	}

	public void setOp1(Expression op1) {
		this.op1 = op1;
	}

	@Override
	public String javascript() {
		return "(" + op1.javascript() + " || " + op2.javascript() + ")";
	}
	@Override
	public String sql() {
		return "or(" + op1.sql() +"," + op2.sql() + ")";
	}
}
