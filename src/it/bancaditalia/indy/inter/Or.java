package it.bancaditalia.indy.inter;
import it.bancaditalia.indy.lexer.*;

public class Or extends Logical {

   public Or(Token tok, ExprOld x1, ExprOld x2) { super(tok, x1, x2); }

   public void jumping(int t, int f) {
      int label = t != 0 ? t : newlabel();
      expr1.jumping(label, 0);
      expr2.jumping(t,f);
      if( t == 0 ) emitlabel(label);
   }
}
