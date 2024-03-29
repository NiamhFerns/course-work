package termsTest.missingBase;

/*
 * The answer must have balanced parentheses.
 * You can not use "if","null","for","while","class",";"
 * Complete the following decorator pattern.
 * Since both 'class' and ';' are forbidden, you will need to use
 * some interesting and very useful feature from modern Java.
 */

interface A {
    int m1();

    String m2();
}

// [???]

record C(A a) implements A {
    public int m1() {
        return a().m1() + 1;
    }

    public String m2() {
        return a().m2() + "!";
    }
}

public class MissingBase {
    public static void main(String[] args) {
        A b1 = new B(20, "Hi");
        A b2 = new B(100, "Hello");
        A c1 = new C(b1);
        A c2 = new C(b2);
        A c11 = new C(c1);
        A c22 = new C(c2);

        assert b1.m1() == 20 : "b1.m1=" + b1.m1();
        assert b1.m2().equals("Hi") : "b1.m2=" + b1.m2();
        assert c1.m1() == 21 : "c1.m1=" + c1.m1();
        assert c1.m2().equals("Hi!") : "c1.m2=" + c1.m2();
        assert c11.m1() == 22 : "c11.m1=" + c11.m1();
        assert c11.m2().equals("Hi!!") : "c11.m2=" + c11.m2();

        assert b2.m1() == 100 : "b1.m1=" + b1.m1();
        assert b2.m2().equals("Hello") : "b1.m2=" + b1.m2();
        assert c2.m1() == 101 : "c1.m1=" + c1.m1();
        assert c2.m2().equals("Hello!") : "c1.m2=" + c1.m2();
        assert c22.m1() == 102 : "c11.m1=" + c11.m1();
        assert c22.m2().equals("Hello!!") : "c11.m2=" + c11.m2();
    }
}
