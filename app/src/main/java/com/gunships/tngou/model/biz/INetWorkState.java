package com.gunships.tngou.model.biz;

/**
 * Created by 甘书须 on 2016/10/18.
 */
public interface INetWorkState {
    boolean isConnectionNet();
    boolean isConnectionWifi();
    void connectionWifi();
}
