import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibrarySystemTest {
    LibrarySystem testSystem;
    LibraryHandler h;

    @BeforeEach
    public void setUp() {
        testSystem = new LibrarySystem();
        h = new LibraryHandler();

        testSystem.addRecord("Journal,212,ACM,2010,5,8");
        testSystem.addRecord("Book,240,The Lord of the Rings: The fellowship of the ring,1998,Mary Freeman,164");
        testSystem.addRecord("Movie,200,Remember The Alamo,1945,George Smith");
        testSystem.addRecord("Book,231,How to Make Money,1987,Phil Barton,324");
        testSystem.addRecord("Journal,207,ACM,2009,6,8");

        testSort();
    }

    @Test
    public void testSetup() {
        List<Record> records = testSystem.getRecords();

        String s = "";
        for (Record r : records) { s += r.getID() + ","; }
        assertEquals("200,207,212,231,240,", s, "All items are not in order. First sort failed.");
    }

    @Test
    public void testIDSearch() {
        Record r = testSystem.search(212);
        assertEquals(212, r.getID(), "Search by ID found the wrong item.");
    }

    @Test
    public void testPhraseSearch() {
        ArrayList<Record> r = testSystem.search("The");
        assertEquals(2, r.size(), "Search found the incorrect amount of items.");
        assertEquals(200, r.get(0).getID(), "Search found the incorrect amount of items.");
        assertEquals(240, r.get(1).getID(), "Search found the incorrect amount of items.");
    }

    @Test
    public void testSort() {

    }
}