package cn.jclick.httpwrapper.utils;

import java.io.Serializable;

/**
 * author   Joy
 * Date:  2016/3/7.
 * version:  V1.0
 * Description:
 */
public class TT<T> implements Serializable {
    private boolean error;
    private T results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
