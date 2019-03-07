package loader;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageLoader {

    public static BufferedImage BufferedImageLoader(String imagePath){
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read( new File(imagePath));
        } catch(IOException error){
            error.printStackTrace(); // <- better way to display error
        }
        return bufferedImage;
    }

    public static Image loadImage(String imagePath){ return new Image(imagePath); }

    public static ArrayList<Image> readTileMap(String sourceMap, ArrayList<Image> tilemaps, int resolution){
        BufferedImage bufferedTileMap = ImageLoader.BufferedImageLoader(sourceMap);
        for (int y = 0; y < bufferedTileMap.getHeight(); y+=resolution) {
            for (int x = 0; x < bufferedTileMap.getWidth(); x+=resolution){
                BufferedImage individualTileMap = bufferedTileMap.getSubimage(x, y, resolution, resolution);
                Image image = SwingFXUtils.toFXImage(individualTileMap, null);
                tilemaps.add(image);
            }
        }

        return tilemaps;
    }

    // deprecated, will keep for now
    public static ArrayList<Image> convertBufferedImageArrayToWritableImageArray(ArrayList<BufferedImage> scaledImages, ArrayList<Image> finalImageArray){

        for(BufferedImage scaledImage: scaledImages){
            Image fxImage = SwingFXUtils.toFXImage(scaledImage, null);


            finalImageArray.add(fxImage);
        }
        return finalImageArray;
    }

}

