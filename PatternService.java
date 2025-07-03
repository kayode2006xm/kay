package com.forexai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@Service
public class PatternService {

    private static final String UPLOAD_DIR = "uploads/patterns/";

    @Value("${pattern.api.baseurl:}") // Optional: if you want to call an external pattern API
    private String patternApiBaseUrl;

    @Value("${pattern.api.key:}") // Optional: if your external API requires a key
    private String patternApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Save uploaded pattern file and details locally.
     * Optionally, can call an external API as well (see below).
     */
    public Map<String, Object> savePattern(String name, String description, MultipartFile file) throws IOException {
        String filename = null;
        String fileUrl = null;

        if (file != null && !file.isEmpty()) {
            // Ensure upload directory exists
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            // Clean filename and avoid path traversal
            filename = System.currentTimeMillis() + "_" + Paths.get(file.getOriginalFilename()).getFileName().toString();
            Path filePath = Paths.get(UPLOAD_DIR, filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Assuming the frontend can access /uploads/patterns/ via static mapping
            fileUrl = "/uploads/patterns/" + filename;
        }

        // Optionally, call an external API to register the pattern as well (uncomment if needed)
        /*
        if (patternApiBaseUrl != null && !patternApiBaseUrl.isEmpty()) {
            // Compose payload and call external API as needed
        }
        */

        // Optionally, save to a database here

        // Return result map
        Map<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("description", description);
        if (fileUrl != null) {
            result.put("fileUrl", fileUrl);
            result.put("filename", filename);
        }
        return result;
    }

    /**
     * Example: Analyze sentiment for a given text by proxying to an external API or running local logic.
     */
    public Map<String, Object> analyzeSentiment(String text) {
        // Example: Call an external sentiment analysis API
        if (patternApiBaseUrl != null && !patternApiBaseUrl.isEmpty()) {
            String url = patternApiBaseUrl + "/analyze-sentiment";
            Map<String, String> req = new HashMap<>();
            req.put("text", text);

            // Optionally add headers for API key
            // TODO: implement header logic if needed

            Map response = restTemplate.postForObject(url, req, Map.class);
            return response;
        }
        // Fallback: local dummy sentiment
        Map<String, Object> dummy = new HashMap<>();
        dummy.put("label", "neutral");
        dummy.put("polarity", 0.0);
        return dummy;
    }
}
