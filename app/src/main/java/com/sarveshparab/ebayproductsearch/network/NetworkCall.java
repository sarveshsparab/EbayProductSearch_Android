package com.sarveshparab.ebayproductsearch.network;

import android.content.Context;
import android.util.Log;

import com.sarveshparab.ebayproductsearch.adapters.ZipAutoAdapter;
import com.sarveshparab.ebayproductsearch.utility.StrUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NetworkCall {

    public static JSONObject currZipCode;

    public static void fetchCurrLocation(Context actCtx) {
        CallArbitrator.makeRequest(StrUtil.IP_API_URL, new NetworkCallBack() {
            @Override
            public void onSuccess(JSONObject result) throws JSONException {
                JSONObject jsonObject = new JSONObject()
                        .put(StrUtil.JSON_RESPONSE_STATUS_KEY, StrUtil.JSON_SUCCESS_RESPONSE)
                        .put(StrUtil.JSON_RESPONSE_MESSAGE_KEY, result.getString(StrUtil.IP_API_ZIP_KEY));

                currZipCode = jsonObject;
            }

            @Override
            public void onError(String result) throws Exception {
                Log.v(StrUtil.LOG_TAG+"|IP-API_Error", "Call failed with error : "+result);
                JSONObject jsonObject = new JSONObject()
                        .put(StrUtil.JSON_RESPONSE_STATUS_KEY, StrUtil.JSON_ERROR_RESPONSE)
                        .put(StrUtil.JSON_RESPONSE_MESSAGE_KEY, "IP-API call failed");

                currZipCode = jsonObject;
            }
        }, actCtx, null);
    }

    public static void fetchAutoCompleteZip(String text, final ZipAutoAdapter zipAutoAdapter,
                                            Context actCtx) {
        CallArbitrator.makeRequest(StrUtil.NODE_BASE + StrUtil.NODE_ZIP_AUTO + text,
            new NetworkCallBack() {
                @Override
                public void onSuccess(JSONObject result) throws JSONException {
                    List<String> zipsList = new ArrayList<>();
                    try {
                        JSONArray postalCodes = result.getJSONArray(StrUtil.ZIP_ARRAY);
                        for (int i = 0; i < postalCodes.length(); i++) {
                            JSONObject eachZip = postalCodes.getJSONObject(i);
                            zipsList.add(eachZip.getString(StrUtil.ZIP_ENTRY));
                        }
                        Log.v(StrUtil.LOG_TAG + "|GeoNamesSuccess", zipsList.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    zipAutoAdapter.setData(zipsList);
                    zipAutoAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError(String result) throws Exception {
                    Log.v(StrUtil.LOG_TAG + "|GeoNamesError", result.toString());
                }
            }, actCtx, null);
    }

}
