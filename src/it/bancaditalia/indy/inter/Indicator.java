package it.bancaditalia.indy.inter;

public class Indicator {
	public Id2 name;
	
	public Indicator(Id2 name) {
		this.name = name;
	}
	public String toString(){
		return "(indicator " + name.toString() + ")";
	}
}
