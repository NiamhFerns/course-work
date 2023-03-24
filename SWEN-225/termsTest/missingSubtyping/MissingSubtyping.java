package termsTest.missingSubtyping;

/*
 * The answer must have balanced parentheses.
 *
 * Read the code below carefully.
 * What should you add to make it compile?
 */

// [???]

public class MissingSubtyping {
    public static void main(String[] args) {
        I1 a1 = new AA();
        I3 a2 = new AA();
        I2 b = new BB();
        assert a1.toString() != a2.toString();
        a1 = b;
    }
}
