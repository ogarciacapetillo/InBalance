package main.db_interface;

/**
 * Created by os344312 on 9/7/2016.
 */
public interface TestDataQueries {

    public static String getRequestQuery ="select * from GV_ADMIN.yfs_export where user_reference like 'InBalance=Request' order by export_key desc";

    public static String getResponseQuery="select * from gv_admin.yfs_export where export_key = '2016090807195393545568  ' and user_reference like 'InBalance=Response' order by export_key desc";
}
