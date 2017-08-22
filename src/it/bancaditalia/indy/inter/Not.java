package it.bancaditalia.indy.inter;

import it.bancaditalia.indy.symbols.Type;

public class Not extends BooleanExpression {

	private Expression op1;

	public Not(Expression op1) throws TypeException {
		super();
		this.op1 = op1;

		if (op1.type != Type.BOOLEAN)
			error("Primo operando in AND non ha tipo BOOLEAN , ma " + op1.type);

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
		return "(!" + op1.javascript() + ")";
	}

	@Override
	public String sql() {
		return "not(" + op1.sql() + ")";
	}
}
