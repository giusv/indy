package it.bancaditalia.indy.inter;
import it.bancaditalia.indy.symbols.*;

public class If extends Stmt {

   ExprOld expr; Stmt stmt;

   public If(ExprOld x, Stmt s) {
      expr = x;  stmt = s;
      if( expr.type != Type.Bool ) expr.error("boolean required in if");
   }

   public void gen(int b, int a) {
      int label = newlabel(); // label for the code for stmt
      expr.jumping(0, a);     // fall through on true, goto a on false
      emitlabel(label); stmt.gen(label, a);
   }
}
