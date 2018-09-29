package com.mostafa.fci.androidtask.Model.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.text.format.Formatter;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class WifiNetwork {

    Context context;
    OnDiscoverPeers onDiscoverPeers;
    public WifiNetwork(Context context) {
        this.context = context;
        onDiscoverPeers = (OnDiscoverPeers) context;
        new MyAsyncTask().execute();
    }

    public class MyAsyncTask  extends AsyncTask<Void, Void, ArrayList<String>>{

        @Override
        protected void onPreExecute() {
            onDiscoverPeers.onStartTask();
        }

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {

            WifiManager wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);

            WifiInfo connectionInfo = wm.getConnectionInfo();
            int ipAddress = connectionInfo.getIpAddress();
            String ipString = Formatter.formatIpAddress(ipAddress);

            String prefix = ipString.substring(0, ipString.lastIndexOf(".") + 1);
            ArrayList<String> hosts = scanSubNet(prefix);
            return hosts;

        }

        @Override
        protected void onPostExecute(ArrayList<String> list) {
           onDiscoverPeers.onDiscoveredPeers(list);
           onDiscoverPeers.onEndTask();
        }
    }

    private ArrayList<String> scanSubNet(String subnet){
        ArrayList<String> hosts = new ArrayList<String>();

        InetAddress inetAddress = null;
        for(int i=1; i < 10; i++){
            Log.d("IP_ADDRESS", "Trying: " + subnet + String.valueOf(i));
            try {
                inetAddress = InetAddress.getByName(subnet + String.valueOf(i));
                if(inetAddress.isReachable(1000)){
                    hosts.add(inetAddress.getHostAddress());
                    Log.d("IP_ADRESS", inetAddress.getHostName());
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return hosts;
    }
}
