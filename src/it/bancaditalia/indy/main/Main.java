package it.bancaditalia.indy.main;
import java.io.*; 

import it.bancaditalia.indy.inter.Indicator;
import it.bancaditalia.indy.lexer.*; 
import it.bancaditalia.indy.parser.*;

public class Main {

	public static void main(String[] args) throws IOException {
		Lexer lex = new Lexer();
		Parser parse = new Parser(lex);
		Indicator ind = parse.indicator();
		System.out.println(ind.toString());
	}
}
