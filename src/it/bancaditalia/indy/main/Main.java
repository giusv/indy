package it.bancaditalia.indy.main;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import it.bancaditalia.indy.inter.*;
import it.bancaditalia.indy.lexer.*;
import it.bancaditalia.indy.parser.*;
import it.bancaditalia.indy.symbols.Type;
import it.bancaditalia.indy.utils.ListUtils;
import it.bancaditalia.indy.utils.MapUtils;

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
//		String input = "sco1 (v:veicolo) (comparatore(x : numero, y : numero) : logico = x == y) "
//				+ "		let b = 2,"
//				+ "		    trans(v : veicolo) : numero = 1,"
//				+ "         fact(x : numero) : numero = se x == 0 allora 1 altrimenti x*fact(x-1)"
//				+ "		in trans2(v) == 1";
////
//		String sco1 = "sco1 (sogg: soggetto) (mesi: numero, occorrenze: numero) "
//				+ "	   		let sin = sinistri(coinvolto(sogg,sin))"
//				+ "	   		in cluster(sin,mesi) > occorrenze";

		String input = "sco1 (soggetto) (inizio: numero = 1, mesi: numero = 2, occorrenze: numero = 1) "
				+ "	   		let sin = sinistri(beneficiario(soggetto, sinistro) && !danneggiato(soggetto, sinistro))"
				+ "	   		in cluster(sin,mesi) == occorrenze";

