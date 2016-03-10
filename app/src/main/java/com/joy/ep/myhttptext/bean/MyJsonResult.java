package com.joy.ep.myhttptext.bean;

import java.io.Serializable;

public class MyJsonResult<T> implements Serializable {

    private boolean success;
    private String msg;
    private int errorCode;
    private T record;


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

    public T getRecord() {
        return record;
    }

    public void setRecord(T record) {
        this.record = record;
    }

    @Override
    public String toString() {
        return "JsonResult{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", errorCode=" + errorCode +
                ", record=" + record +
                '}';
    }
}
