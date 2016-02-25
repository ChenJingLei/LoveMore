package com.app.cjl20.lovemore.model;

import java.util.Arrays;

/**
 * Created by cjl20 on 2016/2/22.
 */

public class Evaluate {

    private byte[] icno;

    private String name;

    private String content;

    private byte[] images;

    public Evaluate() {
    }

    public Evaluate(byte[] icno, String name, String content, byte[] images) {
        this.icno = icno;
        this.name = name;
        this.content = content;
        this.images = images;
    }

    public Evaluate(String name, String content, byte[] images) {
        this.name = name;
        this.content = content;
        this.images = images;
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

    public byte[] getImages() {
        return images;
    }

    public void setImages(byte[] images) {
        this.images = images;
    }

    public byte[] getIcno() {
        return icno;
    }

    public void setIcno(byte[] icno) {
        this.icno = icno;
    }

    @Override
    public String toString() {
        return "Evaluate{" +
                "icno=" + Arrays.toString(icno) +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", images=" + Arrays.toString(images) +
                '}';
    }
}
