package loader;

import java.io.File;
import java.util.ArrayList;

public class GetFilePath {

    public static File[] files;
    public static File file;


    public static void getFilePaths(String parentDirectory, ArrayList<String> fileList) {
        file = new File(parentDirectory);
        if(file.isDirectory()){
            files = new File(parentDirectory).listFiles();
            if(files != null) {
                for(File file: files){
                    String fileName = "assets/audio/";
                    fileName += file.getName();
                    System.out.println(fileName);
                    fileList.add(fileName);
                }
            }

        } else {
            System.out.println("The parent directory you supplied is not a directory!");
        }

    }

}