package com.sarveshparab.ebayproductsearch.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.sarveshparab.ebayproductsearch.utility.ValUtil;

public class SRDetails implements Parcelable {

    private String itemId;
    private String title;
    private String titleCutoff;
    private String zipcode;
    private String shippingCost;
    private String condition;
    private String price;
    private String imageURL;
    private ShippingDetails shippingDetails;
    private SellerDetails sellerDetails;
    private ReturnsDetails returnsDetails;
    private Boolean isInWishList;

    public SRDetails() {
        this.isInWishList = false;
    }

    public SRDetails(String itemId, String title, String zipcode, String shippingCost,
                     String condition, String price, String imageURL,
                     ShippingDetails shippingDetails, SellerDetails sellerDetails,
                     ReturnsDetails returnsDetails, Boolean isInWishList) {
        this.itemId = itemId;
        this.title = title;
        this.zipcode = zipcode;
        this.shippingCost = shippingCost;
        this.condition = condition;
        this.price = price;
        this.imageURL = imageURL;
        this.shippingDetails = shippingDetails;
        this.sellerDetails = sellerDetails;
        this.returnsDetails = returnsDetails;
        this.isInWishList = isInWishList;
        this.titleCutoff = calcTitleCutoff();
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.titleCutoff = calcTitleCutoff();
    }

    public String getTitleCutoff() {
        return titleCutoff;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(String shippingCost) {
        this.shippingCost = shippingCost;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public ShippingDetails getShippingDetails() {
        return shippingDetails;
    }

    public void setShippingDetails(ShippingDetails shippingDetails) {
        this.shippingDetails = shippingDetails;
    }

    public SellerDetails getSellerDetails() {
        return sellerDetails;
    }

    public void setSellerDetails(SellerDetails sellerDetails) {
        this.sellerDetails = sellerDetails;
    }

    public ReturnsDetails getReturnsDetails() {
        return returnsDetails;
    }

    public void setReturnsDetails(ReturnsDetails returnsDetails) {
        this.returnsDetails = returnsDetails;
    }

    public Boolean getInWishList() {
        return isInWishList;
    }

    public void setInWishList(Boolean inWishList) {
        isInWishList = inWishList;
    }

    // Parcelable constructs

    @Override
    public String toString() {
        return "SRDetails{" +
                "itemId='" + itemId + '\'' +
                ", title='" + title + '\'' +
                ", titleCutoff='" + titleCutoff + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", shippingCost='" + shippingCost + '\'' +
                ", condition='" + condition + '\'' +
                ", price='" + price + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", shippingDetails=" + shippingDetails +
                ", sellerDetails=" + sellerDetails +
                ", returnsDetails=" + returnsDetails +
                ", isInWishList=" + isInWishList +
                '}';
    }

    private String calcTitleCutoff() {
        String cutoff = this.title;
        if(this.title.length() >= ValUtil.TITLE_LENGTH_LIMIT){
            cutoff = cutoff.substring(0, ValUtil.TITLE_LENGTH_LIMIT - 4) + " ...";
        }
        return cutoff;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.itemId);
        dest.writeString(this.title);
        dest.writeString(this.titleCutoff);
        dest.writeString(this.zipcode);
        dest.writeString(this.shippingCost);
        dest.writeString(this.condition);
        dest.writeString(this.price);
        dest.writeString(this.imageURL);
        dest.writeParcelable(this.shippingDetails, 0);
        dest.writeParcelable(this.sellerDetails, 0);
        dest.writeParcelable(this.returnsDetails, 0);
        dest.writeString(this.isInWishList.toString());
    }

    protected SRDetails(Parcel in) {
        this.itemId = in.readString();
        this.title = in.readString();
        this.titleCutoff = in.readString();
        this.zipcode = in.readString();
        this.shippingCost = in.readString();
        this.condition = in.readString();
        this.price = in.readString();
        this.imageURL = in.readString();
        this.shippingDetails = in.readParcelable(ShippingDetails.class.getClassLoader());
        this.sellerDetails = in.readParcelable(SellerDetails.class.getClassLoader());
        this.returnsDetails = in.readParcelable(ReturnsDetails.class.getClassLoader());
        this.isInWishList = Boolean.valueOf(in.readString());
    }

    public static final Parcelable.Creator<SRDetails> CREATOR = new Parcelable.Creator<SRDetails>() {
        @Override
        public SRDetails createFromParcel(Parcel source) {
            return new SRDetails(source);
        }

        @Override
        public SRDetails[] newArray(int size) {
            return new SRDetails[size];
        }
    };
}
