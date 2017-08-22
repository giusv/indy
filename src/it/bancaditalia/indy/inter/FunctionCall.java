package it.bancaditalia.indy.inter;

import it.bancaditalia.indy.utils.ListUtils;

import java.util.List;

public class FunctionCall extends Expression {

	private FunctionDeclaration func;
	private List<Expression> parameters;

	public FunctionCall(FunctionDeclaration func, List<Expression> parameters)
			throws TypeException {
		super();
		this.func = func;
		this.parameters = parameters;

		if (parameters.size() != func.getParameters().size())
			error("Errato numero di argomenti passati alla funzione "
					+ func.getId() + ": attesi " + func.getParameters().size()
					+ ", passati " + parameters.size());
		for (int i = 0; i < parameters.size(); i++) {
			System.out.println("expected parameter " + (i+1) + ": " + func.getParameters().get(i) + ", type " + func.getParameters().get(i).type);
			System.out.println("parameter " + (i+1) + ": " + parameters.get(i) + ", type " + parameters.get(i).type);
			if (parameters.get(i).type != func.getParameters().get(i).type)
				error("Errato tipo dell'argomento in posizione " + (i + 1)
						+ ": atteso " + func.getParameters().get(i).type
						+ ", passato " + parameters.get(i).type);
		}
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
		return func.getId().javascript()
				+ "("
				+ ListUtils.car(parameters).javascript()
				+ ListUtils.cdr(parameters).stream()
						.map((binding) -> binding.javascript())
						.reduce("", (acc, elem) -> acc + ", " + elem) + ")";
	}

	@Override
	public String sql() {
		return javascript();
	}

}
