package com.mostafa.fci.androidtask.Model.Network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;


import java.util.ArrayList;

public class WifiP2PNetworkManager {

    private Context context;
    private WifiP2pManager mManager;
    private BroadcastReceiver mReceiver;
    private WifiP2pManager.Channel mChannel;
    private OnDiscoverPeers mOnDiscoverPeers;
    private final IntentFilter intentFilter = new IntentFilter();

    public WifiP2PNetworkManager(Context context) {

        this.context = context;
        mOnDiscoverPeers = (OnDiscoverPeers) context;

        mManager = (WifiP2pManager) context.getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(context, context.getMainLooper(), null);

        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.v("WIFI_NETWORK", "Discovery Initiated");
                mManager.requestPeers(mChannel, mPeerListListener);
            }

            @Override
            public void onFailure(int reasonCode) {
                Log.v("WIFI_NETWORK", "Discovery Failed : " + reasonCode);
            }
        });

        mReceiver = new WifiP2PBroadcastReceiver();
        context.registerReceiver(mReceiver,intentFilter);

    }


    class WifiP2PBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
                // Determine if Wifi P2P mode is enabled or not, alert
                // the Activity.
                Log.v("WIFI_NETWORK","WIFI_P2P_STATE_CHANGED_ACTION");
            } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

                // The peer list has changed! We should probably do something about
                // that.
                if (mManager != null) {
                    mManager.requestPeers(mChannel, mPeerListListener);
                }
                Log.v("WIFI_NETWORK", "P2P peers changed");
            } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

                // Connection state changed! We should probably do something about
                // that.
                Log.v("WIFI_NETWORK","WIFI_P2P_CONNECTION_CHANGED_ACTION");
            } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
                Log.v("WIFI_NETWORK","WIFI_P2P_THIS_DEVICE_CHANGED_ACTION");
            }
        }
    }

    WifiP2pManager.PeerListListener mPeerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peers) {
            ArrayList<String> devices = new ArrayList<>();
            for (WifiP2pDevice mWifiP2pDevice:peers.getDeviceList()){
                devices.add(mWifiP2pDevice.deviceName);
            }
            mOnDiscoverPeers.onDiscoveredPeers(devices);
        }
    };

    public void unRegister(){
        context.unregisterReceiver(mReceiver);
    }

}
