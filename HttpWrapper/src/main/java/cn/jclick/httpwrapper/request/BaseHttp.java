package cn.jclick.httpwrapper.request;

import java.util.HashMap;
import java.util.Map;

import cn.jclick.httpwrapper.callback.ObjectCallback;

/**
 * author   Joy
 * Date:  2016/3/4.
 * version:  V1.0
 * Description:   https://github.com/jclick/JWHttpWrapper
 */
public class BaseHttp extends HttpRequestAgent {

    private static Map<String, String> requestParam = new HashMap<>();

    private BaseHttp() {
    }

    public static void get(String url, ObjectCallback<?> listener) {
        RequestParams params = new RequestParams.Builder().url(url)
                //  .cacheMode(RequestConfig.HttpCacheMode.CACHE_WHEN_NO_NETWORK)
                .get().build();

        getInstance().executeRequest(params, listener);
    }

    public static void post(String url, Map<String, String> map, ObjectCallback<?> listener) {
        requestParam.clear();
        if (null != map) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                requestParam.put(entry.getKey(), entry.getValue());
            }
        }
        RequestParams params = new RequestParams.Builder().requestParams(requestParam).url(url)
                //   .cacheMode(RequestConfig.HttpCacheMode.CACHE_WHEN_NO_NETWORK)
                .post().build();
        getInstance().executeRequest(params, listener);
    }


}
