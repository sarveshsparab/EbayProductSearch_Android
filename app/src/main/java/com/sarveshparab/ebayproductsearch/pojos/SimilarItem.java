package com.sarveshparab.ebayproductsearch.pojos;

public class SimilarItem {

    private String itemId;
    private String imageURL;
    private String itemURL;
    private String title;
    private String shippingCost;
    private String price;
    private String priceVal;
    private String daysLeft;
    private String daysLeftVal;

    public SimilarItem() {

    }

    public SimilarItem(String itemId, String imageURL, String itemURL, String title, String shippingCost, String price, String priceVal, String daysLeft, String daysLeftVal) {
        this.itemId = itemId;
        this.imageURL = imageURL;
        this.itemURL = itemURL;
        this.title = title;
        this.shippingCost = shippingCost;
        this.price = price;
        this.priceVal = priceVal;
        this.daysLeft = daysLeft;
        this.daysLeftVal = daysLeftVal;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getItemURL() {
        return itemURL;
    }

    public void setItemURL(String itemURL) {
        this.itemURL = itemURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(String shippingCost) {
        this.shippingCost = shippingCost;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceVal() {
        return priceVal;
    }

    public void setPriceVal(String priceVal) {
        this.priceVal = priceVal;
    }

    public String getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(String daysLeft) {
        this.daysLeft = daysLeft;
    }

    public String getDaysLeftVal() {
        return daysLeftVal;
    }

    public void setDaysLeftVal(String daysLeftVal) {
        this.daysLeftVal = daysLeftVal;
    }

    @Override
    public String toString() {
        return "SimilarItem{" +
                "itemId='" + itemId + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", itemURL='" + itemURL + '\'' +
                ", title='" + title + '\'' +
                ", shippingCost='" + shippingCost + '\'' +
                ", price='" + price + '\'' +
                ", daysLeft='" + daysLeft + '\'' +
                '}';
    }

    public SimilarItem deepCopy(){
        SimilarItem sItem = new SimilarItem();

        sItem.setItemId(this.getItemId());
        sItem.setTitle(this.getTitle());
        sItem.setImageURL(this.getImageURL());
        sItem.setPrice(this.getPrice());
        sItem.setPriceVal(this.getPriceVal());
        sItem.setItemURL(this.getItemURL());
        sItem.setDaysLeft(this.getDaysLeft());
        sItem.setDaysLeftVal(this.getDaysLeftVal());
        sItem.setShippingCost(this.getShippingCost());

        return sItem;
    }
}
