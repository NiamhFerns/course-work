package termsTest.jumpStrategy2;

/*
 * The answer must have balanced parentheses.
 * You can not use "if","null","for","while"
 * Complete the following strategy pattern,
 * where the strategy object is passed as a parameter.
 */

interface JumpStrategy {
    JumpStrategy frontFlip = s -> s.strength + 1;
    JumpStrategy backFlip = s -> s.strength + 2;

    double jump(Athlete self);
}

class Athlete {
    final double strength = 5;

    public Athlete() {
    }

    // [???]
}

public class JumpStrategy2 {
    public static void main(String[] args) {
        Athlete bob = new Athlete();
        Athlete alice = new Athlete();
        assert bob.jump(JumpStrategy.frontFlip) == 6d : "bob jump " + bob.jump(
            JumpStrategy.frontFlip);
        assert alice.jump(JumpStrategy.backFlip) == 7d : "alice jump " + alice.jump(
            JumpStrategy.backFlip);
    }
}
