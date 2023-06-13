import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * Handout code for an interface for` assignment 4.
 * 
 * @author tony
 */
public class Assignment4_Interface {
    private static final int SEARCH_COLS = 20;
    private static final int EDITOR_ROWS = 30;
    private static final int EDITOR_COLS = 60;
    private static final int COMP_OUTPUT_ROWS = 20;
    private static final int COMP_OUTPUT_COLS = 50;
    private static final int LAYOUT_GAP = 5;

    private static final Charset CHARSET = StandardCharsets.UTF_8;

    private static final String DEFAULT_EDITOR_FILE = "../data/war_and_peace.txt";
    private static final String DEFAULT_EDITOR_TEXT = "../data/war_and_peace.txt not found, please load a file manually.";

    // The list of algorithms in the dropdown box.
        // If you want to add another, you should include it here and add
        // another case in the onAlgorithmRun method.
    private static final String[] ALGORITHMS = { "Huffman coding", "Lempel Ziv" };

    private JFrame frame;
    private JFileChooser fileChooser;

    // editor components.
    private JTextField searchField;
    private JTextArea textEditor;

    // compression components and state.
    private JComboBox<String> list;
    private JLabel compressionFileSelection;
    private JTextArea compressionOutput;
    private File compressionInputFile;
    private File compressionOutputFile;

    public Assignment4_Interface() {
        initialise();
    }

    /**
     * Handles all the logic of setting up and running each algorithm,
     * such as making input and output streams, and writing some useful
     * information to the output area in the GUI.
     */
    private void onAlgorithmRun() {
        try {
            if (compressionInputFile == null || !compressionInputFile.exists()) {
                JOptionPane.showMessageDialog(frame, "Input file does not exist or not selected.");
                return;
            }
            if (compressionOutputFile == null) {
                JOptionPane.showMessageDialog(frame, "Output file not unselected.");
                return;
            }
            if (!compressionInputFile.getName().endsWith(".txt")) {
                JOptionPane.showMessageDialog(frame, "This assignment only deals with compressing .txt files.");
                // but let them do it anyway.
            }

            // clear the output area.
            compressionOutput.setText("");

            String algorithm = (String) list.getSelectedItem();
            if (algorithm.equals("Huffman coding")) {
                // read in the file.
                String text = readFile(compressionInputFile);

                // run the algorithms.
                HuffmanCoding huffman = new HuffmanCoding(text);
                String encoded = huffman.encode(text);
                String decoded = huffman.decode(encoded);

                if (!encoded.matches("[01]*"))
                    compressionOutput.append(
                            "ERROR: Encoded string contains characters that are not 0 or 1. It is not a valid Huffman encoding.\n\n");

                // write out the encoded text.
                List<String> lines = Arrays.asList(encoded);
                Files.write(compressionOutputFile.toPath(), lines, CHARSET);

                // figure out file sizes. the encoded string represents bits,
                // so we divide it by 8 to get the size in bytes.
                compressionOutput.append("input length:  " + compressionInputFile.length() + " bytes \n");
                compressionOutput.append("output length: " + encoded.length() / 8 + " bytes \n\n");

                // check they're the same and display user output.
                compressionOutput
                        .append("original and decoded texts " + (text.equals(decoded) ? "" : "DO NOT ") + "match.\n");
                compressionOutput.append(huffman.getInformation());

            } else if (algorithm.equals("Lempel Ziv")) {
                // read in the file.
                String text = readFile(compressionInputFile);

                // run the algorithms.
                LempelZiv lz = new LempelZiv();
                String compressed = lz.compress(text);
                String decompressed = lz.decompress(compressed);

                // write out the encoded text.
                List<String> lines = Arrays.asList(compressed);
                Files.write(compressionOutputFile.toPath(), lines, CHARSET);

                // figure out file sizes, in characters this time.
                compressionOutput.append("Input length:  " + text.length() + " characters \n");
                compressionOutput.append("Output length: " + compressed.length() + " characters \n");

                // check they're the same and display user output.
                compressionOutput.append(
                        "\nOriginal and decoded texts " + (text.equals(decompressed) ? "" : "DO NOT ") + "match!\n");
                compressionOutput.append(lz.getInformation());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This does all the work of creating the GUI.
     */
    private void initialise() {

        /*
         * Let's start with the text editor tab.
         */

        fileChooser = new JFileChooser();

        // first, setup a listener to call onEditorLoad when the user loads a
        // file.
        JButton load = new JButton("Load");
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                // set up the file chooser
                fileChooser.setCurrentDirectory(new File("."));
                fileChooser.setDialogTitle("Select input file.");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                // run the file chooser and check the user didn't hit cancel
                if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    textEditor.setText(readFile(file));
                }
            }
        });

        // next, add in the search box on the top right.
        searchField = new JTextField(SEARCH_COLS);
        searchField.setMaximumSize(new Dimension(0, 25));
        searchField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String pattern = searchField.getText();
                String text = textEditor.getText();
                int index = KMP.search(pattern, text);

                if (index == -1) {
                    JOptionPane.showMessageDialog(frame, "Pattern not found.");
                } else {
                    textEditor.requestFocus();
                    textEditor.setSelectionStart(index);
                    textEditor.setSelectionEnd(index + pattern.length());
                    textEditor.setSelectionColor(Color.YELLOW);
                }
            }
        });

        // both of the previous components go in a 'controls' panel at the top
        // of the pane.
        JPanel controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.LINE_AXIS));

        // make an empty border so the components aren't right up against the
        // frame edge.
        Border edge = BorderFactory.createEmptyBorder(LAYOUT_GAP, LAYOUT_GAP, LAYOUT_GAP, LAYOUT_GAP);
        controls.setBorder(edge);

        // add all the components to the frame.
        controls.add(load);
        controls.add(Box.createHorizontalGlue());
        controls.add(new JLabel("Search"));
        controls.add(Box.createRigidArea(new Dimension(LAYOUT_GAP, 0)));
        controls.add(searchField);

        // then, we need to make the editor area itself.
        textEditor = new JTextArea(EDITOR_ROWS, EDITOR_COLS);
        textEditor.setLineWrap(true);
        textEditor.setWrapStyleWord(true);
        textEditor.setEditable(true);

        File defaultFile = new File(DEFAULT_EDITOR_FILE);
        if (defaultFile.exists())
            textEditor.setText(readFile(defaultFile));
        else
            textEditor.setText(DEFAULT_EDITOR_TEXT);

        JScrollPane scroll = new JScrollPane(textEditor);

        // lastly, we need to put this all in a panel.
        JPanel editor = new JPanel();
        editor.setLayout(new BorderLayout());
        editor.add(controls, BorderLayout.NORTH);
        editor.add(scroll, BorderLayout.CENTER);

        /*
         * Now let's make the second tabbed pane.
         */

        // first the left panel, which is the input side.

        // a load button.
        load = new JButton("Input file");
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                // set up the file chooser
                fileChooser.setCurrentDirectory(new File("."));
                fileChooser.setDialogTitle("Select input file.");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                // run the file chooser and check the user didn't hit cancel
                if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    compressionInputFile = fileChooser.getSelectedFile();

                    String inputName = compressionInputFile == null ? "" : compressionInputFile.getName();
                    String outputName = compressionOutputFile == null ? "" : compressionOutputFile.getName();
                    compressionFileSelection
                            .setText("<html>input: " + inputName + "<br>output: " + outputName + "</html>");
                }
            }
        });

        // a save button for the compressed file.
        JButton save = new JButton("Output file");
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                // set up the file chooser
                fileChooser.setCurrentDirectory(new File("."));
                fileChooser.setDialogTitle("Select output file.");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                // run the file chooser and check the user didn't hit cancel
                if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    compressionOutputFile = fileChooser.getSelectedFile();

                    String inputName = compressionInputFile == null ? "" : compressionInputFile.getName();
                    String outputName = compressionOutputFile == null ? "" : compressionOutputFile.getName();
                    compressionFileSelection
                            .setText("<html>input: " + inputName + "<br>output: " + outputName + "</html>");
                }
            }
        });

        // a selection box for algorithms.
        list = new JComboBox<>(ALGORITHMS);
        list.setMaximumSize(list.getPreferredSize());

        // put it all together.
        JPanel leftTop = new JPanel();
        BorderLayout layout = new BorderLayout();
        layout.setVgap(LAYOUT_GAP);
        leftTop.setLayout(layout);
        leftTop.add(load, BorderLayout.NORTH);
        leftTop.add(save, BorderLayout.CENTER);
        leftTop.add(list, BorderLayout.SOUTH);

        // a go button.
        JButton run = new JButton("Run");
        run.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                onAlgorithmRun();
            }
        });

        compressionFileSelection = new JLabel("<html>input:<br>output:</html>");

        JPanel leftBottom = new JPanel();
        layout = new BorderLayout();
        layout.setVgap(LAYOUT_GAP);
        leftBottom.setLayout(layout);
        leftBottom.add(compressionFileSelection, BorderLayout.NORTH);
        leftBottom.add(run, BorderLayout.SOUTH);

        // the left panel is just leftTop, a bunch of vertical space, and the
        // run button at the bottom.
        JPanel left = new JPanel();
        layout = new BorderLayout();
        layout.setVgap(LAYOUT_GAP);
        left.setLayout(layout);
        left.add(new JPanel(), BorderLayout.CENTER);
        left.add(leftTop, BorderLayout.NORTH);
        left.add(leftBottom, BorderLayout.SOUTH);

        // put a title border around it. We compose that with an empty border to
        // give the components some breathing room.
        Border title = BorderFactory.createTitledBorder("input");
        edge = BorderFactory.createEmptyBorder(LAYOUT_GAP, LAYOUT_GAP, LAYOUT_GAP, LAYOUT_GAP);
        Border border = BorderFactory.createCompoundBorder(title, edge);
        left.setBorder(border);

        // now the right panel, which is output.

        // a text output area for writing information.
        compressionOutput = new JTextArea(COMP_OUTPUT_ROWS, COMP_OUTPUT_COLS);
        compressionOutput.setLineWrap(true);
        compressionOutput.setWrapStyleWord(true);
        compressionOutput.setEditable(false);
        scroll = new JScrollPane(compressionOutput);

        // put it all together
        JPanel right = new JPanel();
        layout = new BorderLayout();
        layout.setVgap(LAYOUT_GAP);
        right.setLayout(layout);
        right.add(compressionOutput, BorderLayout.CENTER);

        JPanel compression = new JPanel();
        compression.setLayout(new BoxLayout(compression, BoxLayout.LINE_AXIS));
        compression.add(left);
        compression.add(right);

        // same border as for the left.
        title = BorderFactory.createTitledBorder("output");
        edge = BorderFactory.createEmptyBorder(LAYOUT_GAP, LAYOUT_GAP, LAYOUT_GAP, LAYOUT_GAP);
        border = BorderFactory.createCompoundBorder(title, edge);
        right.setBorder(border);

        // now make the tabbed pane.
        JTabbedPane tabs = new JTabbedPane();
        tabs.add("editor", editor);
        tabs.add("compression", compression);

        // some convenience shortcuts for marking.
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent ev) {
                if (ev.getID() == KeyEvent.KEY_PRESSED) {
                    int shortcut = -1;
                    if (ev.isControlDown() && ev.getKeyCode() == 81) {
                        shortcut = 1;
                    } else if (ev.isControlDown() && ev.getKeyCode() == 87) {
                        shortcut = 2;
                    } else if (ev.isControlDown() && ev.getKeyCode() == 69) {
                        shortcut = 3;
                    } else if (ev.isControlDown() && ev.getKeyCode() == 82) {
                        shortcut = 4;
                    } else if (ev.isControlDown() && ev.getKeyCode() == 84) {
                        shortcut = 5;
                    }

                    if (shortcut >= 0) {
                        String home = System.getProperty("user.home");
                        compressionInputFile = new File(home + "/a4_io/input_" + shortcut + ".txt");
                        compressionOutputFile = new File(home + "/a4_io/output_" + shortcut + ".txt");
                        String inputName = compressionInputFile == null ? "" : compressionInputFile.getName();
                        String outputName = compressionOutputFile == null ? "" : compressionOutputFile.getName();
                        compressionFileSelection
                                .setText("<html>input: " + inputName + "<br>output: " + outputName + "</html>");
                    }
                }
                return false;
            }
        });

        frame = new JFrame("Assignment_4_Interface");
        // this makes the program actually quit when the frame's close button is
        // pressed.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(tabs, BorderLayout.CENTER);

        // always do these two things last, in this order.
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * A helper method for reading a file into a string.
     */
    private static String readFile(File file) {
        try {
            byte[] encoded = Files.readAllBytes(file.toPath());
            return new String(encoded, CHARSET);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) {
        new Assignment4_Interface();
    }
}

// code for COMP261 assignments
