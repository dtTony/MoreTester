package more.util;


import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class JsonUtil {

    public static String jsonFileToString(String jsonFilePath){
        JsonParser parse = new JsonParser();
        JsonObject json= null;
        try {
            json = (JsonObject) parse.parse(new FileReader(jsonFilePath));
            return json.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Map jsonToMap(String jsonStr){
        return (Map) JSON.parse(jsonStr);
    }

    public static void main(String[] arg){
        System.out.println(jsonToMap(jsonFileToString("./test.json")));
    }
}
