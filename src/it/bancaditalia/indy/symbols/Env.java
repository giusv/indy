package it.bancaditalia.indy.symbols;

import java.util.*;

import it.bancaditalia.indy.inter.*;

public class Env {

	private Hashtable<String, Expression> table;
	protected Env prev;

	public Env(Env n) {
		table = new Hashtable<String, Expression>();
		prev = n;
	}

	public void put(String i, Expression e) {
		table.put(i, e);
	}

	public Expression get(String id) {
		for (Env e = this; e != null; e = e.prev) {
			Expression found = (Expression) (e.table.get(id));
			if (found != null)
				return found;
		}
		return null;
	}
}
