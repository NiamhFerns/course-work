package termsTest.streamAverage;

/*
 * The answer must have balanced parentheses and can not use
 * "if","while","for","switch","static","List","collect"
 *
 * Hints:
 *  you may have to use Stream.mapToInt
 *  you may have to use IntStream.sum
 */

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class StreamAverage {
    public static <A> int average(Stream<A> s, Function<A, Integer> f) {
        // [???]
    }

    public static void main(String[] a) {
        var l1 = List.of("Hi", " ", "World!");
        assert average(l1.stream(), s -> s.length()) == 3;
        var l2 = List.of(12, 30, 100, 100, 100);
        assert average(l2.stream(), i -> Math.max(i, 50)) == 80;
    }
}
