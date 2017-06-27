package it.bancaditalia.indy.inter;
import it.bancaditalia.indy.lexer.*; import it.bancaditalia.indy.symbols.*;

public class Id extends Expr {

	public int offset;     // relative address

	public Id(Word id, Type p, int b) { super(id, p); offset = b; }

//	public String toString() {return "" + op.toString() + offset;}
}
