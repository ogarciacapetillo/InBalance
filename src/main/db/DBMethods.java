package main.db;


import main.db_interface.TestDataQueries;
import main.utils.FileManagement;
import main.utils.PropertyLoader;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        //FileManagement fm = new FileManagement();
        //fm.purgeDirectory((File) (System.getProperty("user.dir") + "\\temporal"));
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

    public Map<String,String> getRequestMessage() {
        Map<String,String> mapReq=null;
        PreparedStatement inbalance = null;
        ResultSet resultSet=null;
        int iCount=0;
        try{
            inbalance = connectDB().prepareStatement(getRequestQuery);
            resultSet = inbalance.executeQuery();
            FileManagement fm = new FileManagement();
            while (resultSet.next()){
                iCount++;
                String requestXML = resultSet.getString("MESSAGE");
                fm.createRequestFile(requestXML,iCount);
                mapReq= fm.parseFile(System.getProperty("user.dir") + "\\temporal\\"+"Request"+ Integer.toString(iCount)+".xml");
                //System.out.println(requestXML);
            }
            //ArrayList<HashMap> list = resultSetToArrayList(resultSet);
            //System.out.println(list);
            closeOpenConnections();
            //return list;
        }catch (SQLException ex){
            //return null;
        }finally {
            return mapReq;
        }
    }

    public Map<String,String> getResponseMessage(){
        Map<String,String> mapRes=null;
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
                fm.createResponseFile(responseXML,iCount);
                mapRes=fm.parseFile(System.getProperty("user.dir") + "\\temporal\\"+"Response"+ Integer.toString(iCount)+".xml");
                //System.out.println(requestXML);
            }
            ArrayList<HashMap> list = resultSetToArrayList(resultSet);
            System.out.println(list);
            closeOpenConnections();
        }catch (SQLException ex){

        }finally {
            return mapRes;
        }

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
