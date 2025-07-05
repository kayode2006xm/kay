package com.forexai.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/api/market")
public class MarketDataController {

    @Value("${marketdata.api.baseurl}")
    private String marketDataApiBaseUrl;

    @Value("${marketdata.api.key}")
    private String marketDataApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    /** Get OHLCV candle data for a symbol and interval */
    @GetMapping("/candles")
    public ResponseEntity<?> getCandles(
            @RequestParam(defaultValue = "EURUSD") String symbol,
            @RequestParam(defaultValue = "1min") String interval
    ) {
        // Twelve Data expects intervals like 1min, 5min, 15min, etc.
        String url = String.format("%s/time_series?symbol=%s&interval=%s&outputsize=100&apikey=%s",
                marketDataApiBaseUrl,
                symbol,
                interval,
                marketDataApiKey);

        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Object values = response.getBody().get("values");
                return ResponseEntity.ok(values);
            }
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No candle data found");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Failed to fetch candle data.");
        }
    }

    /** Get latest price for a symbol */
    @GetMapping("/price")
    public ResponseEntity<?> getLatestPrice(@RequestParam(defaultValue = "EURUSD") String symbol) {
        String url = String.format("%s/price?symbol=%s&apikey=%s",
                marketDataApiBaseUrl,
                symbol,
                marketDataApiKey);

        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return ResponseEntity.ok(response.getBody());
            }
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No price data found");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Failed to fetch price.");
        }
    }

    /** Get available forex pairs */
    @GetMapping("/pairs")
    public ResponseEntity<?> getAvailablePairs() {
        String url = String.format("%s/forex_pairs?apikey=%s",
                marketDataApiBaseUrl,
                marketDataApiKey);

        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Object data = response.getBody().get("data");
                return ResponseEntity.ok(data);
            }
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("No pairs data found");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Failed to fetch pairs list.");
        }
    }
}

