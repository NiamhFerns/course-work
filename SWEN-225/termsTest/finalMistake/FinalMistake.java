package termsTest.finalMistake;

/*
 * The answer must have balanced parentheses and can not use
 * "if","while","for","switch"
 */

public class FinalMistake {
    // [???]

    public static void main(String[] a) {
        Boolean x = meth();
        try {
            assert x;
        } catch (NullPointerException npe) {
            return;
        }
        assert false;
    }
}
