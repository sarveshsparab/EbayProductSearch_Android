package com.sarveshparab.ebayproductsearch.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.sarveshparab.ebayproductsearch.R;
import com.sarveshparab.ebayproductsearch.adapters.SimFragAdapter;
import com.sarveshparab.ebayproductsearch.expections.JSONDataException;
import com.sarveshparab.ebayproductsearch.network.CallArbitrator;
import com.sarveshparab.ebayproductsearch.network.NetworkCallBack;
import com.sarveshparab.ebayproductsearch.pojos.SRDetails;
import com.sarveshparab.ebayproductsearch.pojos.SimilarItem;
import com.sarveshparab.ebayproductsearch.utility.SimFragUtil;
import com.sarveshparab.ebayproductsearch.utility.StrUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class SimilarFragment extends Fragment {

    private LinearLayout simfProgressLL, simfErrorLL, simfListingLL, simfSortLL;
    private RecyclerView simfRV;
    private Spinner simfSortOrderSpin, simfSortParamSpin;

    public SimilarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_similar, container, false);

        simfProgressLL = view.findViewById(R.id.simfProgressLL);
        simfErrorLL = view.findViewById(R.id.simfErrorLL);
        simfSortLL = view.findViewById(R.id.simfSortLL);
        simfListingLL = view.findViewById(R.id.simfListingLL);
        simfRV = view.findViewById(R.id.simfRV);
        simfSortOrderSpin = view.findViewById(R.id.simfSortOrderSpin);
        simfSortParamSpin = view.findViewById(R.id.simfSortParamSpin);

        simfProgressLL.setVisibility(View.VISIBLE);
        simfErrorLL.setVisibility(View.GONE);
        simfSortLL.setVisibility(View.GONE);
        simfListingLL.setVisibility(View.GONE);
        simfSortOrderSpin.setEnabled(false);
        simfSortParamSpin.setEnabled(false);

        SimFragUtil.populateSortingSpinners(getContext(), simfSortOrderSpin, simfSortParamSpin);

        final SRDetails srDetails = getArguments().getParcelable(StrUtil.SR_ITEM_PARCEL);
        Log.v(StrUtil.LOG_TAG+"|ForwardFragACK",srDetails.getItemId());

        CallArbitrator.makeRequest(StrUtil.NODE_BASE + StrUtil.NODE_EBAY_SIMILAR,
            new NetworkCallBack() {
                @Override
                public void onSuccess(JSONObject result) throws JSONException {
                    Log.v(StrUtil.LOG_TAG+"|EbaySimilarSuccess", result.toString());

                    try {
                        List<SimilarItem> simList = SimFragUtil.fetchSimilarItems(result);

                        simfProgressLL.setVisibility(View.GONE);

                        if(simList.size() == 0){
                            simfErrorLL.setVisibility(View.VISIBLE);
                            simfSortLL.setVisibility(View.VISIBLE);
                            simfListingLL.setVisibility(View.GONE);
                            simfSortOrderSpin.setEnabled(false);
                            simfSortParamSpin.setEnabled(false);
                        } else {
                            SimFragAdapter adapter = new SimFragAdapter(getContext(), simList);
                            simfRV.setItemAnimator(new DefaultItemAnimator());
                            simfRV.setLayoutManager(new LinearLayoutManager(getContext()));
                            simfRV.setAdapter(adapter);



                            simfSortOrderSpin.setEnabled(true);
                            simfSortParamSpin.setEnabled(true);
                            simfErrorLL.setVisibility(View.GONE);
                            simfSortLL.setVisibility(View.VISIBLE);
                            simfListingLL.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONDataException e){
                        Log.v(StrUtil.LOG_TAG + "|EbaySimilarError", "fetchSimilarItems call threw an exception\n"+e.getMessage());
                        simfProgressLL.setVisibility(View.GONE);
                        simfErrorLL.setVisibility(View.VISIBLE);
                        simfSortLL.setVisibility(View.VISIBLE);
                        simfListingLL.setVisibility(View.GONE);
                        simfSortOrderSpin.setEnabled(false);
                        simfSortParamSpin.setEnabled(false);
                    }

                }

                @Override
                public void onError(String result) throws Exception {
                    Log.v(StrUtil.LOG_TAG + "|EbaySimilarError", result.toString());
                    simfProgressLL.setVisibility(View.GONE);
                    simfErrorLL.setVisibility(View.VISIBLE);
                    simfSortLL.setVisibility(View.VISIBLE);
                    simfListingLL.setVisibility(View.GONE);
                    simfSortOrderSpin.setEnabled(false);
                    simfSortParamSpin.setEnabled(false);
                }
            }, getContext(), new HashMap<String, String>(){{
                put("itemid", srDetails.getItemId());
            }});

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

}
