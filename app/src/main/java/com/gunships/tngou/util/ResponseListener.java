package com.gunships.tngou.util;

import retrofit2.Response;

/**
 * Created by 甘书须 on 2016/10/2.
 */
public interface ResponseListener<T> {
    void onRequestSuccess(int code, Response<T> response);
    void onRequestFail(int code,String msg);
}
