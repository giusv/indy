package it.bancaditalia.indy.symbols;
public enum Type {

	NUMBER,
	BOOLEAN,
	STRING,
	VEICOLO,
	SOGGETTO,
	DATE;
//   public static boolean numeric(Type p) {
//      if (p == Type.Char || p == Type.Int || p == Type.Float) return true;
//      else return false;
//   }
//
//   public static Type max(Type p1, Type p2 ) {
//      if ( ! numeric(p1) || ! numeric(p2) ) return null;
//      else if ( p1 == Type.Float || p2 == Type.Float ) return Type.Float;
//      else if ( p1 == Type.Int   || p2 == Type.Int   ) return Type.Int;
//      else return Type.Char;
//   }
}
