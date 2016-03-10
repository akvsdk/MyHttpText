package com.joy.ep.myhttptext.bean;

import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;

public class JsonResult<T> implements Serializable {

    private boolean success;
    private String msg;
    private int errorCode;
    private T record;

    public JsonResult(JSONObject jsonResult, Type clsType) {
        success = jsonResult.optBoolean("success");
        msg = jsonResult.optString("msg");
        errorCode = jsonResult.optInt("errorCode");

        if (clsType == JSONObject.class) {
            record = (T) jsonResult.optJSONObject("record");
        } else if (clsType == JSONObject.class) {
            record = (T) jsonResult.optJSONArray("record");
        } else if (clsType == String.class) {
            record = (T) jsonResult.optString("record");
        } else {
            record = (T) com.alibaba.fastjson.JSONObject.parseObject(jsonResult.optString("record"), clsType);
        }
    }


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
