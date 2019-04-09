package com.sarveshparab.ebayproductsearch.pojos;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.sarveshparab.ebayproductsearch.utility.StrUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class PSForm implements Parcelable {

    private String keyword;
    private String category;
    private String categoryValue;
    private Boolean condNew;
    private Boolean condUsed;
    private Boolean condUnspecified;
    private Boolean localPickup;
    private Boolean freeShipping;
    private String miles;
    private Boolean isNearBySearchEnabled;
    private String currZipCode;
    private String custZipCode;
    private String zipCodeType;

    public PSForm() {
        // default blank constructor
        this.miles = "10";
        this.condNew = Boolean.FALSE;
        this.condUsed = Boolean.FALSE;
        this.condUnspecified = Boolean.FALSE;
        this.freeShipping = Boolean.FALSE;
        this.localPickup = Boolean.FALSE;
        this.isNearBySearchEnabled = Boolean.FALSE;
        this.currZipCode = "";
        this.custZipCode = "";
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
        this.setCategoryValue(this.identifyCategoryValue(category));
    }

    public String getCategoryValue() {
        return categoryValue;
    }

    public void setCategoryValue(String categoryValue) {
        this.categoryValue = categoryValue;
    }

    public boolean isCondNew() {
        return condNew;
    }

    public void setCondNew(boolean condNew) {
        this.condNew = condNew;
    }

    public boolean isCondUsed() {
        return condUsed;
    }

    public void setCondUsed(boolean condUsed) {
        this.condUsed = condUsed;
    }

    public boolean isCondUnspecified() {
        return condUnspecified;
    }

    public void setCondUnspecified(boolean condUnspecified) {
        this.condUnspecified = condUnspecified;
    }

    public boolean isLocalPickup() {
        return localPickup;
    }

    public void setLocalPickup(boolean localPickup) {
        this.localPickup = localPickup;
    }

    public boolean isFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(boolean freeShipping) {
        this.freeShipping = freeShipping;
    }

    public String getMiles() {
        return miles;
    }

    public void setMiles(String miles) {
        this.miles = miles;
    }

    public boolean isNearBySearchEnabled() {
        return isNearBySearchEnabled;
    }

    public void setNearBySearchEnabled(boolean nearBySearchEnabled) {
        isNearBySearchEnabled = nearBySearchEnabled;
    }

    public String getCurrZipCode() {
        return currZipCode;
    }

    public void setCurrZipCode(String currZipCode) {
        this.currZipCode = currZipCode;
    }

    public String getCustZipCode() {
        return custZipCode;
    }

    public void setCustZipCode(String custZipCode) {
        this.custZipCode = custZipCode;
    }

    public String getZipCodeType() {
        return zipCodeType;
    }

    public void setZipCodeType(String zipCodeType) {
        this.zipCodeType = zipCodeType;
    }

    @Override
    public String toString() {
        return "PSForm{" +
                "keyword='" + keyword + '\'' +
                ", category='" + category + '\'' +
                ", categoryValue='" + categoryValue + '\'' +
                ", condNew=" + condNew +
                ", condUsed=" + condUsed +
                ", condUnspecified=" + condUnspecified +
                ", localPickup=" + localPickup +
                ", freeShipping=" + freeShipping +
                ", miles='" + miles + '\'' +
                ", isNearBySearchEnabled=" + isNearBySearchEnabled +
                ", currZipCode='" + currZipCode + '\'' +
                ", custZipCode='" + custZipCode + '\'' +
                ", zipCodeType='" + zipCodeType + '\'' +
                '}';
    }

    private String identifyCategoryValue(String category) {
        String catVal = "";
        switch (category) {
            case "All Categories":
                catVal = "-1";
                break;
            case "Art":
                catVal = "550";
                break;
            case "Baby":
                catVal = "2984";
                break;
            case "Books":
                catVal = "267";
                break;
            case "Clothing, Shoes & Accessories":
                catVal = "11450";
                break;
            case "Computers/Tablets & Networking":
                catVal = "58058";
                break;
            case "Health & Beauty":
                catVal = "26395";
                break;
            case "Music":
                catVal = "11233";
                break;
            case "Video Games & Consoles":
                catVal = "1249";
                break;
            default:
                catVal = "-1";
                break;
        }
        return catVal;
    }

    public Map<String, String> buildQueryParamsMap() {
        Map<String, String> queryParamsMap = new HashMap<>();

        try {
            queryParamsMap.put("keyword", URLEncoder.encode(this.keyword, "UTF-8"));
            queryParamsMap.put("category", this.categoryValue);
            queryParamsMap.put("miles", this.miles);
            queryParamsMap.put("condNew", this.condNew.toString());
            queryParamsMap.put("condUsed", this.condUsed.toString());
            queryParamsMap.put("condUnspecified", this.condUnspecified.toString());
            queryParamsMap.put("freeShipping", this.freeShipping.toString());
            queryParamsMap.put("localPickup", this.localPickup.toString());
            queryParamsMap.put("zipCodeType", this.zipCodeType);
            queryParamsMap.put("currZipCode", this.currZipCode);
            queryParamsMap.put("custZipCode", this.custZipCode);
            queryParamsMap.put("zipcode", this.zipCodeType.equals(StrUtil.ZIP_TYPE_CURR) ?
                    this.currZipCode : this.custZipCode);
            queryParamsMap.put("nearByEnabled", this.isNearBySearchEnabled.toString());
        } catch (UnsupportedEncodingException e) {
            Log.v(StrUtil.LOG_TAG+"|KeywordEncodingError", e.getMessage());
            e.printStackTrace();
        }

        return queryParamsMap;
    }

    // Parcelable constructs

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.keyword);
        dest.writeString(this.category);
        dest.writeString(this.categoryValue);
        dest.writeString(this.condNew.toString());
        dest.writeString(this.condUsed.toString());
        dest.writeString(this.condUnspecified.toString());
        dest.writeString(this.localPickup.toString());
        dest.writeString(this.freeShipping.toString());
        dest.writeString(this.miles);
        dest.writeString(this.isNearBySearchEnabled.toString());
        dest.writeString(this.currZipCode);
        dest.writeString(this.custZipCode);
        dest.writeString(this.zipCodeType);
    }

    protected PSForm(Parcel in) {
        this.keyword = in.readString();
        this.category = in.readString();
        this.categoryValue = in.readString();
        this.condNew = Boolean.valueOf(in.readString());
        this.condUsed = Boolean.valueOf(in.readString());
        this.condUnspecified = Boolean.valueOf(in.readString());
        this.localPickup = Boolean.valueOf(in.readString());
        this.freeShipping = Boolean.valueOf(in.readString());
        this.miles = in.readString();
        this.isNearBySearchEnabled = Boolean.valueOf(in.readString());
        this.currZipCode = in.readString();
        this.custZipCode = in.readString();
        this.zipCodeType = in.readString();
    }

    public static final Parcelable.Creator<PSForm> CREATOR = new Parcelable.Creator<PSForm>() {
        @Override
        public PSForm createFromParcel(Parcel source) {
            return new PSForm(source);
        }

        @Override
        public PSForm[] newArray(int size) {
            return new PSForm[size];
        }
    };
}
