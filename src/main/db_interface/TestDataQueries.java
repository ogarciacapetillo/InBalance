package main.db_interface;

import main.utils.PropertyLoader;

/**
 * Created by os344312 on 9/7/2016.
 */
public interface TestDataQueries {

    public static final PropertyLoader property= new PropertyLoader();

    // Filtered query to return one record
    public static String getRequestQuery ="select * from GV_ADMIN.yfs_export where message like '"+property.loadProperty("inbalance.order.number")+"' and user_reference like 'InBalance=Request' order by export_key desc";

    //public static String getRequestQuery ="select * from GV_ADMIN.yfs_export where user_reference like 'InBalance=Request' order by export_key desc";

    // Filtered query to return one record
    public static String getResponseQuery="select * from gv_admin.yfs_export where message like '"+property.loadProperty("inbalance.order.number")+"' and user_reference like 'InBalance=Response' order by export_key desc";

    //public static String getResponseQuery="select * from gv_admin.yfs_export where user_reference like 'InBalance=Response' order by export_key desc";
}
