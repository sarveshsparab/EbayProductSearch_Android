package com.sarveshparab.ebayproductsearch.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyHandler {

    private static VolleyHandler volleyHandler;
    private RequestQueue requestQueue;
    private static Context context;

    private VolleyHandler(Context ctx) {
        context = ctx;
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleyHandler getInstance(Context ctx) {
        if (volleyHandler == null) {
            volleyHandler = new VolleyHandler(ctx);
        }
        return volleyHandler;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public  void addToRequestQueue(Request req) {
        getRequestQueue().add(req);
    }

}
