package com.joy.ep.myhttptext;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.Toast;

import com.bugtags.library.Bugtags;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.jclick.httpwrapper.callback.ResponseData;
import cn.jclick.httpwrapper.interceptor.HandlerInterceptor;
import cn.jclick.httpwrapper.request.HttpRequestAgent;
import cn.jclick.httpwrapper.request.RequestConfig;
import cn.jclick.httpwrapper.request.RequestParams;

/**
 * author   Joy
 * Date:  2016/2/4.
 * version:  V1.0adb
 * Description:
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RequestConfig config = new RequestConfig.Builder(this).logEnable(true).cacheMode(RequestConfig.HttpCacheMode.ALWAYS_CACHE)
                .cacheTimeInSeconds(10).connectionTimeOut(30 * 1000).addInterceptor(new HandlerInterceptor() {
                    @Override
                    public boolean preHandler(RequestParams params) {
                        if (Constant.TEST == 1) {
                            return false;
                        } else {
                            return true;
                        }

                    }

                    @Override
                    public void postSuccessHandler(final RequestParams params, final int statusCode, Map<String, List<String>> headers) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(BaseActivity.this, "(￣︶￣)↗ 请求成功咯" + statusCode + "---" + params.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void postFailedHandler(IOException exception) {
                        //TODO 请求失败的拦截器
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(BaseActivity.this, "o(︶︿︶)o 敢不敢不坑爹", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void afterCompletion(RequestParams params, ResponseData<String> responseData) {

                    }
                }).build();
        HttpRequestAgent.getInstance().init(config);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Bugtags.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Bugtags.onDispatchTouchEvent(this, ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpRequestAgent.getInstance().interruptAllRequest();
    }
}
