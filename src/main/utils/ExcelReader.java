package main.utils;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by OS344312 on 9/15/2016.
 */
public class ExcelReader {
    private HSSFWorkbook hssFWorkbook = null;

    private boolean allSheetsDone = false;

    public List<String> getSheetNames() {
        return sheetNames;
    }

    private List<String> sheetNames = new ArrayList<>();

    private Map<String, Map<String, Map<String, String>>> allSheetsMap = new LinkedHashMap<>();

    public ExcelReader(String fileName) throws IOException {
        FileInputStream fs = new FileInputStream(fileName);
        hssFWorkbook = new HSSFWorkbook(fs);
        int numberOfSheets = hssFWorkbook.getNumberOfSheets();
        for(int i = 0; i < numberOfSheets; i++) {
            sheetNames.add(hssFWorkbook.getSheetName(i));
        }
    }

    public Map<String, Map<String, Map<String, String>>> convertAllSheetsToMap() {

        if(allSheetsDone) {
            return allSheetsMap;
        } else {
            for(String sheetName : sheetNames) {
                if(allSheetsMap.containsKey(sheetName)) {
                    continue;
                }
                allSheetsMap.put(sheetName, convertSheetToMap(hssFWorkbook.getSheet(sheetName)));
            }
            allSheetsDone = true;
        }
        return allSheetsMap;
    }

    public Map<String, Map<String, String>> getSheetAsMap(String sheetName) {

        if(!sheetNames.contains(sheetName)) {
            throw new RuntimeException(sheetName + " not found");
        }

        Map<String, Map<String, String>> sheetMap = allSheetsMap.get(sheetName);

        if(sheetMap == null) {
            sheetMap = convertSheetToMap(hssFWorkbook.getSheet(sheetName));
            allSheetsMap.put(sheetName, sheetMap);
        }

        return sheetMap;
    }

    private Map<String, Map<String, String>> convertSheetToMap(HSSFSheet sheet) {
        Map<String, Map<String, String>> sheetMap = new LinkedHashMap();

        int rows = sheet.getPhysicalNumberOfRows();
        int cols = sheet.getRow(0).getLastCellNum();

        HSSFRow headerRow = sheet.getRow(0);
        List<String> headerRowNames = new ArrayList<>();

        for(int i = 0; i < cols; i++) {
            headerRowNames.add(headerRow.getCell(i).getStringCellValue());
        }
        //System.out.println(sheet.getSheetName());
        for(int i = 1; i < rows; i++) {
            Map<String, String> rowMap = new LinkedHashMap<>();
            HSSFRow dataRow = sheet.getRow(i);
            String key = dataRow.getCell(0).getStringCellValue();
            if(key == null || key.trim().length() == 0) {
                continue;
            }
            //System.out.println("TestCase = " + key);
            for(int j = 0; j < cols; j++) {
                HSSFCell cell = dataRow.getCell(j);
                if(cell != null) {
                    rowMap.put(headerRowNames.get(j), cell.toString());
                } else {
                    rowMap.put(headerRowNames.get(j), null);
                }

            }
            sheetMap.put(key, rowMap);
        }
        return sheetMap;
    }


}

