package it.bancaditalia.indy.inter;

import it.bancaditalia.indy.symbols.Type;
import it.bancaditalia.indy.utils.ListUtils;

import java.util.List;

public class FunctionDeclaration extends Expression {

	// private Identifier name;
	Identifier id;
	private List<Identifier> parameters;
	Expression expr;

	public FunctionDeclaration(Identifier id, Type type,
			List<Identifier> parameters, Expression expr) {
		super();
		this.id = id;
		this.parameters = parameters;
		this.expr = expr;
		this.type = type;
	}
	
	public FunctionDeclaration(Identifier id, Type type,
			List<Identifier> parameters) {
		super();
		this.id = id;
		this.parameters = parameters;
		this.expr = null;
		this.type = type;
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
		return "function "
				+ id.javascript()
				+ "("
				+ ListUtils.car(parameters).javascript()
				+ ListUtils.cdr(parameters).stream()
						.map((binding) -> binding.javascript())
						.reduce("", (acc, elem) -> acc + ", " + elem)
				+ ") { return " + expr.javascript() + ";}";
	}

	@Override
	public String sql() {
		throw new Error("SQL non generabile per dichiarazione di funzione");
	}
}
