package com.app.cjl20.lovemore.model;


import java.util.Arrays;

/**
 * Created by cjl20 on 2016/2/22.
 */

public class User {

    private String username;
    private String password;
    private String phone;
    private byte[] image;

    public User() {
    }

    public User(String username, String password, String phone, byte[] image) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.image = image;
    }

    public User(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}
