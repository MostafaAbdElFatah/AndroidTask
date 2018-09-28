package com.mostafa.fci.androidtask.Model.Network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

class WIFIBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkInfo mNetworkInfo = intent.
                getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if (mNetworkInfo.getState() == NetworkInfo.State.CONNECTED){
            String bssid=intent.getStringExtra(WifiManager.EXTRA_BSSID);
            Log.d("WIFI_NETWORK", "Connected to BSSID:"+bssid);
            String ssid = ((WifiInfo) intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO)).getSSID();
            String log ="Connected to SSID:"+ssid;
            Log.d("WIFI_NETWORK","Connected to SSID:"+ssid);
            Toast.makeText(context, log, Toast.LENGTH_SHORT).show();
        }
    }

}