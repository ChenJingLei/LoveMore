package com.app.cjl20.lovemore.model;


/**
 * Created by cjl20 on 2016/2/24.
 */

public class Feedback {

    private String username;

    private String title;

    private String content;

    public Feedback(String username, String title, String content) {
        this.username = username;
        this.title = title;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "username='" + username + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
