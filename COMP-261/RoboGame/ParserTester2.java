import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import javax.swing.JFileChooser;


/**
 * Tests the parser:
 * Has GOOD (valid) and BAD (invalid) test cases
 * Each test case has the program and a brief description of what it is testing.
 * parserTester2 runs the parser on the test program, checking whether it
 * throws an exception, returns a parse tree, or returns null (shouldn't happen).
 * It prints the test program.
 * If the test case is valid and the parser returns a tree, or if
 *  the test case is invalid and the parser throws an exception, it prints "OK".
 * otherwise it prints "BAD"
 * If the parser returns a tree, then it prints out that program on one line.
 */
public class ParserTester2{

    /**
     * For testing a parser without requiring the world or the game:
     */
    public static void main(String[] args) {
        System.out.println("Testing parser");
        System.out.println("=======================");
        System.out.println("For each program in a collection of test programs,");
        System.out.println("it attempts to parse the program.");
        System.out.println("It reports the program and what the parser should do");
        System.out.println("and then reports whether the parser failed, generated a null tree, or succeeded.");
        System.out.println("It then prints the tree.");
        System.out.println("================================================================");
        Parser parser = new Parser();

        for (int stage=0; stage<4; stage++){
            System.out.println("\nTesting Parser on Stage "+stage+":\n");
            for (String[] test : programs[stage]){
                boolean valid = test[0]=="VALID";
                String program = test[1];
                String message = test[2];
                System.out.println("------\nParsing: "+ (valid?"valid: ":"INVALID: ")+program+ " ["+message+"]");
                try{
                    ProgramNode ast = parser.parse(new Scanner(program));
                    String printedForm = (ast!=null)?ast.toString().replaceAll("\\n", " "):"null tree";
                    if (valid){
                        if (ast!=null){System.out.println("OK, valid program: "+printedForm);}
                        else          {System.out.println("BAD, failed to generate tree for valid program");}
                    }
                    else {
                        if (ast!=null){System.out.println("BAD, program is invalid, parser gave: "+printedForm);}
                        else          {System.out.println("???, program is invalid, parser did not throw exception but did not build a tree");}

                    }
                }
                catch (ParserFailureException e) {
                    System.out.println("  parser exception: "+e.toString().replaceAll("\\n", " "));
                    if (valid) {System.out.println("BAD, threw exception for a valid program");}
                    else       {System.out.println("OK, parser correctly threw an exception");}
                }
                catch (Exception e) {
                    System.out.println("BAD, Parser broke with some other kind of exception: ");
                    e.printStackTrace(System.out);
                }
            }
            System.out.println("Done");
        }
    }




