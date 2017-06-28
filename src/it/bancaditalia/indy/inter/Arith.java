package it.bancaditalia.indy.inter;
import it.bancaditalia.indy.lexer.*; import it.bancaditalia.indy.symbols.*;

public class Arith extends Op {

   public ExprOld expr1, expr2;

   public Arith(Token tok, ExprOld x1, ExprOld x2)  {
      super(tok, null); expr1 = x1; expr2 = x2;
      type = Type.max(expr1.type, expr2.type);
      if (type == null ) error("type error");
   }

   public ExprOld gen() {
      return new Arith(op, expr1.reduce(), expr2.reduce());
   }

   public String toString() {
      return expr1.toString()+" "+op.toString()+" "+expr2.toString();
   }
}
