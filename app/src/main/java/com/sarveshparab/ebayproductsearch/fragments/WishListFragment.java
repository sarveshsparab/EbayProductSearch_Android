package com.sarveshparab.ebayproductsearch.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sarveshparab.ebayproductsearch.R;
import com.sarveshparab.ebayproductsearch.adapters.GridSpacingItemDecoration;
import com.sarveshparab.ebayproductsearch.adapters.WishListAdapter;
import com.sarveshparab.ebayproductsearch.pojos.SRDetails;
import com.sarveshparab.ebayproductsearch.utility.ValUtil;

import java.util.ArrayList;
import java.util.List;

public class WishListFragment extends Fragment {

    private LinearLayout wishfErrorLL;
    private RecyclerView wishRV;
    private List<SRDetails> wishList;
    private WishListAdapter wishListAdapter;
    private TextView wishfItemCountTV, wishfNetValTV;

    public WishListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);

        wishfErrorLL = view.findViewById(R.id.wishfErrorLL);
        wishRV = view.findViewById(R.id.wishRV);
        wishfItemCountTV = view.findViewById(R.id.wishfItemCountTV);
        wishfNetValTV = view.findViewById(R.id.wishfNetValTV);

        wishList = new ArrayList<>();

        wishListAdapter = new WishListAdapter(getContext(), wishList);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        wishRV.setLayoutManager(layoutManager);
        wishRV.addItemDecoration(new GridSpacingItemDecoration(2, ValUtil.dpToPx(2, getContext()),
                true));
        wishRV.setItemAnimator(new DefaultItemAnimator());
        wishRV.setAdapter(wishListAdapter);

        wishList.add(new SRDetails());
        wishList.add(new SRDetails());
        wishList.add(new SRDetails());

        wishListAdapter.notifyDataSetChanged();


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
