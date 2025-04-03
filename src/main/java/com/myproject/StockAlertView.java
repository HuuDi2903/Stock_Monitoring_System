package com.myproject;

import java.util.HashMap;
import java.util.Map;

public class StockAlertView implements StockViewer {
    private double alertThresholdHigh;
    private double alertThresholdLow;
    private Map<String, Double> lastAlertedPrices = new HashMap<>(); // TODO: Stores last alerted price per stock

    public StockAlertView(double highThreshold, double lowThreshold) {
        this.alertThresholdHigh = highThreshold;
        this.alertThresholdLow = lowThreshold;
    }

    @Override
    public void onUpdate(StockPrice stockPrice) {
        String stockCode = stockPrice.getCode();
        double price = stockPrice.getAvgPrice();
    
        if (price >= alertThresholdHigh || price <= alertThresholdLow) {
            // Get the last price we alerted about (if any)
            Double lastAlertedPrice = lastAlertedPrices.get(stockCode);
            
            // Alert if either:
            // 1. We've never alerted about this stock before, OR
            // 2. The price is different from the last alerted price, OR
            // 3. The last price was within normal range 
            if (lastAlertedPrice == null || lastAlertedPrice != price || 
                (lastAlertedPrice < alertThresholdHigh && lastAlertedPrice > alertThresholdLow)) {
                if (price >= alertThresholdHigh) {
                    alertAbove(stockCode, price);
                } else alertBelow(stockCode, price);
            }
        } else {
            // If price is in normal range, just update last alerted price
            lastAlertedPrices.put(stockCode, price);
        }
    }

    private void alertAbove(String stockCode, double price) {
        lastAlertedPrices.put(stockCode, price);
        // System.out.println("--------------ABOVE THRESHOLD ALERT--------------");
        Logger.logAlert(stockCode, price);
    }

    private void alertBelow(String stockCode, double price) {
        lastAlertedPrices.put(stockCode, price);
        // System.out.println("--------------BELOW THRESHOLD ALERT--------------");
        Logger.logAlert(stockCode, price);
    }

    // public double getThresholdHigh() {
    //     return alertThresholdHigh;
    // }

    // public double getThresholdLow() {
    //     return alertThresholdLow;
    // }
}
