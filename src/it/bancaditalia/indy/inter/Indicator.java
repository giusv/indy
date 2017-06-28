package it.bancaditalia.indy.inter;

public class Indicator {
	public Id2 name;
	public BoolExpr expr;
	public Indicator(Id2 name,BoolExpr expr) {
		this.name = name;
		this.expr = expr;
	}
	public String toString(){
		return "(indicator " + name.toString() + ")";
	}
}
