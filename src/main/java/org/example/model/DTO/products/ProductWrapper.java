package org.example.model.DTO.products;

public class ProductWrapper {
    private String name;
    private int stock;
    private double stockBuyingCost;
    private double stockSellingValue;

    public ProductWrapper( String name, int stock, double stockBuyingCost, double stockSellingValue) {
        this.name = name;
        this.stock = stock;
        this.stockBuyingCost = stockBuyingCost;
        this.stockSellingValue = stockSellingValue;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getStockBuyingCost() {
        return stockBuyingCost;
    }

    public void setStockBuyingCost(double stockBuyingCost) {
        this.stockBuyingCost = stockBuyingCost;
    }

    public double getStockSellingValue() {
        return stockSellingValue;
    }

    public void setStockSellingValue(double stockSellingValue) {
        this.stockSellingValue = stockSellingValue;
    }

    @Override
    public String toString() {
        return "Product{" +
                ", name='" + name + '\'' +
                ", stock=" + stock +
                ", stockBuyingCost=" + stockBuyingCost +
                ", stockSellingValue=" + stockSellingValue +
                '}';
    }
}
