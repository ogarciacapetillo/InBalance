package main.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by os344312 on 9/7/2016.
 */
public class PropertyLoader {

    public PropertyLoader() {}


    public String loadProperty(String property)  {
        ResourceBundle _prop;
        String value=null;
        try{
            // go through the resources files
            Iterator<String> iterator = enumResources(System.getProperty("user.dir") + "\\resource").iterator();
            while (iterator.hasNext()){
                try{
                    _prop = ResourceBundle.getBundle(iterator.next());
                    //logger.info("Bundle: "+_prop.getBaseBundleName());
                    value = _prop.getString(property);
                    break;
                }catch (Exception e){
                    if(e.getMessage().contains("NullPointer")){

                        throw new NullPointerException(e.getMessage());
                    }
                }

            }
        }catch(Exception ex){

            if(ex.getMessage().contains("NullPointer")){
                try {

                    throw new NullPointerException(ex.getMessage());
                } catch (Exception e) {

                    throw new NullPointerException(e.getMessage());
                }
            }

        }
        return value;
    }

    private static List<String> enumResources(String resourceLocation) throws IOException {
        List<String> result = new ArrayList<String>();
        File resourceFolder = new File(resourceLocation);
        File[] listOfFiles = resourceFolder.listFiles();
        String propReg = "[A-Za-z]+?\\.properties";

        for(File file:listOfFiles){
            if (file.getName().matches(propReg)) result.add(file.getName().substring(0,file.getName().indexOf(".")));
        }
        return result;
    }


}
