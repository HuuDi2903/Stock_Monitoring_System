package com.myproject;

import java.util.HashMap;
import java.util.Map;

public class StockRealtimePriceView implements StockViewer {
    private final Map<String, Double> lastPrices = new HashMap<>();

    @Override
    public void onUpdate(StockPrice stockPrice) {
        // TODO: Implement logic to check if price has changed and log it
        String stockCode = stockPrice.getCode();
        double newPrice = stockPrice.getAvgPrice();

        if (!lastPrices.containsKey(stockCode)) {
            // First time updating for this stock
            lastPrices.put(stockCode, newPrice);
            Logger.logRealtime(stockCode, newPrice);
        } else {
            double lastPrice = lastPrices.get(stockCode);
            // Update if the price has changed
            if (newPrice != lastPrice) {
                lastPrices.put(stockCode, newPrice);
                Logger.logRealtime(stockCode, newPrice);
            }
        }
    }
}
