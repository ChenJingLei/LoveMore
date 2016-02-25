package com.app.cjl20.lovemore.model;


import java.util.Arrays;

/**
 * Created by cjl20 on 2016/2/17.
 */

public class Volunteer {

    private Long id;

    private String title;

    private String principal;

    private String member;

    private String date;

    private String phone;

    private byte[] image;

    public Volunteer() {
    }

    public Volunteer(String title, String principal, String member, String date, String phone, byte[] image) {
        this.title = title;
        this.principal = principal;
        this.member = member;
        this.date = date;
        this.phone = phone;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
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
        return "Volunteer{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", principal='" + principal + '\'' +
                ", member='" + member + '\'' +
                ", date='" + date + '\'' +
                ", phone='" + phone + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}

