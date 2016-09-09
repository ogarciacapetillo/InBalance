package main.utils;

import java.util.*;

/**
 * Created by OS344312 on 9/8/2016.
 */
public class HashMapCompare {

    public void compareMaps(ArrayList<ArrayList<HashMap<String, String>>> requestMapList, ArrayList<ArrayList<HashMap<String, String>>> responseMapList){
        String requestKey="";
        String requestValue="";
        String responseKey="";
        String responseValue="";

        // Going tru the request Array Map list
        for (int a =0; a<requestMapList.size();a++) {
            ArrayList<HashMap<String, String>> reqMapList = (ArrayList<HashMap<String, String>>) requestMapList.get(a);
            for (int aa=0; aa<reqMapList.size(); aa++){
                HashMap<String,String> reqMap = (HashMap<String, String>) reqMapList.get(aa);
                requestValue= reqMap.get("dIndex");
                // Going tru response Array Map list
                for (int b = 0; b < responseMapList.size(); b++) {
                    ArrayList<HashMap<String, String>> resMapList = (ArrayList<HashMap<String, String>>) responseMapList.get(b);
                    for (int bb=0; bb<resMapList.size(); bb++) {
                        HashMap<String, String> resMap = (HashMap<String, String>) resMapList.get(bb);
                        if (resMap.get("eChecked").equalsIgnoreCase("false")) {
                            responseValue = resMap.get("dIndex");
                            if (requestValue.equalsIgnoreCase(responseValue.toString())) {
                                reqMap.replace("eChecked", "true");
                                //reqMap.replace("AssignmentKey", resMap.get("AssignmentKey"));
                                resMap.replace("eChecked", "true");
                                reqMap.replace("cRejectChoice",resMap.get("cRejectChoice"));
                                //break;
                            }
                        }
                    }
                }
            }
        }
        //Compare complete now, produce a csv with the final results
        String line="";
        String spliter=",";
        boolean header=false;
        for (int a =0; a<requestMapList.size();a++) {
            ArrayList<HashMap<String, String>> reqMapList = (ArrayList<HashMap<String, String>>) requestMapList.get(a);
            for (int aa = 0; aa < reqMapList.size(); aa++) {
                HashMap<String,String> reqMap = (HashMap<String, String>) reqMapList.get(aa);
                // Not the most elegant way to do it
                if (!header){
                    line = reqMap.keySet().toString();
                    line = line.replace("[","");
                    line = line.replace("]","");
                    line +=System.lineSeparator();
                    header =true;
                }
                line +=reqMap.values();
                line = line.replace("[","");
                line = line.replace("]","");
                line += System.lineSeparator();
            }
        }
        FileManagement fm = new FileManagement();
        fm.createResultfile(line);
        fm = null;
    }
}
