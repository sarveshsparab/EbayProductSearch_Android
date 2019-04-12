package com.sarveshparab.ebayproductsearch.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.sarveshparab.ebayproductsearch.R;
import com.sarveshparab.ebayproductsearch.expections.JSONDataException;
import com.sarveshparab.ebayproductsearch.network.CallArbitrator;
import com.sarveshparab.ebayproductsearch.network.NetworkCallBack;
import com.sarveshparab.ebayproductsearch.pojos.SRDetails;
import com.sarveshparab.ebayproductsearch.utility.ProdFragUtil;
import com.sarveshparab.ebayproductsearch.utility.StrUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ProductFragment extends Fragment {

    private DataProgressListener mListener;

    private LinearLayout profProgressLL;
    private ScrollView profContentsSV;

    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_product, container, false);

        profProgressLL = view.findViewById(R.id.profProgressLL);
        profContentsSV = view.findViewById(R.id.profContentsSV);

        profProgressLL.setVisibility(View.VISIBLE);
        profContentsSV.setVisibility(View.GONE);


        final SRDetails srDetails = getArguments().getParcelable(StrUtil.SR_ITEM_PARCEL);
        Log.v(StrUtil.LOG_TAG+"|ForwardFragACK",srDetails.getItemId());

        CallArbitrator.makeRequest(StrUtil.NODE_BASE + StrUtil.NODE_EBAY_DETAILS,
            new NetworkCallBack() {
                @Override
                public void onSuccess(JSONObject result) throws JSONException {
                    Log.v(StrUtil.LOG_TAG+"|EbayDetailSuccess", result.toString());

                    updateSRListings(result, srDetails);

                    try{
                        ProdFragUtil.populateHeaderFields(view, srDetails);
                        ProdFragUtil.populateHighlights(result, view, srDetails);
                        ProdFragUtil.populateSpecs(result, view, getLayoutInflater());
                        ProdFragUtil.populateImageScrollView(result, view, getContext());
                    } catch (JSONDataException e){
                        Log.v(StrUtil.LOG_TAG+"|Error",e.getMessage());
                    }

                    profProgressLL.setVisibility(View.GONE);
                    profContentsSV.setVisibility(View.VISIBLE);
                    mListener.onDataFetchCompletion(srDetails, "SHIPPING");
                }

                @Override
                public void onError(String result) throws Exception {
                    Log.v(StrUtil.LOG_TAG + "|EbayDetailError", result.toString());
                }
            }, getContext(), new HashMap<String, String>(){{
                put("itemid", srDetails.getItemId());
            }});

        return view;
    }

    private void updateSRListings(JSONObject result, SRDetails srDetails) {
        try{
            srDetails.getShippingDetails().setGlobalShipping(ProdFragUtil.fetchGlobalShipping(result));
            srDetails.getReturnsDetails().setPolicy(ProdFragUtil.fetchPolicy(result));
            srDetails.getReturnsDetails().setReturnsWithin(ProdFragUtil.fetchReturnsWithin(result));
            srDetails.getReturnsDetails().setRefundMode(ProdFragUtil.fetchRefundMode(result));
            srDetails.getReturnsDetails().setShippedBy(ProdFragUtil.fetchShippedBy(result));
        } catch (JSONDataException e){
            Log.v(StrUtil.LOG_TAG+"|Error",e.getMessage());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DataProgressListener) {
            mListener = (DataProgressListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DataProgressListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface DataProgressListener {
        void onDataFetchCompletion(SRDetails updatedPojo, String dest);
    }
}
