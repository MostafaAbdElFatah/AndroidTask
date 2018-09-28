package com.mostafa.fci.androidtask.Model.Network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import java.util.List;

public class WifiNetworkManager {

    Context context;
    WifiManager mWifiManager;

    String SSID ;
    String pass ;


    public WifiNetworkManager(Context context,String SSID, String pass){
        this.context = context;
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        this.SSID = SSID;
        this.pass = pass;
    }

    public void connectToWifiNetwork() {

        if (!mWifiManager.isWifiEnabled()){
            mWifiManager.setWifiEnabled(true);
        }
        String currentSSID = mWifiManager.getConnectionInfo().getSSID();
        if(currentSSID.equals( "\""+ SSID + "\"" ) ){
            Toast.makeText(context,"You Already connected to Wifi network"
                    ,Toast.LENGTH_SHORT).show();
            return;
        }

        ScanResult mScanResult = getWiFiConfig();
        if (mScanResult != null){
            WifiConfiguration config = new WifiConfiguration();
            if(mScanResult.capabilities.contains("WPA"))
            {
                // We know there is WPA encryption
                connectedToWifiNetwork( "WPA" );
            }
            else if(mScanResult.capabilities.contains("WEP"))
            {
                // We know there is WEP encryption
                connectedToWifiNetwork( "WEP" );
            }else if (mScanResult.capabilities.contains("WPA/WPA2 PSK")){
                connectedToWifiNetwork("WPA/WPA2 PSK");
            } else if (mScanResult.capabilities.contains("WPA2 PSK")){
                connectedToWifiNetwork("WPA2 PSK");
            }

        }else {
            Toast.makeText(context,"This network not exist"
                    ,Toast.LENGTH_LONG).show();
        }

    }

    private ScanResult getWiFiConfig() {

        List<ScanResult> networkList = mWifiManager.getScanResults();
        for (ScanResult network : networkList)
            if ( network.SSID.equals( SSID ) ) {
                return network;
            }
        return null;
    }

    private void connectedToWifiNetwork(String security){

        WifiConfiguration conf = new WifiConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            conf.SSID = SSID;
        } else {
            conf.SSID = "\"" + SSID + "\"";
        }

        Log.d("WIFI_NETWORK", "Security Type :: " + security);
        if (security.equalsIgnoreCase("WEP")) {
            conf.wepKeys[0] = pass;
            conf.wepTxKeyIndex = 0;
            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
        } else if (security
                .equalsIgnoreCase("NONE")) {
            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        } else if ("WPA"
                .equalsIgnoreCase(security)
                || "WPA2"
                .equalsIgnoreCase(security)
                || "WPA/WPA2 PSK"
                .equalsIgnoreCase(security)) {
            // appropriate ciper is need to set according to security type used,
            // ifcase of not added it will not be able to connect
            conf.preSharedKey = "\"" + pass + "\"" ;
            conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            conf.status = WifiConfiguration.Status.ENABLED;
            conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            conf.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.TKIP);
            conf.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.CCMP);
            conf.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            conf.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        }
        int newNetworkId = mWifiManager.addNetwork(conf);
        mWifiManager.enableNetwork(newNetworkId, true);
        mWifiManager.saveConfiguration();
    }

}
