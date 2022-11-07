package com.ecom.priceengine.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="product_price")
public class ProductPrice {
    @Id
    private String title;
    @Column
    private double price;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductPrice{" +
                "title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}
