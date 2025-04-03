// package com.myproject;

// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;
// import java.util.TimerTask;

// import java.util.Timer;


// public class testcase {
//     private static StockFeeder stockFeeder;
//     private static PriceFetchManager priceFetchManager;

//     public static void main(String[] args) {
//         init();
//         run();
//     }
    
//     private static void init() {
//         stockFeeder = StockFeeder.getInstance();

//         String filePath = "src/resources/stocks.json";

//         HosePriceFetchLib hoseLib = new HosePriceFetchLib(filePath);
//         List<String> hoseStockCodes = Arrays.asList("VIC", "VNM", "HPG", "FPT");
//         HoseAdapter hoseAdapter = new HoseAdapter(hoseLib, hoseStockCodes);

//         HnxPriceFetchLib hnxLib = new HnxPriceFetchLib(filePath);
//         List<String> hnxStockCodes = Arrays.asList("SHB", "ACB", "PVS");
//         HnxAdapter hnxAdapter = new HnxAdapter(hnxLib, hnxStockCodes);

//         priceFetchManager = new PriceFetchManager(stockFeeder);
//         priceFetchManager.addFetcher(hoseAdapter);
//         priceFetchManager.addFetcher(hnxAdapter);

//         // Stock VICstock = new Stock("VIC", "Vingroup");
//         // stockFeeder.addStock(VICstock); 

//         // stockFeeder.registerViewer("VIC", new StockAlertView(60000, 55000));
//         // stockFeeder.registerViewer("VIC", new StockRealtimePriceView());
//         // stockFeeder.registerViewer("VIC", new StockTickerView());

//         // // Combine the lists to test all available stocks.
//         // List<String> allStockCodes = new ArrayList<>();
//         // allStockCodes.addAll(hoseStockCodes);
//         // allStockCodes.addAll(hnxStockCodes);

//         // // For each stock, add the stock to monitoring and register viewers.
//         // for (String code : allStockCodes) {
//         //     // Create a Stock instance (using code + " Company" as a sample company name).
//         //     Stock stock = new Stock(code, code + " Company");
//         //     stockFeeder.addStock(stock);

//         //     // Register the viewers.
//         //     stockFeeder.registerViewer(code, new StockAlertView(60000, 55000));
//         //     stockFeeder.registerViewer(code, new StockRealtimePriceView());
//         //     stockFeeder.registerViewer(code, new StockTickerView());
//         // }

//         // priceFetchManager.start(); // Start fetching stock prices


//         // Combine the lists to monitor all available stocks.
//         List<String> allStockCodes = new ArrayList<>();
//         allStockCodes.addAll(hoseStockCodes);
//         allStockCodes.addAll(hnxStockCodes);

//         // Prepare lists to hold viewers for potential unregistration
//         List<StockViewer> alertViewers = new ArrayList<>();
//         List<StockViewer> realtimeViewers = new ArrayList<>();
//         StockTickerView tickerView = new StockTickerView();

//         // For each stock, add the stock to monitoring and register viewers.
//         for (String code : allStockCodes) {
//             // Create a Stock instance (using code + " Company" as a sample company name).
//             Stock stock = new Stock(code, code + " Company");
//             stockFeeder.addStock(stock);

//             // Instantiate viewers
//             StockAlertView alertView = new StockAlertView(60000, 55000);
//             StockRealtimePriceView realtimeView = new StockRealtimePriceView();

//             // Register the viewers.
//             stockFeeder.registerViewer(code, alertView);
//             stockFeeder.registerViewer(code, realtimeView);
//             stockFeeder.registerViewer(code, tickerView);

//             // Store the viewers for later unregistration
//             alertViewers.add(alertView);
//             realtimeViewers.add(realtimeView);
//         }

//         priceFetchManager.start(); // Start fetching stock prices

//         // Schedule unregistration of certain viewers after a delay
//         scheduleViewerUnregistration(allStockCodes, alertViewers, realtimeViewers, tickerView, 60 * 1000); // Unregister after 60 seconds
//     }

//     private static void run() {
//         try {
//             Thread.sleep(Long.MAX_VALUE); // Keeps the program running
//         } catch (InterruptedException e) {
//             priceFetchManager.stop();
//             System.out.println("[ERROR] Stock monitoring service interrupted.");
//         }
//     }

