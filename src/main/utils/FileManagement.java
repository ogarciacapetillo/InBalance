package main.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by os344312 on 9/8/2016.
 */
public class FileManagement {

    public void createRequestFile(String xmlString, int index){
        try{
            try (PrintWriter out = new PrintWriter(System.getProperty("user.dir") + "\\temporal\\" + "Request" + Integer.toString(index) + ".xml")) {
                out.println(xmlString);
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createResponseFile(String xmlString, int index){
        try{
            try (PrintWriter out = new PrintWriter(System.getProperty("user.dir") + "\\temporal\\" + "Response" + Integer.toString(index) + ".xml")) {
                out.println(xmlString);
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createResultfile(String result){
        try{
            PrintWriter out = new PrintWriter(System.getProperty("user.dir") + "\\temporal\\Result.csv");
            out.print(result);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<HashMap<String,String>> parseFile(String fileName){
        XMLParser xmlParser = new XMLParser(fileName);
        return xmlParser.parseFile();
    }
}
