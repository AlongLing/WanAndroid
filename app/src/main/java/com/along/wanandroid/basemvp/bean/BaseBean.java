package com.along.wanandroid.basemvp.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 数据类的基类，从网络获取到的数据一共包括三个部分：data(数据实体类，根据不同的数据，有不同的类型)，errorCode，errorMsg。
 * @param <T>
 */
public class BaseBean<T> {

    @SerializedName("data")
    private T data;

    @SerializedName("errorCode")
    private int errorCode;

    @SerializedName("errorMsg")
    private String errorMsg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
