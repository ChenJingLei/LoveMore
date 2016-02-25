package com.app.cjl20.lovemore.model;

/**
 * Created by cjl20 on 2016/2/25.
 * PROJECT_NAME by LoveMore
 */
public class MyActivity {
    private String name;
    private String content;

    public MyActivity() {
    }

    public MyActivity(String name, String content) {
        this.name = name;
        this.content = content;
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

    @Override
    public String toString() {
        return "MyActivity{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
