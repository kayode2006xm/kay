package com.forexai.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/api/market")
public class MarketController {

    @Value("${marketdata.api.baseurl}")
    private String marketDataApiBaseUrl;

    @Value("${marketdata.api.key:}") // Optional: If your provider requires an API key
    private String marketDataApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Get latest price for a symbol (e.g. EURUSD, GBPUSD).
     */
    @GetMapping("/price")
    public ResponseEntity<?> getLatestPrice(@RequestParam(defaultValue = "EURUSD") String symbol) {
        // Example: GET {baseurl}/price?symbol=EURUSD
        String url = String.format("%s/price?symbol=%s", marketDataApiBaseUrl, symbol);

        HttpHeaders headers = new HttpHeaders();
        if (marketDataApiKey != null && !marketDataApiKey.isEmpty()) {
            headers.set("Authorization", "Bearer " + marketDataApiKey);
        }
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Map.class
            );
            return ResponseEntity.ok(response.getBody());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Failed to fetch live market price.");
        }
    }

    /**
     * Get available pairs (instruments) from your provider.
     */
    @GetMapping("/pairs")
    public ResponseEntity<?> getAvailablePairs() {
        // Example: GET {baseurl}/pairs or /symbols
        String url = String.format("%s/pairs", marketDataApiBaseUrl);

        HttpHeaders headers = new HttpHeaders();
        if (marketDataApiKey != null && !marketDataApiKey.isEmpty()) {
            headers.set("Authorization", "Bearer " + marketDataApiKey);
        }
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                List.class
            );
            return ResponseEntity.ok(response.getBody());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Failed to fetch pairs list.");
        }
    }

    // You can add more endpoints as needed (orderbook, spreads, etc.)
}


