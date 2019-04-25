package com.sarveshparab.ebayproductsearch.utility;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.sarveshparab.ebayproductsearch.R;
import com.sarveshparab.ebayproductsearch.expections.JSONDataException;
import com.sarveshparab.ebayproductsearch.pojos.SRDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProdFragUtil {
    public static String fetchSubtitle(JSONObject res) {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            if(res != null){
                if(res.has("Item")) {
                    if (res.getJSONObject("Item").has("Subtitle")) {
                        retVal = res.getJSONObject("Item").get("Subtitle").toString();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay SingleItem Call | Fetching subtitle failed", e.getCause());
        }
        return retVal;
    }

    public static String fetchBrand(JSONObject res) {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            if(res != null){
                if(res.has("Item")) {
                    if (res.getJSONObject("Item").has("ItemSpecifics")) {
                        JSONArray itemSpecsArray = res.getJSONObject("Item").getJSONObject("ItemSpecifics").getJSONArray("NameValueList");
                        for (int i = 0; i < itemSpecsArray.length(); i++) {
                            if (itemSpecsArray.getJSONObject(i).getString("Name").equals("Brand")) {
                                retVal = itemSpecsArray.getJSONObject(i).getJSONArray("Value").getString(0);
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay SingleItem Call | Fetching brand failed", e.getCause());
        }
        return retVal;
    }

    public static List<String> fetchSpecsDetails(JSONObject res) {
        List<String> detailsList = new ArrayList<>();

        String brandName = ProdFragUtil.fetchBrand(res);
        if(!brandName.equals(StrUtil.DEFAULT_NA_VALUE)){
            detailsList.add(brandName);
        }

        try {
            if(res != null){
                if(res.has("Item")) {
                    if (res.getJSONObject("Item").has("ItemSpecifics")) {
                        JSONArray itemSpecsArray = res.getJSONObject("Item").getJSONObject("ItemSpecifics").getJSONArray("NameValueList");
                        for (int i = 0; i < itemSpecsArray.length(); i++) {
                            if (!itemSpecsArray.getJSONObject(i).getString("Name").equals("Brand")) {
                                detailsList.add(itemSpecsArray.getJSONObject(i).getJSONArray("Value").getString(0));
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay SingleItem Call | Fetching itemSpecs failed", e.getCause());
        }

        return detailsList;
    }

    public static List<String> fetchItemImages(JSONObject res) {
        List<String> imagesList = new ArrayList<>();

        try {
            if(res != null){
                if(res.has("Item")) {
                    if (res.getJSONObject("Item").has("PictureURL")) {
                        JSONArray itemImgArray = res.getJSONObject("Item").getJSONArray("PictureURL");
                        for (int i = 0; i < itemImgArray.length(); i++) {
                            imagesList.add(itemImgArray.getString(i));
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay SingleItem Call | Fetching itemImages failed", e.getCause());
        }

        return imagesList;
    }

    public static String fetchGlobalShipping(JSONObject res) {
        String retVal = "No";
        try {
            if(res != null){
                if(res.has("Item")) {
                    if (res.getJSONObject("Item").has("GlobalShipping")) {
                        retVal = res.getJSONObject("Item").getBoolean("GlobalShipping") ? "Yes" : "No";
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay SingleItem Call | Fetching globalShipping failed", e.getCause());
        }
        return retVal;
    }

    public static String fetchPolicy(JSONObject res) {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            if(res != null){
                if(res.has("Item")) {
                    if (res.getJSONObject("Item").has("ReturnPolicy")) {
                        if(res.getJSONObject("Item").getJSONObject("ReturnPolicy").has("ReturnsAccepted")) {
                            retVal = res.getJSONObject("Item").getJSONObject("ReturnPolicy").getString("ReturnsAccepted");
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay SingleItem Call | Fetching policy failed", e.getCause());
        }
        return retVal;
    }

    public static String fetchRefundMode(JSONObject res) {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            if(res != null){
                if(res.has("Item")) {
                    if (res.getJSONObject("Item").has("ReturnPolicy")) {
                        if(res.getJSONObject("Item").getJSONObject("ReturnPolicy").has("Refund")) {
                            retVal = res.getJSONObject("Item").getJSONObject("ReturnPolicy").getString("Refund");
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay SingleItem Call | Fetching refundMode failed", e.getCause());
        }
        return retVal;
    }

    public static String fetchShippedBy(JSONObject res) {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            if(res != null){
                if(res.has("Item")) {
                    if (res.getJSONObject("Item").has("ReturnPolicy")) {
                        if(res.getJSONObject("Item").getJSONObject("ReturnPolicy").has("ShippingCostPaidBy")) {
                            retVal = res.getJSONObject("Item").getJSONObject("ReturnPolicy").getString("ShippingCostPaidBy");
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay SingleItem Call | Fetching shippedBy failed", e.getCause());
        }
        return retVal;
    }

    public static String fetchReturnsWithin(JSONObject res) {
        String retVal = StrUtil.DEFAULT_NA_VALUE;
        try {
            if(res != null){
                if(res.has("Item")) {
                    if (res.getJSONObject("Item").has("ReturnPolicy")) {
                        if(res.getJSONObject("Item").getJSONObject("ReturnPolicy").has("ReturnsWithin")) {
                            retVal = res.getJSONObject("Item").getJSONObject("ReturnPolicy").getString("ReturnsWithin");
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Ebay SingleItem Call | Fetching returnsWithin failed", e.getCause());
        }
        return retVal;
    }

    public static void populateImageScrollView(JSONObject result, View view, Context ctx) {
        HorizontalScrollView profHSV = view.findViewById(R.id.profHSV);
        LinearLayout profHSVLL = view.findViewById(R.id.profHSVLL);
        List<String> itemImagesList = ProdFragUtil.fetchItemImages(result);

        if(itemImagesList.size() == 0){
            profHSV.setVisibility(View.GONE);
        } else {
            if(itemImagesList.size() == 1){
//                LinearLayout.LayoutParams profHSVLLParams = new LinearLayout.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.MATCH_PARENT);
//                profHSVLLParams.gravity = Gravity.CENTER;
//                profHSVLL.setLayoutParams(profHSVLLParams);
            }
            for(int img=0; img<itemImagesList.size(); img++){
                ImageView imageView = new ImageView(ctx);
                LinearLayout.LayoutParams imgLayoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                imgLayoutParams.setMarginStart(ValUtil.getDPVal(ValUtil.HOR_SCROLL_VIEW_IMG_MARGIN_START, ctx));
                imgLayoutParams.setMarginEnd(ValUtil.getDPVal(ValUtil.HOR_SCROLL_VIEW_IMG_MARGIN_END, ctx));
                imageView.setLayoutParams(imgLayoutParams);
                Glide.with(ctx)
                        .load(itemImagesList.get(img))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                                Log.v(StrUtil.LOG_TAG + "|GlideImageLoadFailed", "Potentially Image not found[404] : " + model.toString());
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .error(R.drawable.image_broken_icon)
                        .into(imageView);

                profHSVLL.addView(imageView);
            }

            profHSV.setVisibility(View.VISIBLE);
        }
    }

    public static void populateSpecs(JSONObject result, View view, LayoutInflater inflater) {
        LinearLayout profSpecsLL = view.findViewById(R.id.profSpecsLL);

        List<String> itemSpecsList = ProdFragUtil.fetchSpecsDetails(result);
        if(itemSpecsList.size() == 0){
            profSpecsLL.setVisibility(View.GONE);
        } else {
            TableLayout profSpecsTL = view.findViewById(R.id.profSpecsTL);
            for(int spec=0; spec<itemSpecsList.size(); spec++){
                TableRow specRow = (TableRow)inflater.inflate(R.layout.spec_row, profSpecsTL, false);
                TextView profSpecsRowValueTV = specRow.findViewById(R.id.profSpecsRowValueTV);
                String specText = itemSpecsList.get(spec);
                profSpecsRowValueTV.setText(specText.substring(0, 1).toUpperCase() + specText.substring(1));
                profSpecsTL.addView(specRow);
            }

            profSpecsLL.setVisibility(View.VISIBLE);
        }
    }

    public static void populateHighlights(JSONObject result, View view, SRDetails srDetails) {
        LinearLayout profHighlightsLL = view.findViewById(R.id.profHighlightsLL);
        int highlightItems = 0;

        TableRow profHighlightsSubtitleRowTR = view.findViewById(R.id.profHighlightsSubtitleRowTR);
        TableRow profHighlightsPriceRowTR = view.findViewById(R.id.profHighlightsPriceRowTR);
        TableRow profHighlightsBrandRowTR = view.findViewById(R.id.profHighlightsBrandRowTR);

        TextView profHighlightsSubtitleValueTV = view.findViewById(R.id.profHighlightsSubtitleValueTV);
        TextView profHighlightsPriceValueTV = view.findViewById(R.id.profHighlightsPriceValueTV);
        TextView profHighlightsBrandValueTV = view.findViewById(R.id.profHighlightsBrandValueTV);

        String itemSubTitle = ProdFragUtil.fetchSubtitle(result);
        if(StrUtil.DEFAULT_NA_VALUE.equals(itemSubTitle)){
            profHighlightsSubtitleRowTR.setVisibility(View.GONE);
            profHighlightsSubtitleValueTV.setText("");
        } else {
            profHighlightsSubtitleRowTR.setVisibility(View.VISIBLE);
            profHighlightsSubtitleValueTV.setText(itemSubTitle);
            highlightItems++;
        }

        if(StrUtil.DEFAULT_NA_VALUE.equals(srDetails.getPrice())){
            profHighlightsPriceRowTR.setVisibility(View.GONE);
            profHighlightsPriceValueTV.setText("");
        } else {
            profHighlightsPriceRowTR.setVisibility(View.VISIBLE);
            profHighlightsPriceValueTV.setText(srDetails.getPrice());
            highlightItems++;
        }

        String itemBrand = ProdFragUtil.fetchBrand(result);
        if(StrUtil.DEFAULT_NA_VALUE.equals(itemBrand)){
            profHighlightsBrandRowTR.setVisibility(View.GONE);
            profHighlightsBrandValueTV.setText("");
        } else {
            profHighlightsBrandRowTR.setVisibility(View.VISIBLE);
            profHighlightsBrandValueTV.setText(itemBrand);
            highlightItems++;
        }

        if(highlightItems == 0){
            profHighlightsLL.setVisibility(View.GONE);
        } else {
            profHighlightsLL.setVisibility(View.VISIBLE);
        }
    }

    public static void populateHeaderFields(View view, SRDetails srDetails) {
        TextView profTitleTV = view.findViewById(R.id.profTitleTV);
        profTitleTV.setText(srDetails.getTitle());

        TextView profPriceTitleTV = view.findViewById(R.id.profPriceTitleTV);
        profPriceTitleTV.setText(srDetails.getPrice());

        TextView profShippingTitleTV = view.findViewById(R.id.profShippingTitleTV);
        if("Free Shipping".equals(srDetails.getShippingCost())){
            profShippingTitleTV.setText("With Free Shipping");
        } else {
            profShippingTitleTV.setText("With " + srDetails.getShippingCost() + " Shipping");
        }
    }


}
