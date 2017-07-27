package it.bancaditalia.indy.inter;

import it.bancaditalia.indy.utils.ListUtils;

import java.util.List;

public class FunctionDeclaration extends Expression {

	// private Identifier name;
	Identifier id;
	private List<Identifier> parameters;
	Expression expr;

	public FunctionDeclaration(Identifier id, List<Identifier> parameters,
			Expression expr) {
		super();
		this.id = id;
		this.parameters = parameters;
		this.expr = expr;
	}

	public Identifier getId() {
		return id;
	}

	public void setId(Identifier id) {
		this.id = id;
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
		return "function " + id.javascript() + "("
				+ ListUtils.car(parameters).javascript()
				+ ListUtils.cdr(parameters).stream()
						.map((binding) -> binding.javascript())
						.reduce("", (acc, elem) -> acc + ", " + elem)
				+ ") { return " + expr.javascript() + ";}";
	}
}
