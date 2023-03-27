import java.io.File;
import java.io.IOException;
import java.util.*;

class DataReader {
    // some bits of java code that you may use if you wish.
    int numCategories;
    int numAtts;
    Set<String> categoryNames;
    List<String> attNames;
    List<Instance> allInstances;

    private void readDataFile(String fname) {
        /* format of names file:
         * names of attributes (the first one should be the class/category)
         * category followed by true's and false's for each instance
         */
        System.out.println("Reading data from file " + fname);
        try {
            Scanner din = new Scanner(new File(fname));

            attNames = new ArrayList<>();
            Scanner s = new Scanner(din.nextLine());
            // Skip the "Class" attribute.
            s.next();
            while (s.hasNext()) {
                attNames.add(s.next());
            }
            numAtts = attNames.size();
            System.out.println(numAtts + " attributes");

            allInstances = readInstances(din);
            din.close();

            categoryNames = new HashSet<>();
            for (Instance i : allInstances) {
                categoryNames.add(i.category);
            }
            numCategories = categoryNames.size();
            System.out.println(numCategories + " categories");

            for (Instance i : allInstances) {
                System.out.println(i);
            }
        } catch (IOException e) {
            throw new RuntimeException("Data File caused IO exception");
        }
    }

    private List<Instance> readInstances(Scanner din) {
        /* instance = classname and space separated attribute values */
        List<Instance> instances = new ArrayList<>();
        while (din.hasNext()) {
            Scanner line = new Scanner(din.nextLine());
            instances.add(new Instance(line.next(), line));
        }
        System.out.println("Read " + instances.size() + " instances");
        return instances;
    }

    private static class Instance {

        private final String category;
        private final List<Boolean> vals;

        public Instance(String cat, Scanner s) {
            category = cat;
            vals = new ArrayList<>();
            while (s.hasNextBoolean()) {
                vals.add(s.nextBoolean());
            }
        }

        public boolean getAtt(int index) {
            return vals.get(index);
        }

        public String getCategory() {
            return category;
        }

        public String toString() {
            StringBuilder ans = new StringBuilder(category);
            ans.append(" ");
            for (Boolean val : vals) {
                ans.append(val ? "true " : "false ");
            }
            return ans.toString();
        }

    }
}