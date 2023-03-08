import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import javax.swing.JFileChooser;

public class ParserTester{

    /**
     * For testing your parser without requiring the world or the game:
     * Calling main with no arguments will repeatedly ask the user
     * to choose a program file and will then attempt to parse it and print it.
     *
     * Calling main with arguments will treat each argument as a file name
     * and will attempt to parse and print each file.
     */

    public static void main(String[] args) {
        Parser parser = new Parser();
        System.out.println("=================");
        if (args.length > 0) {
            for (String arg : args) {
                File file = new File(arg);
                if (file.exists()) {testParserOnFile(parser, file);}
                else {System.out.println("Can't find file '" + file + "'");}
            }
        } else {
            while (true) {
                JFileChooser chooser = new JFileChooser(RoboGame.CODE_DIRECTORY);
                int res = chooser.showOpenDialog(null);
                if (res != JFileChooser.APPROVE_OPTION) { break; }
                testParserOnFile(parser,chooser.getSelectedFile());
            }
        }
        System.out.println("Done");
    }

    /**
     * Tests the parser on a single file.
     */
    static void testParserOnFile(Parser parser, File file){
        System.out.println("Parsing '" + file + "'");
        try{
            Scanner scan = new Scanner(file);
            ProgramNode prog = parser.parse(scan);
            System.out.println("Parsing completed ");
            if (prog == null) {System.out.println("No program generated"); }
            else              {System.out.println("Program: \n" + prog); }
            scan.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Robot program source file not found");
        }
        catch (ParserFailureException e) {
            System.out.println("Parser error:");
            System.out.println(e.getMessage());
        }
        System.out.println("=================");
    }

}
