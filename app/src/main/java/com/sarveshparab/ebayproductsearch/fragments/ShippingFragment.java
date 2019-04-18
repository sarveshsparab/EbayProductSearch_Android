package com.sarveshparab.ebayproductsearch.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import com.sarveshparab.ebayproductsearch.R;
import com.sarveshparab.ebayproductsearch.pojos.SRDetails;
import com.sarveshparab.ebayproductsearch.utility.StrUtil;
import com.wssholmes.stark.circular_score.CircularScoreView;


public class ShippingFragment extends Fragment {

    private LinearLayout shipfProgressLL, shipfErrorLL;
    private ScrollView shipfContentsSV;

    private TableRow shipfStoreNameRowTR, shipfFeedbackScoreRowTR, shipfPopularityRowTR, shipfFeedbackStarRowTR;
    private TableRow shipfShippingCostRowTR, shipfGlobalShippingRowTR, shipfHandlingTimeRowTR, shipfConditionRowTR;
    private TableRow shipfPolicyRowTR, shipfReturnsWithinRowTR, shipfRefundModeRowTR, shipfShippedByRowTR;

    private TextView shipfStoreNameValueTV, shipfFeedbackScoreValueTV;
    private TextView shipfShippingCostValueTV, shipfGlobalShippingValueTV, shipfHandlingTimeValueTV, shipfConditionValueTV;
    private TextView shipfPolicyValueTV, shipfReturnsWithinValueTV, shipfRefundModeValueTV, shipfShippedByValueTV;

    private CircularScoreView shipfPopularityValueCSV;

    private LinearLayout shipfSoldByLL, shipfInfoLL, shipfReturnLL;

