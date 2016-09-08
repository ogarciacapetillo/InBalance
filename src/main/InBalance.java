package main;

import db.DBMethods;
import utils.PropertyLoader;

import java.sql.SQLException;

/**
 * Created by OS344312 on 9/7/2016.
 */
public class InBalance {

    public static final PropertyLoader property= new PropertyLoader();

    public static final String DRIVER_URL =property.loadProperty("inbalance.conn.driver");
    public static final String CONNECTION_URL=property.loadProperty("inbalance.conn.url");
    public static final String CONNECTION_USERNAME=property.loadProperty("inbalance.conn.username");
    public static final String CONNECTION_PASSWORD=property.loadProperty("inbalance.conn.password");

    static public void main(String[] args) throws SQLException {
        DBMethods dbMethods;
        dbMethods = new DBMethods();
        dbMethods.getRequestMessage();
        dbMethods.getResponseMessage();
    }

}
