package com.gunships.tngou.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.gunships.tngou.application.App;

/**
 * Created by 甘书须 on 2016/9/29.
 */
public class NetworkUtil {

    private static String TAG = "NetworkUtil";



    /*-----------------是否有可用的网络-------------------------*/
    public static boolean isNetworkAvailable(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager ==null){
            Log.w(TAG,"无法获取ConnectivityManager");
        }else{
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if(info!=null&&info.isAvailable()){
                return true;
            }else {
                return false;
            }

        }
        return false;
    }

    /*-----------------WIFI或 Moble是否连接 -------------------------*/
    public static boolean isConnectedWifiOrMoble(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager ==null){
            Log.w(TAG,"无法获取ConnectivityManager");
        }else{
            NetworkInfo info= connectivityManager.getActiveNetworkInfo();
            if(info!=null&&info.isAvailable()) {
                Log.i("网络状态",info.isConnected()+":"+info.isAvailable());
                if ((info.getType() == ConnectivityManager.TYPE_WIFI || info.getType() == ConnectivityManager.TYPE_MOBILE) && info.isConnected()) {
                    return true;
                } else {
                    return false;
                }
            }
            Log.i("网络状态","没有可用的网络");
        }
        return false;
    }

    /*-----------------当前连接是否WIFI -------------------------*/
    public static boolean isConnecteWifi(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager ==null){
            Log.w(TAG,"无法获取ConnectivityManager");
        }else{
            NetworkInfo info= connectivityManager.getActiveNetworkInfo();
            if(info.getType()==ConnectivityManager.TYPE_WIFI&&info.isConnected()){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    /*-----------------开启WIFI -------------------------*/
    public static void openWifi(Context context,boolean enable){
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(enable);
    }
}


