package it.bancaditalia.indy.inter;
import it.bancaditalia.indy.lexer.*; import it.bancaditalia.indy.symbols.*;

public class Unary extends Op {

   public ExprOld expr;

   public Unary(Token tok, ExprOld x) {    // handles minus, for ! see Not
      super(tok, null);  expr = x;
      type = Type.max(Type.Int, expr.type);
      if (type == null ) error("type error");
   }

   public ExprOld gen() { return new Unary(op, expr.reduce()); }

   public String toString() { return op.toString()+" "+expr.toString(); }
}