//		String cust2 = "cust2 (sogg: soggetto) (occorrenze: numero, soglia: numero)"
//				+ "			 sia n = conta(sinistri dove beneficiario(sogg, sinistro) & (!danneggiato(soggetto, sinistro))"
//				+ "				 m = conta(sinistri dove beneficiario(sogg, sinistro) & (!danneggiato(soggetto, sinistro) & score(sinistro) == alto)";

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
		Parser parser = new Parser(lex);

		ArrayList<StdLibEntry> stdlib = new ArrayList<StdLibEntry>();
		stdlib.add(new StdLibEntry(
				"factorial",
				Type.NUMBER,
				ListUtils.cons(new Identifier(new Identifier(new Word("x",
						Tag.ID)), Type.NUMBER), new ArrayList<Identifier>()),
				"function factorial(n) {if (n === 0) {return 1;}  return n * factorial(n - 1);}"));

		stdlib.add(new StdLibEntry("trans2", Type.NUMBER, ListUtils.cons(
				new Identifier(new Identifier(new Word("v", Tag.ID)),
						Type.VEICOLO), new ArrayList<Identifier>()),
				"function trans2(v) {return 1;}"));

		stdlib.add(new StdLibEntry("dataaccadimento", Type.NUMBER, 
					ListUtils.cons(new Identifier(new Identifier(new Word("sinistro", Tag.ID)), Type.STRING),
						new ArrayList<Identifier>()),
				"function dataaccadimento(sinistro) {return \"data_accadimento\"}"));
		
		stdlib.add(new StdLibEntry("beneficiario", Type.BOOLEAN, 
				ListUtils.cons(new Identifier(new Identifier(new Word("soggetto", Tag.ID)), Type.SOGGETTO),
				ListUtils.cons(new Identifier(new Identifier(new Word("sinistro", Tag.ID)), Type.STRING),
					new ArrayList<Identifier>())),
			"function beneficiario(soggetto,sinistro) {return \"(D_FLG_BENEFICIARIO = TRUE)\"}"));
		
		stdlib.add(new StdLibEntry("danneggiato", Type.BOOLEAN, 
				ListUtils.cons(new Identifier(new Identifier(new Word("soggetto", Tag.ID)), Type.SOGGETTO),
				ListUtils.cons(new Identifier(new Identifier(new Word("sinistro", Tag.ID)), Type.STRING),
					new ArrayList<Identifier>())),
			"function danneggiato(soggetto,sinistro) {return \"(D_FLG_DANNEGGIATO = TRUE)\"}"));
		
		stdlib.add(new StdLibEntry("cluster", Type.NUMBER, 
					ListUtils.cons(new Identifier(new Identifier(new Word("sinistri", Tag.ID)),Type.QUERY), 
					ListUtils.cons(new Identifier(new Identifier(new Word("mesi", Tag.ID)), Type.NUMBER),
						new ArrayList<Identifier>())),
				"function cluster(sinistri,mesi) {return 1;}"));
		
		stdlib.add(new StdLibEntry("querySinistri", Type.NUMBER,
				ListUtils.cons(new Identifier(new Word("table", Tag.ID)),
				ListUtils.cons(new Identifier(new Identifier(new Word("query", Tag.ID)),Type.STRING),
							new ArrayList<Identifier>())),
				"function querySinistri(input,query) {print(\"SELECT * FROM SINISTRI WHERE ID_SOGG = \" + input + \" AND \" + query); return 1;}"));
		
		stdlib.add(new StdLibEntry("queryVeicoli", Type.NUMBER,
				ListUtils.cons(new Identifier(new Word("table", Tag.ID)),
				ListUtils.cons(new Identifier(new Identifier(new Word("query", Tag.ID)),Type.STRING),
							new ArrayList<Identifier>())),
				"function queryVeicoli(input,query) {print(\"SELECT * FROM VEICOLI WHERE ID_TARGA = \" + input + \" AND \" + query); return 1;}"));
	
		stdlib.add(new StdLibEntry("equal", Type.STRING,
				ListUtils.cons(new Identifier(new Word("op1", Tag.ID)),
				ListUtils.cons(new Identifier(new Word("op2", Tag.ID)),
							new ArrayList<Identifier>())),
				"function equal(op1,op2) {return \"(\" + op1 + \" = \" + op2 + \")\";}"));

		stdlib.add(new StdLibEntry("and", Type.STRING,
				ListUtils.cons(new Identifier(new Word("op1", Tag.ID)),
				ListUtils.cons(new Identifier(new Word("op2", Tag.ID)),
							new ArrayList<Identifier>())),
				"function and(op1,op2) {return \"(\" + op1 + \" AND \" + op2 + \")\";}"));

		stdlib.add(new StdLibEntry("or", Type.STRING,
				ListUtils.cons(new Identifier(new Word("op1", Tag.ID)),
				ListUtils.cons(new Identifier(new Word("op2", Tag.ID)),
							new ArrayList<Identifier>())),
				"function or(op1,op2) {return \"(\" + op1 + \" OR \" + op2 + \")\";}"));
		stdlib.add(new StdLibEntry("not", Type.STRING,
				ListUtils.cons(new Identifier(new Word("op1", Tag.ID)),
							new ArrayList<Identifier>()),
				"function not(op1) {return \"(NOT \" + op1 +\")\";}"));

		for (StdLibEntry entry : stdlib) {
			parser.getTop().put(
					entry.getName(),
					new FunctionDeclaration(new Identifier(new Word(entry
							.getName(), Tag.ID)), entry.getType(), entry
							.getParameters()));
		}

		try {
			ind = parser.indicatore();
		} catch (TypeException e1) {
			e1.printStackTrace();
		}
		parser.match(Tag.EOF);
		String script = stdlib.stream()
				.map((StdLibEntry e) -> e.getImplementation())
				.reduce("", String::concat)
				+ ind.javascript();
		System.out.println("resulting javascript: " + script);

		long startTime = System.currentTimeMillis();
		int n = 1;
		for (int i = 0; i < n; i++) {
			Object result;
			try {
				engine.eval(script);
				Invocable invocable = (Invocable) engine;
				result = invocable.invokeFunction("sco1", 5);
				System.out.println("resulting value: " + result);
			} catch (ScriptException | NoSuchMethodException e) {
				e.printStackTrace();
			}

		}

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("elapsed time for each evaluation: "
				+ (float) elapsedTime / (float) n + " msec");
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
