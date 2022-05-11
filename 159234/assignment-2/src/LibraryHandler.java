import java.util.Scanner;

public class LibraryHandler {
    public enum States {
        OPEN,
        CLOSED,
        ON_HOLD,
        ERROR,
        QUIT
    }
    private States currentState = States.QUIT;
    public States getState() { return currentState; }

    public void nextAction(LibrarySystem system) {
            currentState = States.ON_HOLD;

            Scanner sc = new Scanner(System.in);
            String userIn = sc.next();

            currentState = States.CLOSED;
            Record r = null;
            switch(userIn) {
                case "s":
                    system.sortRecords();
                    break;

                case "i":
                    r = system.search(Integer.parseInt(userIn));
                    if(r == null) { System.out.println("Item with that ID could not be found."); break; }

                    r.shortPrint();
                    System.out.println("Enter s to select or anything else to continue.");
                    userIn = sc.next();
                    if(userIn.equals("s")) nextAction(r);

                    break;

                case "q":
                    currentState = States.QUIT;
                    return;

                default:
                    r = system.search(userIn);
                    if(r == null) { System.out.println("Item with that phrase could not be found."); break; }

                    r.shortPrint();
                    System.out.println("Enter s to select or anything else to continue.");
                    userIn = sc.next();
                    if(userIn.equals("s")) nextAction(r);

                    break;
            }

            currentState = States.OPEN;
    }
    public void nextAction(Record record) {
        record.fullPrint();

    }

    public LibraryHandler() {

    }
}
