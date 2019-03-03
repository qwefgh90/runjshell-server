
package com.runjshell.runjshellserver.model;

public class BrowserRequest {
    String type;
    String msg;

    public BrowserRequest() {
    }

    public BrowserRequest(String type, String msg) {
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