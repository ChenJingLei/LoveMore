package com.app.cjl20.lovemore.model;

/**
 * Created by cjl20 on 2016/2/23.
 */
public class CheckStatus {

    private String status;
    private String result;

    public CheckStatus(String status, String result) {
        this.status = status;
        this.result = result;
    }

    public CheckStatus() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "CheckStatus{" +
                "status='" + status + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
