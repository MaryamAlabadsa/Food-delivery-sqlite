package com.example.toc_test;

import java.util.ArrayList;

public class item {
    private int id;
    private double price;
    private String name;
    private String desc;
    private String img;

    public item(double price, String name, String desc, String img) {
        this.price = price;
        this.name = name;
        this.desc = desc;
        this.img = img;
    }

    public item(int id, double price, String name, String desc, String img) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.desc = desc;
        this.img = img;
    }

    public item(double price, String name, String img) {
        this.price = price;
        this.name = name;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
