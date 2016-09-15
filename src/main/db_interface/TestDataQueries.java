package main.db_interface;

import main.utils.FileManagement;
import main.utils.PropertyLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.readAllBytes;

/**
 * Created by os344312 on 9/7/2016.
 */
public interface TestDataQueries {

    public static final PropertyLoader property= new PropertyLoader();
    public FileManagement fm= new FileManagement();
    Path path = Paths.get(System.getProperty("user.dir")+"\\temporal\\query.txt");

    // Filtered query to return one record
    public static String getRequestQuery ="select MESSAGE from GV_ADMIN.yfs_export where "+ fm.readQueryString(path) +" and user_reference like 'InBalance=Request' order by export_key desc";

    //public static String getRequestQuery ="select * from GV_ADMIN.yfs_export where user_reference like 'InBalance=Request' order by export_key desc";

    // Filtered query to return one record
    public static String getResponseQuery="select MESSAGE from gv_admin.yfs_export where "+fm.readQueryString(path)+" and user_reference like 'InBalance=Response' order by export_key desc";

    //public static String getResponseQuery="select * from gv_admin.yfs_export where user_reference like 'InBalance=Response' order by export_key desc";
}
