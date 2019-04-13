package com.sarveshparab.ebayproductsearch.utility;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.sarveshparab.ebayproductsearch.R;
import com.sarveshparab.ebayproductsearch.adapters.SimFragAdapter;
import com.sarveshparab.ebayproductsearch.expections.JSONDataException;
import com.sarveshparab.ebayproductsearch.pojos.SimilarItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
                    sItem.setImageURL(fetchImageURL(itemArr.getJSONObject(i)));
                    sItem.setItemURL(fetchItemURL(itemArr.getJSONObject(i)));
                    sItem.setShippingCost(fetchShippingCost(itemArr.getJSONObject(i)));

                    String fetchDaysLeft = fetchDaysLeft(itemArr.getJSONObject(i));
                    sItem.setDaysLeft(StrUtil.DEFAULT_NA_VALUE.equals(fetchDaysLeft) ? fetchDaysLeft : (Integer.parseInt(fetchDaysLeft) > 1 ? fetchDaysLeft + " Days Left" : fetchDaysLeft + " Day Left"));
                    sItem.setDaysLeftVal(StrUtil.DEFAULT_NA_VALUE.equals(fetchDaysLeft) ? "0" : fetchDaysLeft);

                    String priceFetched = fetchPrice(itemArr.getJSONObject(i));
                    sItem.setPrice(StrUtil.DEFAULT_NA_VALUE.equals(priceFetched) ? priceFetched : "$" + priceFetched);
                    sItem.setPriceVal(StrUtil.DEFAULT_NA_VALUE.equals(priceFetched) ? "0.0" : priceFetched);

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
                        retVal = res.getJSONObject("buyItNowPrice").getString("__value__");
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

    public static void customSort(int paramPos, int orderPos, SimFragAdapter adapter, List<SimilarItem> simList) {
        switch (paramPos){
            case 1:
                Collections.sort(simList, new Comparator<SimilarItem>() {
                    @Override
                    public int compare(SimilarItem lhs, SimilarItem rhs) {
                        return lhs.getTitle().compareTo(rhs.getTitle());
                    }
                });
                break;
            case 2:
                Collections.sort(simList, new Comparator<SimilarItem>() {
                    @Override
                    public int compare(SimilarItem lhs, SimilarItem rhs) {
                        return Float.valueOf(lhs.getPriceVal()).compareTo(Float.valueOf(rhs.getPriceVal()));
                    }
                });
                break;
            case 3:
                Collections.sort(simList, new Comparator<SimilarItem>() {
                    @Override
                    public int compare(SimilarItem lhs, SimilarItem rhs) {
                        return Integer.valueOf(lhs.getDaysLeftVal()).compareTo(Integer.valueOf(rhs.getDaysLeftVal()));
                    }
                });
                break;
        }

        if(orderPos == 1){
            Collections.reverse(simList);
        }
    }

    public static void handleSortSpinnerChangeListeners(final List<SimilarItem> simList,
                                                        final List<SimilarItem> simListDefaultCopy,
                                                        final SimFragAdapter adapter,
                                                        final Spinner simfSortParamSpin,
                                                        final Spinner simfSortOrderSpin) {
        simfSortParamSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        simfSortOrderSpin.setEnabled(false);
                        adapter.setSimList(simListDefaultCopy);
                        break;
                    case 1:
                    case 2:
                    case 3:
                        simfSortOrderSpin.setEnabled(true);
                        SimFragUtil.customSort(position, simfSortOrderSpin.getSelectedItemPosition(), adapter, simList);
                        adapter.setSimList(simList);
                        break;
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        simfSortOrderSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SimFragUtil.customSort(simfSortParamSpin.getSelectedItemPosition(), position, adapter, simList);
                adapter.setSimList(simList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
