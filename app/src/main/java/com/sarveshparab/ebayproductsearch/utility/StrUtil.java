package com.sarveshparab.ebayproductsearch.utility;

import android.util.Log;

import java.util.Map;

public class StrUtil {

    public static final String LOG_TAG = "EPS";

    public static final String IP_API_URL = "http://ip-api.com/json";
    public static final String IP_API_ZIP_KEY = "zip";


    public static final String JSON_RESPONSE_STATUS_KEY = "ResponseStatus";
    public static final String JSON_RESPONSE_MESSAGE_KEY = "ResponseMessage";
    public static final String JSON_ERROR_RESPONSE = "Error";
    public static final String JSON_SUCCESS_RESPONSE = "Success";


    public static final String NODE_BASE = "http://node-dot-csci-571-webtech-8.appspot.com/";

    public static final String NODE_ZIP_AUTO = "zipAuto/";

    public static final String ZIP_ARRAY = "postalCodes";
    public static final String ZIP_ENTRY = "postalCode";

    public static final String PSFORM_PARCEL = "psFormParcel";


    public static final String ZIP_AUTO_QP_KEY = "startZip";


    public static String formatQueryParams(Map<String,String> params) {
        StringBuilder qp = new StringBuilder();

        if(params!=null && params.size()>0){
            for(Map.Entry<String, String> entry : params.entrySet()){
                qp.append(entry.getKey());
                qp.append("=");
                qp.append(entry.getValue());
                qp.append("&");
            }
            qp.deleteCharAt(qp.length()-1);
        }

        Log.v(StrUtil.LOG_TAG+"|QueryParams", qp.toString());

        return qp.toString();
    }
}
