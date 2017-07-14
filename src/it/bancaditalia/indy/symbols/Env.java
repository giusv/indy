package it.bancaditalia.indy.symbols;

import java.util.*;

import it.bancaditalia.indy.inter.*;

public class Env {

	private Hashtable<String, Identifier> table;
	protected Env prev;

	public Env(Env n) {
		table = new Hashtable<String, Identifier>();
		prev = n;
	}

	public void put(String i, Identifier e) {
		table.put(i, e);
	}

	public Identifier get(String id) {
		for (Env e = this; e != null; e = e.prev) {
			Identifier found = (Identifier) (e.table.get(id));
			if (found != null)
				return found;
		}
		return null;
	}
}
