package it.bancaditalia.indy.inter;
import it.bancaditalia.indy.lexer.*; import it.bancaditalia.indy.symbols.*;

public class Op extends ExprOld {

   public Op(Token tok, Type p)  { super(tok, p); }

   public ExprOld reduce() {
      ExprOld x = gen();
      Temp t = new Temp(type);
      emit( t.toString() + " = " + x.toString() );
      return t;
   }
}
