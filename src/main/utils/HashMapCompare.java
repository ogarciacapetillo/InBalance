package main.utils;

import java.util.*;

/**
 * Created by OS344312 on 9/8/2016.
 */
public class HashMapCompare {

    public void compareMaps(ArrayList<Map<String,String>> requestMapList, ArrayList<Map<String,String>> responseMapList){
        String requestKey="";
        String requestValue="";
        String responseKey="";
        String responseValue="";

        // Going tru the request Array Map list
        for (int a =0; a<requestMapList.size();a++) {
            HashMap<String, String> reqMap = (HashMap<String, String>) requestMapList.get(a);
            for (Map.Entry<String, String> entry1 : reqMap.entrySet()) {
                if (entry1.getKey().equalsIgnoreCase("Index")) {
                    requestKey = entry1.getKey();
                    //int hash1 = System.identityHashCode(key1);
                    requestValue = entry1.getValue();
                }
            }
            // Going tru response Array Map list
            for (int b = 0; b < responseMapList.size(); b++) {
                HashMap<String, String> resMap = (HashMap<String, String>) responseMapList.get(b);
                for (Map.Entry<String, String> entry2 : resMap.entrySet()) {
                    if (entry2.getKey().equalsIgnoreCase("Index")){
                        responseKey = entry2.getKey();
                        //if (hash1 > System.identityHashCode(key2)) continue;
                        responseValue = entry2.getValue();
                        // compare value1 and value2;
                        if (requestValue.equalsIgnoreCase(responseValue.toString())){
                            System.out.println("");
                        }
                    }
                }
            }
        }
            /*Set<String> key = reqMap.keySet();
            Iterator iterator = key.iterator();
            while(iterator.hasNext()){
                String
            }*/



    }
}
