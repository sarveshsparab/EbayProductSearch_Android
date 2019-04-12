package com.sarveshparab.ebayproductsearch.utility;

import com.sarveshparab.ebayproductsearch.expections.JSONDataException;
import com.sarveshparab.ebayproductsearch.pojos.ReturnsDetails;
import com.sarveshparab.ebayproductsearch.pojos.SellerDetails;
import com.sarveshparab.ebayproductsearch.pojos.ShippingDetails;

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
            } else if (!result.has("findItemsAdvancedResponse")){
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
                if(srItem.has("itemId")){
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
                if(srItem.has("title")){
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
                if(srItem.has("condition")){
                    if(srItem.getJSONArray("condition").getJSONObject(0) != null) {
                        if(srItem.getJSONArray("condition").getJSONObject(0).has("conditionDisplayName")) {
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

    public static String fetchItemURL(JSONArray result, int i) throws JSONDataException {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            JSONObject srItem = result.getJSONObject(i);
            if(srItem != null){
                if(srItem.has("viewItemURL")){
                    retVal = srItem.getJSONArray("viewItemURL").get(0).toString();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay Find Call | Fetching itemURL failed", e.getCause());
        }
        return retVal;
    }

    public static String fetchImageURL(JSONArray result, int i) throws JSONDataException {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            JSONObject srItem = result.getJSONObject(i);
            if(srItem != null){
                if(srItem.has("galleryURL")){
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
                if(srItem.has("sellingStatus")){
                    if(srItem.getJSONArray("sellingStatus").getJSONObject(0) != null) {
                        if(srItem.getJSONArray("sellingStatus").getJSONObject(0).has("currentPrice")) {
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
                if(srItem.has("shippingInfo")){
                    if(srItem.getJSONArray("shippingInfo").getJSONObject(0) != null) {
                        if(srItem.getJSONArray("shippingInfo").getJSONObject(0).has("shippingServiceCost")) {
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
                if(srItem.has("postalCode")){
                    retVal = srItem.getJSONArray("postalCode").get(0).toString();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay Find Call | Fetching zipcode failed", e.getCause());
        }
        return retVal;
    }

    public static ShippingDetails fetchShippingDetails(JSONArray result, int i) throws JSONDataException {
        ShippingDetails retVal = new ShippingDetails();
        try {
            JSONObject srItem = result.getJSONObject(i);
            if(srItem != null){
                String shippingCost = fetchShippingCost(result, i);
                if(!StrUtil.DEFAULT_NA_VALUE.equals(shippingCost))
                    retVal.setShippingCost(shippingCost);

//                retVal.setGlobalShipping("");  ** to be fetched from itemdetails call

                String handlingTime = fetchHandlingTime(result, i);
                if(!StrUtil.DEFAULT_NA_VALUE.equals(handlingTime))
                    retVal.setHandlingTime(handlingTime);

                String condition = fetchCondition(result, i);
                if(!StrUtil.DEFAULT_NA_VALUE.equals(condition))
                    retVal.setCondition(condition);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay Find Call | Fetching shippingDetails failed", e.getCause());
        }
        return retVal;
    }

    public static String fetchHandlingTime(JSONArray result, int i) {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            JSONObject srItem = result.getJSONObject(i);
            if(srItem != null){
                if(srItem.has("shippingInfo")){
                    if(srItem.getJSONArray("shippingInfo").getJSONObject(0) != null) {
                        if(srItem.getJSONArray("shippingInfo").getJSONObject(0).has("handlingTime")) {
                            if(srItem.getJSONArray("shippingInfo").getJSONObject(0).getJSONArray("handlingTime").get(0) != null) {
                                retVal = srItem.getJSONArray("shippingInfo").getJSONObject(0).getJSONArray("handlingTime").get(0).toString();
                                retVal += retVal.equals("1") ? " Day" : " Days";
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay Find Call | Fetching handlingTime failed", e.getCause());
        }
        return retVal;
    }

    public static SellerDetails fetchSellerDetails(JSONArray result, int i) throws JSONDataException {
        SellerDetails retVal = new SellerDetails();
        try {
            JSONObject srItem = result.getJSONObject(i);
            if(srItem != null){

                String storeName = fetchStoreName(result, i);
                if(!StrUtil.DEFAULT_NA_VALUE.equals(storeName))
                    retVal.setStoreName(storeName);

                String storeURL = fetchStoreURL(result, i);
                if(!StrUtil.DEFAULT_NA_VALUE.equals(storeURL))
                    retVal.setStoreURL(storeURL);

                String feedbackScore = fetchFeedbackScore(result, i);
                if(!StrUtil.DEFAULT_NA_VALUE.equals(feedbackScore))
                    retVal.setFeedbackScore(feedbackScore);

                String popularity = fetchPopularity(result, i);
                if(!StrUtil.DEFAULT_NA_VALUE.equals(popularity))
                    retVal.setPopularity(popularity);

                String feedbackStar = fetchFeedbackStar(result, i);
                if(!StrUtil.DEFAULT_NA_VALUE.equals(feedbackStar))
                    retVal.setFeedbackStar(feedbackStar);

            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay Find Call | Fetching sellerDetails failed", e.getCause());
        }
        return retVal;
    }

    public static String fetchStoreName(JSONArray result, int i) {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            JSONObject srItem = result.getJSONObject(i);
            if(srItem != null){
                if(srItem.has("storeInfo")){
                    if(srItem.getJSONArray("storeInfo").getJSONObject(0) != null) {
                        if(srItem.getJSONArray("storeInfo").getJSONObject(0).has("storeName")) {
                            if(srItem.getJSONArray("storeInfo").getJSONObject(0).getJSONArray("storeName").get(0) != null) {
                                retVal = srItem.getJSONArray("storeInfo").getJSONObject(0).getJSONArray("storeName").get(0).toString();
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay Find Call | Fetching storeName failed", e.getCause());
        }
        return retVal;
    }

    public static String fetchStoreURL(JSONArray result, int i) {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            JSONObject srItem = result.getJSONObject(i);
            if(srItem != null){
                if(srItem.has("storeInfo")){
                    if(srItem.getJSONArray("storeInfo").getJSONObject(0) != null) {
                        if(srItem.getJSONArray("storeInfo").getJSONObject(0).has("storeURL")) {
                            if(srItem.getJSONArray("storeInfo").getJSONObject(0).getJSONArray("storeURL").get(0) != null) {
                                retVal = srItem.getJSONArray("storeInfo").getJSONObject(0).getJSONArray("storeURL").get(0).toString();
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay Find Call | Fetching storeURL failed", e.getCause());
        }
        return retVal;
    }

    public static String fetchFeedbackScore(JSONArray result, int i) {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            JSONObject srItem = result.getJSONObject(i);
            if(srItem != null){
                if(srItem.has("sellerInfo")){
                    if(srItem.getJSONArray("sellerInfo").getJSONObject(0) != null) {
                        if(srItem.getJSONArray("sellerInfo").getJSONObject(0).has("feedbackScore")) {
                            if(srItem.getJSONArray("sellerInfo").getJSONObject(0).getJSONArray("feedbackScore").get(0) != null) {
                                retVal = srItem.getJSONArray("sellerInfo").getJSONObject(0).getJSONArray("feedbackScore").get(0).toString();
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay Find Call | Fetching feedbackScore failed", e.getCause());
        }
        return retVal;
    }

    public static String fetchPopularity(JSONArray result, int i) {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            JSONObject srItem = result.getJSONObject(i);
            if(srItem != null){
                if(srItem.has("sellerInfo")){
                    if(srItem.getJSONArray("sellerInfo").getJSONObject(0) != null) {
                        if(srItem.getJSONArray("sellerInfo").getJSONObject(0).has("positiveFeedbackPercent")) {
                            if(srItem.getJSONArray("sellerInfo").getJSONObject(0).getJSONArray("positiveFeedbackPercent").get(0) != null) {
                                retVal = srItem.getJSONArray("sellerInfo").getJSONObject(0).getJSONArray("positiveFeedbackPercent").get(0).toString();
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay Find Call | Fetching popularity failed", e.getCause());
        }
        return retVal;
    }

    public static String fetchFeedbackStar(JSONArray result, int i) {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            JSONObject srItem = result.getJSONObject(i);
            if(srItem != null){
                if(srItem.has("sellerInfo")){
                    if(srItem.getJSONArray("sellerInfo").getJSONObject(0) != null) {
                        if(srItem.getJSONArray("sellerInfo").getJSONObject(0).has("feedbackRatingStar")) {
                            if(srItem.getJSONArray("sellerInfo").getJSONObject(0).getJSONArray("feedbackRatingStar").get(0) != null) {
                                retVal = srItem.getJSONArray("sellerInfo").getJSONObject(0).getJSONArray("feedbackRatingStar").get(0).toString();
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay Find Call | Fetching feedbackStar failed", e.getCause());
        }
        return retVal;
    }

    public static ReturnsDetails fetchReturnsDetails(JSONArray result, int i) throws JSONDataException {
        ReturnsDetails retVal = new ReturnsDetails();
        // ** to be fetched from itemdetails call
        return retVal;
    }

}
