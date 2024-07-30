package com.example.assigment2_22h1120116.model;

public class Category {
    public int id;
    public String category_name;
    public String anh;

    public Category(int id, String category_name, String anh) {
        this.id = id;
        this.category_name = category_name;
        this.anh = anh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
}
