package com.sarveshparab.ebayproductsearch.pojos;

public class PSForm {

    private String keyword;
    private String category;
    private String categoryValue;
    private boolean condNew;
    private boolean condUsed;
    private boolean condUnspecified;
    private boolean localPickup;
    private boolean freeShipping;
    private String miles;
    private boolean isNearBySearchEnabled;
    private String currZipCode;
    private String custZipCode;
    private String zipCodeType;

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
}
