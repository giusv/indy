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
		return "(function ("
			 + ListUtils.car(bindings).getId().toString() 
			 + ListUtils.cdr(bindings).stream().map((binding) -> binding.getId().toString()).reduce("",(acc, elem) -> acc + ", " + elem)
			 + ") { return"
			 + expr.javascript()
			 + ";}) ("
			 + ListUtils.car(bindings).getExp().javascript()
			 + ListUtils.cdr(bindings).stream().map((binding) -> binding.getExp().javascript()).reduce("",(acc, elem) -> acc + ", " + elem)
			 + ")";
	}

	
}
