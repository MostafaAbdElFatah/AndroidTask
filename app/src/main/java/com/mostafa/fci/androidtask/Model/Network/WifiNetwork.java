package com.mostafa.fci.androidtask.Model.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.text.format.Formatter;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class WifiNetwork {


    public  static void getClientList(Context context) {
        new AsycTask(context).execute();
        return ;
    }


    static class AsycTask extends AsyncTask<Void, Void, Void> {

        Context context;
        OnDiscoverPeers onDiscoverPeers;

        public AsycTask(Context context) {
            super();
            this.context = context;
            onDiscoverPeers = (OnDiscoverPeers) context;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            ArrayList<String> list = new ArrayList<>();

            try {
                ConnectivityManager cm = (ConnectivityManager)context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                WifiManager wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);

                WifiInfo connectionInfo = wm.getConnectionInfo();
                int ipAddress = connectionInfo.getIpAddress();
                String ipString = Formatter.formatIpAddress(ipAddress);

                Log.v("WIFI_NETWORK", "activeNetwork: " + String.valueOf(activeNetwork));
                Log.v("WIFI_NETWORK", "ipString: " + String.valueOf(ipString));

                String prefix = ipString.substring(0, ipString.lastIndexOf(".") + 1);
                Log.v("WIFI_NETWORK", "prefix: " + prefix);

                for (int i = 0; i < 255; i++) {
                    String testIp = prefix + String.valueOf(i);

                    InetAddress address = InetAddress.getByName(testIp);
                    boolean reachable = address.isReachable(1000);
                    String hostName = address.getHostName();
                    if (reachable) {
                        list.add(String.valueOf(hostName));
                        Log.v("WIFI_NETWORK","device connected");
                        Log.v("WIFI_NETWORK", "Host:::: " + String.valueOf(hostName) + "("
                                + String.valueOf(testIp) + ") is reachable!");
                    }
                }

                onDiscoverPeers.onDiscoveredPeers(list);
            } catch (Throwable t) {
                Log.v("WIFI_NETWORK", "Well that's not good.", t);
                Log.v("WIFI_NETWORK",  t.getLocalizedMessage());
                Log.v("WIFI_NETWORK",  t.getMessage());
                Log.v("WIFI_NETWORK",  t.toString());
                t.printStackTrace();
            }

            return null;
        }

    }

}
