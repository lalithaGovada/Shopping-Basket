package com.ecom.priceengine.bean;

public class Product {
    private String name;
    private long quantity;
   

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public Product(String name, long quantity) {
        this.name = name;
        this.quantity = quantity;
       
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
