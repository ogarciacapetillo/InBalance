package main.db;


import main.db_interface.TestDataQueries;
import main.utils.FileManagement;
import main.utils.PropertyLoader;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by OS344312 on 9/7/2016.
 */

public class DBMethods implements TestDataQueries {

    public static final PropertyLoader property= new PropertyLoader();

    public static final String DRIVER_URL =property.loadProperty("inbalance.conn.driver");
    public static final String CONNECTION_URL=property.loadProperty("inbalance.conn.url");
    public static final String CONNECTION_USERNAME=property.loadProperty("inbalance.conn.username");
    public static final String CONNECTION_PASSWORD=property.loadProperty("inbalance.conn.password");

    private Connection connection;


    public DBMethods(){
        this.connection = null;
        try {
            purgeDirectory(System.getProperty("user.dir") + "\\temporal");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //FileManagement fm = new FileManagement();
        //fm.purgeDirectory((File) (System.getProperty("user.dir") + "\\temporal"));
    }

    private static void purgeDirectory(String resourceLocation) throws IOException {
        File resourceFolder = new File(resourceLocation);
        File[] listOfFiles = resourceFolder.listFiles();
        String propReg = "([\\w\\d]+)*\\.xml";
        try{
            for(File file:listOfFiles){
                if (file.delete()){
                    //System.out.println("file deleted: "+file.getName());
                }else{
                    System.out.println("Error");
                }
            }
        }catch (Exception e){

        }


    }
    private int checkDriver(){

        try{
            Class.forName(DRIVER_URL);
            return 1;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private Connection connectDB(){
        if (checkDriver()>0){
            try{
                if (connection ==null || connection.isClosed()) {
                    try {
                        connection = DriverManager.getConnection(CONNECTION_URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
                        if (connection != null && !connection.isClosed()) {
                            System.out.println("Connection Established Successfully");
                            return connection;
                        }
                    } catch (SQLException e) {
                        System.out.println("Connection Failed! please check console");
                        e.printStackTrace();
                        return null;
                    }
                }
            } catch (SQLException e) {
                System.out.println("Connection Failed! please check console");
                e.printStackTrace();
                return null;
            }

        }
        return connection;
    }

    public static ArrayList resultSetToArrayList(ResultSet rs) throws SQLException{
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        ArrayList<HashMap> list = new ArrayList(50);
        while (rs.next()){
            System.out.println("Reading row");
            HashMap row = new HashMap(columns);
            for(int i=1; i<=columns; ++i){
                row.put(md.getColumnName(i),rs.getObject(i));
            }
            list.add(row);
        }
        return list;
    }

    public ArrayList<ArrayList<HashMap<String, String>>> getRequestMessage() {
        ArrayList<HashMap<String,String>> list=new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> fatherList = new  ArrayList<ArrayList<HashMap<String, String>>>();
        PreparedStatement inbalance = null;
        ResultSet resultSet=null;
        int iCount=0;
        try{
            FileManagement fm = new FileManagement();
            fm.readInputFile();
            inbalance = connectDB().prepareStatement(getRequestQuery);
            resultSet = inbalance.executeQuery();
            while (resultSet.next()){
                iCount++;
                String requestXML = resultSet.getString("MESSAGE");
                fm.createRequestFile(requestXML,iCount);
                list= fm.parseFile(System.getProperty("user.dir") + "\\temporal\\"+"Request"+ Integer.toString(iCount)+".xml");
                fatherList.add(list);
                //list.add(mapReq);
                //System.out.println(requestXML);
            }
            //ArrayList<HashMap> list = resultSetToArrayList(resultSet);
            //System.out.println(list);
            closeOpenConnections();
            //return list;
        }catch (SQLException ex){
            //return null;
        }
        return fatherList;
    }

    public ArrayList<ArrayList<HashMap<String, String>>> getResponseMessage(){
        ArrayList<HashMap<String,String>> list=new ArrayList<>();
        ArrayList<ArrayList<HashMap<String, String>>> fatherList = new  ArrayList<ArrayList<HashMap<String, String>>>();
        PreparedStatement sRespose=null;
        ResultSet resultSet=null;
        int iCount=0;
        try{
            sRespose = connectDB().prepareStatement(getResponseQuery);
            resultSet = sRespose.executeQuery();
            FileManagement fm = new FileManagement();
            while (resultSet.next()){
                iCount++;
                String responseXML = resultSet.getString("MESSAGE");
                // Check the leng of the XML file
                if (responseXML.length()<50){
                    // Error file not properly build
                    break;
                }
                fm.createResponseFile(responseXML,iCount);
                list=fm.parseFile(System.getProperty("user.dir") + "\\temporal\\"+"Response"+ Integer.toString(iCount)+".xml");
                fatherList.add(list);
                //list.add(mapRes);
                //System.out.println(requestXML);
            }

            //ArrayList<HashMap> list = resultSetToArrayList(resultSet);
            //System.out.println(list);
            closeOpenConnections();
        }catch (SQLException ex){

        }
        return fatherList;

    }

    public void closeOpenConnections(){
        try{
            if( connection!= null){
                connection.close();
                System.out.println("Active Connection Closed");

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    static{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.setProperty("current.date", dateFormat.format(new Date()));
    }


}
