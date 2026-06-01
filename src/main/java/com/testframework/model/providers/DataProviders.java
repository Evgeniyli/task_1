package com.testframework.model.providers;
import com.testframework.core.parser.JSONParserUtils;
import java.util.Map;
import java.util.Objects;

public class DataProviders {
    private static final String BASE_PATH = "src/main/resources/test_data_web_elements";
    private static Map<String, Map<String, String>> elementsStore = null;
    private final static String JSON_FILE_NAME = String.format("%s/data_web_elements.json", BASE_PATH);

    /**
     * Get value by keys for new UI
     *
     * @param pageName    JSON object
     * @param elementName JSON object field
     * @return value of the specified parameters
     */
    public static String getValue(String pageName, String elementName) {
        if (elementsStore == null) {
            elementsStore = JSONParserUtils.parseJSON(JSON_FILE_NAME);
        }
        return Objects.requireNonNull(elementsStore).get(pageName).get(elementName);
    }
}
