
import java.util.*;

public class LempelZiv {
    /**
     * Take uncompressed input as a text string, compress it, and return it as a
     * text string.
     */
    public static String compress(String input) {
        /**
         * PSEUDO CODE:
         *  cursor := 0
         *  windowSize := 100
         *.
         *  WHILE cursor < text.length:
         *      length := 1
         *      prevMatch := 0
         *      LOOP:
         *          match := stringMatch(
         *              text[cursor .. cursor + length],
         *              text[(cursor < windowSize ? 0 : cursor - windowSize) .. cursor]
         *          )
         *.
         *          IF match succeeded:
         *              prevMatch := match
         *              length += 1
         *          ELSE:
         *              output([a value for prevMatch, length - 1, text[cursor + length - 1]])
         *              cursor += length
         *              BREAK
         */

        StringBuilder encoded = new StringBuilder();

        var cursor = 0;
        var windowSize = 100;

        while (cursor < input.length()) {
            var length = 1;
            var prevMatch = 0;

            MATCH_LOOP:
            while (true) {
                // Don't know why this ternary is needed. Ask Fan-glue...
                var searchable = input.substring(cursor < windowSize ? 0 : cursor - windowSize, cursor);
                var match = searchable.indexOf(input.substring(cursor, cursor + length));

                if (match != -1) {
                    prevMatch = match;
                    length++;
                } else {
                    encoded.append("[" + "" + "|" + "" + "|" + "" + "]");
                    cursor += length;
                    break MATCH_LOOP;
                }
            }
        }

        return encoded.toString();
    }

    /**
     * Take compressed input as a text string, decompress it, and return it as a
     * text string.
     */
    public static String decompress(String compressed) {
        StringBuilder decoded = new StringBuilder();

        var tuples = compressed.split("]\\[");
        var cursor = 0;

        for (var tuple : tuples) {
            var tupleValues = tuple.substring(1, tuple.length() - 1).split("\\|");

            var offset = Integer.parseInt(tupleValues[0]);
            var length = Integer.parseInt(tupleValues[1]);
            var ch = tupleValues[2];

            if (!(length == 0 && offset == 0)) {
                decoded.append(decoded.substring(cursor - offset, length));
                cursor += length;
            }
            decoded.append(ch);
            cursor++;
        }

        return decoded.toString();
    }

    /**
     * The getInformation method is here for your convenience, you don't need to
     * fill it in if you don't want to. It is called on every run and its return
     * value is displayed on-screen. You can use this to print out any relevant
     * information from your compression.
     */
    public String getInformation() {
        return "";
    }
}
