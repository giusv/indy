package it.bancaditalia.indy.inter;

public class Not extends BooleanExpression {
	
	private Expression op1;
	
	public Not(Expression op1) {
		super();
		this.op1 = op1;
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
	
}

