package models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

public class GameMap {

    public int tileWidth, tileHeight;
    public int width, height;
    public Hashtable<String, int[]> layers = new Hashtable<>();

    public GameMap(String filename) throws IOException, ParserConfigurationException, SAXException {


        File file = new File("assets/" + filename);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();
        Element mapElement = (Element)document.getElementsByTagName("map").item(0);


        tileWidth = Integer.parseInt(mapElement.getAttribute("tilewidth"));
        tileHeight = Integer.parseInt(mapElement.getAttribute("tileheight"));
        width = Integer.parseInt(mapElement.getAttribute("width"));
        height = Integer.parseInt(mapElement.getAttribute("height"));

        NodeList layerElements = document.getElementsByTagName("layer");

        for (int i = 0; i < layerElements.getLength(); i++) {
            Element layer = (Element)layerElements.item(i);
            String[] strData = layer.getTextContent().replaceAll("\\s", "").split(",");

            int[] data = new int[strData.length];
            for (int j = 0; j < strData.length; j++)
                data[j] = Integer.parseInt(strData[j]);

            layers.put(layer.getAttribute("name"), data);
        }

    }

    public int getIndex(int x, int y) {
        if (x > width - 1 || x < 0)  return -1;
        if (y > height - 1 || y < 0) return -1;
        return y*width + x;
    }
}
