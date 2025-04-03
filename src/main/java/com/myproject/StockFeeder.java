package com.myproject;

import java.util.*;

public class StockFeeder {
    private List<Stock> stockList = new ArrayList<>();
    private Map<String, List<StockViewer>> viewers = new HashMap<>();
    private static volatile StockFeeder instance = null;

    // TODO: Implement Singleton pattern
    private StockFeeder() {}

    public static StockFeeder getInstance() {
        // Double-check synchronization for thread safety
        if (instance == null) {
            synchronized (StockFeeder.class) {
                if (instance == null) {
                    instance = new StockFeeder();
                }
            }
        }

        return instance;
    }

    public void addStock(Stock stock) {
        Objects.requireNonNull(stock, "[ERROR] Stock can not be null");
        String code = stock.getCode();

        if(!stockList.contains(stock)) {
            stockList.add(stock);
            viewers.computeIfAbsent(code, k -> new ArrayList<>());
        } else {
            System.out.printf("[ERROR] Stock %s already exists!\n", code);
        }
    } 
    
    public void registerViewer(String code, StockViewer stockViewer) {
        Objects.requireNonNull(code, "[ERROR] Stock can not be null");
        Objects.requireNonNull(stockViewer, "[ERROR] StockViewer cannot be null");
    
        if(!viewers.containsKey(code)) {
            Logger.errorRegister(code);
            return;
        }

        List<StockViewer> viewers_list = viewers.get(code);
    
        // Only check for exact same object instance (optional)
        if (viewers_list.contains(stockViewer)) {
            Logger.errorRegister(code);
        } else {
            viewers_list.add(stockViewer);
            // System.out.printf("[INFO] Viewer registered for stock %s\n", code);
        }
    }

    public void unregisterViewer(String code, StockViewer stockViewer) {
        Objects.requireNonNull(code, "[ERROR] Stock can not be null");
        Objects.requireNonNull(stockViewer, "[ERROR] StockViewer cannot be null");

        if (!viewers.containsKey(code)) {
            Logger.errorUnregister(code);
            return;
        }
    
        List<StockViewer> viewers_list = viewers.get(code);    
        if (viewers_list.remove(stockViewer)) {
            // System.out.printf("[INFO] Viewer unregistered for stock %s\n", code);
        } else {
            Logger.errorUnregister(code);
        }
    }

    public void notify(StockPrice stockPrice) {
        String code = stockPrice.getCode();

        if(viewers.containsKey(code)) {
            List<StockViewer> viewers_list = viewers.get(code);
            for(StockViewer viewer : viewers_list) {
                viewer.onUpdate(stockPrice);
            }
        }
    }
}