//     /**
//      * Schedules the unregistration of viewers after a specified delay.
//      *
//      * @param stockCodes      List of stock codes being monitored
//      * @param alertViewers    List of StockAlertView instances
//      * @param realtimeViewers List of StockRealtimePriceView instances
//      * @param tickerViewers   List of StockTickerView instances
//      * @param delayMillis     Delay in milliseconds before unregistration
//      */
//     private static void scheduleViewerUnregistration(
//             List<String> stockCodes,
//             List<StockViewer> alertViewers,
//             List<StockViewer> realtimeViewers,
//             StockTickerView tickerViewers,
//             long delayMillis) {

//         Timer timer = new Timer(true);
//         timer.schedule(new TimerTask() {
//             @Override
//             public void run() {
//                 System.out.println("[INFO] Starting viewer unregistration process...");

//                 for (int i = 0; i < stockCodes.size(); i++) {
//                     String code = stockCodes.get(i);
                    
//                     // Example: Unregister the StockAlertView for each stock
//                     StockViewer alertView = alertViewers.get(i);
//                     stockFeeder.unregisterViewer(code, alertView);
//                     System.out.println("[INFO] Unregistered StockAlertView for stock: " + code);

//                     // Similarly, you can choose to unregister other viewers
//                     StockViewer realtimeView = realtimeViewers.get(i);
//                     stockFeeder.unregisterViewer(code, realtimeView);
//                     System.out.println("[INFO] Unregistered StockRealtimePriceView for stock: " + code);

//                     StockViewer tickerView = tickerViewers;
//                     stockFeeder.unregisterViewer(code, tickerView);
//                     System.out.println("[INFO] Unregistered StockTickerView for stock: " + code);
//                 }

//                 System.out.println("[INFO] Viewer unregistration process completed.");
//             }
//         }, delayMillis);
//     }
// }

// Author: NTM

package com.myproject;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

interface Testcase {
    boolean run();
    boolean run_and_show(int index);
}

class GetOutputStream {
    private static ByteArrayOutputStream outputStream;
    private static PrintStream printStream;
    private static PrintStream originalOut;

    protected static void newOuput() {
        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
        originalOut = System.out;
        System.setOut(printStream);
    }

    protected static String getOutput() {
        String capturedOutput = outputStream.toString().trim();
        System.setOut(originalOut);
        return capturedOutput;
    }
}

abstract class MyTestcase extends GetOutputStream implements Testcase {
    protected static StockTickerView stockTickerView = new StockTickerView();
    protected static StockRealtimePriceView stockRealtimePriceView = new StockRealtimePriceView();
    //protected static StockAlertView stockAlertView = new StockAlertView(1000, 300);
    protected static String expect;
    protected static String output;
    @Override
    public boolean run_and_show(int index) {
        try {
            System.out.print("Testcase " + index + ": ");
            if (run()) {
                System.out.println("Pass");
                return true;
            } else {
                System.out.println("Fail");
                System.out.println("Output:");
                System.out.println(output);
                System.out.println("Expect:");
                System.out.println(expect);
                return false;
            }
        } catch (Exception e) {
            String deleteString = getOutput();
            System.out.println("Error");
            return false;
        }
    }
}

class Testcase1 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "Equal";
        StockFeeder stockFeeder1 = StockFeeder.getInstance();
        StockFeeder stockFeeder2 = StockFeeder.getInstance();
        if (stockFeeder1 == stockFeeder2) output = "Equal"; else output = "Unequal";
        return expect.equals(output);
    }
}

