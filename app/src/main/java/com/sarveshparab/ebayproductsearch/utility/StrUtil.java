package com.sarveshparab.ebayproductsearch.utility;

import android.util.Log;

import java.util.Map;
import java.util.regex.Pattern;

public class StrUtil {

    public static final String LOG_TAG = "EPS";

    public static final String IP_API_URL = "http://ip-api.com/json";
    public static final String IP_API_ZIP_KEY = "zip";


    public static final String JSON_RESPONSE_STATUS_KEY = "ResponseStatus";
    public static final String JSON_RESPONSE_MESSAGE_KEY = "ResponseMessage";
    public static final String JSON_ERROR_RESPONSE = "Error";
    public static final String JSON_SUCCESS_RESPONSE = "Success";


    public static final String NODE_BASE = "http://node-dot-csci-571-webtech-9.appspot.com/";
//    public static final String NODE_BASE = "http://node-dot-csci-571-webtech-8.appspot.com/";
//    public static final String NODE_BASE = "http://10.26.30.251:3000/";

    public static final String NODE_ZIP_AUTO = "zipAuto/";
    public static final String NODE_EBAY_FIND = "ebay/find/";
    public static final String NODE_EBAY_DETAILS = "ebay/detail/";
    public static final String NODE_EBAY_SIMILAR = "ebay/similar/";
    public static final String NODE_PHOTOS = "photos/";

    public static final String ZIP_ARRAY = "postalCodes";
    public static final String ZIP_ENTRY = "postalCode";

    public static final String PSFORM_PARCEL = "psFormParcel";
    public static final String SR_ITEM_PARCEL = "srDetailsParcel";


    public static final String ZIP_AUTO_QP_KEY = "startZip";


    public static final String ZIP_TYPE_CURR = "curr";
    public static final String ZIP_TYPE_CUST = "cust";

    public static final String IN_WISHLIST_TAG = "in";
    public static final String NOT_IN_WISHLIST_TAG = "out";


    public static final String DEFAULT_NA_VALUE = "N/A";
    public static final String WISHLIST_PREF = "wishListPref";
    public static final String ITEM_KEY_PREFIX = "EPS_WL_ITEM";

    public static String ZIP_CODE_REGEX = "^[0-9]{5}(?:-[0-9]{4})?$";
    public static Pattern ZIP_CODE_PATTERN = Pattern.compile(ZIP_CODE_REGEX);


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

    public static boolean checkValid(String str){
        if(StrUtil.DEFAULT_NA_VALUE.equals(str) || str == null || "".equals(str))
            return false;
        return true;
    }

}
