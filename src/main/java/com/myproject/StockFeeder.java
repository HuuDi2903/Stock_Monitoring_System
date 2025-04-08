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
        String code = stock.getCode();

        if(!stockList.contains(stock)) {
            stockList.add(stock);
            viewers.computeIfAbsent(code, k -> new ArrayList<>());
        } else {
            System.out.printf("[ERROR] Stock %s already exists!\n", code);
        }
    } 
    
    public void registerViewer(String code, StockViewer stockViewer) {
        if(stockViewer == null) Logger.errorRegister(code);    
    
        if(!viewers.containsKey(code)) {
            Logger.errorRegister(code);
            return;
        }

        List<StockViewer> viewers_list = viewers.get(code);

        /*
         1. One code can have different viewers
         2. One code cannot have same type of viewer
         */
        boolean hasSameTypeViewer = viewers_list.stream().anyMatch(viewer -> viewer.getClass() == stockViewer.getClass());       

        if (hasSameTypeViewer) {
            Logger.errorRegister(code);
        } else {
            viewers_list.add(stockViewer);
        }
    }

    public void unregisterViewer(String code, StockViewer stockViewer) {
        if(stockViewer == null) Logger.errorUnregister(code);   

        if (!viewers.containsKey(code)) {
            Logger.errorUnregister(code);
            return;
        }
    
        List<StockViewer> viewers_list = viewers.get(code);    
        if (viewers_list.remove(stockViewer)) {
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
