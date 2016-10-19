package com.gunships.tngou.model.biz;

import com.gunships.tngou.application.App;
import com.gunships.tngou.util.NetworkUtil;

/**
 * Created by 甘书须 on 2016/10/18.
 */
public class NetWorkState implements INetWorkState {
    @Override
    public boolean isConnectionNet() {

        return NetworkUtil.isConnectedWifiOrMoble(App.instance.getApplicationContext());
    }

    @Override
    public boolean isConnectionWifi() {
        if(isConnectionNet()) {
            return NetworkUtil.isConnecteWifi(App.instance.getApplicationContext());
        }else {
            return false;
        }
    }

    @Override
    public void connectionWifi() {
        NetworkUtil.openWifi(App.instance.getApplicationContext(),true);
    }
}
