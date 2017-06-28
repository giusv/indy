package it.bancaditalia.indy.lexer;
public class Word extends Token {

   public String lexeme = "";
   public Word(String s, int tag) { super(tag); lexeme = s; }
   public String toString() { return lexeme; }

   public static final Word

      and = new Word( "and", Tag.AND ),  or = new Word( "or", Tag.OR ),
      eq  = new Word( "eq", Tag.EQ  ),  ne = new Word( "!=", Tag.NE ),
      le  = new Word( "<=", Tag.LE  ),  ge = new Word( ">=", Tag.GE ),

	  open = new Word("(", Tag.OPEN),
   	  close = new Word(")", Tag.CLOSE),
      minus  = new Word( "minus", Tag.MINUS ),
      True   = new Word( "true",  Tag.TRUE  ),
      False  = new Word( "false", Tag.FALSE ),
      temp   = new Word( "t",     Tag.TEMP  );
}
