package it.bancaditalia.indy.inter;

import it.bancaditalia.indy.utils.ListUtils;

import java.util.List;

public class Let extends Expression {

	private List<Binding> bindings;
	private Expression expr;

	public Let(List<Binding> bindings, Expression expr) {
		super();
		this.bindings = bindings;
		this.expr = expr;
		this.type = expr.type;
	}

	public List<Binding> getBindings() {
		return bindings;
	}

	public void setBindings(List<Binding> bindings) {
		this.bindings = bindings;
	}

	public Expression getExpr() {
		return expr;
	}

	public void setExpr(Expression expr) {
		this.expr = expr;
	}

	@Override
	public String javascript() {
		if (bindings.isEmpty())
			return expr.javascript() + ";";
		else
			return "(function ("
					+ ListUtils.car(bindings).getId().toString()
					+ ") { return "
					+ new Let(ListUtils.cdr(bindings), expr).javascript()
					+ "}) ("
					+ ListUtils.car(bindings).getExp().javascript()
					+ ")";
	}

	@Override
	public String sql() {
		return javascript();
	}

}
