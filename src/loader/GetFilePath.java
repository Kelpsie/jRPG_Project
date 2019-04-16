package loader;

import java.io.File;
import java.util.ArrayList;

public class GetFilePath {

    public static File[] files;
    public static File file;


    public static void getFilePaths(String parentDirectory, ArrayList<String> fileList, ArrayList<String> nameList ) {
        file = new File(parentDirectory);
        if(file.isDirectory()){
            files = new File(parentDirectory).listFiles();
            if(files != null) {
                for(File file: files) {
                    String fileName = parentDirectory;
                    fileName += file.getName();
                    nameList.add(file.getName());
                    fileList.add(fileName);
                }
            }

        } else {
            System.out.println("The parent directory you supplied is not a directory!");
        }

    }



}