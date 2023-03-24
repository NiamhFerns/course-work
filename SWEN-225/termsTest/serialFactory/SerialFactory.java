package termsTest.serialFactory;

/*
 * The answer must have balanced parentheses and can not use
 * "if","while","for","switch","static"
 *
 * Use The factory method pattern to solve this exercise.
 */
// [???]

public class SerialFactory {
    public static void main(String[] a) {
        var c0 = new Factory();
        var c1 = new Factory();
        assert c0.make().number() == 100;
        assert c0.make().number() == 101;
        assert c0.make().number() == 102;
        assert c1.make().number() == 100;
        assert c1.make().number() == 101;
    }
}
