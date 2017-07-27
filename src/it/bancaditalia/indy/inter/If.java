package it.bancaditalia.indy.inter;

public class If extends Expression {

	private BooleanExpression condition;
	private Expression then, otherwise;

	public If(BooleanExpression expr, Expression then, Expression otherwise) {
		super();
		this.condition = expr;
		this.then = then;
		this.otherwise = otherwise;
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
