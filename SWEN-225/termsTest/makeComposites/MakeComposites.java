package termsTest.makeComposites;

/*
 The answer must have balanced parentheses and not contain
 ';', '{', '[', '=','.'

 This question is about the Composite Pattern
 */

import java.util.List;

interface Exp {
    String toS();
}

record Lit(int val) implements Exp {
    public String toS() {
        return "" + val;
    }
}

record Add(Exp left, Exp right) implements Exp {
    public String toS() {
        return "(" + left.toS() + "+" + right.toS() + ")";
    }
}

public class MakeComposites {
    public static void main(String[] a) {
        List<Exp> data = List.of(
            // [???]
        );
        assert data.get(0).toS().equals("42") : data.get(0).toS();
        assert data.get(1).toS().equals("(2+3)") : data.get(1).toS();
        assert data.get(2).toS().equals("((4+5)+6)") : data.get(2).toS();
        assert data.get(3).toS().equals("(4+(5+6))") : data.get(3).toS();
    }
}

/*
Object o=pp.list; // should not compile
 */
