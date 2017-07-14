package it.bancaditalia.indy.parser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import it.bancaditalia.indy.lexer.*;
import it.bancaditalia.indy.symbols.*;
import it.bancaditalia.indy.inter.*;
import it.bancaditalia.indy.utils.*;

public class Parser {

	private Lexer lex; // lexical analyzer for this parser
	private Token look; // lookahead tag
	Env top = null; // current or top symbol table
	int used = 0; // storage used for declarations

	public Parser(Lexer lex) throws IOException {
		this.lex = lex;
		move();
	}

	void move() throws IOException {
		look = lex.scan();
	}

	void error(String s) {
		throw new Error("near line " + Lexer.line + ": " + s);
	}

	public void match(int t) throws IOException {
		if (look.tag == t) {
			System.out.println(look);
			move();
		} else
			error("syntax error: expecting " + t + ", found " + look.tag);
	}

	public Expression espressione() throws IOException
	{
	    switch(look.tag)
	    {
	        case Tag.LEFT:
	        {
	            Expression node = termine();
	            Expression syn = restoEspressione(node);
	            return syn;
	        }
	        case Tag.ID:
	        {
	            Expression node = termine();
	            Expression syn = restoEspressione(node);
	            return syn;
	        }
	        case Tag.NUM:
	        {
	            Expression node = termine();
	            Expression syn = restoEspressione(node);
	            return syn;
	        }
	        default:
	        {
	            throw new Error("Error in production for ESPRESSIONE: expecting LEFT, ID, NUM, " + "found " + look);
	        }
	    }
	}
	public Expression restoEspressione(Expression expr) throws IOException
	{
	    switch(look.tag)
	    {
	        case Tag.OR:
	        {
	            return expr;
	        }
	        case Tag.EOF:
	        {
	            return expr;
	        }
	        case Tag.AND:
	        {
	            return expr;
	        }
	        case Tag.EQUAL:
	        {
	            return expr;
	        }
	        case Tag.COMMA:
	        {
	            return expr;
	        }
	        case Tag.RIGHT:
	        {
	            return expr;
	        }
	        case Tag.IN:
	        {
	            return expr;
	        }
	        case Tag.PLUS:
	        {
	            match(Tag.PLUS);
	            Expression node = termine();
	            Expression syn = restoEspressione(new Plus(expr,node));
	            return syn;
	        }
	        default:
	        {
	            throw new Error("Error in production for RESTO-ESPRESSIONE: expecting OR, EOF, AND, EQUAL, COMMA, RIGHT, IN, PLUS, " + "found " + look);
	        }
	    }
	}
	public Expression termine() throws IOException
	{
	    switch(look.tag)
	    {
	        case Tag.NUM:
	        {
	            Expression node = ff();
	            Expression syn = tp(node);
	            return syn;
	        }
	        case Tag.ID:
	        {
	            Expression node = ff();
	            Expression syn = tp(node);
	            return syn;
	        }
	        case Tag.LEFT:
	        {
	            Expression node = ff();
	            Expression syn = tp(node);
	            return syn;
	        }
	        default:
	        {
	            throw new Error("Error in production for TERMINE: expecting NUM, ID, LEFT, " + "found " + look);
	        }
	    }
	}
	public Expression tp(Expression expr) throws IOException
	{
	    switch(look.tag)
	    {
	        case Tag.IN:
	        {
	            return expr;
	        }
	        case Tag.RIGHT:
	        {
	            return expr;
	        }
	        case Tag.COMMA:
	        {
	            return expr;
	        }
	        case Tag.EQUAL:
	        {
	            return expr;
	        }
	        case Tag.AND:
	        {
	            return expr;
	        }
	        case Tag.EOF:
	        {
	            return expr;
	        }
	        case Tag.OR:
	        {
	            return expr;
	        }
	        case Tag.PLUS:
	        {
	            return expr;
	        }
	        case Tag.TIMES:
	        {
	            match(Tag.TIMES);
	            Expression node = ff();
	            Expression syn = tp(new Times(expr,node));
	            return syn;
	        }
	        default:
	        {
	            throw new Error("Error in production for TP: expecting IN, RIGHT, COMMA, EQUAL, AND, EOF, OR, PLUS, TIMES, " + "found " + look);
	        }
	    }
	}
	public Expression ff() throws IOException
	{
	    switch(look.tag)
	    {
	        case Tag.ID:
	        {
	            Expression node = ((Expression) top.get(((Word) look).lexeme));
	            if(node == null)
	            {
	                error(look.toString() + " undeclared");
	            }
	            match(Tag.ID);
	            return node;
	        }
	        case Tag.NUM:
	        {
	            NumericConstant node = new NumericConstant(((Num) look).value);
	            match(Tag.NUM);
	            return node;
	        }
	        case Tag.LEFT:
	        {
	            match(Tag.LEFT);
	            Expression node = espressioneBooleana();
	            match(Tag.RIGHT);
	            return node;
	        }
	        default:
	        {
	            throw new Error("Error in production for FF: expecting ID, NUM, LEFT, " + "found " + look);
	        }
	    }
	}
	public Expression indyLet() throws IOException
	{
	    switch(look.tag)
	    {
	        case Tag.LET:
	        {
	            match(Tag.LET);
	            Env saved = top;
	            top = new Env(top);
	            ArrayList<Binding> binds = indyBinds();
	            match(Tag.IN);
	            Expression node = espressioneBooleana();
	            top = saved;
	            return new Let(binds,node);
	        }
	        default:
	        {
	            throw new Error("Error in production for INDY-LET: expecting LET, " + "found " + look);
	        }
	    }
	}
	public ArrayList<Binding> indyBinds() throws IOException
	{
	    switch(look.tag)
	    {
	        case Tag.ID:
	        {
	            Binding bind = indyBind();
	            ArrayList<Binding> binds = indyBindsRest();
	            return ((ArrayList<Binding>) ListUtils.cons(bind,binds));
	        }
	        default:
	        {
	            throw new Error("Error in production for INDY-BINDS: expecting ID, " + "found " + look);
	        }
	    }
	}
	public ArrayList<Binding> indyBindsRest() throws IOException
	{
	    switch(look.tag)
	    {
	        case Tag.COMMA:
	        {
	            match(Tag.COMMA);
	            Binding bind = indyBind();
	            ArrayList<Binding> binds = indyBindsRest();
	            return ((ArrayList<Binding>) ListUtils.cons(bind,binds));
	        }
	        case Tag.RIGHT:
	        {
	            return new ArrayList<Binding>();
	        }
	        case Tag.IN:
	        {
	            return new ArrayList<Binding>();
	        }
	        default:
	        {
	            throw new Error("Error in production for INDY-BINDS-REST: expecting COMMA, RIGHT, IN, " + "found " + look);
	        }
	    }
	}
	public Binding indyBind() throws IOException
	{
	    switch(look.tag)
	    {
	        case Tag.ID:
	        {
	            Identifier id = new Identifier(((Word) look));
	            match(Tag.ID);
	            match(Tag.ASSIGN);
	            Expression node = espressione();
	            top.put(id.getId()
	                      .lexeme,id);
	            return new Binding(id,node);
	        }
	        default:
	        {
	            throw new Error("Error in production for INDY-BIND: expecting ID, " + "found " + look);
	        }
	    }
	}
	public Expression espressioneBooleana() throws IOException
	{
	    switch(look.tag)
	    {
	        case Tag.NOT:
	        {
	            Expression node = termineBooleano();
	            Expression syn = restoEspressioneBooleana(node);
	            return syn;
	        }
	        case Tag.FALSE:
	        {
	            Expression node = termineBooleano();
	            Expression syn = restoEspressioneBooleana(node);
	            return syn;
	        }
	        case Tag.NUM:
	        {
	            Expression node = termineBooleano();
	            Expression syn = restoEspressioneBooleana(node);
	            return syn;
	        }
	        case Tag.ID:
	        {
	            Expression node = termineBooleano();
	            Expression syn = restoEspressioneBooleana(node);
	            return syn;
	        }
	        case Tag.LEFT:
	        {
	            Expression node = termineBooleano();
	            Expression syn = restoEspressioneBooleana(node);
	            return syn;
	        }
	        case Tag.SINISTRI:
	        {
	            Expression node = termineBooleano();
	            Expression syn = restoEspressioneBooleana(node);
	            return syn;
	        }
	        case Tag.TRUE:
	        {
	            Expression node = termineBooleano();
	            Expression syn = restoEspressioneBooleana(node);
	            return syn;
	        }
	        case Tag.LET:
	        {
	            Expression node = indyLet();
	            return node;
	        }
	        default:
	        {
	            throw new Error("Error in production for ESPRESSIONE-BOOLEANA: expecting NOT, FALSE, NUM, ID, LEFT, SINISTRI, TRUE, LET, " + "found " + look);
	        }
	    }
	}
	public Expression restoEspressioneBooleana(Expression expr) throws IOException
	{
	    switch(look.tag)
	    {
	        case Tag.EOF:
	        {
	            return expr;
	        }
	        case Tag.RIGHT:
	        {
	            return expr;
	        }
	        case Tag.OR:
	        {
	            match(Tag.OR);
	            Expression node = termineBooleano();
	            Expression syn = restoEspressioneBooleana(new Or(expr,node));
	            return syn;
	        }
	        default:
	        {
	            throw new Error("Error in production for RESTO-ESPRESSIONE-BOOLEANA: expecting EOF, RIGHT, OR, " + "found " + look);
	        }
	    }
	}
	public Expression termineBooleano() throws IOException
	{
	    switch(look.tag)
	    {
	        case Tag.TRUE:
	        {
	            Expression node = fattoreBooleano();
	            Expression syn = restoTermineBooleano(node);
	            return syn;
	        }
	        case Tag.SINISTRI:
	        {
	            Expression node = fattoreBooleano();
	            Expression syn = restoTermineBooleano(node);
	            return syn;
	        }
	        case Tag.LEFT:
	        {
	            Expression node = fattoreBooleano();
	            Expression syn = restoTermineBooleano(node);
	            return syn;
	        }
	        case Tag.ID:
	        {
	            Expression node = fattoreBooleano();
	            Expression syn = restoTermineBooleano(node);
	            return syn;
	        }
	        case Tag.NUM:
	        {
	            Expression node = fattoreBooleano();
	            Expression syn = restoTermineBooleano(node);
	            return syn;
	        }
	        case Tag.FALSE:
	        {
	            Expression node = fattoreBooleano();
	            Expression syn = restoTermineBooleano(node);
	            return syn;
	        }
	        case Tag.NOT:
	        {
	            Expression node = fattoreBooleano();
	            Expression syn = restoTermineBooleano(node);
	            return syn;
	        }
	        default:
	        {
	            throw new Error("Error in production for TERMINE-BOOLEANO: expecting TRUE, SINISTRI, LEFT, ID, NUM, FALSE, NOT, " + "found " + look);
	        }
	    }
	}
	public Expression restoTermineBooleano(Expression expr) throws IOException
	{
	    switch(look.tag)
	    {
	        case Tag.RIGHT:
	        {
	            return expr;
	        }
	        case Tag.EOF:
	        {
	            return expr;
	        }
	        case Tag.OR:
	        {
	            return expr;
	        }
	        case Tag.AND:
	        {
	            match(Tag.AND);
	            Expression node = fattoreBooleano();
	            Expression syn = restoTermineBooleano(new And(expr,node));
	            return syn;
	        }
	        default:
	        {
	            throw new Error("Error in production for RESTO-TERMINE-BOOLEANO: expecting RIGHT, EOF, OR, AND, " + "found " + look);
	        }
	    }
	}
	public Expression fattoreBooleano() throws IOException
	{
	    switch(look.tag)
	    {
	        case Tag.SINISTRI:
	        {
	            Expression node = funzione();
	            return node;
	        }
	        case Tag.LEFT:
	        {
	            Expression node = relazione();
	            return node;
	        }
	        case Tag.ID:
	        {
	            Expression node = relazione();
	            return node;
	        }
	        case Tag.NUM:
	        {
	            Expression node = relazione();
	            return node;
	        }
	        case Tag.FALSE:
	        {
	            match(Tag.FALSE);
	            return new BooleanConstant(false);
	        }
	        case Tag.TRUE:
	        {
	            match(Tag.TRUE);
	            return new BooleanConstant(true);
	        }
	        case Tag.NOT:
	        {
	            match(Tag.NOT);
	            Expression syn = fattoreBooleano();
	            return new Not(syn);
	        }
	        default:
	        {
	            throw new Error("Error in production for FATTORE-BOOLEANO: expecting SINISTRI, LEFT, ID, NUM, FALSE, TRUE, NOT, " + "found " + look);
	        }
	    }
	}
	public Expression relazione() throws IOException
	{
	    switch(look.tag)
	    {
	        case Tag.NUM:
	        {
	            Expression node = espressione();
	            Expression syn = restoRelazione(node);
	            return syn;
	        }
	        case Tag.ID:
	        {
	            Expression node = espressione();
	            Expression syn = restoRelazione(node);
	            return syn;
	        }
	        case Tag.LEFT:
	        {
	            Expression node = espressione();
	            Expression syn = restoRelazione(node);
	            return syn;
	        }
	        default:
	        {
	            throw new Error("Error in production for RELAZIONE: expecting NUM, ID, LEFT, " + "found " + look);
	        }
	    }
	}
	public Expression restoRelazione(Expression expr) throws IOException
	{
	    switch(look.tag)
	    {
	        case Tag.EQUAL:
	        {
	            match(Tag.EQUAL);
	            Expression node = espressione();
	            return new Relation(Relop.EQUAL,expr,node);
	        }
	        case Tag.OR:
	        {
	            return expr;
	        }
	        case Tag.EOF:
	        {
	            return expr;
	        }
	        case Tag.RIGHT:
	        {
	            return expr;
	        }
	        case Tag.AND:
	        {
	            return expr;
	        }
	        default:
	        {
	            throw new Error("Error in production for RESTO-RELAZIONE: expecting EQUAL, OR, EOF, RIGHT, AND, " + "found " + look);
	        }
	    }
	}
	public Expression funzione() throws IOException
	{
	    switch(look.tag)
	    {
	        case Tag.SINISTRI:
	        {
	            match(Tag.SINISTRI);
	            match(Tag.LEFT);
	            Expression node = espressioneBooleana();
	            match(Tag.RIGHT);
	            return node;
	        }
	        default:
	        {
	            throw new Error("Error in production for FUNZIONE: expecting SINISTRI, " + "found " + look);
	        }
	    }
	}
	public Indicator indicatore() throws IOException
	{
	    switch(look.tag)
	    {
	        case Tag.ID:
	        {
	            Env saved = top;
	            top = new Env(top);
	            Identifier id = new Identifier(((Word) look));
	            match(Tag.ID);
	            match(Tag.LEFT);
	            ArrayList<Binding> params = indyBinds();
	            match(Tag.RIGHT);
	            Expression expr = espressioneBooleana();
	            top = saved;
	            return new Indicator(id,params,expr);
	        }
	        default:
	        {
	            throw new Error("Error in production for INDICATORE: expecting ID, " + "found " + look);
	        }
	    }
	}
}
