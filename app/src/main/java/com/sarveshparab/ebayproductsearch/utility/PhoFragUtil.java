package com.sarveshparab.ebayproductsearch.utility;

import com.sarveshparab.ebayproductsearch.expections.JSONDataException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PhoFragUtil {
    public static List<String> fetchImageURL(JSONObject res) {
        List<String> imageURLList = new ArrayList<>();

        try {
            if(res != null){
                if(res.has("items")) {
                    JSONArray imgArray = res.getJSONArray("items");
                    for (int i = 0; i < imgArray.length(); i++) {
                        imageURLList.add(imgArray.getJSONObject(i).getString("link"));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new JSONDataException("Google Photos Call | Fetching goolglePhotos failed", e.getCause());
        }

        return imageURLList;
    }
}
