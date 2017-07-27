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

	public Expression espressione() throws IOException, TypeException {
		switch (look.tag) {
		case Tag.LEFT: {
			Expression node = termine();
			Expression syn = restoEspressione(node);
			return syn;
		}
		case Tag.ID: {
			Expression node = termine();
			Expression syn = restoEspressione(node);
			return syn;
		}
		case Tag.NUM: {
			Expression node = termine();
			Expression syn = restoEspressione(node);
			return syn;
		}
		default: {
			throw new Error(
					"Error in production for ESPRESSIONE: expecting LEFT, ID, NUM, "
							+ "found " + look);
		}
		}
	}

	public Expression restoEspressione(Expression expr) throws IOException,
			TypeException {
		switch (look.tag) {
		case Tag.OR: {
			return expr;
		}
		case Tag.RIGHT: {
			return expr;
		}
		case Tag.ALLORA: {
			return expr;
		}
		case Tag.ALTRIMENTI: {
			return expr;
		}
		case Tag.EOF: {
			return expr;
		}
		case Tag.IN: {
			return expr;
		}
		case Tag.COMMA: {
			return expr;
		}
		case Tag.AND: {
			return expr;
		}
		case Tag.EQUAL: {
			return expr;
		}
		case Tag.MINUS: {
			match(Tag.MINUS);
			Expression node = termine();
			Expression syn = restoEspressione(new Minus(expr, node));
			return syn;
		}
		case Tag.PLUS: {
			match(Tag.PLUS);
			Expression node = termine();
			Expression syn = restoEspressione(new Plus(expr, node));
			return syn;
		}
		default: {
			throw new Error(
					"Error in production for RESTO-ESPRESSIONE: expecting OR, RIGHT, ALLORA, ALTRIMENTI, EOF, IN, COMMA, AND, EQUAL, MINUS, PLUS, "
							+ "found " + look);
		}
		}
	}

	public Expression termine() throws IOException, TypeException {
		switch (look.tag) {
		case Tag.NUM: {
			Expression node = fattore();
			Expression syn = restoTermine(node);
			return syn;
		}
		case Tag.ID: {
			Expression node = fattore();
			Expression syn = restoTermine(node);
			return syn;
		}
		case Tag.LEFT: {
			Expression node = fattore();
			Expression syn = restoTermine(node);
			return syn;
		}
		default: {
			throw new Error(
					"Error in production for TERMINE: expecting NUM, ID, LEFT, "
							+ "found " + look);
		}
		}
	}

	public Expression restoTermine(Expression expr) throws IOException,
			TypeException {
		switch (look.tag) {
		case Tag.EQUAL: {
			return expr;
		}
		case Tag.AND: {
			return expr;
		}
		case Tag.COMMA: {
			return expr;
		}
		case Tag.IN: {
			return expr;
		}
		case Tag.EOF: {
			return expr;
		}
		case Tag.ALTRIMENTI: {
			return expr;
		}
		case Tag.ALLORA: {
			return expr;
		}
		case Tag.RIGHT: {
			return expr;
		}
		case Tag.OR: {
			return expr;
		}
		case Tag.PLUS: {
			return expr;
		}
		case Tag.MINUS: {
			return expr;
		}
		case Tag.DIVIDE: {
			match(Tag.DIVIDE);
			Expression node = fattore();
			Expression syn = restoTermine(new Divide(expr, node));
			return syn;
		}
		case Tag.TIMES: {
			match(Tag.TIMES);
			Expression node = fattore();
			Expression syn = restoTermine(new Times(expr, node));
			return syn;
		}
		default: {
			throw new Error(
					"Error in production for RESTO-TERMINE: expecting EQUAL, AND, COMMA, IN, EOF, ALTRIMENTI, ALLORA, RIGHT, OR, PLUS, MINUS, DIVIDE, TIMES, "
							+ "found " + look);
		}
		}
	}

	public Expression fattore() throws IOException, TypeException {
		switch (look.tag) {
		case Tag.ID: {
			Expression node = chiamataId();
			return node;
		}
		case Tag.NUM: {
			NumericConstant node = new NumericConstant(((Num) look).value);
			match(Tag.NUM);
			return node;
		}
		case Tag.LEFT: {
			match(Tag.LEFT);
			Expression node = espressioneBooleana();
			match(Tag.RIGHT);
			return node;
		}
		default: {
			throw new Error(
					"Error in production for FATTORE: expecting ID, NUM, LEFT, "
							+ "found " + look);
		}
		}
	}

	public Expression chiamataId() throws IOException, TypeException {
		switch (look.tag) {
		case Tag.ID: {
			Expression node = ((Expression) top.get(((Word) look).lexeme));
			if (node == null) {
				error(look.toString() + " undeclared");
			}
			match(Tag.ID);
			Expression syn = restoChiamataId(node);
			return syn;
		}
		default: {
			throw new Error(
					"Error in production for CHIAMATA-ID: expecting ID, "
							+ "found " + look);
		}
		}
	}

	public Expression restoChiamataId(Expression expr) throws IOException,
			TypeException {
		switch (look.tag) {
		case Tag.LEFT: {
			match(Tag.LEFT);
			ArrayList<Expression> pars = invocazioneParametriFunzione();
			match(Tag.RIGHT);
			return new FunctionCall(((FunctionDeclaration) expr), pars);
		}
		case Tag.DIVIDE: {
			return expr;
		}
		case Tag.TIMES: {
			return expr;
		}
		case Tag.EQUAL: {
			return expr;
		}
		case Tag.AND: {
			return expr;
		}
		case Tag.COMMA: {
			return expr;
		}
		case Tag.IN: {
			return expr;
		}
		case Tag.EOF: {
			return expr;
		}
		case Tag.ALTRIMENTI: {
			return expr;
		}
		case Tag.ALLORA: {
			return expr;
		}
		case Tag.RIGHT: {
			return expr;
		}
		case Tag.OR: {
			return expr;
		}
		case Tag.PLUS: {
			return expr;
		}
		case Tag.MINUS: {
			return expr;
		}
		default: {
			throw new Error(
					"Error in production for RESTO-CHIAMATA-ID: expecting LEFT, DIVIDE, TIMES, EQUAL, AND, COMMA, IN, EOF, ALTRIMENTI, ALLORA, RIGHT, OR, PLUS, MINUS, "
							+ "found " + look);
		}
		}
	}

	public Expression sia() throws IOException, TypeException {
		switch (look.tag) {
		case Tag.LET: {
			match(Tag.LET);
			Env saved = top;
			top = new Env(top);
			ArrayList<Binding> binds = legami();
			match(Tag.IN);
			Expression node = espressioneBooleana();
			top = saved;
			return new Let(binds, node);
		}
		default: {
			throw new Error("Error in production for SIA: expecting LET, "
					+ "found " + look);
		}
		}
	}

	public Expression se() throws IOException, TypeException {
		switch (look.tag) {
		case Tag.SE: {
			match(Tag.SE);
			Expression expr = espressioneBooleana();
			match(Tag.ALLORA);
			Expression then = espressioneBooleana();
			match(Tag.ALTRIMENTI);
			Expression otherwise = espressioneBooleana();
			return new If(((BooleanExpression) expr), then, otherwise);
		}
		default: {
			throw new Error("Error in production for SE: expecting SE, "
					+ "found " + look);
		}
		}
	}

	public ArrayList<Identifier> dichiarazioneParametriFunzione()
			throws IOException, TypeException {
		switch (look.tag) {
		case Tag.ID: {
			Identifier par = new Identifier(((Word) look));
			match(Tag.ID);
			match(Tag.COLON);
			Type type = tipo();
			top.put(par.getId().lexeme, new Identifier(par, type));
			ArrayList<Identifier> pars = restoDichiarazioneParametriFunzione();
			return ((ArrayList<Identifier>) ListUtils.cons(par, pars));
		}
		default: {
			throw new Error(
					"Error in production for DICHIARAZIONE-PARAMETRI-FUNZIONE: expecting ID, "
							+ "found " + look);
		}
		}
	}

	public ArrayList<Identifier> restoDichiarazioneParametriFunzione()
			throws IOException, TypeException {
		switch (look.tag) {
		case Tag.COMMA: {
			match(Tag.COMMA);
			Identifier par = new Identifier(((Word) look));
			match(Tag.ID);
			top.put(par.getId().lexeme, par);
			ArrayList<Identifier> pars = restoDichiarazioneParametriFunzione();
			return ((ArrayList<Identifier>) ListUtils.cons(par, pars));
		}
		case Tag.RIGHT: {
			return new ArrayList<Identifier>();
		}
		default: {
			throw new Error(
					"Error in production for RESTO-DICHIARAZIONE-PARAMETRI-FUNZIONE: expecting COMMA, RIGHT, "
							+ "found " + look);
		}
		}
	}

	public ArrayList<Expression> invocazioneParametriFunzione()
			throws IOException, TypeException {
		switch (look.tag) {
		case Tag.SE: {
			Expression par = espressioneBooleana();
			ArrayList<Expression> pars = restoInvocazioneParametriFunzione();
			return ((ArrayList<Expression>) ListUtils.cons(par, pars));
		}
		case Tag.TRUE: {
			Expression par = espressioneBooleana();
			ArrayList<Expression> pars = restoInvocazioneParametriFunzione();
			return ((ArrayList<Expression>) ListUtils.cons(par, pars));
		}
		case Tag.LEFT: {
			Expression par = espressioneBooleana();
			ArrayList<Expression> pars = restoInvocazioneParametriFunzione();
			return ((ArrayList<Expression>) ListUtils.cons(par, pars));
		}
		case Tag.ID: {
			Expression par = espressioneBooleana();
			ArrayList<Expression> pars = restoInvocazioneParametriFunzione();
			return ((ArrayList<Expression>) ListUtils.cons(par, pars));
		}
		case Tag.NUM: {
			Expression par = espressioneBooleana();
			ArrayList<Expression> pars = restoInvocazioneParametriFunzione();
			return ((ArrayList<Expression>) ListUtils.cons(par, pars));
		}
		case Tag.FALSE: {
			Expression par = espressioneBooleana();
			ArrayList<Expression> pars = restoInvocazioneParametriFunzione();
			return ((ArrayList<Expression>) ListUtils.cons(par, pars));
		}
		case Tag.NOT: {
			Expression par = espressioneBooleana();
			ArrayList<Expression> pars = restoInvocazioneParametriFunzione();
			return ((ArrayList<Expression>) ListUtils.cons(par, pars));
		}
		case Tag.LET: {
			Expression par = espressioneBooleana();
			ArrayList<Expression> pars = restoInvocazioneParametriFunzione();
			return ((ArrayList<Expression>) ListUtils.cons(par, pars));
		}
		default: {
			throw new Error(
					"Error in production for INVOCAZIONE-PARAMETRI-FUNZIONE: expecting SE, TRUE, LEFT, ID, NUM, FALSE, NOT, LET, "
							+ "found " + look);
		}
		}
	}

	public ArrayList<Expression> restoInvocazioneParametriFunzione()
			throws IOException, TypeException {
		switch (look.tag) {
		case Tag.COMMA: {
			match(Tag.COMMA);
			Expression par = espressioneBooleana();
			ArrayList<Expression> pars = restoInvocazioneParametriFunzione();
			return ((ArrayList<Expression>) ListUtils.cons(par, pars));
		}
		case Tag.RIGHT: {
			return new ArrayList<Expression>();
		}
		default: {
			throw new Error(
					"Error in production for RESTO-INVOCAZIONE-PARAMETRI-FUNZIONE: expecting COMMA, RIGHT, "
							+ "found " + look);
		}
		}
	}

	public Type tipo() throws IOException, TypeException {
		switch (look.tag) {
		case Tag.NUMBER: {
			match(Tag.NUMBER);
			return Type.NUMBER;
		}
		default: {
			throw new Error("Error in production for TIPO: expecting NUMBER, "
					+ "found " + look);
		}
		}
	}

	public ArrayList<Binding> legami() throws IOException, TypeException {
		switch (look.tag) {
		case Tag.ID: {
			Binding bind = legame();
			ArrayList<Binding> binds = restoLegami();
			return ((ArrayList<Binding>) ListUtils.cons(bind, binds));
		}
		default: {
			throw new Error("Error in production for LEGAMI: expecting ID, "
					+ "found " + look);
		}
		}
	}

	public ArrayList<Binding> restoLegami() throws IOException, TypeException {
		switch (look.tag) {
		case Tag.COMMA: {
			match(Tag.COMMA);
			Binding bind = legame();
			ArrayList<Binding> binds = restoLegami();
			return ((ArrayList<Binding>) ListUtils.cons(bind, binds));
		}
		case Tag.IN: {
			return new ArrayList<Binding>();
		}
		case Tag.RIGHT: {
			return new ArrayList<Binding>();
		}
		default: {
			throw new Error(
					"Error in production for RESTO-LEGAMI: expecting COMMA, IN, RIGHT, "
							+ "found " + look);
		}
		}
	}

	public Binding legame() throws IOException, TypeException {
		switch (look.tag) {
		case Tag.ID: {
			Identifier id = new Identifier(((Word) look));
			match(Tag.ID);
			Expression node = restoLegame(id);
			return new Binding(id, node);
		}
		default: {
			throw new Error("Error in production for LEGAME: expecting ID, "
					+ "found " + look);
		}
		}
	}

	public Expression restoLegame(Identifier id) throws IOException,
			TypeException {
		switch (look.tag) {
		case Tag.ASSIGN: {
			match(Tag.ASSIGN);
			top.put(id.getId().lexeme, id);
			Expression node = espressioneBooleana();
			return node;
		}
		case Tag.LEFT: {
			match(Tag.LEFT);
			Env saved = top;
			top = new Env(top);
			ArrayList<Identifier> pars = dichiarazioneParametriFunzione();
			match(Tag.RIGHT);
			match(Tag.COLON);
			Type type = tipo();
			top.put(id.getId().lexeme, new FunctionDeclaration(id, type, pars));
			match(Tag.ASSIGN);
			Expression expr = espressioneBooleana();
			top = saved;
			top.put(id.getId().lexeme, new FunctionDeclaration(id, type, pars,
					expr));
			return new FunctionDeclaration(id, type, pars, expr);
		}
		default: {
			throw new Error(
					"Error in production for RESTO-LEGAME: expecting ASSIGN, LEFT, "
							+ "found " + look);
		}
		}
	}

	public Expression espressioneBooleana() throws IOException, TypeException {
		switch (look.tag) {
		case Tag.NOT: {
			Expression node = termineBooleano();
			Expression syn = restoEspressioneBooleana(node);
			return syn;
		}
		case Tag.FALSE: {
			Expression node = termineBooleano();
			Expression syn = restoEspressioneBooleana(node);
			return syn;
		}
		case Tag.NUM: {
			Expression node = termineBooleano();
			Expression syn = restoEspressioneBooleana(node);
			return syn;
		}
		case Tag.ID: {
			Expression node = termineBooleano();
			Expression syn = restoEspressioneBooleana(node);
			return syn;
		}
		case Tag.LEFT: {
			Expression node = termineBooleano();
			Expression syn = restoEspressioneBooleana(node);
			return syn;
		}
		case Tag.TRUE: {
			Expression node = termineBooleano();
			Expression syn = restoEspressioneBooleana(node);
			return syn;
		}
		case Tag.SE: {
			Expression node = se();
			return node;
		}
		case Tag.LET: {
			Expression node = sia();
			return node;
		}
		default: {
			throw new Error(
					"Error in production for ESPRESSIONE-BOOLEANA: expecting NOT, FALSE, NUM, ID, LEFT, TRUE, SE, LET, "
							+ "found " + look);
		}
		}
	}

	public Expression restoEspressioneBooleana(Expression expr)
			throws IOException, TypeException {
		switch (look.tag) {
		case Tag.RIGHT: {
			return expr;
		}
		case Tag.ALLORA: {
			return expr;
		}
		case Tag.ALTRIMENTI: {
			return expr;
		}
		case Tag.EOF: {
			return expr;
		}
		case Tag.IN: {
			return expr;
		}
		case Tag.COMMA: {
			return expr;
		}
		case Tag.OR: {
			match(Tag.OR);
			Expression node = termineBooleano();
			Expression syn = restoEspressioneBooleana(new Or(expr, node));
			return syn;
		}
		default: {
			throw new Error(
					"Error in production for RESTO-ESPRESSIONE-BOOLEANA: expecting RIGHT, ALLORA, ALTRIMENTI, EOF, IN, COMMA, OR, "
							+ "found " + look);
		}
		}
	}

	public Expression termineBooleano() throws IOException, TypeException {
		switch (look.tag) {
		case Tag.TRUE: {
			Expression node = fattoreBooleano();
			Expression syn = restoTermineBooleano(node);
			return syn;
		}
		case Tag.LEFT: {
			Expression node = fattoreBooleano();
			Expression syn = restoTermineBooleano(node);
			return syn;
		}
		case Tag.ID: {
			Expression node = fattoreBooleano();
			Expression syn = restoTermineBooleano(node);
			return syn;
		}
		case Tag.NUM: {
			Expression node = fattoreBooleano();
			Expression syn = restoTermineBooleano(node);
			return syn;
		}
		case Tag.FALSE: {
			Expression node = fattoreBooleano();
			Expression syn = restoTermineBooleano(node);
			return syn;
		}
		case Tag.NOT: {
			Expression node = fattoreBooleano();
			Expression syn = restoTermineBooleano(node);
			return syn;
		}
		default: {
			throw new Error(
					"Error in production for TERMINE-BOOLEANO: expecting TRUE, LEFT, ID, NUM, FALSE, NOT, "
							+ "found " + look);
		}
		}
	}

	public Expression restoTermineBooleano(Expression expr) throws IOException,
			TypeException {
		switch (look.tag) {
		case Tag.COMMA: {
			return expr;
		}
		case Tag.IN: {
			return expr;
		}
		case Tag.EOF: {
			return expr;
		}
		case Tag.ALTRIMENTI: {
			return expr;
		}
		case Tag.ALLORA: {
			return expr;
		}
		case Tag.RIGHT: {
			return expr;
		}
		case Tag.OR: {
			return expr;
		}
		case Tag.AND: {
			match(Tag.AND);
			Expression node = fattoreBooleano();
			Expression syn = restoTermineBooleano(new And(expr, node));
			return syn;
		}
		default: {
			throw new Error(
					"Error in production for RESTO-TERMINE-BOOLEANO: expecting COMMA, IN, EOF, ALTRIMENTI, ALLORA, RIGHT, OR, AND, "
							+ "found " + look);
		}
		}
	}

	public Expression fattoreBooleano() throws IOException, TypeException {
		switch (look.tag) {
		case Tag.LEFT: {
			Expression node = relazione();
			return node;
		}
		case Tag.ID: {
			Expression node = relazione();
			return node;
		}
		case Tag.NUM: {
			Expression node = relazione();
			return node;
		}
		case Tag.FALSE: {
			match(Tag.FALSE);
			return new BooleanConstant(false);
		}
		case Tag.TRUE: {
			match(Tag.TRUE);
			return new BooleanConstant(true);
		}
		case Tag.NOT: {
			match(Tag.NOT);
			Expression syn = fattoreBooleano();
			return new Not(syn);
		}
		default: {
			throw new Error(
					"Error in production for FATTORE-BOOLEANO: expecting LEFT, ID, NUM, FALSE, TRUE, NOT, "
							+ "found " + look);
		}
		}
	}

	public Expression relazione() throws IOException, TypeException {
		switch (look.tag) {
		case Tag.NUM: {
			Expression node = espressione();
			Expression syn = restoRelazione(node);
			return syn;
		}
		case Tag.ID: {
			Expression node = espressione();
			Expression syn = restoRelazione(node);
			return syn;
		}
		case Tag.LEFT: {
			Expression node = espressione();
			Expression syn = restoRelazione(node);
			return syn;
		}
		default: {
			throw new Error(
					"Error in production for RELAZIONE: expecting NUM, ID, LEFT, "
							+ "found " + look);
		}
		}
	}

	public Expression restoRelazione(Expression expr) throws IOException,
			TypeException {
		switch (look.tag) {
		case Tag.EQUAL: {
			match(Tag.EQUAL);
			Expression node = espressione();
			return new Relation(Relop.EQUAL, expr, node);
		}
		case Tag.AND: {
			return expr;
		}
		case Tag.COMMA: {
			return expr;
		}
		case Tag.IN: {
			return expr;
		}
		case Tag.EOF: {
			return expr;
		}
		case Tag.ALTRIMENTI: {
			return expr;
		}
		case Tag.ALLORA: {
			return expr;
		}
		case Tag.RIGHT: {
			return expr;
		}
		case Tag.OR: {
			return expr;
		}
		default: {
			throw new Error(
					"Error in production for RESTO-RELAZIONE: expecting EQUAL, AND, COMMA, IN, EOF, ALTRIMENTI, ALLORA, RIGHT, OR, "
							+ "found " + look);
		}
		}
	}

	public Indicator indicatore() throws IOException, TypeException {
		switch (look.tag) {
		case Tag.ID: {
			Env saved = top;
			top = new Env(top);
			Identifier id = new Identifier(((Word) look));
			match(Tag.ID);
			match(Tag.LEFT);
			ArrayList<Binding> params = legami();
			match(Tag.RIGHT);
			Expression expr = espressioneBooleana();
			top = saved;
			return new Indicator(id, params, expr);
		}
		default: {
			throw new Error(
					"Error in production for INDICATORE: expecting ID, "
							+ "found " + look);
		}
		}
	}
}
