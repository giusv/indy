package it.bancaditalia.indy.inter;

import it.bancaditalia.indy.lexer.*;
import it.bancaditalia.indy.symbols.*;

public class Rel extends Logical {

	public Rel(Token tok, ExprOld x1, ExprOld x2) {
		super(tok, x1, x2);
	}

	public Type check(Type p1, Type p2) {
		if (p1 == p2)
			return Type.Bool;
		else
			return null;
	}

	public void jumping(int t, int f) {
		ExprOld a = expr1.reduce();
		ExprOld b = expr2.reduce();
		String test = a.toString() + " " + op.toString() + " " + b.toString();
		emitjumps(test, t, f);
	}
}
