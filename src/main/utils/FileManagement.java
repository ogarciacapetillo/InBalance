package main.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.*;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Files.readAllLines;

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

    private void queryString(String query){
        try{
            PrintWriter out = new PrintWriter(System.getProperty("user.dir") + "\\temporal\\query.txt");
            out.print(query);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void readInputFile(){
        PropertyLoader property= new PropertyLoader();
        String queryFilter=null;
        HashMap<String,String> orderList = new HashMap<>();
        try {
            ExcelReader excelReader= new ExcelReader(System.getProperty("user.dir")+"\\resource\\InputData.xls");
            // read as many sheets it has
            for (int i=0; i<excelReader.getSheetNames().size(); i++) {
                Map<String, Map<String, String>> sheetMap = excelReader.getSheetAsMap(excelReader.getSheetNames().get(i));
                queryFilter ="(";
                int index = 0;
                int col=0;
                int orCount =sheetMap.size()-1;
                int addOr =0;
                for(Map.Entry<String, Map<String, String>> rowMap : sheetMap.entrySet()) {
                    if(index++ == 0) {
                        for(String header : rowMap.getValue().keySet()) {
                            //System.out.print(header + "\t");
                        }
                    }
                    //System.out.println();
                    for(String value : rowMap.getValue().values()) {
                        col++;
                        //System.out.print(value + "\t");
                        if (col == (Integer.parseInt(property.loadProperty("inbalance.order.column")))){
                            //System.out.print("Col: "+ col+ "\t");
                            queryFilter += "message like '%"+value+"%' ";
                            col =0;
                            if (addOr < orCount){
                                queryFilter += " OR ";
                                addOr++;
                            }else{
                                queryFilter += ")";
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            queryString(queryFilter);
        }
    }

    public String readQueryString(Path path){
        List<String> query=null;
        String line=null;
        try {
            query = readAllLines(path);
            for (int i=0; i<query.size(); i++){
                line=query.get(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }
}
