package com.myproject;

import java.util.*;

public class StockFeeder {
    private List<Stock> stockList = new ArrayList<>();
    private Map<String, List<StockViewer>> viewers = new HashMap<>();
    private static volatile StockFeeder instance = null;

    // TODO: Implement Singleton pattern
    private StockFeeder() {}

    // Double-check synchronization for thread safety
    public static StockFeeder getInstance() {
        // TODO: Implement Singleton logic
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
        // TODO: Implement adding a stock to stockList
        stockList.add(stock);
        viewers.computeIfAbsent(stock.getCode(), k -> new ArrayList<>());
        // String code = stock.getCode();
        // if (!viewers.containsKey(code)) {
        //     stockList.add(stock);
        //     viewers.put(stock.getCode(), new ArrayList<>());
        // }
    }

    public void registerViewer(String code, StockViewer stockViewer) {
        // TODO: Implement registration logic, including checking stock existence
        if(!viewers.containsKey(code)) {
            Logger.errorRegister(code);
            return;
        }

        List<StockViewer> stockViewers = viewers.get(code);
        // Prevent duplicate registrations
        if (stockViewers.contains(stockViewer)) {
            return; 
        }
    
        stockViewers.add(stockViewer);
    }    

    public void unregisterViewer(String code, StockViewer stockViewer) {
        // TODO: Implement unregister logic, including error logging
        if(!viewers.containsKey(code) || !viewers.get(code).contains(stockViewer)) {
            Logger.errorUnregister(code);
            return;
        }

        viewers.get(code).remove(stockViewer);
    }

    public void notify(StockPrice stockPrice) {
        // TODO: Implement notifying registered viewers about price updates
        String code = stockPrice.getCode();

        if(viewers.containsKey(code)) {
            for(StockViewer viewer : viewers.get(code)) {
                viewer.onUpdate(stockPrice);
            }
        }
    }
}
