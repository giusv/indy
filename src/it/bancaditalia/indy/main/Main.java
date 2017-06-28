package it.bancaditalia.indy.main;
import java.io.*; 
import java.nio.charset.StandardCharsets;

import it.bancaditalia.indy.inter.BoolExpr;
//import it.bancaditalia.indy.inter.Indicator;
import it.bancaditalia.indy.lexer.*; 
import it.bancaditalia.indy.parser.*;

public class Main {

	public static void main(String[] args) throws IOException {
		String input = "(and (eq i j))";
		ByteArrayInputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		Lexer lex = new Lexer(stream);
		Parser parse = new Parser(lex);
//		Indicator ind = parse.indicator();
//		System.out.println(ind.toString());
		BoolExpr expr = parse.bexpr();
		System.out.println(expr.toString());
	}
}
