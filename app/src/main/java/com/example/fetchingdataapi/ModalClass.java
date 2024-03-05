package com.example.fetchingdataapi;

public class ModalClass {

    String imgUrl;
    String name;
    int price;

    public ModalClass(String imgUrl, String name, int price) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
