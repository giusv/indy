package it.bancaditalia.indy.main;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

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
		String input = "sco1 (veicolo) (comparatore(x : numero, y : numero) : logico = x == y) " 
				+ "		let b = 2,"
				+ "		    trans(v : veicolo) : numero = 1,"
				+ "         fact(x : numero) : numero = se x == 0 allora 1 altrimenti x*fact(x-1)"
				+ "		in trans(veicolo) == 1";

		// // Indicator ind = parse.indicator();
		// // System.out.println(ind.toString());
		ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");
		
        ByteArrayInputStream stream = new ByteArrayInputStream(
				input.getBytes(StandardCharsets.UTF_8));
        
        stream.reset();
        Lexer lex = new Lexer(stream);
        // Expression expr;
        Indicator ind = null;
        // List<Token> list = lex.scanAll();
        // System.out.println(list);
        Parser parse = new Parser(lex);
        try {
			ind = parse.indicatore();
		} catch (TypeException e1) {
			e1.printStackTrace();
		}
        parse.match(Tag.EOF);
        System.out.println("resulting javascript: " + ind.javascript());
        
        long startTime = System.currentTimeMillis();
        int n = 100;
		for (int i = 0; i < n; i++) {
	        Object result;
			try {
				engine.eval(ind.javascript());
				Invocable invocable = (Invocable) engine;
				result = invocable.invokeFunction("sco1", 5);
				System.out.println("resulting value: " + result);
			} catch (ScriptException | NoSuchMethodException e) {
				e.printStackTrace();
			}
	        
	        
		}

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("elapsed time for each evaluation: " + (float) elapsedTime/(float) n + " msec");
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
