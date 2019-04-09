package com.sarveshparab.ebayproductsearch.utility;

import com.sarveshparab.ebayproductsearch.expections.JSONDataException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SRUtil {


    public static boolean validateJSONResponse(JSONObject result) throws JSONDataException{
        boolean isValid = true;

        try {
            if(result == null){
                isValid = false;
                throw new JSONDataException("Ebay Find Call | No Results");
            } else if (result.getJSONArray("findItemsAdvancedResponse") == null){
                isValid = false;
                throw new JSONDataException("Ebay Find Call | No Results");
            } else if (result.getJSONArray("findItemsAdvancedResponse").getJSONObject(0) == null){
                isValid = false;
                throw new JSONDataException("Ebay Find Call | No Results");
            } else {

                if(result.getJSONArray("findItemsAdvancedResponse").getJSONObject(0).getJSONArray("ack").get(0).equals("Failure")){

                    if(result.getJSONArray("findItemsAdvancedResponse").getJSONObject(0).getJSONArray("errorMessage").getJSONObject(0).getJSONArray("error").getJSONObject(0).getJSONArray("errorId").get(0).equals("18")) {
                        isValid = false;
                        throw new JSONDataException("Ebay Find Call | Invalid Zip Code");
                    } else if(result.getJSONArray("findItemsAdvancedResponse").getJSONObject(0).getJSONArray("errorMessage").getJSONObject(0).getJSONArray("error").getJSONObject(0).getJSONArray("errorId").get(0).equals("36")) {
                        isValid = false;
                        throw new JSONDataException("Ebay Find Call | Invalid Keyword");
                    }

                } else {
                    if (result.getJSONArray("findItemsAdvancedResponse").getJSONObject(0).getJSONArray("searchResult") == null) {
                        isValid = false;
                        throw new JSONDataException("Ebay Find Call | No Results");
                    } else if (result.getJSONArray("findItemsAdvancedResponse").getJSONObject(0).getJSONArray("searchResult").getJSONObject(0) == null) {
                        isValid = false;
                        throw new JSONDataException("Ebay Find Call | No Results");
                    } else if (result.getJSONArray("findItemsAdvancedResponse").getJSONObject(0).getJSONArray("searchResult").getJSONObject(0).getJSONArray("item") == null) {
                        isValid = false;
                        throw new JSONDataException("Ebay Find Call | No Results");
                    } else if (result.getJSONArray("findItemsAdvancedResponse").getJSONObject(0).getJSONArray("searchResult").getJSONObject(0).getJSONArray("item").length() == 0) {
                        isValid = false;
                        throw new JSONDataException("Ebay Find Call | No Results");
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            isValid = false;
            throw new JSONDataException("Ebay Find Call | Result validation failed", e.getCause());
        }

        return isValid;
    }

    public static String getResultCount(JSONObject result) throws JSONDataException {
        int count = 0;

        try {
            count = result.getJSONArray("findItemsAdvancedResponse").getJSONObject(0)
                    .getJSONArray("searchResult").getJSONObject(0)
                    .getJSONArray("item").length();
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay Find Call | Result count failed", e.getCause());
        }

        return String.valueOf(count);
    }

    public static JSONArray getIterableResult(JSONObject result) throws JSONDataException {
        try {
            return result.getJSONArray("findItemsAdvancedResponse").getJSONObject(0)
                    .getJSONArray("searchResult").getJSONObject(0)
                    .getJSONArray("item");
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay Find Call | iterable result failed", e.getCause());
        }
    }

    public static String fetchItemId(JSONArray result, int i) throws JSONDataException {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            JSONObject srItem = result.getJSONObject(i);
            if(srItem != null){
                if(srItem.getJSONArray("itemId") != null){
                    retVal = srItem.getJSONArray("itemId").get(0).toString();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay Find Call | Fetching itemId failed", e.getCause());
        }
        return retVal;
    }

    public static String fetchTitle(JSONArray result, int i) throws JSONDataException {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            JSONObject srItem = result.getJSONObject(i);
            if(srItem != null){
                if(srItem.getJSONArray("title") != null){
                    retVal = srItem.getJSONArray("title").get(0).toString();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay Find Call | Fetching title failed", e.getCause());
        }
        return retVal;
    }

    public static String fetchCondition(JSONArray result, int i) throws JSONDataException {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            JSONObject srItem = result.getJSONObject(i);
            if(srItem != null){
                if(srItem.getJSONArray("condition") != null){
                    if(srItem.getJSONArray("condition").getJSONObject(0) != null) {
                        if(srItem.getJSONArray("condition").getJSONObject(0).getJSONArray("conditionDisplayName") != null) {
                            retVal = srItem.getJSONArray("condition").getJSONObject(0).getJSONArray("conditionDisplayName").get(0).toString();
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay Find Call | Fetching condition failed", e.getCause());
        }
        return retVal;
    }

    public static String fetchImageURL(JSONArray result, int i) throws JSONDataException {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            JSONObject srItem = result.getJSONObject(i);
            if(srItem != null){
                if(srItem.getJSONArray("galleryURL") != null){
                    retVal = srItem.getJSONArray("galleryURL").get(0).toString();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay Find Call | Fetching imageURL failed", e.getCause());
        }
        return retVal;
    }

    public static String fetchPrice(JSONArray result, int i) throws JSONDataException {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            JSONObject srItem = result.getJSONObject(i);
            if(srItem != null){
                if(srItem.getJSONArray("sellingStatus") != null){
                    if(srItem.getJSONArray("sellingStatus").getJSONObject(0) != null) {
                        if(srItem.getJSONArray("sellingStatus").getJSONObject(0).getJSONArray("currentPrice") != null) {
                            if(srItem.getJSONArray("sellingStatus").getJSONObject(0).getJSONArray("currentPrice").getJSONObject(0) != null) {
                                retVal = "$" + srItem.getJSONArray("sellingStatus").getJSONObject(0).getJSONArray("currentPrice").getJSONObject(0).get("__value__").toString();
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay Find Call | Fetching price failed", e.getCause());
        }
        return retVal;
    }

    public static String fetchShippingCost(JSONArray result, int i) throws JSONDataException {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            JSONObject srItem = result.getJSONObject(i);
            if(srItem != null){
                if(srItem.getJSONArray("shippingInfo") != null){
                    if(srItem.getJSONArray("shippingInfo").getJSONObject(0) != null) {
                        if(srItem.getJSONArray("shippingInfo").getJSONObject(0).getJSONArray("shippingServiceCost") != null) {
                            if(srItem.getJSONArray("shippingInfo").getJSONObject(0).getJSONArray("shippingServiceCost").getJSONObject(0) != null) {
                                retVal = srItem.getJSONArray("shippingInfo").getJSONObject(0).getJSONArray("shippingServiceCost").getJSONObject(0).get("__value__").toString();
                                retVal = retVal.equals("0.0") ? "Free Shipping" : "$" + retVal;
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay Find Call | Fetching shippingCost failed", e.getCause());
        }
        return retVal;
    }

    public static String fetchZipCode(JSONArray result, int i) throws JSONDataException {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            JSONObject srItem = result.getJSONObject(i);
            if(srItem != null){
                if(srItem.getJSONArray("postalCode") != null){
                    retVal = srItem.getJSONArray("postalCode").get(0).toString();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay Find Call | Fetching zipcode failed", e.getCause());
        }
        return retVal;
    }

//    public static ShippingDetails fetchShippingDetails(JSONArray result, int i) throws JSONDataException {
//        ShippingDetails retVal = new ShippingDetails();
//        try {
//            JSONObject srItem = result.getJSONObject(i);
//            if(srItem != null){
//                if(srItem.getJSONArray("postalCode") != null){
//
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//            throw new JSONDataException("Ebay Find Call | Fetching shippingDetails failed", e.getCause());
//        }
//        return retVal;
//    }

}