class Testcase2 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "";
        newOuput();
        Stock stock = new Stock("Jennie", "rubyjane");
        StockFeeder stockFeeder = StockFeeder.getInstance();
        stockFeeder.addStock(stock);
        stockFeeder.registerViewer("Jennie", stockTickerView);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase3 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[WARNING] Error when registering with Lisa";
        newOuput();
        Stock stock = new Stock("Lisa", "rockstar");
        StockFeeder stockFeeder = StockFeeder.getInstance();
        stockFeeder.registerViewer("Lisa", stockTickerView);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase4 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "";
        newOuput();
        Stock stock = new Stock("Jisoo", "flower");
        StockFeeder stockFeeder = StockFeeder.getInstance();
        stockFeeder.addStock(stock);
        stockFeeder.registerViewer("Jisoo", stockTickerView);
        stockFeeder.unregisterViewer("Jisoo", stockTickerView);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase5 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[WARNING] Error when unregistering with Rose";
        newOuput();
        Stock stock = new Stock("Rose", "APT");
        StockFeeder stockFeeder = StockFeeder.getInstance();
        stockFeeder.addStock(stock);
        stockFeeder.unregisterViewer("Rose", stockTickerView);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase6 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[WARNING] Error when registering with BK";
        newOuput();
        Stock stock = new Stock("BK", "hcmut");
        StockFeeder stockFeeder = StockFeeder.getInstance();
        stockFeeder.registerViewer("BK", stockTickerView);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase7 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[WARNING] Error when unregistering with BK";
        newOuput();
        Stock stock = new Stock("BK", "hcmut");
        StockFeeder stockFeeder = StockFeeder.getInstance();
        StockViewer stockViewer = new StockTickerView();
        stockFeeder.unregisterViewer("BK", stockViewer);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase8 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[REALTIME] Realtime Price Update: YG is now $500.5";
        newOuput();
        StockPrice firsStockPrice = new StockPrice("YG", 500, 100, 999);
        StockPrice stockPrice = new StockPrice("YG", 500.5, 100, 999);
        stockRealtimePriceView.onUpdate(firsStockPrice); 
        stockRealtimePriceView.onUpdate(stockPrice); 
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase9 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[REALTIME] Realtime Price Update: Hybe is now $500.5";
        newOuput();
        StockPrice firsStockPrice = new StockPrice("Hybe", 500, 100, 999);
        StockPrice stockPrice = new StockPrice("Hybe", 500.5, 100, 999);
        stockRealtimePriceView.onUpdate(firsStockPrice);
        stockRealtimePriceView.onUpdate(stockPrice);
        stockRealtimePriceView.onUpdate(stockPrice);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase10 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[REALTIME] Realtime Price Update: FPT_10 is now $500.5\n[REALTIME] Realtime Price Update: FPT_10 is now $510.5";
        newOuput();
        StockPrice stockPrice1 = new StockPrice("FPT_10", 500.5, 100, 999);
        StockPrice stockPrice2 = new StockPrice("FPT_10", 510.5, 100, 999);
        StockPrice firsStockPrice = new StockPrice("FPT_10", 500, 100, 999);
        stockRealtimePriceView.onUpdate(firsStockPrice);
        stockRealtimePriceView.onUpdate(stockPrice1);
        stockRealtimePriceView.onUpdate(stockPrice2);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase11 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[REALTIME] Realtime Price Update: FPT_11 is now $500.5";
        newOuput();
        StockRealtimePriceView stockRealtimePriceView = new StockRealtimePriceView();
        StockPrice stockPrice = new StockPrice("FPT_11", 500.5, 100, 999);
        StockPrice firsStockPrice = new StockPrice("FPT_11", 500, 100, 999);
        Stock stock1 = new Stock("FPT_11", "FPT software");
        Stock stock2 = new Stock("Vin_11", "Vin");
        StockFeeder stockFeeder = StockFeeder.getInstance();
        stockFeeder.addStock(stock1);
        stockFeeder.addStock(stock2);
        stockFeeder.registerViewer("FPT_11", stockRealtimePriceView);
        stockFeeder.registerViewer("Vin_11", stockRealtimePriceView);
        stockFeeder.notify(firsStockPrice);
        stockFeeder.notify(stockPrice);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase12 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[REALTIME] Realtime Price Update: FPT_12 is now $500.5\n[REALTIME] Realtime Price Update: FPT_12 is now $500.5";
        newOuput();
        StockRealtimePriceView stockRealtimePriceView1 = new StockRealtimePriceView();
        StockRealtimePriceView stockRealtimePriceView2 = new StockRealtimePriceView();
        StockPrice stockPrice = new StockPrice("FPT_12", 500.5, 100, 999);
        StockPrice firsStockPrice = new StockPrice("FPT_12", 500, 100, 999);
        Stock stock1 = new Stock("FPT_12", "FPT software");
        Stock stock2 = new Stock("Vin_12", "Vin");
        StockFeeder stockFeeder = StockFeeder.getInstance();
        stockFeeder.addStock(stock1);
        stockFeeder.addStock(stock2);
        stockFeeder.registerViewer("FPT_12", stockRealtimePriceView1);
        stockFeeder.registerViewer("FPT_12", stockRealtimePriceView2);
        stockFeeder.notify(firsStockPrice);
        stockFeeder.notify(stockPrice);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase13 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "";
        newOuput();
        StockRealtimePriceView stockRealtimePriceView = new StockRealtimePriceView();
        StockPrice firsStockPrice = new StockPrice("FPT_13", 500, 100, 999);
        StockPrice stockPrice = new StockPrice("fake_FPT_13", 500.5, 100, 999);
        Stock stock1 = new Stock("FPT_13", "FPT software");
        Stock stock2 = new Stock("Vin_13", "Vin");
        Stock fake_stock = new Stock("fake_FPT_13", "FPT software");
        StockFeeder stockFeeder = StockFeeder.getInstance();
        stockFeeder.addStock(stock1);
        stockFeeder.addStock(stock2);
        stockFeeder.addStock(fake_stock);
        stockFeeder.registerViewer("FPT_13", stockRealtimePriceView);
        stockFeeder.registerViewer("Vin_13", stockRealtimePriceView);
        stockFeeder.notify(firsStockPrice);
        stockFeeder.notify(stockPrice);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase14 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "";
        newOuput();
        StockAlertView stockAlertView = new StockAlertView(1000, 300);
        StockPrice stockPrice = new StockPrice("VietNam", 500, 100, 5000);
        stockAlertView.onUpdate(stockPrice);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase15 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[ALERT] VietNam price changed significantly to $200.0";
        newOuput();
        StockAlertView stockAlertView = new StockAlertView(1000, 300);
        StockPrice stockPrice = new StockPrice("VietNam", 200, 100, 5000);
        stockAlertView.onUpdate(stockPrice);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase16 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[ALERT] VietNam price changed significantly to $1200.0";
        newOuput();
        StockAlertView stockAlertView = new StockAlertView(1000, 300);
        StockPrice stockPrice = new StockPrice("VietNam", 1200, 100, 5000);
        stockAlertView.onUpdate(stockPrice);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase17 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[ALERT] VietNam price changed significantly to $1200.0\n[ALERT] VietNam price changed significantly to $1205.0";
        newOuput();
        StockAlertView stockAlertView = new StockAlertView(1000, 300);
        StockPrice stockPrice1 = new StockPrice("VietNam", 1200, 100, 5000);
        StockPrice stockPrice2 = new StockPrice("VietNam", 1200, 100, 5000);
        StockPrice stockPrice3 = new StockPrice("VietNam", 1205, 100, 5000);
        stockAlertView.onUpdate(stockPrice1);
        stockAlertView.onUpdate(stockPrice2);
        stockAlertView.onUpdate(stockPrice3);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase18 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[ALERT] VietNam price changed significantly to $200.0\n[ALERT] VietNam price changed significantly to $205.0";
        newOuput();
        StockAlertView stockAlertView = new StockAlertView(1000, 300);
        StockPrice stockPrice1 = new StockPrice("VietNam", 200, 100, 5000);
        StockPrice stockPrice2 = new StockPrice("VietNam", 200, 100, 5000);
        StockPrice stockPrice3 = new StockPrice("VietNam", 205, 100, 5000);
        stockAlertView.onUpdate(stockPrice1);
        stockAlertView.onUpdate(stockPrice2);
        stockAlertView.onUpdate(stockPrice3);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase19 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[ALERT] VietNam price changed significantly to $200.0\n[ALERT] VietNam price changed significantly to $1200.0\n[ALERT] VietNam price changed significantly to $200.0";
        newOuput();
        StockAlertView stockAlertView = new StockAlertView(1000, 300);
        StockPrice stockPrice1 = new StockPrice("VietNam", 200, 100, 5000);
        StockPrice stockPrice2 = new StockPrice("VietNam", 1200, 100, 5000);
        StockPrice stockPrice3 = new StockPrice("VietNam", 200, 100, 5000);
        stockAlertView.onUpdate(stockPrice1);
        stockAlertView.onUpdate(stockPrice2);
        stockAlertView.onUpdate(stockPrice3);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase20 extends MyTestcase {
    @Override
    public boolean run() {
        newOuput();
        expect = "VIC VNM HPG";
        String filePath = "src/resources/stocks.json";
        HosePriceFetchLib hoseLib = new HosePriceFetchLib(filePath);
        List<String> hoseStockCodes = Arrays.asList("VIC", "VNM", "HPG", "FPT");
        HoseAdapter hoseAdapter = new HoseAdapter(hoseLib, hoseStockCodes);
        List<StockPrice> list = hoseAdapter.fetch();
        for (StockPrice stockPrice : list) {
            System.out.print(stockPrice.getCode() + " ");
        }
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase21 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[ALERT] VietNam price changed significantly to $1000.0";
        newOuput();
        StockAlertView stockAlertView = new StockAlertView(1000, 300);
        StockPrice stockPrice1 = new StockPrice("VietNam", 1000, 100, 5000);
        StockPrice stockPrice2 = new StockPrice("VietNam", 999, 100, 5000);
        stockAlertView.onUpdate(stockPrice1);
        stockAlertView.onUpdate(stockPrice2);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase22 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[ALERT] VietNam price changed significantly to $300.0";
        newOuput();
        StockAlertView stockAlertView = new StockAlertView(1000, 300);
        StockPrice stockPrice1 = new StockPrice("VietNam", 301, 100, 5000);
        StockPrice stockPrice2 = new StockPrice("VietNam", 300, 100, 5000);
        stockAlertView.onUpdate(stockPrice1);
        stockAlertView.onUpdate(stockPrice2);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase23 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[ALERT] VietNam price changed significantly to $1001.0\n[ALERT] VietNam price changed significantly to $1001.0";
        newOuput();
        StockAlertView stockAlertView = new StockAlertView(1000, 300);
        StockPrice stockPrice1 = new StockPrice("VietNam", 998, 100, 5000);
        StockPrice stockPrice2 = new StockPrice("VietNam", 1001, 100, 5000);
        StockPrice stockPrice3 = new StockPrice("VietNam", 999, 100, 5000);
        StockPrice stockPrice4 = new StockPrice("VietNam", 1001, 100, 5000);
        // System.out.println("1");
        stockAlertView.onUpdate(stockPrice1);
        // System.out.println("2");
        stockAlertView.onUpdate(stockPrice2);
        // System.out.println("3");
        stockAlertView.onUpdate(stockPrice3);
        // System.out.println("4");
        stockAlertView.onUpdate(stockPrice4);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase24 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[WARNING] Error when unregistering with 242";
        newOuput();
        Stock stock = new Stock("242", "hcmut");
        StockFeeder stockFeeder = StockFeeder.getInstance();
        stockFeeder.addStock(stock);
        stockFeeder.registerViewer("242", stockTickerView);
        stockFeeder.unregisterViewer("242", stockTickerView);
        stockFeeder.unregisterViewer("242", stockTickerView);
        output = getOutput();
        return expect.equals(output);
    }
}

