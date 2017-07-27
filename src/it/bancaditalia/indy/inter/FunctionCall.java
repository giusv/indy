package it.bancaditalia.indy.inter;

import it.bancaditalia.indy.utils.ListUtils;

import java.util.List;

public class FunctionCall extends Expression {

	private FunctionDeclaration func;
	private List<Expression> parameters;

	public FunctionCall(FunctionDeclaration func, List<Expression> parameters) {
		super();
		this.func = func;
		this.parameters = parameters;
		this.type = func.type;
	}

	public FunctionDeclaration getfunc() {
		return func;
	}

	public void setfunc(FunctionDeclaration func) {
		this.func = func;
	}

	public List<Expression> getParameters() {
		return parameters;
	}

	public void setParameters(List<Expression> parameters) {
		this.parameters = parameters;
	}

	@Override
	public String javascript() {
		return func.getId().javascript() + "("
				+ ListUtils.car(parameters).javascript()
				+ ListUtils.cdr(parameters).stream()
						.map((binding) -> binding.javascript())
						.reduce("", (acc, elem) -> acc + ", " + elem)
				+ ")";
	}

}
