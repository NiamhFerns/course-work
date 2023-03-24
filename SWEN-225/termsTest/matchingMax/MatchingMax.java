package termsTest.matchingMax;

/*
 * The answer must have balanced parentheses, and not containing if-for-while and other statement
 * based control flow operations. Also, no nulls, primitive arrays or reflection.
 * No access by fully qualified name to other Java libraries.
 * Note: you can use 'Math.max(_,_)'
 *
 * This code resembles the hard code shown at the start of the course.
 * Note how the type List.Cons represents a non-empty list at the type level.
 */

interface OnEmpty<R> {
    R of();
}

interface OnCons<E, R> {
    R of(List.Cons<E> es);
}

interface List<E> {
    @SuppressWarnings("unchecked")
    static <E> List<E> of() {
        return (List.Empty<E>) Empty.instance;
    }

    @SafeVarargs
    static <E> List.Cons<E> of(E e, E... es) {
        if (es.length == 0) {
            return new Cons<E>(e, of());
        }
        var res = new Cons<E>(es[es.length - 1], of());
        for (int i = es.length - 2; i >= 0; i--) {
            res = new Cons<E>(es[i], res);
        }
        return new Cons<E>(e, res);
    }

    <R> R match(OnEmpty<R> a, OnCons<E, R> b);

    final class Empty<E> implements List<E> {
        private static Empty<?> instance = new Empty<>();

        private Empty() {
        }

        public <R> R match(OnEmpty<R> a, OnCons<E, R> b) {
            return a.of();
        }
    }

    record Cons<E>(E e, List<E> es) implements List<E> {
        public <R> R match(OnEmpty<R> a, OnCons<E, R> b) {
            return b.of(this);
        }
    }
}

public class Exercise {
    static int max(List.Cons<Integer> l) {
        // [???]
    }

    public static void main(String[] arg) {
        var es2 = List.of(1, 2, 3, 4, 5);
        var es3 = List.of(1, 2, 7, 3, 4, 5);
        var es4 = List.of(1, 2, 3, 4, -5);
        assert max(es2) == 5;
        assert max(es3) == 7;
        assert max(es4) == 4;
        // max(List.of()) would not compile
    }
}
