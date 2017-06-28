package it.bancaditalia.indy.inter;
import it.bancaditalia.indy.lexer.*;

public class AndOld extends Logical {

   public AndOld(Token tok, ExprOld x1, ExprOld x2) { super(tok, x1, x2); }

   public void jumping(int t, int f) {
      int label = f != 0 ? f : newlabel();
      expr1.jumping(0, label);
      expr2.jumping(t,f);
      if( f == 0 ) emitlabel(label);
   }
}
