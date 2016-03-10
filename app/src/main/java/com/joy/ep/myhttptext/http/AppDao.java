package com.joy.ep.myhttptext.http;

import com.joy.ep.myhttptext.bean.Brand;
import com.joy.ep.myhttptext.bean.DemoResultBean;
import com.joy.ep.myhttptext.bean.Gan;
import com.joy.ep.myhttptext.bean.Location;
import com.joy.ep.myhttptext.bean.MyJsonResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jclick.httpwrapper.callback.MyCallBack;
import cn.jclick.httpwrapper.callback.ObjectCallback;
import cn.jclick.httpwrapper.request.BaseHttp;

/**
 * author   Joy
 * Date:  2016/2/14.
 * version:  V1.0
 * Description:
 */
public class AppDao {
    private static AppDao instance;


    public static AppDao getInstance() {
        if (instance == null) {
            instance = new AppDao();
        }
        return instance;
    }


    private AppDao() {
    }

    public Map<String, String> createMap() {
        Map<String, String> map = new HashMap<>();
        return map;
    }


//    public void getNewsList(ObjectCallback<NewBrand>,String url) {
//        Map<String, String> map = createMap();
//        map.put("newsTypeVal", "CC");
//        RequestParams params = new RequestParams.Builder().requestParams(map).url(url).cacheMode(RequestConfig.HttpCacheMode.ALWAYS_CACHE).post().build();
//        HttpRequestAgent.getInstance().executeRequest(params, callback);
//        HttpRequestAgent.getInstance().(params, callback);
//        Http.post("url", map, listener);
//    }
//
//    public void gankIo(int index, int page, ObjectCallback<List<GanHuo>> listener) {
//        String type = "all";
//        String gank = "http://gank.avosapps.com/api/data/" + "Android" + "/30/" + "1";
//        BaseHttp.get(gank, listener);
//    }

    public void fuck(ObjectCallback<Gan> callback) {
        Map<String, String> map = createMap();
        String gank = "http://120.25.0.216/userfindmacth.json";
        map.put("pageNo", "1");
        BaseHttp.post(gank, map, callback);
    }


    public void news(String url, MyCallBack<MyJsonResult<Brand>> callback) {
        Map<String, String> map = createMap();
        map.put("newsTypeVal", "CC");
        BaseHttp.post(url, map, callback);
    }


    public void Te(MyCallBack<List<Gan>> callback) {
        String gank = "https://gank.io/api/data/Android/1/1";
        BaseHttp.get(gank, callback);
    }

    public void fu(MyCallBack<DemoResultBean<Location>> callback) {
        Map<String, String> map = createMap();
        String gank = "http://ip.taobao.com/service/getIpInfo2.php";
        map.put("ip", "221.217.176.144");

        BaseHttp.post(gank, map, callback);
    }

}


