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

import com.sarveshparab.ebayproductsearch.R;
import com.sarveshparab.ebayproductsearch.adapters.PhoFragAdapter;
import com.sarveshparab.ebayproductsearch.network.CallArbitrator;
import com.sarveshparab.ebayproductsearch.network.NetworkCallBack;
import com.sarveshparab.ebayproductsearch.pojos.SRDetails;
import com.sarveshparab.ebayproductsearch.utility.PhoFragUtil;
import com.sarveshparab.ebayproductsearch.utility.StrUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class PhotosFragment extends Fragment {

    private LinearLayout phofProgressLL, phofErrorLL;
    private RecyclerView phofRV;

    public PhotosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);

        phofProgressLL = view.findViewById(R.id.phofProgressLL);
        phofErrorLL = view.findViewById(R.id.phofErrorLL);
        phofRV = view.findViewById(R.id.phofRV);

        phofProgressLL.setVisibility(View.VISIBLE);
        phofErrorLL.setVisibility(View.GONE);
        phofRV.setVisibility(View.GONE);

        final SRDetails srDetails = getArguments().getParcelable(StrUtil.SR_ITEM_PARCEL);
        Log.v(StrUtil.LOG_TAG+"|ForwardFragACK",srDetails.getTitle());

        CallArbitrator.makeRequest(StrUtil.NODE_BASE + StrUtil.NODE_PHOTOS,
            new NetworkCallBack() {
                @Override
                public void onSuccess(JSONObject result) throws JSONException {
                    Log.v(StrUtil.LOG_TAG+"|GooglePhotosSuccess", result.toString());

                    List<String> imageURLList = PhoFragUtil.fetchImageURL(result);
                    phofProgressLL.setVisibility(View.GONE);

                    if(imageURLList.size() == 0){
                        phofErrorLL.setVisibility(View.VISIBLE);
                        phofRV.setVisibility(View.GONE);
                    } else {
                        PhoFragAdapter adapter = new PhoFragAdapter(getContext(), imageURLList);
                        phofRV.setItemAnimator(new DefaultItemAnimator());
                        phofRV.setLayoutManager(new LinearLayoutManager(getContext()));
                        phofRV.setAdapter(adapter);

                        phofErrorLL.setVisibility(View.GONE);
                        phofRV.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onError(String result) throws Exception {
                    Log.v(StrUtil.LOG_TAG + "|GooglePhotosError", result.toString());
                    phofProgressLL.setVisibility(View.GONE);
                    phofErrorLL.setVisibility(View.VISIBLE);
                    phofRV.setVisibility(View.GONE);
                }
            }, getContext(), new HashMap<String, String>(){{
                put("queryString", srDetails.getTitle());
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
