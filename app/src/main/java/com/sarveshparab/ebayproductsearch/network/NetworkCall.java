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
import java.util.Map;

public class NetworkCall {

    public static void fetchAutoCompleteZip(final ZipAutoAdapter zipAutoAdapter,
                                            Context actCtx, Map<String, String> params) {
        CallArbitrator.makeRequest(StrUtil.NODE_BASE + StrUtil.NODE_ZIP_AUTO,
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
            }, actCtx, params);
    }

}
