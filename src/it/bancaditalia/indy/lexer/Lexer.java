package it.bancaditalia.indy.lexer;

import java.io.*;
import java.util.*;

import it.bancaditalia.indy.symbols.*;

public class Lexer {
	public static int line = 1;
	char peek = ' ';
	Hashtable<String, Word> words = new Hashtable<String, Word>();
	private ByteArrayInputStream input;

	void reserve(Word w) {
		words.put(w.lexeme, w);
	}

	public Lexer(ByteArrayInputStream input) {
		this.input = input;
		reserve(new Word("se", Tag.SE));
		reserve(new Word("allora", Tag.ALLORA));
		reserve(new Word("altrimenti", Tag.ALTRIMENTI));
		reserve(new Word("while", Tag.WHILE));
		reserve(new Word("do", Tag.DO));
		reserve(new Word("break", Tag.BREAK));
		reserve(new Word("let", Tag.LET));
		reserve(new Word("in", Tag.IN));
		reserve(new Word("funzione", Tag.FUNZIONE));
		reserve(new Word("numero", Tag.NUMBER));
		reserve(new Word("logico", Tag.BOOLEAN));
		reserve(new Word("soggetto", Tag.SOGGETTO));
		reserve(new Word("veicolo", Tag.VEICOLO));

		reserve(Word.True);
		reserve(Word.False);

	}

	void readch() throws IOException {
		// peek = (char) System.in.read();
		int val = input.read();
		if (val == -1)
			peek = '$';
		else
			peek = (char) val;
	}

	boolean readch(char c) throws IOException {
		readch();
		if (peek != c)
			return false;
		peek = ' ';
		return true;
	}

	public List<Token> scanAll() throws IOException {
		List<Token> list = new ArrayList<Token>();
		Token token = null;
		do {
			token = scan();
			// System.out.println(token);
			list.add(token);
		} while (token != Word.eof);
		System.out.println(list);
		return list;
	}

	public Token scan() throws IOException {
		for (;; readch()) {
			if (peek == '$')
				return Word.eof;
			if (peek == ' ' || peek == '\t')
				continue;
			else if (peek == '\n')
				line = line + 1;
			else
				break;
		}
		switch (peek) {
		case ',':
			readch();
			return Word.comma;
		case ':':
			readch();
			return Word.colon;
		case '+':
			readch();
			return Word.plus;
		case '-':
			readch();
			return Word.minus;
		case '/':
			readch();
			return Word.divide;
		case '*':
			readch();
			return Word.times;
		case '(':
			readch();
			return Word.left;
		case ')':
			readch();
			return Word.right;
		case '&':
			if (readch('&'))
				return Word.and;
			else
				return new Token('&');
		case '|':
			if (readch('|'))
				return Word.or;
			else
				return new Token('|');
		case '=':
			if (readch('='))
				return Word.equal;
			else
				return Word.assign;
		case '!':
			if (readch('='))
				return Word.ne;
			else
				return Word.ne;
		case '<':
			if (readch('='))
				return Word.le;
			else
				return new Token('<');
		case '>':
			if (readch('='))
				return Word.ge;
			else
				return new Token('>');
		}
		if (Character.isDigit(peek)) {
			int v = 0;
			do {
				v = 10 * v + Character.digit(peek, 10);
				readch();
			} while (Character.isDigit(peek));
			if (peek != '.')
				return new Num(v);
			float x = v;
			float d = 10;
			for (;;) {
				readch();
				if (!Character.isDigit(peek))
					break;
				x = x + Character.digit(peek, 10) / d;
				d = d * 10;
			}
			return new Real(x);
		}
		if (Character.isLetter(peek)) {
			StringBuffer b = new StringBuffer();
			do {
				b.append(peek);
				readch();
			} while (Character.isLetterOrDigit(peek));
			String s = b.toString();
			Word w = (Word) words.get(s);
			if (w != null)
				return w;
			w = new Word(s, Tag.ID);
			words.put(s, w);
			return w;
		}
		Token tok = new Token(peek);
		peek = ' ';
		return tok;
	}
}