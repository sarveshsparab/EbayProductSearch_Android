package com.sarveshparab.ebayproductsearch.pojos;

import android.os.Parcel;
import android.os.Parcelable;

public class ShippingDetails implements Parcelable {
    private String shippingCost;
    private String globalShipping;
    private String handlingTime;
    private String condition;
    private Boolean containsDetails;

    public ShippingDetails() {
        this.containsDetails = false;
    }

    public ShippingDetails(String shippingCost, String globalShipping, String handlingTime, String condition) {
        this.shippingCost = shippingCost;
        this.globalShipping = globalShipping;
        this.handlingTime = handlingTime;
        this.condition = condition;
        this.containsDetails = true;
    }

    public String getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(String shippingCost) {
        this.shippingCost = shippingCost;
        this.containsDetails = true;
    }

    public String getGlobalShipping() {
        return globalShipping;
    }

    public void setGlobalShipping(String globalShipping) {
        this.globalShipping = globalShipping;
        this.containsDetails = true;
    }

    public String getHandlingTime() {
        return handlingTime;
    }

    public void setHandlingTime(String handlingTime) {
        this.handlingTime = handlingTime;
        this.containsDetails = true;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
        this.containsDetails = true;
    }

    public Boolean getContainsDetails() {
        return containsDetails;
    }

    @Override
    public String toString() {
        return "ShippingDetails{" +
                "shippingCost='" + shippingCost + '\'' +
                ", globalShipping='" + globalShipping + '\'' +
                ", handlingTime='" + handlingTime + '\'' +
                ", condition='" + condition + '\'' +
                ", containsDetails=" + containsDetails +
                '}';
    }

    // Parcelable constructs

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.shippingCost);
        dest.writeString(this.globalShipping);
        dest.writeString(this.handlingTime);
        dest.writeString(this.condition);
        dest.writeString(this.containsDetails.toString());
    }

    protected ShippingDetails(Parcel in) {
        this.shippingCost = in.readString();
        this.globalShipping = in.readString();
        this.handlingTime = in.readString();
        this.condition = in.readString();
        this.containsDetails = Boolean.valueOf(in.readString());
    }

    public static final Parcelable.Creator<ShippingDetails> CREATOR = new Parcelable.Creator<ShippingDetails>() {
        @Override
        public ShippingDetails createFromParcel(Parcel source) {
            return new ShippingDetails(source);
        }

        @Override
        public ShippingDetails[] newArray(int size) {
            return new ShippingDetails[size];
        }
    };
}
