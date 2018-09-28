package com.mostafa.fci.androidtask.Model.Network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Mostafa AbdEl_Fatah on 9/8/2017.
 */

public class VolleyManager {

    private static VolleyManager sInstance;
    private RequestQueue requestQueue;
    private static Context context;

    private VolleyManager(Context context1){
        context = context1;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue(){
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return  requestQueue;
    }
    public static synchronized VolleyManager getsInstance(Context context1){
        if (sInstance == null){
            sInstance = new VolleyManager(context1);
        }
        return sInstance;
    }
    public<T> void addToRequestQueue(Request<T> request){
        requestQueue.add(request);
    }
}