class Testcase25 extends MyTestcase {
    @Override
    public boolean run() {
        expect = "[REALTIME] Realtime Price Update: FPT_25 is now $500.5";
        newOuput();
        StockRealtimePriceView stockRealtimePriceView1 = new StockRealtimePriceView();
        StockRealtimePriceView stockRealtimePriceView2 = new StockRealtimePriceView();
        StockPrice firsStockPrice = new StockPrice("FPT_25", 500, 100, 999);
        StockPrice stockPrice = new StockPrice("FPT_25", 500.5, 100, 999);
        Stock stock1 = new Stock("FPT_25", "FPT software");
        Stock stock2 = new Stock("Vin_25", "Vin");
        StockFeeder stockFeeder = StockFeeder.getInstance();
        stockFeeder.addStock(stock1);
        stockFeeder.addStock(stock2);
        stockFeeder.registerViewer("FPT_25", stockRealtimePriceView1);
        stockFeeder.registerViewer("Vin_25", stockRealtimePriceView1);
        stockFeeder.registerViewer("FPT_25", stockRealtimePriceView2);
        stockFeeder.registerViewer("Vin_25", stockRealtimePriceView2);
        stockFeeder.unregisterViewer("FPT_25", stockRealtimePriceView1);
        stockFeeder.notify(firsStockPrice);
        stockFeeder.notify(stockPrice);
        output = getOutput();
        return expect.equals(output);
    }
}

