package application;

import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Handles API requests to the Gemini AI for generating hints.
 */
public class GeminiAPI {
    // Replace with a valid API key
    private static final String API_KEY = "AIzaSyBTRbTS4FqpBgrJ05dqfgp4g4yxxcdtxes";

    /**
     * Sends a request to the Gemini AI API and retrieves a response.
     *
     * @param requestBody The JSON request payload.
     * @return A hint extracted from the API response, or an error message.
     */
    public static String callGemini(String requestBody) {
        try {
            String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + API_KEY;
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Send the request payload
            try (OutputStream os = connection.getOutputStream()) {
                os.write(requestBody.getBytes("utf-8"));
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read API response
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    return extractSolution(response.toString());
                }
            } else {
                return "Error: API response code " + responseCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Extracts the generated hint from the API JSON response.
     *
     * @param jsonResponse The raw JSON response from the API.
     * @return A formatted hint, or an error message if extraction fails.
     */
    private static String extractSolution(String jsonResponse) {
        try {
            JSONObject json = new JSONObject(jsonResponse);

            if (!json.has("candidates")) {
                return "Error: No candidates found in response";
            }

            JSONObject candidate = json.getJSONArray("candidates").getJSONObject(0);

            if (!candidate.has("content")) {
                return "Error: No content found in candidate";
            }

            JSONObject content = candidate.getJSONObject("content");

            if (!content.has("parts")) {
                return "Error: No parts found in content";
            }

            String res = content.getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text");

            System.out.println("🔍 AI Hint: " + res);

            // Extract the last meaningful line from the response
            String[] lines = res.split("\n");
            return lines[lines.length - 1].trim();

        } catch (Exception e) {
            return "Error extracting solution";
        }
    }
}
