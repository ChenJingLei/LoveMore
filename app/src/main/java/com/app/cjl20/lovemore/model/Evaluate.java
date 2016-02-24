package com.app.cjl20.lovemore.model;

import java.util.Arrays;

/**
 * Created by cjl20 on 2016/2/22.
 */

public class Evaluate {

    private String name;

    private String content;

    private byte[] images;

    public Evaluate() {
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

    @Override
    public String toString() {
        return "Evaluate{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", images=" + Arrays.toString(images) +
                '}';
    }
}
