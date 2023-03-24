package termsTest.visitors2;

/*
 * The answer must have balanced parentheses and can not use
 * "if","while","for","switch","static"
 *
 * Use Visitor Pattern to solve this exercise.
 * In this exercise you have to extend Clone visitor to use a function to map the text inside P
 * tags into something else, and then back into a string.
 * If you have a strong grasp on the visitor, you may be able to do this
 * question without having to read all the tests.
 */

import java.util.List;
import java.util.function.Function;

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

class CloneVisitor implements Visitor<Node> {
    public Node visitP(P e) {
        return e;
    }

    public Div visitDiv(Div e) {
        var ns = e.ns().stream().map(ei -> ei.accept(this)).toList();
        return new Div(ns);
    }

    public A visitA(A e) {
        return e;
    }

    public Node visitUl(Ul e) {
        var lis = e.lis().stream().map(ei -> visitLi(ei)).toList();
        return new Ul(lis);
    }

    public Li visitLi(Li e) {
        return new Li(e.node().accept(this));
    }
}

// [???]

class ToHtml implements Visitor<String> {
    /*omitted*/
}

public class Visitors2 {
    static void example1G() {
        Node n = new Div(List.of(new A("www.google.com", "Hello"), new P("World"), new P("Again")));
        String original = n.accept(new ToHtml());
        String res = n.accept(new Mapper<Integer>(e -> e.length())).accept(new ToHtml());
        assert res.equals("""
                              <DIV>
                              <A href="www.google.com">Hello</A>
                              <P>5</P>
                              <P>5</P>
                              </DIV>""") : "example1GFailed: " + res;
        assert original.equals("""
                                   <DIV>
                                   <A href="www.google.com">Hello</A>
                                   <P>World</P>
                                   <P>Again</P>
                                   </DIV>""") : "example1GFailedOriginal: " + original;
    }

    static void example2G() {
        Node n = new Ul(List.of(new Li(new P("Hello")), new Li(new P("World"))));
        String original = n.accept(new ToHtml());
        Function<String, String> upper = String::toUpperCase;
        String res = n.accept(new Mapper<String>(upper)).accept(new ToHtml());
        assert res.equals("""
                              <UL>
                              <LI><P>HELLO</P></LI>
                              <LI><P>WORLD</P></LI>
                              </UL>""") : "example2GFailed: " + res;
        assert original.equals("""
                                   <UL>
                                   <LI><P>Hello</P></LI>
                                   <LI><P>World</P></LI>
                                   </UL>""") : "example2GFailedOriginal: " + original;
    }

    static void example3G() {
        Node n = new Ul(List.of(new Li(new P("Oh")), new Li(
            new Ul(List.of(new Li(new P("You")), new Li(new P("Again")))))));
        String original = n.accept(new ToHtml());
        String res = n.accept(new Mapper<OldText>(OldText::new)).accept(new ToHtml());
        assert res.equals("""
                              <UL>
                              <LI><P>OldText[text=Oh]</P></LI>
                              <LI><UL>
                              <LI><P>OldText[text=You]</P></LI>
                              <LI><P>OldText[text=Again]</P></LI>
                              </UL></LI>
                              </UL>""") : "example3GFailed: " + res;
        assert original.equals("""
                                   <UL>
                                   <LI><P>Oh</P></LI>
                                   <LI><UL>
                                   <LI><P>You</P></LI>
                                   <LI><P>Again</P></LI>
                                   </UL></LI>
                                   </UL>""") : "example3GFailedOriginal: " + original;
    }

    public static void main(String[] a) {
        example1G();
        example2G();
        example3G();
    }

    record OldText(String text) {
    }
}
