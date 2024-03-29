package com.runjshell.runjshellserver.model;

public class BrowserResponse {
    String type;
    String msg;

    public BrowserResponse() {
    }

    public BrowserResponse(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
