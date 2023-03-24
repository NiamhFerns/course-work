package termsTest.visitors1;

/*
 * The answer must have balanced parentheses and can not use
 * "if","while","for","switch","static"
 *
 * Use Visitor Pattern to solve this exercise.
 * In this exercise you have to collect all the 'Li' inside a 'Node'.
 * If you have a strong grasp on the visitor, you may be able to do this
 * question without having to read all the tests.
 */

import java.util.List;

interface Visitor<T> {
    T visitP(P e);

    T visitDiv(Div e);

    T visitA(A e);

    T visitUl(Ul e);

    T visitLi(Li e);
}

interface Node {
    <T> T accept(Visitor<T> v);
}

record P(String text) implements Node {
    public <T> T accept(Visitor<T> v) {
        return v.visitP(this);
    }
}

record Div(List<Node> ns) implements Node {
    public <T> T accept(Visitor<T> v) {
        return v.visitDiv(this);
    }
}

record A(String href, String text) implements Node {
    public <T> T accept(Visitor<T> v) {
        return v.visitA(this);
    }
}

record Ul(List<Li> lis) implements Node {
    public <T> T accept(Visitor<T> v) {
        return v.visitUl(this);
    }
}

record Li(Node node) implements Node {
    public <T> T accept(Visitor<T> v) {
        return v.visitLi(this);
    }
}

class PropagatorVisitor implements Visitor<Boolean> {
    public Boolean visitP(P e) {
        return false;
    }

    public Boolean visitDiv(Div e) {
        return e.ns().stream().anyMatch(ei -> ei.accept(this));
    }

    public Boolean visitA(A e) {
        return false;
    }

    public Boolean visitUl(Ul e) {
        return e.lis().stream().anyMatch(ei -> visitLi(ei));
    }

    public Boolean visitLi(Li e) {
        return e.node().accept(this);
    }
}

class ListLi extends PropagatorVisitor {
    // [???]
}

class ToHtml implements Visitor<String> {
    /* omitted */
}

public class Visitors1 {
    static void example1F() {
        Node n = new Div(List.of(new A("www.google.com", "Hello"), new P("World"), new P("Again")));
        String original = n.accept(new ToHtml());
        List<Li> lis = new ListLi().collect(n);
        String res = "" + lis.stream().map(e -> e.accept(new ToHtml())).toList();
        assert res.equals("[]") : "example1FFailed: " + res;
        assert original.equals("""
                                   <DIV>
                                   <A href="www.google.com">Hello</A>
                                   <P>World</P>
                                   <P>Again</P>
                                   </DIV>""") : "example1FFailedOriginal: " + original;
    }

    static void example2F() {
        Node n = new Ul(List.of(new Li(new P("Hello")), new Li(new P("World"))));
        String original = n.accept(new ToHtml());
        List<Li> lis = new ListLi().collect(n);
        String res = "" + lis.stream().map(e -> e.accept(new ToHtml())).toList();
        assert res.equals("[<LI><P>Hello</P></LI>, <LI><P>World</P></LI>]")
            : "example2FFailed: " + res;
        assert original.equals("""
                                   <UL>
                                   <LI><P>Hello</P></LI>
                                   <LI><P>World</P></LI>
                                   </UL>""") : "example2FFailedOriginal: " + original;
    }

    static void example3F() {
        Node n = new Ul(List.of(new Li(new P("Oh")), new Li(
            new Ul(List.of(new Li(new P("You")), new Li(new P("Again")))))));
        String original = n.accept(new ToHtml());
        List<Li> lis = new ListLi().collect(n);
        String res = "" + lis.stream().map(e -> e.accept(new ToHtml())).toList();
        assert res.equals("""
                              [<LI><P>Oh</P></LI>, <LI><UL>
                              <LI><P>You</P></LI>
                              <LI><P>Again</P></LI>
                              </UL></LI>, <LI><P>You</P></LI>, <LI><P>Again</P></LI>]""")
            : "example3FFailed: " + res;
        assert original.equals("""
                                   <UL>
                                   <LI><P>Oh</P></LI>
                                   <LI><UL>
                                   <LI><P>You</P></LI>
                                   <LI><P>Again</P></LI>
                                   </UL></LI>
                                   </UL>""") : "example3FFailedOriginal: " + original;
    }

    public static void main(String[] a) {
        example1F();
        example2F();
        example3F();
    }
}
