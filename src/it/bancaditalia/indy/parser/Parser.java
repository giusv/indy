package it.bancaditalia.indy.parser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import it.bancaditalia.indy.lexer.*;
import it.bancaditalia.indy.symbols.*;
import it.bancaditalia.indy.inter.*;

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

	void match(int t) throws IOException {
		if (look.tag == t) {
			System.out.println(look);
			move();
		}
		else
			error("syntax error: expecting " + t + ", found " + look.tag);
	}

	public Indicator indicator() throws IOException { // indicator -> (indicator
														// ID2 EXPR2)
		match(Tag.OPEN);
		match(Tag.INDICATOR);
		Token tok = look;
		match(Tag.ID);
		match(Tag.CLOSE);
		BoolExpr bexpr = bexpr();
		Id2 name = new Id2((Word) tok);
		return new Indicator(name, bexpr);
	}

	public Expr expr() throws IOException {
		Token tok = look;
		Expr ret = null;
		switch (tok.tag) {
		case Tag.ID:
			match(Tag.ID);
			ret = new Id2((Word) tok);
		}
		return ret;
	}

	public BoolExpr bexpr() throws IOException {
		System.out.println("entering bexpr()");
		match(Tag.OPEN);
		Token tok = look;
		BoolExpr ret = null;
		switch (tok.tag) {
		case Tag.AND:
			match(Tag.AND);
			List<BoolExpr> expressions = bexprs();
			ret = new And2(expressions);
		case Tag.EQ:
			match(Tag.EQ);
			Expr lhs = expr();
			Expr rhs = expr();
			ret = new Eq(lhs, rhs);
		}

		match(Tag.CLOSE);
		System.out.println("exiting bexpr() look " + look);
		return ret;
	}

	private List<BoolExpr> bexprs() throws IOException {
		List<BoolExpr> list = new ArrayList<BoolExpr>();
		while (look.tag != Tag.CLOSE) {
			list.add(bexpr());
		}
		return list;
	}

	// public void program() throws IOException { // program -> block
	// Stmt s = block();
	// int begin = s.newlabel(); int after = s.newlabel();
	// s.emitlabel(begin); s.gen(begin, after); s.emitlabel(after);
	// }
	//
	// Stmt block() throws IOException { // block -> { decls stmts }
	// match('{'); Env savedEnv = top; top = new Env(top);
	// decls(); Stmt s = stmts();
	// match('}'); top = savedEnv;
	// return s;
	// }
	//
	// void decls() throws IOException {
	//
	// while( look.tag == Tag.BASIC ) { // D -> type ID ;
	// Type p = type(); Token tok = look; match(Tag.ID); match(';');
	// Id id = new Id((Word)tok, p, used);
	// top.put( tok, id );
	// used = used + p.width;
	// }
	// }

	// Type type() throws IOException {
	//
	// Type p = (Type)look; // expect look.tag == Tag.BASIC
	// match(Tag.BASIC);
	// return p;
	// // if( look.tag != '[' ) return p; // T -> basic
	// // else return dims(p); // return array type
	// }

	// Type dims(Type p) throws IOException {
	// match('['); Token tok = look; match(Tag.NUM); match(']');
	// if( look.tag == '[' )
	// p = dims(p);
	// return new Array(((Num)tok).value, p);
	// }
	//
	// Stmt stmts() throws IOException {
	// if ( look.tag == '}' ) return Stmt.Null;
	// else return new Seq(stmt(), stmts());
	// }
	//
	// Stmt stmt() throws IOException {
	// Expr x; Stmt s1, s2;
	// Stmt savedStmt; // save enclosing loop for breaks
	//
	// switch( look.tag ) {
	//
	// case ';':
	// move();
	// return Stmt.Null;
	//
	// case Tag.IF:
	// match(Tag.IF); match(Tag.OPEN); x = bool(); match(Tag.CLOSE);
	// s1 = stmt();
	// if( look.tag != Tag.ELSE ) return new If(x, s1);
	// match(Tag.ELSE);
	// s2 = stmt();
	// return new Else(x, s1, s2);
	//
	// case Tag.WHILE:
	// While whilenode = new While();
	// savedStmt = Stmt.Enclosing; Stmt.Enclosing = whilenode;
	// match(Tag.WHILE); match(Tag.OPEN); x = bool(); match(Tag.CLOSE);
	// s1 = stmt();
	// whilenode.init(x, s1);
	// Stmt.Enclosing = savedStmt; // reset Stmt.Enclosing
	// return whilenode;
	//
	// case Tag.DO:
	// Do donode = new Do();
	// savedStmt = Stmt.Enclosing; Stmt.Enclosing = donode;
	// match(Tag.DO);
	// s1 = stmt();
	// match(Tag.WHILE); match(Tag.OPEN); x = bool(); match(Tag.CLOSE);
	// match(';');
	// donode.init(s1, x);
	// Stmt.Enclosing = savedStmt; // reset Stmt.Enclosing
	// return donode;
	//
	// case Tag.BREAK:
	// match(Tag.BREAK); match(';');
	// return new Break();
	//
	// case '{':
	// return block();
	//
	// default:
	// return assign();
	// }
	// }

	// Stmt assign() throws IOException {
	// Stmt stmt; Token t = look;
	// match(Tag.ID);
	// Id id = top.get(t);
	// if( id == null ) error(t.toString() + " undeclared");
	//
	// if( look.tag == '=' ) { // S -> id = E ;
	// move(); stmt = new Set(id, bool());
	// }
	// else { // S -> L = E ;
	// Access x = offset(id);
	// match('='); stmt = new SetElem(x, bool());
	// }
	// match(';');
	// return stmt;
	// }
	//
	// Expr bool() throws IOException {
	// Expr x = join();
	// while( look.tag == Tag.OR ) {
	// Token tok = look; move(); x = new Or(tok, x, join());
	// }
	// return x;
	// }
	//
	// Expr join() throws IOException {
	// Expr x = equality();
	// while( look.tag == Tag.AND ) {
	// Token tok = look; move(); x = new And(tok, x, equality());
	// }
	// return x;
	// }
	//
	// Expr equality() throws IOException {
	// Expr x = rel();
	// while( look.tag == Tag.EQ || look.tag == Tag.NE ) {
	// Token tok = look; move(); x = new Rel(tok, x, rel());
	// }
	// return x;
	// }
	//
	// Expr rel() throws IOException {
	// Expr x = expr();
	// switch( look.tag ) {
	// case '<': case Tag.LE: case Tag.GE: case '>':
	// Token tok = look; move(); return new Rel(tok, x, expr());
	// default:
	// return x;
	// }
	// }
	//
	// Expr expr() throws IOException {
	// Expr x = term();
	// while( look.tag == '+' || look.tag == '-' ) {
	// Token tok = look; move(); x = new Arith(tok, x, term());
	// }
	// return x;
	// }
	//
	// Expr term() throws IOException {
	// Expr x = unary();
	// while(look.tag == '*' || look.tag == '/' ) {
	// Token tok = look; move(); x = new Arith(tok, x, unary());
	// }
	// return x;
	// }
	//
	// Expr unary() throws IOException {
	// if( look.tag == '-' ) {
	// move(); return new Unary(Word.minus, unary());
	// }
	// else if( look.tag == '!' ) {
	// Token tok = look; move(); return new Not(tok, unary());
	// }
	// else return factor();
	// }
	//
	// Expr factor() throws IOException {
	// Expr x = null;
	// switch( look.tag ) {
	// case Tag.OPEN:
	// move(); x = bool(); match(Tag.CLOSE);
	// return x;
	// case Tag.NUM:
	// x = new Constant(look, Type.Int); move(); return x;
	// case Tag.REAL:
	// x = new Constant(look, Type.Float); move(); return x;
	// case Tag.TRUE:
	// x = Constant.True; move(); return x;
	// case Tag.FALSE:
	// x = Constant.False; move(); return x;
	// default:
	// error("syntax error");
	// return x;
	// case Tag.ID:
	// Id id = top.get(look);
	// if( id == null ) error(look.toString() + " undeclared");
	// move();
	// return id;
	// // if( look.tag != '[' ) return id;
	// // else return offset(id);
	// }
	// }

	// Access offset(Id a) throws IOException { // I -> [E] | [E] I
	// Expr i; Expr w; Expr t1, t2; Expr loc; // inherit id
	//
	// Type type = a.type;
	// match('['); i = bool(); match(']'); // first index, I -> [ E ]
	// type = ((Array)type).of;
	// w = new Constant(type.width);
	// t1 = new Arith(new Token('*'), i, w);
	// loc = t1;
	// while( look.tag == '[' ) { // multi-dimensional I -> [ E ] I
	// match('['); i = bool(); match(']');
	// type = ((Array)type).of;
	// w = new Constant(type.width);
	// t1 = new Arith(new Token('*'), i, w);
	// t2 = new Arith(new Token('+'), loc, t1);
	// loc = t2;
	// }
	//
	// return new Access(a, loc, type);
	// }
}
