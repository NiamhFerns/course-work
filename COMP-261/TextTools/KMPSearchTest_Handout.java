

public class KMPSearchTest_Handout
{
    public static void main(String[] args) {
        // Test cases
        String text = "Hello, world!";
        String pattern1 = "world";
        String pattern2 = "Hello";
        String pattern3 = "Java";
        
        // Expected results
        int expectedMatch1 = 7;  // "world" starts at index 7
        int expectedMatch2 = 0;  // "Hello" starts at index 0
        int expectedMatch3 = -1; // "Java" is not found
        
        // Perform substring search
        int match1 = KMP.search(pattern1, text);
        int match2 = KMP.search(pattern2, text);
        int match3 = KMP.search(pattern3, text);
        
        // Print results        
        // Check if patterns were found at the correct positions
        if (match1 == expectedMatch1) {
            System.out.println("OK!! Matched at a correct position " + match1);
        } else {
            System.out.println("Wrong!! Expected at: " + expectedMatch1);
        }
        
        if (match2 == expectedMatch2) {
            System.out.println("OK!! Matched at a correct position " + match2);
        } else {
            System.out.println("Wrong!! Expected at: " + expectedMatch2);
        }
        
        if (match2 == expectedMatch2) {
            System.out.println("OK!! Matched at a correct position " + match3);
        } else {
            System.out.println("Wrong!! Expected at: " + expectedMatch3);
        }
    }
}
