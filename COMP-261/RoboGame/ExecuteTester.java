import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import javax.swing.JFileChooser;


public class ExecuteTester{

    /**
     * For testing the execute() methods from your parser without requiring the world or the game:
     * Calling main with no arguments will ask the user to select a stage
     * and will then test programs for that stage 
     */

    public static void main(String[] args) {
        Parser parser = new Parser();
        System.out.println("Testing execute methods");
        System.out.println("=======================");
        System.out.println("For each program in a collection of test programs,");
        System.out.println("it attempts to parse the program.");
        System.out.println("If the parsing fails, it reports that.");
        System.out.println("If the parsing succeeds, it attempts to execute the program,");
        System.out.println("and compares the actual actions that the program made the robot do");
        System.out.println("against the actions that the program was expected to do.");
        System.out.println("If the actions were the same, it reports that the program was OK");
        System.out.println("Otherwise, it prints the actual actions and the expected actions");
        System.out.println("================================================================");
        System.out.println("&& Testing execute methods");
        
        for (int stage=0; stage<4; stage++){
            System.out.println("\n&& Testing Stage "+stage+":");
            for (String[] test : programs[stage]){
                try{
                    ProgramNode node = parser.parse(new Scanner(test[0]));
                    if (node==null){
                        System.out.println("&& BAD: Parsing "+test[0]+"\n&&  reported no errors, but produced a null program");
                        continue;
                    }
                    testProgram(test[0], node, test[1]);
                }
                catch (ParserFailureException e) {
                    System.out.println("&& BAD: Parsing failed (reporting errors) on "+test[0]);
                    System.out.println("       "+e);}
                catch (Exception e) {
                    System.out.println("&& BAD: Parser broke with some other kind of exception: ");
                    e.printStackTrace(System.out);
                }
            }
        }
    }

    /**

     * Tests the parser on a single program
     */
    static void testProgram(String program, ProgramNode ast, String expected){
        TesterRobot robot = new TesterRobot();
        try {ast.execute(robot);}
        catch(RobotInterruptedException e){}
        compare(((TesterRobot)robot).getOutput(), expected, program);
    }
        
    static void compare (List<String> actual, String expected, String program){
        String[] expectedArray = expected.split(" ");
        boolean match = actual.size()==expectedArray.length;
        if (match){
            for (int i=0; i<actual.size(); i++){
                if (!actual.get(i).equals(expectedArray[i])){
                    match = false; break;
                }
            }
        }
        if (match){
            System.out.println("&& OK on program: "+ program);
        }
        else {
            System.out.println("_____________________");
            System.out.println("&& BAD on program:  "+ program);
            System.out.println("&& Expected: "+expected);
            System.out.print("&& Actual:   ");
            for (String str : actual){System.out.print(str + " ");}
            System.out.println("\n---------------------");
        }
    }

