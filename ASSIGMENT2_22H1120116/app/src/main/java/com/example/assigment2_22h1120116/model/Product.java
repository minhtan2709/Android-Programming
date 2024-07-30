package com.example.assigment2_22h1120116.model;

public class Product {
    private int masp;
    private String tensp;
    private String dessp;
    private int giaban;
    private String anhsp;

    public Product(int masp, String tensp, String dessp, int giaban, String anhsp) {
        this.masp = masp;
        this.tensp = tensp;
        this.dessp = dessp;
        this.giaban = giaban;
        this.anhsp = anhsp;
    }

    public Product(String tensp, String dessp, int giaban, String anhsp) {
        this.tensp = tensp;
        this.dessp = dessp;
        this.giaban = giaban;
        this.anhsp = anhsp;
    }

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getDessp() {
        return dessp;
    }

    public void setDessp(String dessp) {
        this.dessp = dessp;
    }

    public int getGiaban() {
        return giaban;
    }

    public void setGiaban(int giaban) {
        this.giaban = giaban;
    }

    public String getAnhsp() {
        return anhsp;
    }

    public void setAnhsp(String anhsp) {
        this.anhsp = anhsp;
    }
}