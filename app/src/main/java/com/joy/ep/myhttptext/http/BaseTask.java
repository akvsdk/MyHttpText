package com.joy.ep.myhttptext.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.TypeReference;
import com.joy.ep.myhttptext.bean.JsonResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.jclick.httpwrapper.callback.ObjectCallback;
import cn.jclick.httpwrapper.callback.ResponseData;
import cn.jclick.httpwrapper.request.HttpRequestAgent;
import cn.jclick.httpwrapper.request.RequestParams;

/**
 * author   Joy
 * Date:  2016/2/18.
 * version:  V1.0
 * Description:
 */
public abstract class BaseTask<E, T> extends ObjectCallback<T> {
    protected Context context;
    protected ProgressDialog loadMask;
    public E result;
    private Map<String, String> requestParams = new HashMap<>();
    private FloatingActionButton fab;

    public BaseTask(Context context, FloatingActionButton view) {
        this.fab = view;
        this.context = context;
        loadMask = new ProgressDialog(context);
        loadMask.setProgressStyle(0);
        loadMask.setMessage("加载中……");
        loadMask.show();
        // 点击后退按钮能取消dialog
        this.loadMask.setCancelable(true);
        // 点击空白地方不能取消dialog
        this.loadMask.setCanceledOnTouchOutside(false);
        this.loadMask.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface arg0) {
                BaseTask.this.doCancel();
            }
        });
    }

    public BaseTask(Context context, String loadingMsg) {
        this.context = context;
        loadMask = new ProgressDialog(context);
        loadMask.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadMask.setMessage(loadingMsg);
        loadMask.show();
        // 点击后退按钮能取消dialog
        this.loadMask.setCancelable(true);
        // 点击空白地方不能取消dialog
        this.loadMask.setCanceledOnTouchOutside(false);
        this.loadMask.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface arg0) {
                BaseTask.this.doCancel();
            }
        });
    }


    public void doCancel() {
        if (loadMask != null && loadMask.isShowing()) {
            loadMask.dismiss();
        }
    }

    public void requestByPost(String url, Map<String, String> map) {
        requestParams.clear();
        if (null != map) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                requestParams.put(entry.getKey(), entry.getValue());
            }
        }
        RequestParams params = new RequestParams.Builder().requestParams(requestParams).url(url).post().build();
        HttpRequestAgent.getInstance().executeRequest(params, this);

    }

    @Override
    protected boolean isCacheProcessSuccess(ResponseData<String> data) {
        return super.isCacheProcessSuccess(data);
    }

    @Override
    protected void onResponse(ResponseData<T> responseData) {
        try {
            if (responseData.isSuccess()) {
                JSONObject jsonObject = new JSONObject((String) responseData.getData().toString());
                result = (E) new JsonResult(jsonObject, setTypeToken().getType()) {
                };
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Snackbar.make(fab, "请检查网络链接<1,-1>", Snackbar.LENGTH_LONG).setAction("更多", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            context.startActivity(new
                                    Intent(Settings.ACTION_WIFI_SETTINGS));
                        }
                    })
                            .show();
                } else {
                    Toast.makeText(context, "请检查网络链接<1,-1>", Toast.LENGTH_SHORT).show();
                }
                doCancel();
                return;
            }
            doCancel();

            onSuccess();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public abstract TypeReference setTypeToken();

    public abstract void onSuccess();

    public abstract void onCache();

}
