package com.mostafa.fci.androidtask.View;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mostafa.fci.androidtask.Model.Database.RoomDB;
import com.mostafa.fci.androidtask.Model.Event;
import com.mostafa.fci.androidtask.Model.Network.MQTTManager;
import com.mostafa.fci.androidtask.Model.Network.VolleyManager;
import com.mostafa.fci.androidtask.Model.Network.URLs;
import com.mostafa.fci.androidtask.Model.User;
import com.mostafa.fci.androidtask.R;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity implements FragmentListenter {



    private String SSID;
    RoomDB.RoomManager mRoomManager;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    SignInFragment signInFragment;
    SignUpFragment signUpFragment;
    MQTTManager mMQttManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        mRoomManager = new RoomDB.RoomManager(this);

        signInFragment = new SignInFragment();
        signUpFragment = new SignUpFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment, signInFragment);
        fragmentTransaction.commit();

        if (mRoomManager.isHasRows()){
            User user = mRoomManager.getUsers().get(0);
            // startActivity

        }
        mMQttManager = new MQTTManager(this);
        mMQttManager.connect();

    }


    @Override
    public void onImageClicked(Event event) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        if (event == Event.SHOW_SIGN_IN){
            fragmentTransaction.remove(signUpFragment);
            fragmentTransaction.add(R.id.fragment, signInFragment);
        }else {
            fragmentTransaction.remove(signInFragment);
            fragmentTransaction.add(R.id.fragment, signUpFragment);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onSuccessfulLogin() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URLs.main, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                   SSID = response.get("SSID").toString();
                    Toast.makeText(getApplicationContext(),"SSID:" + SSID, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        VolleyManager.getsInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
       /****
        MQTTManager mMQttManager = new MQTTManager(this);
        mMQttManager.connect();
        **/
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMQttManager.closeConnection();
    }
}
