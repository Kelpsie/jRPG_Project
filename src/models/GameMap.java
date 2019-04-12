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
import java.util.ArrayList;
import java.util.Hashtable;

public class GameMap {

    public int tileSize;
    public int width, height;
    public Hashtable<String, int[]> layers = new Hashtable<>();
    public ArrayList<Spawner> spawners = new ArrayList<>();

    public GameMap(String filename) throws IOException, ParserConfigurationException, SAXException {
        File file = new File("assets/" + filename);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();
        Element mapElement = (Element)document.getElementsByTagName("map").item(0);

        tileSize = Integer.parseInt(mapElement.getAttribute("tilewidth"));
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

        NodeList spawnerElements = document.getElementsByTagName("object");
        for (int i = 0; i < spawnerElements.getLength(); i++) {
            Element spawnerElement = (Element)spawnerElements.item(i);
            NodeList properties = spawnerElement.getElementsByTagName("property");
            String enemies = "";
            int x, y, spawnRadius=5, enterRadius=3, exitRadius=10;
            for (int j = 0; j < properties.getLength();j++) {
                Element property = (Element) properties.item(j);
                switch (property.getAttribute("name")) {
                    case "enemies":
                        enemies = property.getAttribute("value");
                        break;
                    case "enterRadius":
                        enterRadius = Integer.parseInt(property.getAttribute("value"));
                        break;
                    case "exitRadius":
                        exitRadius = Integer.parseInt(property.getAttribute("value"));
                        break;
                    case "spawnRadius":
                        spawnRadius = Integer.parseInt(property.getAttribute("value"));
                        break;
                }
            }
            x = Integer.parseInt(spawnerElement.getAttribute("x")) / tileSize;
            y = Integer.parseInt(spawnerElement.getAttribute("y")) / tileSize;
            spawners.add(new Spawner(x, y, enterRadius, exitRadius, spawnRadius, enemies));
        }
    }

    public int tileAt(String layer, int x, int y) {
        return layers.get(layer)[getIndex(x, y)];
    }

    public int getIndex(int x, int y) {
        if (x > width - 1 || x < 0)  return -1;
        if (y > height - 1 || y < 0) return -1;
        return y*width + x;
    }

    public boolean collidable(int x, int y) {
        return layers.get("Collision")[getIndex(x, y)] != 0;
    }

}
