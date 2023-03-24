package termsTest.operationSize;

/*
 * The answer must have balanced parentheses and not contain
 * 'if', 'null', 'for', 'while'
 *
 * This following code shows the Composite Pattern in its simplest form.
 * The Composite is the most fundamental OO Pattern.
 * Show that you can add a new operation "size".
 * Size counts how many 'Components' are present.
 */

import java.util.List;

interface Exp {
    String toS();

    int size();
}

// [???]

public class OperationSize {
    public static void main(String[] a) {
        Exp e1 = new Lit(0);
        Exp e2 = new Add(e1, e1);
        assert e1.size() == 1 : e1.size();
        assert e2.size() == 3 : e2.size();
        List<Exp> d = List.of(
            /*omitted*/);
        assert d.get(0).toS().equals("4") : d.get(0).toS();
        assert d.get(0).size() == 1 : d.get(0).size();
        assert d.get(1).toS().equals("(1+2)") : d.get(1).toS();
        assert d.get(1).size() == 3 : d.get(1).size();
        assert d.get(2).toS().equals("(1+(2+3))") : d.get(2).toS();
        assert d.get(2).size() == 5 : d.get(2).size();
        assert d.get(3).toS().equals("((1+2)+3)") : d.get(3).toS();
        assert d.get(3).size() == 5 : d.get(3).size();
        assert d.get(4).toS().equals("((1+2)+(3+50))") : d.get(4).toS();
        assert d.get(4).size() == 7 : d.get(4).size();
    }
}
