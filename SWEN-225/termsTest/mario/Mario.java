package termsTest.mario;

/*
 * The answer must have balanced parentheses.
 * You can not use "if","null","for","while"
 * Read the code below carefully.
 * What should you add to make it compile?
 */

class Player {
    private String name;

    Player(String name) {
        this.name = name;
    }

    final String name() {
        return name;
    }
}

// [???]

public class Exercise {
    public static void main(String[] args) {
        Player mario = new Mario();
        assert mario.name().equals("Mario") : "Assert failed with " + mario.name();
    }
}
