package main;
import main.db.DBMethods;
import main.utils.HashMapCompare;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by OS344312 on 9/7/2016.
 */
public class InBalance {

    static ArrayList<ArrayList<HashMap<String, String>>> requestMap;
    static ArrayList<ArrayList<HashMap<String, String>>> responseMap;

    /*public static final PropertyLoader property= new PropertyLoader();

    public static final String DRIVER_URL =property.loadProperty("inbalance.conn.driver");
    public static final String CONNECTION_URL=property.loadProperty("inbalance.conn.url");
    public static final String CONNECTION_USERNAME=property.loadProperty("inbalance.conn.username");
    public static final String CONNECTION_PASSWORD=property.loadProperty("inbalance.conn.password");*/

    public static void main(String[] args) throws SQLException {

        DBMethods dbMethods;
        dbMethods = new DBMethods();
        HashMapCompare comparator = new HashMapCompare();
        // Read Excel File and extract the order numbers pass. and execute the process for that particular order
        requestMap = dbMethods.getRequestMessage();
        System.out.println("-----------------------------------------");
        responseMap = dbMethods.getResponseMessage();
        comparator.compareMaps(requestMap,responseMap);
        //System.out.println(requestMap.size());
        //System.out.println(responseMap.size());
        System.out.println("-----------------------------------------");
        System.out.println("---------------- Complete ---------------");
        System.out.println("-----------------------------------------");
    }

}

