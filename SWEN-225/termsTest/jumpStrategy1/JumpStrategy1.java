package termsTest.jumpStrategy1;

/*
 * The answer must have balanced parentheses.
 * You can not use "if","null","for","while"
 * Complete the following strategy pattern,
 * where the strategy object is stored as a field.
 */

interface JumpStrategy {
    JumpStrategy frontFlip = s -> s.strength + 1;
    JumpStrategy backFlip = s -> s.strength + 2;

    double jump(Athlete self);
}

class Athlete {
    final double strength = 5;
    private final JumpStrategy js;

    public Athlete(JumpStrategy js) {
        this.js = js;
    }

    // [???]
}

public class JumpStrategy1 {
    public static void main(String[] args) {
        Athlete bob = new Athlete(JumpStrategy.frontFlip);
        Athlete alice = new Athlete(JumpStrategy.backFlip);
        assert bob.jump() == 6d : "bob jump " + bob.jump();
        assert alice.jump() == 7d : "alice jump " + alice.jump();
    }
}
