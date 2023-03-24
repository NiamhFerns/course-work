package termsTest.dataVariantDivision;

/*
 The answer must have balanced parentheses and not contain
 'if', 'null', 'for', 'while', 'class'

 This following code shows the Composite Pattern in its simplest form.
 The Composite is the most fundamental OO Pattern.
 Show that you can add a new kind of data variant.
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

// [???]

public class DataVariantDivision {
    public static void main(String[] a) {
        Exp e = new Division(null, null);
        List<Exp> d = List.of(
            /*omitted*/);
        assert d.get(0).toS().equals("4") : d.get(0).toS();
        assert d.get(1).toS().equals("(1/2)") : d.get(1).toS();
        assert d.get(2).toS().equals("(1/(2+3))") : d.get(2).toS();
        assert d.get(3).toS().equals("((1/2)+3)") : d.get(3).toS();
    }
}

/*
Object o=pp.list; //should not compile
 */
