package com.mostafa.fci.androidtask.View;

import android.app.ProgressDialog;
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
    ProgressDialog progressDialog;
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

        String SSID = getIntent().getStringExtra("SSID");
        String pass = getIntent().getStringExtra("pass");


        WifiNetworkManager manager = new WifiNetworkManager(this,SSID,pass);
        boolean connected = manager.connectToWifiNetwork();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait ....");
        progressDialog.setMessage("Discovering Devices, Please Wait Until finishing");

        if (connected) {
            /**
             * get Devices that connected to wifi Direct
             * */
            //mWifiP2PManager = new WifiP2PNetworkManager(this);

            /**
             * get Devices that connected to wifi network
             * */
            WifiNetwork wifiNetwork = new WifiNetwork(this);

        }

    }


    @Override
    public void onStartTask() {
        progressDialog.show();
    }

    @Override
    public void onDiscoveredPeers(ArrayList<String> devices) {
        devicesNames.clear();
        devicesNames.addAll(devices);
        mDevicesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEndTask() {
        progressDialog.dismiss();
    }

}
