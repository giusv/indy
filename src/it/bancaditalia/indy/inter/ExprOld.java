package it.bancaditalia.indy.inter;
import it.bancaditalia.indy.lexer.*; import it.bancaditalia.indy.symbols.*;

public class ExprOld extends Node {

   public Token op;
   public Type type;

   ExprOld(Token tok, Type p) { op = tok; type = p; }

   public ExprOld gen() { return this; }
   public ExprOld reduce() { return this; }

   public void jumping(int t, int f) { emitjumps(toString(), t, f); }

   public void emitjumps(String test, int t, int f) {
      if( t != 0 && f != 0 ) {
         emit("if " + test + " goto L" + t);
         emit("goto L" + f);
      }
      else if( t != 0 ) emit("if " + test + " goto L" + t);
      else if( f != 0 ) emit("iffalse " + test + " goto L" + f);
      else ; // nothing since both t and f fall through
   }
   public String toString() { return op.toString(); }
}
