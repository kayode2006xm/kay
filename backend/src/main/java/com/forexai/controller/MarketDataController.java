package com.forexai.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.time.Instant;

/**
 * MarketDataController provides endpoints for live market candle data for the TradingView chart.
 * In production, integrate with a real data provider (e.g., TwelveData, AlphaVantage, broker API).
 */
@RestController
@RequestMapping("/api/market")
public class MarketDataController {

    /**
     * Example endpoint for fetching recent candles for a forex symbol and interval.
     * In production, fetch real data from your data provider.
     *
     * @param symbol   e.g., "EURUSD"
     * @param interval e.g., "1m", "5m", "15m"
     * @return list of OHLC candles in TradingView format
     */
    @GetMapping("/candles")
    public ResponseEntity<List<Map<String, Object>>> getCandles(
            @RequestParam(defaultValue = "EURUSD") String symbol,
            @RequestParam(defaultValue = "1m") String interval
    ) {
        // TODO: Replace with real API integration for live data.
        // Demo: generate 20 fake candles for now
        List<Map<String, Object>> candles = new ArrayList<>();
        long currentTime = Instant.now().getEpochSecond();
        double open = 1.1000;
        Random rand = new Random();

        for (int i = 0; i < 20; i++) {
            double high = open + 0.0015 * rand.nextDouble();
            double low = open - 0.0015 * rand.nextDouble();
            double close = low + (high - low) * rand.nextDouble();
            Map<String, Object> candle = new HashMap<>();
            candle.put("time", currentTime - (19 - i) * 60); // 1 min interval
            candle.put("open", round(open));
            candle.put("high", round(high));
            candle.put("low", round(low));
            candle.put("close", round(close));
            candles.add(candle);
            open = close; // next candle opens where previous closed
        }

        return ResponseEntity.ok(candles);
    }

    private double round(double val) {
        return Math.round(val * 100000d) / 100000d;
    }
}
