package com.app.cjl20.lovemore.model;


import java.util.Date;

/**
 * Created by cjl20 on 2016/2/17.
 */

public class Volunteer {

    private Long Id;

    private String title;

    private String principal;

    private String member;

    private Date date;

    private String phone;

    private Byte[] image;

    public Volunteer() {
    }

    public Volunteer(String title, String principal, String member, Date date, String phone, Byte[] image) {
        this.title = title;
        this.principal = principal;
        this.member = member;
        this.date = date;
        this.phone = phone;
        this.image = image;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
                "Id=" + Id +
                ", title='" + title + '\'' +
                ", principal='" + principal + '\'' +
                ", member='" + member + '\'' +
                ", date=" + date +
                ", phone='" + phone + '\'' +
                '}';
    }
}

