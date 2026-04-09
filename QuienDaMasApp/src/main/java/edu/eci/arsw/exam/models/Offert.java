package edu.eci.arsw.exam.models;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Offert implements Serializable {
    private static final long serialVersionUID = 1L;
    private String buyer;
    private String productCode;
    private double price;

    public Offert(String buyer, String productCode, double price) {
        this.buyer = buyer;
        this.productCode = productCode;
        this.price = price;
    }

    public Offert(byte[] body) throws IOException, ClassNotFoundException {
        Offert obj;
        ByteArrayInputStream bis = new ByteArrayInputStream(body);
        ObjectInputStream ois = new ObjectInputStream(bis);
        obj = (Offert) ois.readObject();
        ois.close();
        bis.close();
        this.buyer = obj.buyer;
        this.productCode = obj.productCode;
        this.price = obj.price;
    }

    public void setBuyer(String newBuyer){
        this.buyer = newBuyer;
    }

    public void setProductCode(String newProductCode){
        this.productCode = newProductCode;
    }

    public void setPrice(double newPrice){
        this.price = newPrice;
    }

    public String getBuyer() {
        return buyer;
    }

    public String getProductCode() {
        return productCode;
    }

    public double getPrice() {
        return price;
    }

}
