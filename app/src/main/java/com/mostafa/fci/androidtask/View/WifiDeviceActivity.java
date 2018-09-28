package com.mostafa.fci.androidtask.View;

import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import com.mostafa.fci.androidtask.Model.Network.OnDiscoverPeers;
import com.mostafa.fci.androidtask.Model.Network.WifiNetwork;
import com.mostafa.fci.androidtask.Model.Network.WifiNetworkManager;
import com.mostafa.fci.androidtask.Model.Network.WifiP2PNetworkManager;
import com.mostafa.fci.androidtask.R;
import java.util.ArrayList;


public class WifiDeviceActivity extends AppCompatActivity implements OnDiscoverPeers {

    ListView devicesListView;
    DevicesAdapter mDevicesAdapter;
    ArrayList<String> devicesNames;
    WifiP2PNetworkManager mWifiP2PManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_device);

        devicesNames = new ArrayList<>();
        devicesListView = findViewById(R.id.devices_list);
        mDevicesAdapter = new DevicesAdapter(this,devicesNames);
        devicesListView.setEmptyView(findViewById(R.id.empty_view));
        devicesListView.setAdapter(mDevicesAdapter);

        WifiNetworkManager manager = new WifiNetworkManager(this);
        manager.connectToWifiNetwork();

        /***/

        mWifiP2PManager = new WifiP2PNetworkManager(this);
        //WifiNetwork.getClientList(this);

    }


    @Override
    protected void onStop() {
        super.onStop();
        mWifiP2PManager.unRegister();
    }

    @Override
    public void onDiscoveredPeers(ArrayList<String> devices) {
        devicesNames.clear();
        devicesNames.addAll(devices);
        mDevicesAdapter.notifyDataSetChanged();
    }

}
