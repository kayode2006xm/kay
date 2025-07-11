import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Scanner;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    private static final String REQUEST_URL = "https://api.twelvedata.com/time_series?symbol=AAPL&interval=1min&outputsize=12&apikey=YOUR_API_KEY_HERE";

    public static void main(String[] args) throws Exception {
        URL requestURL = new URL(REQUEST_URL);
        HttpURLConnection connection = (HttpURLConnection)requestURL.openConnection();
        StringBuffer responseData = new StringBuffer();
        JSONParser parser = new JSONParser();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "twelve_java/1.0");
        connection.connect();

        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("Request failed. Error: " + connection.getResponseMessage());
        }

        Scanner scanner = new Scanner(requestURL.openStream());
        while (scanner.hasNextLine()) {
            responseData.append(scanner.nextLine());
        }

        JSONObject json = (JSONObject) parser.parse(responseData.toString());
        JSONObject meta = (JSONObject) json.get("meta");
        JSONArray values = (JSONArray) json.get("values");

        connection.disconnect();
    }
}