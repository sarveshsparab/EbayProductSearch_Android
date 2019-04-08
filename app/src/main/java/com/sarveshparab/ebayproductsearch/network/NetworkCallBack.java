package com.sarveshparab.ebayproductsearch.network;

import org.json.JSONException;
import org.json.JSONObject;

public interface NetworkCallBack {

    void onSuccess(JSONObject result) throws JSONException;
    void onError(String result) throws Exception;

}
