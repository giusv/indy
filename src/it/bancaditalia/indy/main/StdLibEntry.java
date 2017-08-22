package it.bancaditalia.indy.main;

import java.util.List;
import it.bancaditalia.indy.inter.Identifier;
import it.bancaditalia.indy.symbols.Type;

public class StdLibEntry {
	private String name;
	private Type type;
	private List<Identifier> parameters;
	private String implementation;

	public StdLibEntry(String name, Type type, List<Identifier> parameters,
			String implementation) {
		super();
		this.name = name;
		this.type = type;
		this.parameters = parameters;
		this.implementation = implementation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public List<Identifier> getParameters() {
		return parameters;
	}

	public void setParameters(List<Identifier> parameters) {
		this.parameters = parameters;
	}

	public String getImplementation() {
		return implementation;
	}

	public void setImplementation(String implementation) {
		this.implementation = implementation;
	}

}
