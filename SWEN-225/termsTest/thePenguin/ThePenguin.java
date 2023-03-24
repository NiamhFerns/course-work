package termsTest.thePenguin;

import java.util.List;

interface Fish {
    int swim();
}

/*
 * The answer must have balanced parentheses
 * This question is best solved using the adapter pattern
 */
final class Penguin {
    private int distance = 0;

    public int swim() {
        return distance += 5;
    }

    public int distance() {
        return distance;
    }
}

record Acquarium(List<Fish> fs) {
    void allSwim() {
        for (var f : fs) {
            f.swim();
        }
    }
}

public class ThePenguin {
    public static void main(String[] args) {
        final Penguin bob = new Penguin();
        final var a = new Acquarium(List.of(
            // [???]
        ));
        assert bob.distance() == 0 : "assert1 " + bob.distance();
        a.allSwim();
        assert bob.distance() == 5 : "assert2 " + bob.distance();
        a.allSwim();
        assert bob.distance() == 10 : "assert3 " + bob.distance();
    }
}
