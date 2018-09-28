package com.mostafa.fci.androidtask.Model.Network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTTManager {

    Context context;
    MqttAndroidClient client;

    public MQTTManager(Context context){
        this.context = context;
    }


    public void connect(){
        String clientId = MqttClient.generateClientId();
        String serverURL  = URLs.broker + ":" + URLs.port;
        final MqttAndroidClient client =
                new MqttAndroidClient(context.getApplicationContext(), serverURL, clientId);
        try {

            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(URLs.username);
            options.setPassword(URLs.password.toCharArray());

            IMqttToken token = client.connect(options);

            //IMqttToken token = client.connect();
            //IMqttToken token = client.subscribe(URLs.topic);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    receiveMessage();
                    Log.d("MQTTPROTOCOL", "connect:onSuccess");
                    Toast.makeText(context,"Successfull",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d("MQTTPROTOCOL", "connect:onFailure");
                    Log.d("MQTTPROTOCOL", exception.toString());
                    Log.d("MQTTPROTOCOL", exception.getLocalizedMessage());
                    Log.d("MQTTPROTOCOL", exception.getMessage());
                    exception.printStackTrace();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void receiveMessage(){

        int qos = 1;
        try {
            IMqttToken subToken = client.subscribe(URLs.topic, qos);
            subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // The message was published
                    Log.d("MQTTPROTOCOL", "subscribe:onSuccess");
                    Toast.makeText(context,"Successfull",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    // The subscription could not be performed, maybe the user was not
                    // authorized to subscribe on the specified topic e.g. using wildcards
                    Log.d("MQTTPROTOCOL", "subscribe:onFailure");
                    Log.d("MQTTPROTOCOL", exception.toString());
                    Log.d("MQTTPROTOCOL", exception.getLocalizedMessage());
                    Log.d("MQTTPROTOCOL", exception.getMessage());
                    exception.printStackTrace();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d("MQTTPROTOCOL", "messageArrived:"+ new String(message.getPayload()));
                Toast.makeText(context,"Successfull:"+ new String(message.getPayload())
                        ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

    }

    public void closeConnection(){
        try {
            IMqttToken unsubToken = client.unsubscribe(URLs.topic);
            unsubToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // The subscription could successfully be removed from the client
                    disConnectedClient();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    // some error occurred, this is very unlikely as even if the client
                    // did not had a subscription to the topic the unsubscribe action
                    // will be successfully
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    private void disConnectedClient(){
        try {
            IMqttToken disconToken = client.disconnect();
            disconToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // we are now successfully disconnected
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken,
                                      Throwable exception) {
                    // something went wrong, but probably we are disconnected anyway
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
