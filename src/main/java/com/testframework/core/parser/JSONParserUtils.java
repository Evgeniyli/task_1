package com.testframework.core.parser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class JSONParserUtils {

    /**
     * Parse JSON file
     *
     * @param fileName path to JSON file
     * @return Container with all values
     */
    public static Map<String, Map<String, String>> parseJSON(String fileName) {
        try {
            var objectMapper = new ObjectMapper();
            var jsonFile = new File(fileName);
            if (jsonFile.exists()) {
                return objectMapper.readValue(jsonFile, new TypeReference<>() {
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JsonNode parseJSONasNode(String fileName) {
        try {
            var objectMapper = new ObjectMapper();
            var jsonFile = new File(fileName);
            if (jsonFile.exists()) {
                return objectMapper.readTree(jsonFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
