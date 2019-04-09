package com.sarveshparab.ebayproductsearch.pojos;

import android.os.Parcel;
import android.os.Parcelable;

public class ReturnsDetails implements Parcelable {
    private String policy;
    private String returnsWithin;
    private String refundMode;
    private String shippedBy;
    private Boolean containsDetails;

    public ReturnsDetails() {
        this.containsDetails = false;
    }

    public ReturnsDetails(String policy, String returnsWithin, String refundMode, String shippedBy) {
        this.policy = policy;
        this.returnsWithin = returnsWithin;
        this.refundMode = refundMode;
        this.shippedBy = shippedBy;
        this.containsDetails = true;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
        this.containsDetails = true;
    }

    public String getReturnsWithin() {
        return returnsWithin;
    }

    public void setReturnsWithin(String returnsWithin) {
        this.returnsWithin = returnsWithin;
        this.containsDetails = true;
    }

    public String getRefundMode() {
        return refundMode;
    }

    public void setRefundMode(String refundMode) {
        this.refundMode = refundMode;
        this.containsDetails = true;
    }

    public String getShippedBy() {
        return shippedBy;
    }

    public void setShippedBy(String shippedBy) {
        this.shippedBy = shippedBy;
        this.containsDetails = true;
    }

    public Boolean getContainsDetails() {
        return containsDetails;
    }

    @Override
    public String toString() {
        return "ReturnsDetails{" +
                "policy='" + policy + '\'' +
                ", returnsWithin='" + returnsWithin + '\'' +
                ", refundMode='" + refundMode + '\'' +
                ", shippedBy='" + shippedBy + '\'' +
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
        dest.writeString(this.policy);
        dest.writeString(this.returnsWithin);
        dest.writeString(this.refundMode);
        dest.writeString(this.shippedBy);
        dest.writeString(this.containsDetails.toString());
    }

    protected ReturnsDetails(Parcel in) {
        this.policy = in.readString();
        this.returnsWithin = in.readString();
        this.refundMode = in.readString();
        this.shippedBy = in.readString();
        this.containsDetails = Boolean.valueOf(in.readString());
    }

    public static final Parcelable.Creator<ReturnsDetails> CREATOR = new Parcelable.Creator<ReturnsDetails>() {
        @Override
        public ReturnsDetails createFromParcel(Parcel source) {
            return new ReturnsDetails(source);
        }

        @Override
        public ReturnsDetails[] newArray(int size) {
            return new ReturnsDetails[size];
        }
    };
}
