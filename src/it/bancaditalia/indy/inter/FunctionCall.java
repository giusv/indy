package it.bancaditalia.indy.inter;

import it.bancaditalia.indy.utils.ListUtils;

import java.util.List;

public class FunctionCall extends Expression {

	private Identifier name;
	private List<Expression> parameters;

	public FunctionCall(Identifier name, List<Expression> parameters) {
		super();
		this.name = name;
		this.parameters = parameters;
	}

	public Identifier getName() {
		return name;
	}

	public void setName(Identifier name) {
		this.name = name;
	}

	public List<Expression> getParameters() {
		return parameters;
	}

	public void setParameters(List<Expression> parameters) {
		this.parameters = parameters;
	}

	@Override
	public String javascript() {
		return name.javascript() + "("
				+ ListUtils.car(parameters).javascript()
				+ ListUtils.cdr(parameters).stream()
						.map((binding) -> binding.javascript())
						.reduce("", (acc, elem) -> acc + ", " + elem)
				+ ")";
	}

}
