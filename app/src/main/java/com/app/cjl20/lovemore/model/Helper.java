package com.app.cjl20.lovemore.model;


import java.util.Arrays;

/**
 * Created by cjl20 on 2016/2/22.
 */

public class Helper {

    private String title;

    private String origation;

    private String address;

    private String date;

    private String phone;

    private byte[] image;

    public Helper() {
    }

    public Helper(String title, String origation, String address, String date, String phone, byte[] image) {
        this.title = title;
        this.origation = origation;
        this.address = address;
        this.date = date;
        this.phone = phone;
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrigation() {
        return origation;
    }

    public void setOrigation(String origation) {
        this.origation = origation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Helper{" +
                "title='" + title + '\'' +
                ", origation='" + origation + '\'' +
                ", address='" + address + '\'' +
                ", date=" + date +
                ", phone='" + phone + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}
