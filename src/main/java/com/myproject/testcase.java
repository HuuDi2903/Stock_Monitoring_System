package com.myproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;

import java.util.Timer;


public class testcase {
    private static StockFeeder stockFeeder;
    private static PriceFetchManager priceFetchManager;

    public static void main(String[] args) {
        init();
        run();
    }
    
    private static void init() {
        stockFeeder = StockFeeder.getInstance();

        String filePath = "src/resources/stocks.json";

        HosePriceFetchLib hoseLib = new HosePriceFetchLib(filePath);
        List<String> hoseStockCodes = Arrays.asList("VIC", "VNM", "HPG", "FPT");
        HoseAdapter hoseAdapter = new HoseAdapter(hoseLib, hoseStockCodes);

        HnxPriceFetchLib hnxLib = new HnxPriceFetchLib(filePath);
        List<String> hnxStockCodes = Arrays.asList("SHB", "ACB", "PVS");
        HnxAdapter hnxAdapter = new HnxAdapter(hnxLib, hnxStockCodes);

        priceFetchManager = new PriceFetchManager(stockFeeder);
        priceFetchManager.addFetcher(hoseAdapter);
        priceFetchManager.addFetcher(hnxAdapter);

        // Stock VICstock = new Stock("VIC", "Vingroup");
        // stockFeeder.addStock(VICstock); 

        // stockFeeder.registerViewer("VIC", new StockAlertView(60000, 55000));
        // stockFeeder.registerViewer("VIC", new StockRealtimePriceView());
        // stockFeeder.registerViewer("VIC", new StockTickerView());

        // // Combine the lists to test all available stocks.
        // List<String> allStockCodes = new ArrayList<>();
        // allStockCodes.addAll(hoseStockCodes);
        // allStockCodes.addAll(hnxStockCodes);

        // // For each stock, add the stock to monitoring and register viewers.
        // for (String code : allStockCodes) {
        //     // Create a Stock instance (using code + " Company" as a sample company name).
        //     Stock stock = new Stock(code, code + " Company");
        //     stockFeeder.addStock(stock);

        //     // Register the viewers.
        //     stockFeeder.registerViewer(code, new StockAlertView(60000, 55000));
        //     stockFeeder.registerViewer(code, new StockRealtimePriceView());
        //     stockFeeder.registerViewer(code, new StockTickerView());
        // }

        // priceFetchManager.start(); // Start fetching stock prices


        // Combine the lists to monitor all available stocks.
        List<String> allStockCodes = new ArrayList<>();
        allStockCodes.addAll(hoseStockCodes);
        allStockCodes.addAll(hnxStockCodes);

        // Prepare lists to hold viewers for potential unregistration
        List<StockViewer> alertViewers = new ArrayList<>();
        List<StockViewer> realtimeViewers = new ArrayList<>();
        StockTickerView tickerView = new StockTickerView();

        // For each stock, add the stock to monitoring and register viewers.
        for (String code : allStockCodes) {
            // Create a Stock instance (using code + " Company" as a sample company name).
            Stock stock = new Stock(code, code + " Company");
            stockFeeder.addStock(stock);

            // Instantiate viewers
            StockAlertView alertView = new StockAlertView(60000, 55000);
            StockRealtimePriceView realtimeView = new StockRealtimePriceView();

            // Register the viewers.
            stockFeeder.registerViewer(code, alertView);
            stockFeeder.registerViewer(code, realtimeView);
            stockFeeder.registerViewer(code, tickerView);

            // Store the viewers for later unregistration
            alertViewers.add(alertView);
            realtimeViewers.add(realtimeView);
        }

        priceFetchManager.start(); // Start fetching stock prices

        // Schedule unregistration of certain viewers after a delay
        scheduleViewerUnregistration(allStockCodes, alertViewers, realtimeViewers, tickerView, 60 * 1000); // Unregister after 60 seconds
    }

    private static void run() {
        try {
            Thread.sleep(Long.MAX_VALUE); // Keeps the program running
        } catch (InterruptedException e) {
            priceFetchManager.stop();
            System.out.println("[ERROR] Stock monitoring service interrupted.");
        }
    }

    /**
     * Schedules the unregistration of viewers after a specified delay.
     *
     * @param stockCodes      List of stock codes being monitored
     * @param alertViewers    List of StockAlertView instances
     * @param realtimeViewers List of StockRealtimePriceView instances
     * @param tickerViewers   List of StockTickerView instances
     * @param delayMillis     Delay in milliseconds before unregistration
     */
    private static void scheduleViewerUnregistration(
            List<String> stockCodes,
            List<StockViewer> alertViewers,
            List<StockViewer> realtimeViewers,
            StockTickerView tickerViewers,
            long delayMillis) {

        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("[INFO] Starting viewer unregistration process...");

                for (int i = 0; i < stockCodes.size(); i++) {
                    String code = stockCodes.get(i);
                    
                    // Example: Unregister the StockAlertView for each stock
                    StockViewer alertView = alertViewers.get(i);
                    stockFeeder.unregisterViewer(code, alertView);
                    System.out.println("[INFO] Unregistered StockAlertView for stock: " + code);

                    // Similarly, you can choose to unregister other viewers
                    StockViewer realtimeView = realtimeViewers.get(i);
                    stockFeeder.unregisterViewer(code, realtimeView);
                    System.out.println("[INFO] Unregistered StockRealtimePriceView for stock: " + code);

                    StockViewer tickerView = tickerViewers;
                    stockFeeder.unregisterViewer(code, tickerView);
                    System.out.println("[INFO] Unregistered StockTickerView for stock: " + code);
                }

                System.out.println("[INFO] Viewer unregistration process completed.");
            }
        }, delayMillis);
    }
}
