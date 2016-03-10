package cn.jclick.httpwrapper.callback;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.alibaba.fastjson.TypeReference;

/**
 * author   Joy
 * Date:  2016/3/10.
 * version:  V1.0
 * Description:
 */
public abstract class MyCallBack<T> extends ObjectCallback<T> {
    private ProgressDialog loadMask;
    protected Context context;

    public MyCallBack(Context mtx) {
        this.context = mtx;
        loadMask = new ProgressDialog(context);
        loadMask.setProgressStyle(0);
        loadMask.setMessage("加载中……");
        loadMask.show();
        // 点击后退按钮能取消dialog
        this.loadMask.setCancelable(true);
        // 点击空白地方不能取消dialog
        this.loadMask.setCanceledOnTouchOutside(false);
        this.loadMask.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface arg0) {
                doCancel();
            }
        });
    }

    public MyCallBack(Context mtx, TypeReference<T> typeReference) {
        super(typeReference);
        this.context = mtx;
        loadMask = new ProgressDialog(context);
        loadMask.setProgressStyle(0);
        loadMask.setMessage("加载中……");
        loadMask.show();
        // 点击后退按钮能取消dialog
        this.loadMask.setCancelable(true);
        // 点击空白地方不能取消dialog
        this.loadMask.setCanceledOnTouchOutside(false);
        this.loadMask.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface arg0) {
                doCancel();
            }
        });
    }

    private void doCancel() {
        if (loadMask != null && loadMask.isShowing()) {
            loadMask.dismiss();
        }
    }

    @Override
    protected void onResponse(ResponseData<T> responseData) {

        if (responseData.isSuccess() && responseData.isParseSuccess()) {
            doCancel();
            T result = responseData.getData();
            if (responseData.isFromCache()) {
                onCache(result);
            } else {
                onSuccess(result);
            }
        } else {
            doCancel();
            Toast.makeText(context, responseData.getDescription(), Toast.LENGTH_SHORT).show();
        }
    }

    protected abstract void onSuccess(T result);

    protected abstract void onCache(T result);
}
