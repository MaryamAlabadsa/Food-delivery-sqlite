package com.example.toc_test;

public class cart {
    private int id;
    private String name;
    private double price;
    private int count;
    private String has_toy;
    private String has_potato;
    private String name_user;
    private String date;

    public cart(int id,String name, double price, int count, String has_toy, String has_potato,String date) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.has_toy = has_toy;
        this.has_potato = has_potato;
        this.date = date;
    }

    public cart(int id,String name, double price, int count, String has_toy, String has_potato) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.has_toy = has_toy;
        this.has_potato = has_potato;

    }

    public cart(String name, double price, int count, String has_toy, String has_potato, String name_user, String date) {

        this.name = name;
        this.price = price;
        this.count = count;
        this.has_toy = has_toy;
        this.has_potato = has_potato;
        this.name_user = name_user;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getHas_toy() {
        return has_toy;
    }

    public void setHas_toy(String has_toy) {
        this.has_toy = has_toy;
    }

    public String getHas_potato() {
        return has_potato;
    }

    public void setHas_potato(String has_potato) {
        this.has_potato = has_potato;
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
