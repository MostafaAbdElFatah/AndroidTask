package com.mostafa.fci.androidtask.Model.Network;

import android.net.wifi.p2p.WifiP2pDeviceList;

import java.util.ArrayList;

public interface OnDiscoverPeers {

    public void onDiscoveredPeers(ArrayList<String> devices);

}
