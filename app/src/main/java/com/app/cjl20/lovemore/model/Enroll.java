package com.app.cjl20.lovemore.model;


/**
 * Created by cjl20 on 2016/2/25.
 */
public class Enroll {

    private String username;
    private Long activityid;

    public Enroll() {
    }

    public Enroll(String username, Long activityid) {
        this.username = username;
        this.activityid = activityid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getActivityid() {
        return activityid;
    }

    public void setActivityid(Long activityid) {
        this.activityid = activityid;
    }

    @Override
    public String toString() {
        return "Enroll{" +
                ", username='" + username + '\'' +
                ", activityid=" + activityid +
                '}';
    }
}
