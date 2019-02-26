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
            System.out.println(error);
        }
        return bufferedImage;
    }

    public static ArrayList<BufferedImage> readTileMap(String sourceMap, ArrayList<BufferedImage> tilemaps, int resolution , Boolean isScaled){
        BufferedImage bufferedTileMap = ImageLoader.BufferedImageLoader(sourceMap);
        for (int y = 0; y < bufferedTileMap.getHeight(); y+=resolution) {
            System.out.println("readTileMap(): y= " + y);
            for (int x = 0; x < bufferedTileMap.getWidth(); x+=resolution){
                System.out.println("readTileMap(): x= " + x);
                BufferedImage individualTileMap = bufferedTileMap.getSubimage(x, y, resolution, resolution);
                tilemaps.add(individualTileMap);
            }
        }

        return tilemaps;
    }

    public static ArrayList<Image> convertBufferedImageArrayToWritableImageArray(ArrayList<BufferedImage> scaledImages, ArrayList<Image> finalImageArray){

        for(BufferedImage scaledImage: scaledImages){
            Image fxImage = SwingFXUtils.toFXImage(scaledImage, null);


            finalImageArray.add(fxImage);
        }
        return finalImageArray;
    }

}

