package it.bancaditalia.indy.inter;

import it.bancaditalia.indy.utils.ListUtils;

import java.util.List;

public class Indicator implements Attribute {
	private Identifier name;
	private Identifier input;
	private List<Binding> bindings;
	private Expression expr;
	public Indicator(Identifier name, Identifier input, List<Binding> bindings, Expression expr) {
		super();
		this.name = name;
		this.input = input;
		this.bindings = bindings;
		this.expr = expr;
	}
	public Identifier getName() {
		return name;
	}
	public void setName(Identifier name) {
		this.name = name;
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
		return "var " + name.javascript() + " = function(" + input.javascript() + ") { return ((function ("
			 + ListUtils.car(bindings).getId().toString() 
			 + ListUtils.cdr(bindings).stream().map((binding) -> binding.getId().toString()).reduce("",(acc, elem) -> acc + ", " + elem)
			 + ") { return "
			 + expr.javascript()
			 + ";}) ("
			 + ListUtils.car(bindings).getExp().javascript()
			 + ListUtils.cdr(bindings).stream().map((binding) -> binding.getExp().javascript()).reduce("",(acc, elem) -> acc + ", " + elem)
			 + "));};";
	}
	public Identifier getInput() {
		return input;
	}
	public void setInput(Identifier input) {
		this.input = input;
	}	
}
	
