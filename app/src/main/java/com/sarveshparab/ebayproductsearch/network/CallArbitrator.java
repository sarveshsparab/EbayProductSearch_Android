package com.sarveshparab.ebayproductsearch.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sarveshparab.ebayproductsearch.utility.StrUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CallArbitrator {

    public static void makeRequest(final String url, final NetworkCallBack callback, Context ctx,
                                   final Map<String, String> params) {
        Log.v(StrUtil.LOG_TAG+"|NetworkCall", url + StrUtil.formatQueryParams(params));

        JSONRequest rq = new JSONRequest(Request.Method.GET,
                url + StrUtil.formatQueryParams(params), params, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                Log.v(StrUtil.LOG_TAG+"|NetworkSuccess", response.toString());
                try {
                    JSONObject jResponse = (JSONObject)response;
                    callback.onSuccess(jResponse);
                } catch (Exception e) {
                    Log.v(StrUtil.LOG_TAG+"|NetworkSuccess|ExceptionRaised", e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v(StrUtil.LOG_TAG+"|NetworkError", error.toString());
                try {
                    callback.onError(error.toString());
                } catch (Exception e) {
                    Log.v(StrUtil.LOG_TAG+"|NetworkError|ExceptionRaised", e.getMessage());
                    e.printStackTrace();
                }
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return new HashMap<>();
            }

            @Override
            protected Map<String, String> getParams() {
                return params;
            }

        };

        VolleyHandler.getInstance(ctx).addToRequestQueue(rq);
    }

}
