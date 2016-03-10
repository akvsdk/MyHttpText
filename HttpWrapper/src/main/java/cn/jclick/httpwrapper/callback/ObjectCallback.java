package cn.jclick.httpwrapper.callback;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.json.JSONObject;

import cn.jclick.httpwrapper.request.HttpRequestAgent;
import cn.jclick.httpwrapper.utils.GenericsUtils;

/**
 * Created by XuYingjian on 16/1/15.
 */
public abstract class ObjectCallback<T> extends Callback {
    private TypeReference<T> typeReference;

    public ObjectCallback(TypeReference<T> typeReference) {
        this.typeReference = typeReference;
    }

    public ObjectCallback() {

    }


    @Override
    protected boolean isCacheProcessSuccess(ResponseData<String> data) {
        try {
            if (!super.isCacheProcessSuccess(data)) {
                return false;
            }
            ResponseData responseData = convertCache(data);
            T result = processData(data.getData());
            //  T result = processJson(data.getData());
            if (result == null) {
                return false;
            }
            responseData.setData(result);
            response(responseData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void onSuccess(byte[] bytes) {
        ResponseData<T> responseData = wrapResponseData();
        T result = null;
        try {
            // result = processJson(string(bytes));
            result = processData(string(bytes));
        } catch (Exception e) {
            responseData.setParseSuccess(false);
            responseData.setDescription(e.getMessage());
            onFailed(e);
        }
        if (responseData.isParseSuccess()) {
            responseData.setData(result);
            response(responseData);
        } else {
            Log.e("Joy", "解析错误");
        }

    }

    private T processJson(String data) throws Exception {
        T result;
        JSONObject jsonObject = null;

        jsonObject = new JSONObject(data);

        if (jsonObject.isNull("error")) throw new Exception("error key not exists!!");

        if (jsonObject.getBoolean("error")) throw new Exception("input stream length error");

        if (jsonObject.isNull("results")) throw new Exception("results key not exists!!");

        String results = jsonObject.optString("results");
        Class<T> clazz = GenericsUtils.getSuperClassGenricType(data.getClass());
        if (this.typeReference != null) {
            result = JSON.parseObject(results, this.typeReference);
        } else {
            if (clazz == String.class) {        //字符串
                result = (T) results;
            } else {//Object
                result = com.alibaba.fastjson.JSONObject.parseObject(results, new TypeReference<T>() {
                });
            }

        }
        return result;

    }

    private T processData(String data) throws Exception {
        T result;
        if (this.typeReference != null) {
            result = JSON.parseObject(data, this.typeReference);
        } else {
            result = com.alibaba.fastjson.JSONObject.parseObject(data, new TypeReference<T>() {
            });
        }
        return result;
    }

    private void response(final ResponseData<T> responseData) {
        HttpRequestAgent.getInstance().getConfig().mainHandler.post(new Runnable() {
            @Override
            public void run() {
                onResponse(responseData);
            }
        });
    }

    @Override
    protected void onFailed(Exception exception) {
        response(wrapFailedData(exception));
    }

    protected abstract void onResponse(ResponseData<T> responseData);
}
