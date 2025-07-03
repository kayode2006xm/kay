package com.forexai.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SentimentService {
    // Dummy logic, replace with real model or REST call to Python microservice
    public Map<String, Object> analyze(String text) {
        double polarity = 0.0;
        if (text.toLowerCase().contains("bullish")) polarity = 0.8;
        else if (text.toLowerCase().contains("bearish")) polarity = -0.8;
        String label = "Neutral";
        if (polarity > 0.1) label = "Bullish";
        else if (polarity < -0.1) label = "Bearish";
        Map<String, Object> result = new HashMap<>();
        result.put("label", label);
        result.put("polarity", polarity);
        return result;
    }
}