    private static final String[][][] programs = new String[][][]{
        {//STAGE 0
            {"VALID", "move;",  "move action"},
            {"VALID", "turnL;",  "turnL action"},
            {"VALID", "turnR;",  "turnR action"},
            {"VALID", "takeFuel;",  "takeFuel action"},
            {"VALID", "wait;",  "wait action"},
            {"VALID", "move; turnL; turnR; move; takeFuel; ",  "sequence of actions"},
            {"VALID", "loop{move ;}",  "loop with a Block with one action"},
            {"VALID", "loop{move; wait; turnL; turnR;}",  "loop with a Block with four actions"},
            {"VALID", "loop{move; loop{turnL;}}",  "nested loop"},
            {"VALID", "move; turnL; turnR; move; takeFuel; loop{move; turnR; wait;}",  "all stage 0 elements"},

            {"INVALID", "move; turnR move;",  "missing ;"},
            {"INVALID", "move; turnR: move;",  ": instead of ;"},
            {"INVALID", "move; turnL; turnRight; move;",  "invalid action turnRight"},
            {"INVALID", "loop{}",  "Block in a loop with no statements"},
            {"INVALID", "{move;}",  "Block not inside a loop"},
            {"INVALID", "loop{move; turnL;",  "Block with no close }"},
            {"INVALID", "loop{move; loop{turnL;}",  "nested loop with one missing close } on block "},
            {"INVALID", "loop{move; loop{turnL;",  "nested loop with two missing close } on blocks "},
            {"INVALID", "loop{move; turnL;}}",  "Block with extra close }"},
        },
        {//STAGE 1:
            {"VALID", "while(eq(fuelLeft, 2)) { wait; }",   "while and condition using eq and  fuelLeft"},
            {"VALID", "if(lt(oppLR, 2)) { wait; }",   "if with condition using lt and oppLR"},
            {"VALID", "if(gt(oppFB, 2)) { move; }",   "if with condition using gt and oppFB"},
            {"VALID", "if(eq(numBarrels, 1)) {turnL;}",   "if with condition using eq and numbBarrels"},
            {"VALID", "while(lt(barrelLR, 1)) {turnR;}",   "while with condition using lt and barrelLR"},
            {"VALID", "while(gt(barrelFB, 1)) {wait;}",   "while with condition using gt and barrelFB"},
            {"VALID", "while(eq(wallDist, 0)) {turnL; wait;}",   "while with condition using eq and wallDis"},

            {"VALID", "while(gt(wallDist, 0)) {while(eq(fuelLeft, 4)) {turnL;}}",   "while with nested while"},
            {"VALID", "while(gt(wallDist, 0)) {if(eq(fuelLeft, 4)) {turnL;}}",   "while with nested if"},
            {"VALID", "if(gt(wallDist, 0)) {if(eq(fuelLeft, 4)) {turnL;}}",   "if with nested if"},
            {"VALID", "if(gt(wallDist, 0)) {while(eq(fuelLeft, 4)) {turnL;}}",   "if with nested while"},
            {"VALID", "move; while(gt(wallDist, 0)) {turnL;} if(eq(fuelLeft, 4)) {turnL;} wait;",   "sequence of 4 statements, including an if an a while"},

            {"INVALID", "while{move;}",   "while needs a condition"},
            {"INVALID", "if{move;}",   "if needs a condition"},
            {"INVALID", "while(){move;}",   "while can't have an empty condition"},
            {"INVALID", "if(){move;}",   "if can't have an empty condition"},
            {"INVALID", "if(eq(fuelLeft, 1) {move;}",   "Condition in if must have closing )"},
            {"INVALID", "if eq(fuelLeft, 1) {move;}",   "Condition in if must have opening )"},
            {"INVALID", "while(eq(fuelLeft, 1) {move;}",   "Condition in while must have closing )"},
            {"INVALID", "while eq(fuelLeft, 1) {move;}",   "Condition in while must have opening )"},

            {"INVALID", "while(eq(fuelLeft, 2) move;",   "while must have a block, not a statement."},
            {"INVALID", "if(eq(fuelLeft, 2) move;",   "if must have a block, not a statement."},
            {"INVALID", "while(eq(fuelLeft, 1)){}",   "block in a while must have at least one statement"},
            {"INVALID", "if(eq(fuelLeft, 1)){}",   "block in an if must have at least one statement"},

            {"INVALID", "if(eq(fuelLeft, 2)) move;",   "if must have a block, "},
            {"INVALID", "if(shieldOn){shieldOff;}",   "can't have an action as a boolean"},
            {"INVALID", "if(gt(turnL, 1)) {move;}",   "can't have an action as a sensor."},
            {"INVALID", "loop(gt(turnL, 1)){move; turnL;}",  "loop cannot have a condition"},
        },
        {//STAGE 2

            {"VALID", "move(3);",  "move with number argument"},
            {"VALID", "move(fuelLeft);",  "move with sensor argument"},
            {"VALID", "move(add(fuelLeft,2));",  "move with add argument"},
            {"VALID", "move(mul(oppLR,2));",  "move with mul argument"},
            {"VALID", "wait(sub(oppFB,2));",  "wait with sub argument"},
            {"VALID", "wait(div(oppLR,2));",  "wait with div argument"},
            {"VALID", "wait(div(add(3, 5), sub(mul(oppLR,2),sub(5, 6))));",  "wait with complex nested expression"},
            {"VALID", "if (lt(add(3,4), sub(10,2))) { wait; } else {move;}",  "lt on expressions, if with else"},
            {"VALID", "if (gt(mul(3,4), div(100,2))) { wait; } else {move;}",  "gt on expressions, if with else"},
            {"VALID", "while (eq(mul(3,add(1, 4)), 10)) { wait;}",  "eq with nested expression, "},
            {"VALID", "if (and(lt(3,4),gt(10,2))) { wait; } else {move;}",  "condition with and"},
            {"VALID", "if (or(lt(3,4),gt(10,2))) { wait; } else {move;}",  "condition with or"},
            {"VALID", "if (not(lt(4,3))) { wait; } else {move;}",  "condition with not"},
            {"VALID", "if (or(and(lt(3,4),gt(10,2)), not(not(lt(4,3))))) { wait; } else {move;}",  "nested ands, ors, nots"},
            {"VALID", "if (eq(oppLR,3)) { wait; } else {move;}",  "wait"},
            {"VALID", "if (eq(barrelFB,3)) { wait; } else {move;}",  "move"},
            {"VALID", "if (eq(3,oppLR)) { wait; } else {move;}",  "wait"},
            {"VALID", "if (eq(3,barrelFB)) { wait; } else {move;}",  "move"},

            {"INVALID", "turnL(3);", "turnL should not have an argument"},
            {"INVALID", "turnR(fuelLeft);",  "turnR should not have an argument"},
            {"INVALID", "turnAround(oppLR);",  "turnAround should not have an argument"},
            {"INVALID", "move();", "move with an argument needs an argument"},
            {"INVALID", "move(3, 4);", "move must not have two arguments"},
            {"INVALID", "if(lt(3, 4)){move;} else", "else clause must have a block"},
            {"INVALID", "if(lt(3, 4)){move;} else move;", "else clause must have a block"},
            {"INVALID", "if(lt(3,4)) else {move;}", "if must have a then part as well as an else"},
            {"INVALID", "while (and(lt(3,4), gt(5, 3), eq(2,2))) {move;}", "and(..) must not have more than 2 arguments"},
            {"INVALID", "while (and(lt(3,4))) {move;}", "and(..) must not have just 1 argument"},
            {"INVALID", "while (and()) {move;}", "and(..) must not have 0 arguments"},
            {"INVALID", "while (or(lt(3,4), gt(5, 3), eq(2,2))) {move;}", "or(..) must not have more than 2 arguments"},
            {"INVALID", "while (or(lt(3,4))) {move;}", "or(..) must not have just 1 argument"},
            {"INVALID", "while (or()) {move;}", "or(..) must not have 0 arguments"},
            {"INVALID", "while (not(lt(3,4),gt(4,5))) {move;}", "not(..) must not have more than 1 argument"},
            {"INVALID", "while (not()) {move;}", "not(..) must not have 0 arguments"},
            {"INVALID", "while (and(3,4)) {move;}", "and(..) must not have numeric arguments"},
            {"INVALID", "while (or(3,fuelLeft)) {move;}", "or(..) must not have numeric arguments"},
            {"INVALID", "while (not(3)) {move;}", "not(..) must not have numeric arguments"},
            {"INVALID", "wait(add(5));",  "add must not have just 1 argument"},
            {"INVALID", "wait(add());",  "add must not have 0 arguments"},
            {"INVALID", "wait(sub(5));",  "sub must not have just 1 argument"},
            {"INVALID", "wait(sub());",  "sub must not have 0 arguments"},
            {"INVALID", "wait(mul(5));",  "mul must not have just 1 argument"},
            {"INVALID", "wait(mul());",  "mul must not have 0 arguments"},
            {"INVALID", "wait(div(5));",  "div must not have just 1 argument"},
            {"INVALID", "wait(div());",  "div must not have 0 arguments"},
            {"INVALID", "wait(add(5, lt(3, 4)));",  "add must not have boolean arguments"},
            {"INVALID", "wait(sub(lt(3, 4),5));",  "sub must not have boolean arguments"},
            {"INVALID", "wait(mul(5, takeFuel));",  "add must not have action arguments"},
            {"INVALID", "wait(div(turnL,5));",  "div must not have action arguments"},
        },
        {//STAGE 3
            //elif in if
            //optional args to barrelLR and barrelFB
            //variable and assignments (and use of vars in expressions and relops. put spaces around =
            {"VALID", "if (lt(3,4)) {wait;} elif(gt(10,2)) {move;} elif(eq(4,3)) { turnL; } else {turnR;}",  "two elif clauses with else"},
            {"VALID", "if (lt(3,4)) {wait;} elif(gt(10,2)) {move;}",  "one elif clause with no else"},
            {"VALID", "wait(barrelLR);", "barrelLR with no argument"},
            {"VALID", "wait(barrelLR(3));", "barrelLR with argument"},
            {"VALID", "wait(barrelFB);", "barrelFB with no argument"},
            {"VALID", "wait(barrelFB(add(1,fuelLeft)));", "barrelFB with expression argument"},
            {"VALID", "$a = 3; move($a);", "variable assignment to number and use in move argument"},
            {"VALID", "$a = 3 ; $b = add($a, 3);", "variable assignment to expression with variable"},
            {"VALID", "$abcd = 3; move($abcd);", "4 letter variable assignment and use in move argument"},
            {"VALID", "$b = mul(3,fuelLeft); move($b);", "variable assignment to expression and use in move argument"},
            {"VALID", "$a = 3; while(lt($a, fuelLeft)){$a = add($a,1); move;}", "variable assignment and use in while condition, and expression"},
            {"VALID", "$a = 3; $b = 4; if(eq($a, mul($b,3))){wait($a);}", "variable assignments and use in exprs in an if condition"},
            
            {"INVALID", "if (lt(3,4)) {wait;} elif gt(10,2) {move;}",  "elif with no ()"},
            {"INVALID", "if (lt(3,4)) {wait;} elif (10) {move;}",  "elif with expr instead of cond"},
            {"INVALID", "if (lt(3,4)) {wait;} elif (gt(10,2)) move;",  "elif with no {}"},
            {"INVALID", "elif (lt(3,4)) {wait;} else {move;}",  "elif with no if"},
            {"INVALID", "if (lt(3,4)) {wait;} elif {move;}",  "elif with no condition"},
            {"INVALID", "if (lt(3,4)) {wait;} else {turnL;} elif (gt(10,2)) {move;}",  "elif after else"},
            {"INVALID", "a = 3; move(a);", "variable assignment with invalid variable name - no $"},
            {"INVALID", "$a ;", "variable assignment with no = "},
            {"INVALID", "$a = ;", "variable assignment with no value"},
            {"INVALID", "%a1 = 3; move($b2c);", "variable assignment with invalid variable name - digits"},
            {"INVALID", "$a = 3; while(lt($a, fuelLeft){$a = add($a,1); move;}", "variable assignment and use in while condition, and expression"},
            {"INVALID", "if($a){wait(3);}", "variable as a Condition"}
        }
    };

}
/**
Stage 0

PROG  ::= [ STMT ]*
STMT  ::= ACT ";" | LOOP 
ACT   ::= "move" | "turnL" | "turnR" | "takeFuel" | "wait"
LOOP  ::= "loop" BLOCK
BLOCK ::= "{" STMT+ "}"


Stage 1 additions

STMT  ::= ... | IF | WHILE
ACT   ::= ... | "turnAround" | "shieldOn" | "shieldOff" 
IF    ::= "if" "(" COND ")" BLOCK
WHILE ::= "while" "(" COND ")" BLOCK
COND  ::= RELOP "(" SENS "," NUM ")
RELOP ::= "lt" | "gt" | "eq"
SENS  ::= "fuelLeft" | "oppLR" | "oppFB" | "numBarrels" |
          "barrelLR" | "barrelFB" | "wallDist"
NUM   ::= "-?[1-9][0-9]*|0"


Stage 2 additions

ACT   ::= "move" [ "(" EXPR ")" ] | ... | | "wait" [ "(" EXPR ")" ]
IF    ::= ........ [ "else" BLOCK ]
EXPR   ::= NUM | SENS | OP "(" EXPR "," EXPR ")"  
OP    ::= "add" | "sub" | "mul" | "div"
COND  ::= RELOP "(" EXPR "," EXPR ") | "and" "(" COND "," COND ")" | "or" "(" COND "," COND ")" | "not" "(" COND ")"  
          
Stage 3 additions

STMT  ::= ... | ASSGN ";" 
IF    ::= ... [ "elif"  "(" COND ")"  BLOCK ]* ....
ASSGN ::= VAR "=" EXPR
EXPR   ::= ... | VAR 
SENS  ::= ... | "barrelLR" [ "(" EXPR ")" ] | "barrelFB" [ "(" EXPR ")" ] 
VAR   ::= "\\$[A-Za-z][A-Za-z0-9]*"       
*/
