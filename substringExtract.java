import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

    public static Map<String, String> getParameterMap(String input, String json) throws IOException {
        // Extract placeholder keys from the input string
        ArrayList<String> placeholders = extractPlaceholders(input);

        // Parse the JSON string
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        // Create a map to store the placeholder keys and values
        Map<String, String> parameterMap = new HashMap<>();

        // Iterate through the placeholder keys
        for (String placeholder : placeholders) {
            // Extract the value for the placeholder from the JSON object
            JsonNode value = root.path(placeholder);
            String valueString = value.asText();

            // Add the placeholder key and value to the map
            parameterMap.put(placeholder, valueString);
        }

        return parameterMap;
    }

    private static ArrayList<String> extractPlaceholders(String input) {
        ArrayList<String> placeholders = new ArrayList<>();

        // Use a regular expression to match substrings surrounded by "${" and "}"
        Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(input);

        // Find all matches
        while (matcher.find()) {
            placeholders.add(matcher.group(1)); // Add the matched substring to the list
        }

        return placeholders;
    }
}
