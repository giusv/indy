package it.bancaditalia.indy.inter;

import it.bancaditalia.indy.symbols.Type;

public class If extends Expression {

	private BooleanExpression condition;
	private Expression then, otherwise;

	public If(BooleanExpression expr, Expression then, Expression otherwise) throws TypeException {
		super();
		this.condition = expr;
		this.then = then;
		this.otherwise = otherwise;
		
		
		if (condition.type != Type.BOOLEAN)
			error("Condizione in SE non ha tipo BOOLEAN , ma " + condition.type);
		if (then.type != otherwise.type)
			error("I due rami in SE non hanno lo stesso tipo: " + then.type + " != " + otherwise.type);
		this.type = then.type;
	}

	public BooleanExpression getExpr() {
		return condition;
	}

	public void setExpr(BooleanExpression expr) {
		this.condition = expr;
	}

	public Expression getThen() {
		return then;
	}

	public void setThen(Expression then) {
		this.then = then;
	}

	public Expression getOtherwise() {
		return otherwise;
	}

	public void setOtherwise(Expression otherwise) {
		this.otherwise = otherwise;
	}

	@Override
	public String javascript() {
		return  condition.javascript() + " ? " + then.javascript() + " : " + otherwise.javascript();
	}

}
