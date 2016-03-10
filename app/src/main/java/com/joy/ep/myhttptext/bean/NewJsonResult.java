package com.joy.ep.myhttptext.bean;

import java.io.Serializable;

public class NewJsonResult implements Serializable {

    private boolean success;
    private String msg;
    private int errorCode;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }



    @Override
    public String toString() {
        return "JsonResult{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", errorCode=" + errorCode +
                '}';
    }
}
