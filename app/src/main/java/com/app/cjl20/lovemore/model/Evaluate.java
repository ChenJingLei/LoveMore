package com.app.cjl20.lovemore.model;

import java.util.Arrays;

/**
 * Created by cjl20 on 2016/2/22.
 */

public class Evaluate {


    private Long Id;

    private Byte[] icno;

    private String name;

    private String content;

    private Byte[] images;

    public Evaluate() {
    }

    public Evaluate(Byte[] icno, String name, String content, Byte[] images) {
        this.icno = icno;
        this.name = name;
        this.content = content;
        this.images = images;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Byte[] getIcno() {
        return icno;
    }

    public void setIcno(Byte[] icno) {
        this.icno = icno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Byte[] getImages() {
        return images;
    }

    public void setImages(Byte[] images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Evaluate{" +
                "Id=" + Id +
                ", icno=" + Arrays.toString(icno) +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", images=" + Arrays.toString(images) +
                '}';
    }
}
