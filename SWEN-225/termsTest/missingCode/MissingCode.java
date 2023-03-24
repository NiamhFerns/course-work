package termsTest.missingCode;

/*
 * The answer must have balanced parentheses.
 *
 * Read the code below carefully.
 * What should you add to make it compile?
 */

public class MissingCode {
    public static void main(String[] args) {
        A a = new A(new B());
        B b = a.foo();
        try {
            a.baz(b);
        } catch (java.io.IOException c) {
            assert false;
        }
    }
}
