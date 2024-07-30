package com.example.assigment2_22h1120116.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    public int ID;
    public String product_name;
    public Integer price;
    public String anh_sp;
    public String description;
    public int product_ID;

    public SanPham(int ID, String product_name, Integer price, String anh_sp, String description, int product_ID) {
        this.ID = ID;
        this.product_name = product_name;
        this.price = price;
        this.anh_sp = anh_sp;
        this.description = description;
        this.product_ID = product_ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getAnh_sp() {
        return anh_sp;
    }

    public void setAnh_sp(String anh_sp) {
        this.anh_sp = anh_sp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(int product_ID) {
        this.product_ID = product_ID;
    }
}
