package it.bancaditalia.indy.inter;

import it.bancaditalia.indy.symbols.Type;

public class Query extends Expression {

	private Table table;
	private Expression expr;

	public Query(Table table, Expression expr) throws TypeException {
		super();
		this.table = table;

		if (expr.type != Type.BOOLEAN)
			error("Espressione non ha tipo BOOLEAN , ma " + expr.type);
		this.type = Type.QUERY;
		this.expr = expr;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public Expression getExpr() {
		return expr;
	}

	public void setExpr(Expression expr) {
		this.expr = expr;
	}

	@Override
	public String javascript() {
		switch(table) {
		case SINISTRI:
			return "querySinistri(soggetto," + expr.sql() + ")";	
		case VEICOLI:
			return "queryVeicoli(veicolo," + expr.sql() + ")";	
		default: {
			throw new Error("table not found");
		}
		}
		
	}

	@Override
	public String sql() {
		throw new Error("SQL non generabile per query");
	}	
}
