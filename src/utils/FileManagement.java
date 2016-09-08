package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InterruptedIOException;
import java.io.PrintWriter;

/**
 * Created by os344312 on 9/8/2016.
 */
public class FileManagement {

    public void createRequestFile(String xmlString, int index){
        try{
            PrintWriter out = new PrintWriter(System.getProperty("user.dir") + "\\temporal\\"+"Request"+ Integer.toString(index)+".xml");
            out.println(xmlString);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createResponseFile(String xmlString, int index){
        try{
            PrintWriter out = new PrintWriter(System.getProperty("user.dir") + "\\temporal\\"+"Response"+ Integer.toString(index)+".xml");
            out.println(xmlString);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void purgeDirectory(File dir){
        for (File file: dir.listFiles()) {
            if (file.isDirectory()) purgeDirectory(file);
            file.delete();
        }
    }

    public void parseFile(String fileName){
        XMLParser xmlParser = new XMLParser(fileName);
    }
}
