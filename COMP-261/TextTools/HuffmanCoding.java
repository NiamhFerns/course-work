/**
 * A new instance of HuffmanCoding is created for every run. The constructor is
 * passed the full text to be encoded or decoded, so this is a good place to
 * construct the tree. You should store this tree in a field and then use it in
 * the encode and decode methods.
 */


import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class HuffmanCoding {
    /**
     * Holds all the information needed for a single node in the huffman tree used for this piece of text. with a left node
     * being 0 and a right node being 1.
     *
     * @param character   an optional character to be encoded by this node. (Always a leaf node.)
     * @param left        an optional left (0) node. (Always a branch node.)
     * @param right       an optional right (1) node. (Always a branch node.)
     * @param probability the total probability that the desired char is within this node.
     * @param value       the combined value of all characters within this node's subtree.
     */
    private record Node(
            Optional<Character> character,
            Optional<Node> left,
            Optional<Node> right,
            double probability,
            int value
    ) implements Comparable<Node> {

        public Node {
            if (character.isPresent() && (left.isPresent() || right.isPresent()))
                throw new IllegalArgumentException("Leaf node contains branches.");
            else if (character.isEmpty() && (left.isEmpty() || right.isEmpty()))
                throw new IllegalArgumentException("Branching node is missing one or both branches.");
        }

        @Override
        public int compareTo(Node node) {
            var p = Double.compare(probability, node.probability);
            return p == 0
                    ? Integer.compare(value, node.value)
                    : p;
        }
    }

    // Huffman Tree Data
    private final Node rootNode;
    private final HashMap<Character, String> encodingTable;

    // Error Handling
    public final Supplier<IllegalStateException> invalidTreeError = () -> new IllegalStateException("Either you don't have a valid tree or encoded text contains characters not present in this huffman tree.");

    /**
     * Builds a huffman tree and encoding table that can be used to encode and decode arbitrary text.
     *
     * @param text used in analysis to build a well-fitted huffman tree and encoding table.
     */
    public HuffmanCoding(String text) {
        if (text == null || text.isEmpty())
            throw new IllegalArgumentException("Text is empty.");

        // Build our tree.
        rootNode = buildTree(text);

        // Build our encoding table.
        encodingTable = new HashMap<>();
        findEncodedChar(rootNode, "");
    }

    /**
     * Analyzes a piece of text to build a huffman tree.
     *
     * @param text the text to be analyzed and compressed.
     * @return the tree's root node.
     */
    private Node buildTree(String text) {
        // Analyze text.
        HashMap<Character, Integer> charCounts = new HashMap<>();
        PriorityQueue<Node> probabilities = new PriorityQueue<>();

        text.chars().forEach(c -> charCounts.put((char) c, charCounts.getOrDefault((char) c, 0) + 1));

        // Add all leaf nodes.
        for (var entry : charCounts.entrySet()) {
            probabilities.add(
                    new Node(
                            Optional.of(entry.getKey()),
                            Optional.empty(),
                            Optional.empty(),
                            (double) entry.getValue() / text.length(),
                            entry.getKey()));
        }

        // Build our tree.
        while (probabilities.size() > 1) {
            var p = probabilities.remove();
            var q = probabilities.remove();

            probabilities.add(
                    new Node(
                            Optional.empty(),
                            Optional.of(p),                          // Left Tree
                            Optional.of(q),                          // Right Tree
                            p.probability() + q.probability(),       // Combined probabilities for tree.
                            p.value() + q.value()));                 // Combined character value for node.
        }

        return probabilities.remove();
    }

    /**
     * Recursively finds an encoding for each character in a huffman tree with a depth first search.
     * Relies on currentCode that keeps track of where you are in the tree. Each recursion sets the code
     * for the next recursion.
     *
     * @param node the current node in the tree.
     * @param code the current code up to that node.
     */
    private void findEncodedChar(Node node, String code) {
        // Base Case
        if (node.character().isPresent()) {
            encodingTable.put(node.character().get(), code);
            return;
        }

        findEncodedChar(node.left().orElseThrow(invalidTreeError), code + "0");
        findEncodedChar(node.right().orElseThrow(invalidTreeError), code + "1");
    }

    /**
     * Encodes a piece of text based on the huffman tree for this specific text.
     *
     * @param text the text that we want to encode.
     * @return a string of the encoded text in 1s and 0s.
     */
    public String encode(String text) {
        if (text == null || text.isEmpty()) return "";
        var encoded = new StringBuilder();
        text.chars().forEach(c -> encoded.append(encodingTable.getOrDefault((char) c, "")));
        return encoded.toString();
    }

    /**
     * Converts and encoded string into a decoded string based on some this huffman tree.
     * NOTE: encoded string must match tree, or you risk running into exceptions.
     *
     * @param encoded an encoded string of 1s and 0s.
     * @return the decoded text.
     */
    public String decode(String encoded) {
        Node currentNode = rootNode;
        StringBuilder decoded = new StringBuilder();

        for (var c : encoded.chars().toArray()) {
            if ((char) c == '0')
                currentNode = currentNode.left().orElseThrow(invalidTreeError);
            else if ((char) c == '1')
                currentNode = currentNode.right().orElseThrow(invalidTreeError);
            else throw new IllegalArgumentException("Failed to decode invalid string: " + encoded);

            if (currentNode.character().isPresent()) {
                decoded.append(currentNode.character().get());
                currentNode = rootNode;
            }
        }

        return decoded.toString();
    }

    /**
     * The getInformation method is here for your convenience, you don't need to
     * fill it in if you don't want to. It is called on every run and its return
     * value is displayed on-screen. You could use this, for example, to print
     * out the encoding tree.
     */
    public String getInformation() {
        var info = new StringBuilder();
        info.append("Encoding Table (SORRY THIS IS UNSORTED):\n");
        for (var entry : encodingTable.entrySet()) {
            if (entry.getKey() == '\n') info.append("Key: \\n Value: ").append(entry.getValue()).append("\n");
            else info.append("Key: ").append(entry.getKey()).append("Value: ").append(entry.getValue()).append("\n");
        }

        System.out.println("If you're having to look at the actual tree itself... sorry.\n\n");

        return info.toString();
    }
}
