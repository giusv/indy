package it.bancaditalia.indy.inter;

import it.bancaditalia.indy.utils.ListUtils;

import java.util.List;

public class FunctionDeclaration extends Expression {

	// private Identifier name;
	private List<Identifier> parameters;
	Expression expr;

	public FunctionDeclaration(List<Identifier> parameters, Expression expr) {
		super();
		this.parameters = parameters;
		this.expr = expr;
	}

	public List<Identifier> getParameters() {
		return parameters;
	}

	public void setParameters(List<Identifier> parameters) {
		this.parameters = parameters;
	}

	public Expression getExpr() {
		return expr;
	}

	public void setExpr(Expression expr) {
		this.expr = expr;
	}

	@Override
	public String javascript() {
		return "function ("
				+ ListUtils.car(parameters).javascript()
				+ ListUtils.cdr(parameters).stream()
						.map((binding) -> binding.javascript())
						.reduce("", (acc, elem) -> acc + ", " + elem)
				+ ") { return " + expr.javascript() + ";}";
	}
}
