package it.bancaditalia.indy.lexer;
public class Word extends Token {

   public String lexeme = "";
   public Word(String s, int tag) { super(tag); lexeme = s; }
   public String toString() { return lexeme; }

   public static final Word

      and = new Word( "and", Tag.AND ),  or = new Word( "or", Tag.OR ), not = new Word( "not", Tag.NOT ),
      eq  = new Word( "eq", Tag.EQUAL  ),  ne = new Word( "!=", Tag.NE ),
      le  = new Word( "<=", Tag.LE  ),  ge = new Word( ">=", Tag.GE ),

	  left = new Word("(", Tag.LEFT),
   	  right = new Word(")", Tag.RIGHT),
   	  plus  = new Word( "+", Tag.PLUS ),
   	  minus  = new Word( "-", Tag.MINUS ),
   	  times = new Word( "*", Tag.TIMES),
   	  divide = new Word( "/", Tag.DIVIDE),

      True   = new Word( "true",  Tag.TRUE  ),
      False  = new Word( "false", Tag.FALSE ),
	  temp   = new Word( "t",     Tag.TEMP  ),
	  let = new Word( "let",     Tag.LET),
	  in = new Word( "in",     Tag.IN),
	  comma = new Word( ",",     Tag.COMMA),
	  assign = new Word( "assign",     Tag.ASSIGN),
	  equal = new Word( "equal",     Tag.EQUAL),
	  colon = new Word( ":",     Tag.COLON),
      eof = new Word("$", Tag.EOF);
}
