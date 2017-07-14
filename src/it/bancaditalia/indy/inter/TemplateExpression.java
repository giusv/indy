package it.bancaditalia.indy.inter;

public class TemplateExpression<T> {
	private T value;

	public TemplateExpression(T value) {
		super();
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
	
}
