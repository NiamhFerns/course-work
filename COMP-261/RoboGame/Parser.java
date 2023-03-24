import java.util.*;
import java.util.regex.*;

/**
 * See assignment handout for the grammar.
 * You need to implement the parse(..) method and all the rest of the parser.
 * There are several methods provided for you:
 * - several utility methods to help with the parsing
 * See also the TestParser class for testing your code.
 */
public class Parser {


    // Useful Patterns

    static final Pattern NUM_PAT = Pattern.compile("-?[1-9][0-9]*|0");
    static final Pattern OPEN_PAREN_PAT = Pattern.compile("\\(");
    static final Pattern CLOSE_PAREN_PAT = Pattern.compile("\\)");
    static final Pattern OPEN_BRACE_PAT = Pattern.compile("\\{");
    static final Pattern CLOSE_BRACE_PAT = Pattern.compile("\\}");
    static final Pattern COMMA_PAT = Pattern.compile(",");
    static final Pattern TERMINATION_PAT = Pattern.compile(";");
    static final Pattern ACT_PAT = Pattern.compile("move|turnL|turnR|takeFuel|wait|turnAround|shieldOn|shieldOff|");
    static final Pattern LOOP_PAT = Pattern.compile("loop");
    static final Pattern IF_PAT = Pattern.compile("if");
    static final Pattern ELSE_PAT = Pattern.compile("else");
    static final Pattern WHILE_PAT = Pattern.compile("while");
    static final Pattern SENSOR_PAT = Pattern.compile("fuelLeft|oppLR|oppFB|numBarrels|barrelLR|barrelFB|wallDist");
    static final Pattern RELATION_PAT = Pattern.compile("gt|lt|eq|and|or|not");
    static final Pattern ARITHOP_PAT = Pattern.compile("add|sub|mul|div");

    //----------------------------------------------------------------
    /**
     * The top of the parser, which is handed a scanner containing
     * the text of the program to parse.
     * Returns the parse tree.
     */
    ProgramNode parse(Scanner s) {
        // Set the delimiter for the scanner.
        s.useDelimiter("\\s+|(?=[{}(),;])|(?<=[{}(),;])");
        if (!s.hasNext()) Parser.fail("No code provided for parser to parse.", s);
        return ProgramRoot.parse(s);
    }

    //----------------------------------------------------------------
    // utility methods for the parser
    // - fail(..) reports a failure and throws exception
    // - require(..) consumes and returns the next token as long as it matches the pattern
    // - requireInt(..) consumes and returns the next token as an int as long as it matches the pattern
    // - checkFor(..) peeks at the next token and only consumes it if it matches the pattern

    /**
     * Report a failure in the parser.
     */
    static void fail(String message, Scanner s) {
        String msg = message + "\n   @ ...";
        for (int i = 0; i < 5 && s.hasNext(); i++) {
            msg += " " + s.next();
        }
        throw new ParserFailureException(msg + "...");
    }

    /**
     * Requires that the next token matches a pattern if it matches, it consumes
     * and returns the token, if not, it throws an exception with an error
     * message
     */
    static String require(String p, String message, Scanner s) {
        if (s.hasNext(p)) {return s.next();}
        fail(message, s);
        return "";
    }

    static String require(Pattern p, String message, Scanner s) {
        if (s.hasNext(p)) {return s.next();}
        fail(message, s);
        return "";
    }

    /**
     * Requires that the next token matches a pattern (which should only match a
     * number) if it matches, it consumes and returns the token as an integer
     * if not, it throws an exception with an error message
     */
    static int requireInt(String p, String message, Scanner s) {
        if (s.hasNext(p) && s.hasNextInt()) {return s.nextInt();}
        fail(message, s);
        return -1;
    }

    static int requireInt(Pattern p, String message, Scanner s) {
        if (s.hasNext(p) && s.hasNextInt()) {return s.nextInt();}
        fail(message, s);
        return -1;
    }

    /**
     * Checks whether the next token in the scanner matches the specified
     * pattern, if so, consumes the token and return true, otherwise returns
     * false without consuming anything.
     */
    static boolean checkFor(String p, Scanner s) {
        if (s.hasNext(p)) {s.next(); return true;}
        return false;
    }

    static boolean checkFor(Pattern p, Scanner s) {
        if (s.hasNext(p)) {s.next(); return true;} 
        return false;
    }

}

// You could add the node classes here or as separate java files.
// (if added here, they must not be declared public or private)
// For example:
//  class BlockNode implements ProgramNode {.....
//     with fields, a toString() method and an execute() method
//

