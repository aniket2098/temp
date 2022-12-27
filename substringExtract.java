import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubstringExtractor {

    public static void main(String[] args) {
        // Input string
        String input = "This is a sample input string with ${placeholder1} and ${placeholder2}.";

        // Extract substrings surrounded by "${" and "}"
        ArrayList<String> placeholders = extractPlaceholders(input);

        // Print extracted substrings
        for (String placeholder : placeholders) {
            System.out.println(placeholder);
        }
    }

    public static ArrayList<String> extractPlaceholders(String input) {
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
