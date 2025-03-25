package com.myproject;

import java.util.HashMap;
import java.util.Map;

public class StockAlertView implements StockViewer {
    private double alertThresholdHigh;
    private double alertThresholdLow;
    private Map<String, Double> lastAlertedPrices = new HashMap<>(); // TODO: Stores last alerted price per stock

    public StockAlertView(double highThreshold, double lowThreshold) {
        // TODO: Implement constructor
        this.alertThresholdHigh = highThreshold;
        this.alertThresholdLow = lowThreshold;
    }

    @Override
    public void onUpdate(StockPrice stockPrice) {
        // TODO: Implement alert logic based on threshold conditions
        String stockCode = stockPrice.getCode();
        double price = stockPrice.getAvgPrice();
        
        if (price > alertThresholdHigh) {
            // Check if this alert is different from the last one
            if (!lastAlertedPrices.containsKey(stockCode) || lastAlertedPrices.get(stockCode) != price) {
                alertAbove(stockCode, price);
            }
        } else if (price < alertThresholdLow) {
            // Check if this alert is different from the last one
            if (!lastAlertedPrices.containsKey(stockCode) || lastAlertedPrices.get(stockCode) != price) {
                alertBelow(stockCode, price);
            }
        }
    }

    private void alertAbove(String stockCode, double price) {
        // TODO: Call Logger to log the alert
        // Logger.notImplementedYet("alertAbove");
        lastAlertedPrices.put(stockCode, price);
        System.out.println("--------------ABOVE THRESHOLD ALERT--------------");
        Logger.logAlert(stockCode, price);
    }

    private void alertBelow(String stockCode, double price) {
        // TODO: Call Logger to log the alert
        // Logger.notImplementedYet("alertBelow");
        lastAlertedPrices.put(stockCode, price);
        System.out.println("--------------BELOW THRESHOLD ALERT--------------");
        Logger.logAlert(stockCode, price);
    }

    public double getThresholdHigh() {
        return alertThresholdHigh;
    }

    public double getThresholdLow() {
        return alertThresholdLow;
    }
}
