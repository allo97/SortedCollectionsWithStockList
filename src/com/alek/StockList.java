package com.alek;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class StockList {
    private final Map<String, StockItem> list;

    public StockList() {
        this.list = new LinkedHashMap<>();
    }

    public int addStock(StockItem item) {
        if(item != null) {
            //check if already have quantities of this item
            StockItem inStock = list.getOrDefault(item.getName(), item);  //wyciagamy ten item a jesli go nie ma to dajemy nowy item
            //If there are already stock on this item, adjust the quantity
            if(inStock != item) { //znaczy ze byl bo wyciagnelismy
                item.adjustStock(inStock.quantityInStock()); //and we are fitting quantity of item because there was already
            }

            list.put(item.getName(), item); //and we are putting again changed or not
            return item.quantityInStock();
        }
        return 0;
    }

    public int sellStock(String item, int quantity) {
        StockItem inStock = list.getOrDefault(item, null); //jesli nie bylo to null zeby nic nie sprzedawac
        if((inStock != null) && (inStock.quantityInStock() >= quantity) && (quantity > 0)) {
            inStock.adjustStock(-quantity);
            return quantity; //easy just checking is there is more our equal of our product  that we want to sell
        }
        return 0;
    }

    public StockItem get(String key) {
        return list.get(key);
    }

    public Map<String, Double> priceList() {
        Map<String, Double> prices = new LinkedHashMap<>();
        for(Map.Entry<String, StockItem> item : list.entrySet()) {
            prices.put(item.getKey(), item.getValue().getPrice());
        }
        return Collections.unmodifiableMap(prices);
    }

    public Map<String, StockItem> Items() {
        return Collections.unmodifiableMap(list);
    }

    @Override
    public String toString() { //just to show our list
        String s = "\nStock list\n";
        double totalCost = 0.0;
        for(Map.Entry<String, StockItem> item : list.entrySet()) { //item is a key-value pair
            StockItem stockItem = item.getValue();

            double itemValue = stockItem.getPrice() * stockItem.quantityInStock();

            s = s + stockItem + ". There are " + stockItem.quantityInStock() + " in stock. Value of items: ";
            s = s + String.format("%.2f", itemValue) + "\n";
            totalCost += itemValue;
        }

        return s + "Total stock value " + totalCost;
    }
}
