package com.sarveshparab.ebayproductsearch.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.sarveshparab.ebayproductsearch.utility.StrUtil;
import com.sarveshparab.ebayproductsearch.utility.ValUtil;
import com.sarveshparab.ebayproductsearch.utility.WishListUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static android.content.Context.MODE_PRIVATE;

public class WishListFragment extends Fragment {

    private LinearLayout wishfErrorLL;
    private RecyclerView wishRV;
    private List<SRDetails> wishList;
    private WishListAdapter wishListAdapter;
    private TextView wishfItemCountTV, wishfNetValTV;
    private SharedPreferences wishPref;

    private SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener;

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

        wishPref = getActivity().getApplicationContext().getSharedPreferences(StrUtil.WISHLIST_PREF, MODE_PRIVATE);

        wishfErrorLL = view.findViewById(R.id.wishfErrorLL);
        wishRV = view.findViewById(R.id.wishRV);
        wishfItemCountTV = view.findViewById(R.id.wishfItemCountTV);
        wishfNetValTV = view.findViewById(R.id.wishfNetValTV);

        wishList = new ArrayList<>();

        wishListAdapter = new WishListAdapter(getContext(), wishList, getActivity().getApplicationContext());

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        wishRV.setLayoutManager(layoutManager);
        wishRV.addItemDecoration(new GridSpacingItemDecoration(2, ValUtil.dpToPx(2, getContext()),
                true));
        wishRV.setItemAnimator(new DefaultItemAnimator());
        wishRV.setAdapter(wishListAdapter);


        Map<String, ?> wishPrefAll = wishPref.getAll();

        if(wishPrefAll.size() == 0){
            showEmptyWishlistState();
        } else {

            syncAndShowWishlistItems(wishPrefAll);

            wishRV.setVisibility(View.VISIBLE);
            wishfErrorLL.setVisibility(View.GONE);
        }

        onSharedPreferenceChangeListener = (prefs, key) -> {
           Map<String, ?> wishPrefAllAgain = wishPref.getAll();

            if(wishPrefAllAgain.size() == 0){
                showEmptyWishlistState();
            } else {

                syncAndShowWishlistItems(wishPrefAllAgain);

                wishRV.setVisibility(View.VISIBLE);
                wishfErrorLL.setVisibility(View.GONE);
            }
        };

        wishPref.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);


        return view;
    }

    private void showEmptyWishlistState() {
        wishRV.setVisibility(View.GONE);
        wishfErrorLL.setVisibility(View.VISIBLE);
        wishfItemCountTV.setText("Wishlist total(0 item):");
        wishfNetValTV.setText("$0.0");
    }

    private void syncAndShowWishlistItems(Map<String, ?> wishPrefAll) {
        List<SRDetails> wishPrefList = wishPrefAll
                .keySet()
                .stream()
                .map(wl -> wishPref.getString(wl, ""))
                .map(wl -> WishListUtil.gson.fromJson(wl, SRDetails.class))
                .collect(Collectors.toList());

        wishList.clear();
        wishList.addAll(wishPrefList);

        wishListAdapter.notifyDataSetChanged();

        wishfItemCountTV.setText("Wishlist total(" + wishPrefList.size() + (wishPrefList.size()>1 ? " items" : " item") + "):");

        double netVal = 0.0;
        for(int w=0 ; w<wishPrefList.size(); w++){
            if(!StrUtil.DEFAULT_NA_VALUE.equals(wishPrefList.get(w).getPrice())){
                netVal += Double.parseDouble(wishPrefList.get(w).getPrice().substring(1));
            }
        }
        wishfNetValTV.setText("$" + netVal);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        wishPref.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }
}
