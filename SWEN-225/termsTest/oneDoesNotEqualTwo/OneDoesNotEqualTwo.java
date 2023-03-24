package termsTest.oneDoesNotEqualTwo;

/*
 The answer must have balanced parentheses.
 You can not use "if","null","for","while"
*/

class Goku {
    public int powerUp() {
        return 1;
    }
}

public class OneDoesNotEqualTwo {
    public static void main(String[] args) {
        Goku g = (/* [???] */);
        assert g.powerUp() == 2 : "not enough powa!";
    }
}
