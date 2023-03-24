package termsTest.explosion;

/*
 * The answer must have balanced parentheses.
 * You can not use "if","null","for","while"
 * Complete enum with methods.
 * We have seen the needed syntax in assignment 1!
 */

enum Explosive {
    // [???]
    abstract String kaboom();
}

public class Explosion {
    public static void main(String[] args) {
        assert Explosive.Tnt.kaboom().equals("Boom!")
            : "TNT Assert failed with " + Explosive.Tnt.kaboom();
        assert Explosive.Creeper.kaboom().equals("Not Again!")
            : "TNT Assert failed with " + Explosive.Creeper.kaboom();
    }
}