    private ImageView shipfFeedbackStarValueIV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shipping, container, false);

        shipfProgressLL = view.findViewById(R.id.shipfProgressLL);
        shipfErrorLL = view.findViewById(R.id.shipfErrorLL);
        shipfContentsSV = view.findViewById(R.id.shipfContentsSV);

        shipfStoreNameRowTR = view.findViewById(R.id.shipfStoreNameRowTR);
        shipfFeedbackScoreRowTR = view.findViewById(R.id.shipfFeedbackScoreRowTR);
        shipfPopularityRowTR = view.findViewById(R.id.shipfPopularityRowTR);
        shipfFeedbackStarRowTR = view.findViewById(R.id.shipfFeedbackStarRowTR);
        shipfStoreNameValueTV = view.findViewById(R.id.shipfStoreNameValueTV);
        shipfFeedbackScoreValueTV = view.findViewById(R.id.shipfFeedbackScoreValueTV);
        shipfFeedbackStarValueIV = view.findViewById(R.id.shipfFeedbackStarValueIV);

        shipfShippingCostRowTR = view.findViewById(R.id.shipfShippingCostRowTR);
        shipfGlobalShippingRowTR = view.findViewById(R.id.shipfGlobalShippingRowTR);
        shipfHandlingTimeRowTR = view.findViewById(R.id.shipfHandlingTimeRowTR);
        shipfConditionRowTR = view.findViewById(R.id.shipfConditionRowTR);
        shipfShippingCostValueTV = view.findViewById(R.id.shipfShippingCostValueTV);
        shipfGlobalShippingValueTV = view.findViewById(R.id.shipfGlobalShippingValueTV);
        shipfHandlingTimeValueTV = view.findViewById(R.id.shipfHandlingTimeValueTV);
        shipfConditionValueTV = view.findViewById(R.id.shipfConditionValueTV);

        shipfPolicyRowTR = view.findViewById(R.id.shipfPolicyRowTR);
        shipfReturnsWithinRowTR = view.findViewById(R.id.shipfReturnsWithinRowTR);
        shipfRefundModeRowTR = view.findViewById(R.id.shipfRefundModeRowTR);
        shipfShippedByRowTR = view.findViewById(R.id.shipfShippedByRowTR);
        shipfPolicyValueTV = view.findViewById(R.id.shipfPolicyValueTV);
        shipfReturnsWithinValueTV = view.findViewById(R.id.shipfReturnsWithinValueTV);
        shipfRefundModeValueTV = view.findViewById(R.id.shipfRefundModeValueTV);
        shipfShippedByValueTV = view.findViewById(R.id.shipfShippedByValueTV);

        shipfSoldByLL = view.findViewById(R.id.shipfSoldByLL);
        shipfInfoLL = view.findViewById(R.id.shipfInfoLL);
        shipfReturnLL = view.findViewById(R.id.shipfReturnLL);

        shipfPopularityValueCSV = view.findViewById(R.id.shipfPopularityValueCSV);

        shipfProgressLL.setVisibility(View.VISIBLE);
        shipfContentsSV.setVisibility(View.GONE);
        shipfErrorLL.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void onDataReady(SRDetails srDetails){

        if(!StrUtil.checkValid(srDetails.getSellerDetails().getStoreName())){
            shipfStoreNameRowTR.setVisibility(View.GONE);
        } else {
            StringBuilder storeLinkText = new StringBuilder();

            if(StrUtil.checkValid(srDetails.getSellerDetails().getStoreURL())){
                storeLinkText.append("<a href=\"");
                storeLinkText.append(srDetails.getSellerDetails().getStoreURL());
                storeLinkText.append("\">");
            }
            storeLinkText.append(srDetails.getSellerDetails().getStoreName());
            if(StrUtil.checkValid(srDetails.getSellerDetails().getStoreURL())) {
                storeLinkText.append("</a>");
            }

            shipfStoreNameValueTV.setAutoLinkMask(0);
            shipfStoreNameValueTV.setText(Html.fromHtml(storeLinkText.toString(), Html.FROM_HTML_MODE_LEGACY));
            shipfStoreNameValueTV.setMovementMethod(LinkMovementMethod.getInstance());
            shipfStoreNameRowTR.setVisibility(View.VISIBLE);
        }

        if(!StrUtil.checkValid(srDetails.getSellerDetails().getFeedbackScore())){
            shipfFeedbackScoreRowTR.setVisibility(View.GONE);
        } else {
            shipfFeedbackScoreValueTV.setText(srDetails.getSellerDetails().getFeedbackScore());
            shipfFeedbackScoreRowTR.setVisibility(View.VISIBLE);
        }

        if(!StrUtil.checkValid(srDetails.getSellerDetails().getPopularity())){
            shipfPopularityRowTR.setVisibility(View.GONE);
        } else {
            shipfPopularityValueCSV.setScore((int)Float.parseFloat(srDetails.getSellerDetails().getPopularity()));
            shipfPopularityRowTR.setVisibility(View.VISIBLE);
        }

        if(!StrUtil.checkValid(srDetails.getSellerDetails().getFeedbackStar())){
            shipfFeedbackStarRowTR.setVisibility(View.GONE);
        } else {
            setFeedbackStar(shipfFeedbackStarValueIV, srDetails.getSellerDetails().getFeedbackStar());
            shipfFeedbackStarRowTR.setVisibility(View.VISIBLE);
        }

        if(srDetails.getSellerDetails().getContainsDetails()){
            shipfSoldByLL.setVisibility(View.VISIBLE);
        } else {
            shipfSoldByLL.setVisibility(View.GONE);
        }



        if(!StrUtil.checkValid(srDetails.getShippingDetails().getShippingCost())){
            shipfShippingCostRowTR.setVisibility(View.GONE);
        } else {
            shipfShippingCostValueTV.setText(srDetails.getShippingDetails().getShippingCost());
            shipfShippingCostRowTR.setVisibility(View.VISIBLE);
        }

        if(!StrUtil.checkValid(srDetails.getShippingDetails().getGlobalShipping())){
            shipfGlobalShippingRowTR.setVisibility(View.GONE);
        } else {
            shipfGlobalShippingValueTV.setText(srDetails.getShippingDetails().getGlobalShipping());
            shipfGlobalShippingRowTR.setVisibility(View.VISIBLE);
        }

        if(!StrUtil.checkValid(srDetails.getShippingDetails().getHandlingTime())){
            shipfHandlingTimeRowTR.setVisibility(View.GONE);
        } else {
            shipfHandlingTimeValueTV.setText(srDetails.getShippingDetails().getHandlingTime());
            shipfHandlingTimeRowTR.setVisibility(View.VISIBLE);
        }

        if(!StrUtil.checkValid(srDetails.getShippingDetails().getCondition())){
            shipfConditionRowTR.setVisibility(View.GONE);
        } else {
            shipfConditionValueTV.setText(srDetails.getShippingDetails().getCondition());
            shipfConditionRowTR.setVisibility(View.VISIBLE);
        }

        if(srDetails.getShippingDetails().getContainsDetails()){
            shipfInfoLL.setVisibility(View.VISIBLE);
        } else {
            shipfInfoLL.setVisibility(View.GONE);
        }



        if(!StrUtil.checkValid(srDetails.getReturnsDetails().getPolicy())){
            shipfPolicyRowTR.setVisibility(View.GONE);
        } else {
            shipfPolicyValueTV.setText(srDetails.getReturnsDetails().getPolicy());
            shipfPolicyRowTR.setVisibility(View.VISIBLE);
        }

        if(!StrUtil.checkValid(srDetails.getReturnsDetails().getReturnsWithin())){
            shipfReturnsWithinRowTR.setVisibility(View.GONE);
        } else {
            shipfReturnsWithinValueTV.setText(srDetails.getReturnsDetails().getReturnsWithin());
            shipfReturnsWithinRowTR.setVisibility(View.VISIBLE);
        }

        if(!StrUtil.checkValid(srDetails.getReturnsDetails().getRefundMode())){
            shipfRefundModeRowTR.setVisibility(View.GONE);
        } else {
            shipfRefundModeValueTV.setText(srDetails.getReturnsDetails().getRefundMode());
            shipfRefundModeRowTR.setVisibility(View.VISIBLE);
        }

        if(!StrUtil.checkValid(srDetails.getReturnsDetails().getShippedBy())){
            shipfShippedByRowTR.setVisibility(View.GONE);
        } else {
            shipfShippedByValueTV.setText(srDetails.getReturnsDetails().getShippedBy());
            shipfShippedByRowTR.setVisibility(View.VISIBLE);
        }

        if(srDetails.getReturnsDetails().getContainsDetails()){
            shipfReturnLL.setVisibility(View.VISIBLE);
        } else {
            shipfReturnLL.setVisibility(View.GONE);
        }

        shipfProgressLL.setVisibility(View.GONE);

        if(srDetails.getShippingDetails().getContainsDetails()
                || srDetails.getSellerDetails().getContainsDetails()
                || srDetails.getReturnsDetails().getContainsDetails()){
            shipfContentsSV.setVisibility(View.VISIBLE);
            shipfErrorLL.setVisibility(View.GONE);
        } else {
            shipfContentsSV.setVisibility(View.GONE);
            shipfErrorLL.setVisibility(View.VISIBLE);
        }

    }

    private void setFeedbackStar(ImageView shipfFeedbackStarValueIV, String feedbackStar) {
        switch (feedbackStar){
            case "Blue":
                shipfFeedbackStarValueIV.setImageResource(R.drawable.star_circle_blue_24dp);
                break;
            case "Green":
                shipfFeedbackStarValueIV.setImageResource(R.drawable.star_circle_green_24dp);
                break;
            case "Purple":
                shipfFeedbackStarValueIV.setImageResource(R.drawable.star_circle_purple_24dp);
                break;
            case "Red":
                shipfFeedbackStarValueIV.setImageResource(R.drawable.star_circle_red_24dp);
                break;
            case "Turquoise":
                shipfFeedbackStarValueIV.setImageResource(R.drawable.star_circle_turquoise_24dp);
                break;
            case "Yellow":
                shipfFeedbackStarValueIV.setImageResource(R.drawable.star_circle_yellow_24dp);
                break;
            case "None":
                shipfFeedbackStarValueIV.setImageResource(R.drawable.star_circle_black_24dp);
                break;
            case "GreenShooting":
                shipfFeedbackStarValueIV.setImageResource(R.drawable.star_circle_outline_green_24dp);
                break;
            case "PurpleShooting":
                shipfFeedbackStarValueIV.setImageResource(R.drawable.star_circle_outline_purple_24dp);
                break;
            case "RedShooting":
                shipfFeedbackStarValueIV.setImageResource(R.drawable.star_circle_outline_red_24dp);
                break;
            case "SilverShooting":
                shipfFeedbackStarValueIV.setImageResource(R.drawable.star_circle_outline_silver_24dp);
                break;
            case "TurquoiseShooting":
                shipfFeedbackStarValueIV.setImageResource(R.drawable.star_circle_outline_turquoise_24dp);
                break;
            case "YellowShooting":
                shipfFeedbackStarValueIV.setImageResource(R.drawable.star_circle_outline_yellow_24dp);
                break;
        }
    }

}
