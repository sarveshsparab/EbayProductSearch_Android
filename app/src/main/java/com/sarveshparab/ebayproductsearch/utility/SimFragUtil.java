package com.sarveshparab.ebayproductsearch.utility;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.sarveshparab.ebayproductsearch.R;
import com.sarveshparab.ebayproductsearch.expections.JSONDataException;
import com.sarveshparab.ebayproductsearch.pojos.SimilarItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SimFragUtil {

    public static List<SimilarItem> fetchSimilarItems(JSONObject result) {
        List<SimilarItem> simList = new ArrayList<>();

        try {
            if(validateJSONResponse(result)){
                JSONArray itemArr = result.getJSONObject("getSimilarItemsResponse").getJSONObject("itemRecommendations").getJSONArray("item");

                for(int i=0;i<itemArr.length();i++){
                    SimilarItem sItem = new SimilarItem();

                    sItem.setItemId(fetchItemId(itemArr.getJSONObject(i)));
                    sItem.setTitle(fetchTitle(itemArr.getJSONObject(i)));
                    sItem.setShippingCost(fetchShippingCost(itemArr.getJSONObject(i)));
                    sItem.setDaysLeft(fetchDaysLeft(itemArr.getJSONObject(i)));
                    sItem.setPrice(fetchPrice(itemArr.getJSONObject(i)));
                    sItem.setImageURL(fetchImageURL(itemArr.getJSONObject(i)));
                    sItem.setItemURL(fetchItemURL(itemArr.getJSONObject(i)));

                    simList.add(sItem);
                }

            } else {
                return new ArrayList<>();
            }
        } catch (Exception e) {
            throw new JSONDataException("Ebay Similar Call | Parsing result failed");
        }

        return simList;
    }

    private static String fetchItemURL(JSONObject res) {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            if(res != null){
                if(res.has("viewItemURL")){
                    retVal = res.getString("viewItemURL");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay Similar Call | Fetching itemURL failed", e.getCause());
        }
        return retVal;
    }

    private static String fetchImageURL(JSONObject res) {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            if(res != null){
                if(res.has("imageURL")){
                    retVal = res.getString("imageURL");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay Similar Call | Fetching imageURL failed", e.getCause());
        }
        return retVal;
    }

    private static String fetchPrice(JSONObject res) {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            if(res != null){
                if(res.has("buyItNowPrice")){
                    if(res.getJSONObject("buyItNowPrice").has("__value__")) {
                        retVal = "$" + res.getJSONObject("buyItNowPrice").getString("__value__");
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay Similar Call | Fetching price failed", e.getCause());
        }
        return retVal;
    }

    private static String fetchDaysLeft(JSONObject res) {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            if(res != null){
                if(res.has("timeLeft")){
                    retVal = res.getString("timeLeft");
                    retVal = retVal.substring(1, retVal.length());
                    retVal = retVal.substring(0, retVal.indexOf("D"));
                    retVal += Integer.parseInt(retVal) > 1 ? " Days Left" : " Day Left";
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay Similar Call | Fetching timeLeft failed", e.getCause());
        }
        return retVal;
    }

    private static String fetchShippingCost(JSONObject res) {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            if(res != null){
                if(res.has("shippingCost")){
                    if(res.getJSONObject("shippingCost").has("__value__")) {
                        retVal = res.getJSONObject("shippingCost").getString("__value__");
                        retVal = retVal.equals("0.00") ? "Free Shipping" : "$" + retVal;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay Similar Call | Fetching shipping cost failed", e.getCause());
        }
        return retVal;
    }

    private static String fetchTitle(JSONObject res) {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            if(res != null){
                if(res.has("title")){
                    retVal = res.getString("title");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay Similar Call | Fetching title failed", e.getCause());
        }
        return retVal;
    }

    private static String fetchItemId(JSONObject res) {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            if(res != null){
                if(res.has("itemId")){
                    retVal = res.getString("itemId");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay Similar Call | Fetching itemId failed", e.getCause());
        }
        return retVal;
    }

    private static boolean validateJSONResponse(JSONObject res) throws JSONDataException {
        boolean isValid = true;

        try {
            if (res == null) {
                isValid = false;
                throw new JSONDataException("Ebay Similar Call | No Results");
            } else if (!res.has("getSimilarItemsResponse")) {
                isValid = false;
                throw new JSONDataException("Ebay Similar Call | No Results");
            } else if (!res.getJSONObject("getSimilarItemsResponse").has("ack")) {
                isValid = false;
                throw new JSONDataException("Ebay Similar Call | No Results");
            } else if (res.getJSONObject("getSimilarItemsResponse").getString("ack").equals("Failure")) {
                isValid = false;
                throw new JSONDataException("Ebay Similar Call | No Results");
            } else {
                if(!res.getJSONObject("getSimilarItemsResponse").has("itemRecommendations")){
                    isValid = false;
                    throw new JSONDataException("Ebay Similar Call | No Results");
                } else if (!res.getJSONObject("getSimilarItemsResponse").getJSONObject("itemRecommendations").has("item")) {
                    isValid = false;
                    throw new JSONDataException("Ebay Similar Call | No Results");
                } else if (res.getJSONObject("getSimilarItemsResponse").getJSONObject("itemRecommendations").getJSONArray("item").length() == 0) {
                    isValid = false;
                    throw new JSONDataException("Ebay Similar Call | No Results");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            isValid = false;
            throw new JSONDataException("Ebay Similar Call | Result validation failed", e.getCause());
        }
        return isValid;
    }

    public static void populateSortingSpinners(Context ctx, Spinner simfSortOrderSpin, Spinner simfSortParamSpin) {
        ArrayAdapter<String> simfOrderAdapter = new ArrayAdapter<>(ctx,
                android.R.layout.simple_spinner_item,
                ctx.getResources().getStringArray(R.array.sortOrder));
        simfOrderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        simfSortOrderSpin.setAdapter(simfOrderAdapter);

        ArrayAdapter<String> simfParamAdapter = new ArrayAdapter<>(ctx,
                android.R.layout.simple_spinner_item,
                ctx.getResources().getStringArray(R.array.sortParams));
        simfParamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        simfSortParamSpin.setAdapter(simfParamAdapter);
    }
}
