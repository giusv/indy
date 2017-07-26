package it.bancaditalia.indy.main;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

import it.bancaditalia.indy.inter.*;
import it.bancaditalia.indy.lexer.*;
import it.bancaditalia.indy.parser.*;

public class Main {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {

		// Expression e = new Relation(Relop.EQUAL, new Identifier(new Word("a",
		// Tag.ID)),
		// new NumericConstant(new Integer(2)));
		// System.out.println(e.javascript());
		// System.exit(0);

		// String input = "# a=1,b=2,c=3 @ a+b*c";
		// String input = "sco1 (a=2) let b=1 in b==1";
		String input = "sco1 (a=2) " 
				+ "		let f = funzione (x) (2*x),"
				+ "			g = funzione (x) (4*f(x)),"
				+ "			fact = funzione (x) se x == 0 allora 1 altrimenti fact(x-1)"
				+ "		in fact(5)";

		// // Indicator ind = parse.indicator();
		// // System.out.println(ind.toString());
		int n = 1;
		long startTime = System.currentTimeMillis();

		ByteArrayInputStream stream = new ByteArrayInputStream(
				input.getBytes(StandardCharsets.UTF_8));
		for (int i = 0; i < n; i++) {
			stream.reset();
			Lexer lex = new Lexer(stream);
			// Expression expr;
			Indicator ind;
			// List<Token> list = lex.scanAll();
			// System.out.println(list);
			Parser parse = new Parser(lex);
			ind = parse.indicatore();
			parse.match(Tag.EOF);
			System.out.println("result: " + ind.javascript());
		}

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println(elapsedTime);
		//
		// startTime = System.currentTimeMillis();
		//
		// for (int i = 0; i < n; i++) {
		// float a = 1;
		// float b = 2;
		// float d = 1;
		// float c = 3 * d;
		// float expr = a + b * c;
		// }
		//
		// stopTime = System.currentTimeMillis();
		// elapsedTime = stopTime - startTime;
		// System.out.println(elapsedTime);

	}
}
