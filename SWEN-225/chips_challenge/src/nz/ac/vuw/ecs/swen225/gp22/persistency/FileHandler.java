package nz.ac.vuw.ecs.swen225.gp22.persistency;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class FileHandler {

    final Path levelPath;
    final Path savePath;

    /**
     * @param levelPath
     * @param savePath
     */
    FileHandler(Path levelPath, Path savePath) {
        this.levelPath = levelPath;
        this.savePath = savePath;
    }

    /**
     * gets dom4j Document from given path, no invalid input checking done currently.
     * @param xmlFilePath
     * @return
     */
    public static Document getXML(Path xmlFilePath) {
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(new File(xmlFilePath.toString()));
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        return document;
    }

    /**
     * writes a formatted xml element and its child elements to a file.
     * @param element
     * @param id
     */
    public static void saveXML(Element element, String id) {
        Document document = DocumentHelper.createDocument();
        element.setDocument(document);
        document.setRootElement(element);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(byteArrayOutputStream, format);
            writer.write(document);
            writer.close();

            FileWriter fileWriter = new FileWriter(id);
            fileWriter.write(byteArrayOutputStream.toString());
            fileWriter.close();


        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