public class testcase {
    static private List<Testcase> tc_list = new ArrayList<>();
    public static void main(String[] args) {
        int score = 0;
        tc_list.add(new Testcase1());
        tc_list.add(new Testcase2());
        tc_list.add(new Testcase3());
        tc_list.add(new Testcase4());
        tc_list.add(new Testcase5());
        tc_list.add(new Testcase6());
        tc_list.add(new Testcase7());
        tc_list.add(new Testcase8());
        tc_list.add(new Testcase9());
        tc_list.add(new Testcase10());
        tc_list.add(new Testcase11());
        tc_list.add(new Testcase12());
        tc_list.add(new Testcase13());
        tc_list.add(new Testcase14());
        tc_list.add(new Testcase15());
        tc_list.add(new Testcase16());
        tc_list.add(new Testcase17());
        tc_list.add(new Testcase18());
        tc_list.add(new Testcase19());
        tc_list.add(new Testcase20());
        tc_list.add(new Testcase21());
        tc_list.add(new Testcase22());
        tc_list.add(new Testcase23());
        tc_list.add(new Testcase24());
        tc_list.add(new Testcase25());
        for (int i = 0; i < tc_list.size(); ++i) {
            if (tc_list.get(i).run_and_show(i + 1)) ++score;
        }
        System.out.println("Score: " + score + "/" + tc_list.size());
        System.exit(0);
    }
}