package com.trello.utils.dataReader;

import com.trello.utils.logs.LogsManager;
import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class JsonReader {
    String jsonReader;
    String jsonFileName;
    private final String TEST_DATA_PATH = "src/test/resources/test-data/";

    public JsonReader(String jsonFileName) {
        try {
            JSONObject data = (JSONObject) new JSONParser().parse(new FileReader(TEST_DATA_PATH + jsonFileName + ".json"));
            jsonReader = data.toJSONString();
        } catch (Exception e) {
            LogsManager.error("Error reading JSON file: ", jsonFileName, e.getMessage());
            jsonReader = "{}";
        }
    }

    public String getJsonData(String jsonPath) {
        try {
            return JsonPath.read(jsonReader,jsonPath);
        } catch (Exception e) {
            LogsManager.error("Error reading JSON file: ", jsonFileName, e.getMessage());
            return "";
        }
    }
}