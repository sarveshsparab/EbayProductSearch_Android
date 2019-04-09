package com.sarveshparab.ebayproductsearch.pojos;

import android.os.Parcel;
import android.os.Parcelable;

public class SellerDetails implements Parcelable{
    private String storeName;
    private String feedbackScore;
    private String popularity;
    private String feedbackStar;
    private Boolean containsDetails;

    public SellerDetails() {
        this.containsDetails = false;
    }

    public SellerDetails(String storeName, String feedbackScore, String popularity, String feedbackStar) {
        this.storeName = storeName;
        this.feedbackScore = feedbackScore;
        this.popularity = popularity;
        this.feedbackStar = feedbackStar;
        this.containsDetails = true;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
        this.containsDetails = true;
    }

    public String getFeedbackScore() {
        return feedbackScore;
    }

    public void setFeedbackScore(String feedbackScore) {
        this.feedbackScore = feedbackScore;
        this.containsDetails = true;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
        this.containsDetails = true;
    }

    public String getFeedbackStar() {
        return feedbackStar;
    }

    public void setFeedbackStar(String feedbackStar) {
        this.feedbackStar = feedbackStar;
        this.containsDetails = true;
    }

    public Boolean getContainsDetails() {
        return containsDetails;
    }

    @Override
    public String toString() {
        return "SellerDetails{" +
                "storeName='" + storeName + '\'' +
                ", feedbackScore='" + feedbackScore + '\'' +
                ", popularity='" + popularity + '\'' +
                ", feedbackStar='" + feedbackStar + '\'' +
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
        dest.writeString(this.storeName);
        dest.writeString(this.feedbackScore);
        dest.writeString(this.popularity);
        dest.writeString(this.feedbackStar);
        dest.writeString(this.containsDetails.toString());
    }

    protected SellerDetails(Parcel in) {
        this.storeName = in.readString();
        this.feedbackScore = in.readString();
        this.popularity = in.readString();
        this.feedbackStar = in.readString();
        this.containsDetails = Boolean.valueOf(in.readString());
    }

    public static final Parcelable.Creator<SellerDetails> CREATOR = new Parcelable.Creator<SellerDetails>() {
        @Override
        public SellerDetails createFromParcel(Parcel source) {
            return new SellerDetails(source);
        }

        @Override
        public SellerDetails[] newArray(int size) {
            return new SellerDetails[size];
        }
    };
}
