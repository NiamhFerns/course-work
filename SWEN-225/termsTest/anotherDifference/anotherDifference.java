package termsTest.anotherDifference;

/*
 * The answer must have balanced parentheses.
 * You can not use "if","null","for","while"
 * Do you understand the semantic of 'finally'?
 */

public class anotherDifference {

    public static int version1() {
        try {
            if (true) {
                // [???]
            }
        } catch (Error e) {
            return 2;
        } finally {
            return 2;
        }
    }

    public static int version2() {
        try {
            if (true) {
                // [???]
            }
        } catch (Error e) {
            return 2;
        }
        return 2; // No finally here!
    }

    public static void main(String[] arg) {
        assert (version1() != version2()) : "assertion:" + version1() + "!=" + version2();
    }
}