    private static final String[][][] programs = new String[][][]{
        {//STAGE 0
            {"move;", "move"},
            {"turnL;", "turnL"},
            {"turnR;", "turnR"},
            {"takeFuel;", "takeFuel"},
            {"wait;", "wait"},
            {"wait; loop { move; turnL;}", "wait move turnL move turnL move turnL move turnL move turnL move turnL move turnL move turnL move turnL move"},
            {"wait; loop { turnR; loop { move; } turnL;}", "wait turnR move move move move move move move move move move move move move move move move move move"}
        },
        {//STAGE 1:
            {"shieldOn;", "shieldOn"},
            {"shieldOff;", "shieldOff"},
            {"turnAround;", "turnAround"},

            {"wait; if (lt(oppLR, 4)) { turnL; } move;", "wait getOppLR turnL move"},
            {"wait; if (lt(oppLR, 2)) { turnL; } move;", "wait getOppLR move"},
            {"wait; if (gt(oppLR, 2)) { turnL; } move;", "wait getOppLR turnL move"},
            {"wait; if (gt(oppLR, 4)) { turnL; } move;", "wait getOppLR move"},
            {"wait; if (eq(oppLR, 4)) { turnL; } move;", "wait getOppLR move"},
            {"wait; if (eq(oppLR, 3)) { turnL; } move;", "wait getOppLR turnL move"},
            {"wait; if (eq(oppLR, 4)) { turnL; } move;", "wait getOppLR move"},
            {"wait; if (eq(oppLR, 3)) { turnL; } move;", "wait getOppLR turnL move"},
            {"wait; if (eq(oppFB, 4)) { turnL; } move;", "wait getOppFB move"},
            {"wait; if (eq(oppFB, 3)) { turnL; } move;", "wait getOppFB turnL move"},
            {"wait; if (eq(numBarrels, 4)) { turnL; } move;", "wait getNumBarrels turnL move"},
            {"wait; if (eq(numBarrels, 3)) { turnL; } move;", "wait getNumBarrels move"},
            {"wait; if (eq(barrelLR, 4)) { turnL; } move;", "wait getBarrelLR turnL move"},
            {"wait; if (eq(barrelLR, 3)) { turnL; } move;", "wait getBarrelLR move"},
            {"wait; if (eq(barrelFB, 4)) { turnL; } move;", "wait getBarrelFB turnL move"},
            {"wait; if (eq(barrelFB, 3)) { turnL; } move;", "wait getBarrelFB move"},

            {"wait; while (lt(wallDist, 3)) { turnL; } move;", "wait getWallDist turnL getWallDist turnL getWallDist turnL getWallDist move"},
            {"wait; while (gt(fuelLeft, 1)) { turnL; } move;", "wait getFuelLeft turnL getFuelLeft turnL getFuelLeft turnL getFuelLeft turnL getFuelLeft move"}
        },
        {//STAGE 2
            {"move(3);", "move move move"},
            {"move(fuelLeft);", "getFuelLeft move move move move move"},
            {"move(add(fuelLeft,2));", "getFuelLeft move move move move move move move"},
            {"if (lt(add(3,4), sub(10,2))) { wait; } else {move;}", "wait"},
            {"if (lt(add(3,4), sub(10,5))) { wait; } else {move;}", "move"},
            {"if (lt(mul(3,4), div(100,2))) { wait; } else {move;}", "wait"},
            {"if (lt(mul(3,4), div(100,10))) { wait; } else {move;}", "move"},
            {"if (and(lt(3,4),gt(10,2))) { wait; } else {move;}", "wait"},
            {"if (and(lt(3,4),gt(2,10))) { wait; } else {move;}", "move"},
            {"if (and(lt(4,3),gt(10,2))) { wait; } else {move;}", "move"},
            {"if (or(lt(3,4),gt(10,2))) { wait; } else {move;}", "wait"},
            {"if (or(lt(4,3),gt(10,2))) { wait; } else {move;}", "wait"},
            {"if (or(lt(3,4),gt(2,10))) { wait; } else {move;}", "wait"},
            {"if (or(lt(4,3),gt(2,10))) { wait; } else {move;}", "move"},
            {"if (not(lt(4,3))) { wait; } else {move;}", "wait"},
            {"if (not(lt(3,4))) { wait; } else {move;}", "move"},
            {"if (eq(oppLR,3)) { wait; } else {move;}", "getOppLR wait"},
            {"if (eq(barrelFB,3)) { wait; } else {move;}", "getBarrelFB move"},
            {"if (eq(3,oppLR)) { wait; } else {move;}", "getOppLR wait"},
            {"if (eq(3,barrelFB)) { wait; } else {move;}", "getBarrelFB move"}
        },
        {//STAGE 3
         //for the variable assignments, put spaces around =
            {"if (lt(4,3)) {wait;} elif(gt(10,2)) {move;} elif(eq(4,3)) { turnL; } else {turnR;}",  "move"},
            {"if (lt(4,3)) {wait;} elif(gt(2,10)) {move;} elif(eq(4,4)) { turnL; } else {turnR;}",  "turnL"},
            {"if (lt(4,3)) {wait;} elif(gt(2,10)) {move;} elif(eq(4,5)) { turnL; } else {turnR;}",  "turnR"},
            {"if (lt(3,4)) {wait;} elif(gt(10,2)) {move;} turnL;",  "wait turnL"},
            {"if (lt(4,3)) {wait;} elif(gt(10,2)) {move;} turnL;",  "move turnL"},
            {"if (lt(4,3)) {wait;} elif(gt(2,10)) {move;} turnL;",  "turnL"},
            {"wait(barrelLR);", "getBarrelLR wait wait wait wait"},
            {"wait(barrelLR(3));", "getBarrelLR(3) wait wait wait wait wait wait wait"},
            {"wait(barrelFB);", "getBarrelFB wait wait wait wait"},
            {"wait(barrelFB(sub(fuelLeft,6)));turnL;", "getFuelLeft getBarrelFB(-1) wait wait wait turnL"},
            {"$a = 3; move($a);turnL;$a = 5; move($a);turnR;", "move move move turnL move move move move move turnR"},
            {"$a = 3 ; $b = sub($a, 1); move($b); turnL;", "move move turnL"},
            {"$a = 3; $abcd = 2; move($abcd);turnL;", "move move turnL"},
            {"$b = mul(2,fuelLeft); move($b); turnL;", "getFuelLeft move move move move move move move move move move turnL"},
            {"$a = 2; while(lt($a, fuelLeft)){$a = add($a,1); move;} turnL;", "getFuelLeft move getFuelLeft move getFuelLeft turnL"},
            {"$a = 3; $b = 4; if(eq($a, mul($b,3))){if (lt(0,barrelLR(mul($a, $b)))) {move($b); turnL;}} else {turnR;}", "turnR"},
            {"$a = 12; $b = 4; if(eq($a, mul($b,3))){if (lt(0,barrelLR(mul($a, $b)))) {move($b); turnL;}} else {turnR;}", "getBarrelLR(48) move move move move turnL"}            
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
