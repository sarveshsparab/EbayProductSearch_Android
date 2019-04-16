package com.sarveshparab.ebayproductsearch.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sarveshparab.ebayproductsearch.R;
import com.sarveshparab.ebayproductsearch.adapters.GridSpacingItemDecoration;
import com.sarveshparab.ebayproductsearch.adapters.SearchResultsAdapter;
import com.sarveshparab.ebayproductsearch.expections.JSONDataException;
import com.sarveshparab.ebayproductsearch.network.CallArbitrator;
import com.sarveshparab.ebayproductsearch.network.NetworkCallBack;
import com.sarveshparab.ebayproductsearch.pojos.PSForm;
import com.sarveshparab.ebayproductsearch.pojos.ReturnsDetails;
import com.sarveshparab.ebayproductsearch.pojos.SRDetails;
import com.sarveshparab.ebayproductsearch.pojos.SellerDetails;
import com.sarveshparab.ebayproductsearch.pojos.ShippingDetails;
import com.sarveshparab.ebayproductsearch.utility.SRUtil;
import com.sarveshparab.ebayproductsearch.utility.StrUtil;
import com.sarveshparab.ebayproductsearch.utility.ValUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchResultsActivity extends AppCompatActivity {

    private LinearLayout srProgressLL, srListingsLL, srErrorLL;
    private RecyclerView srListingsRV;
    private List<SRDetails> srList;
    private SearchResultsAdapter searchResultsAdapter;
    private SharedPreferences wishPref;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Toolbar toolbar = findViewById(R.id.srToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        srProgressLL = findViewById(R.id.srProgressLL);
        srListingsLL = findViewById(R.id.srListingsLL);
        srErrorLL = findViewById(R.id.srErrorLL);
        srListingsRV = findViewById(R.id.srListingsRV);
        
        srProgressLL.setVisibility(View.VISIBLE);
        srListingsLL.setVisibility(View.GONE);
        srErrorLL.setVisibility(View.GONE);

        Bundle data = getIntent().getExtras();
        final PSForm psForm = (PSForm) data.getParcelable(StrUtil.PSFORM_PARCEL);
        Log.v(StrUtil.LOG_TAG+"|ForwardACK",psForm.toString());

        CallArbitrator.makeRequest(StrUtil.NODE_BASE + StrUtil.NODE_EBAY_FIND,
            new NetworkCallBack() {
                @Override
                public void onSuccess(JSONObject result) throws JSONException {
                    Log.v(StrUtil.LOG_TAG+"|EbayFindSuccess", result.toString());

                    try {
                        initiateAndPopulateSRListings(getApplicationContext(), result,
                                psForm.getKeyword());
                        srProgressLL.setVisibility(View.GONE);
                        srErrorLL.setVisibility(View.GONE);
                        srListingsLL.setVisibility(View.VISIBLE);
                    } catch (JSONDataException e){
                        Log.v(StrUtil.LOG_TAG+"|Error",e.getMessage());
                        srProgressLL.setVisibility(View.GONE);
                        srErrorLL.setVisibility(View.VISIBLE);
                        srListingsLL.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onError(String result) throws Exception {
                    Log.v(StrUtil.LOG_TAG + "|EbayFindError", result.toString());
                    srProgressLL.setVisibility(View.GONE);
                    srErrorLL.setVisibility(View.VISIBLE);
                    srListingsLL.setVisibility(View.GONE);
                }
            }, getApplicationContext(), psForm.buildQueryParamsMap());


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(srList != null) {
            wishPref = getApplicationContext().getSharedPreferences(StrUtil.WISHLIST_PREF, MODE_PRIVATE);

            Map<String, ?> wishPrefAllAgain = wishPref.getAll();

            for (SRDetails srItem : srList) {
                if (wishPrefAllAgain.containsKey(StrUtil.ITEM_KEY_PREFIX + srItem.getItemId())) {
                    srItem.setInWishList(true);
                } else {
                    srItem.setInWishList(false);
                }
            }

            searchResultsAdapter.notifyDataSetChanged();
        }
    }

    private void initiateAndPopulateSRListings(Context ctx, JSONObject result, String keyword) throws JSONDataException {

        TextView srResultKeyword = findViewById(R.id.srResultKeyword);
        TextView srResultCount = findViewById(R.id.srResultCount);

        srList = new ArrayList<>();
        searchResultsAdapter = new SearchResultsAdapter(ctx, srList, getApplicationContext());
        
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ctx, 2);
        srListingsRV.setLayoutManager(layoutManager);
        srListingsRV.addItemDecoration(new GridSpacingItemDecoration(2, ValUtil.dpToPx(2, ctx),
                true));
        srListingsRV.setItemAnimator(new DefaultItemAnimator());
        srListingsRV.setAdapter(searchResultsAdapter);

        try {
            SRUtil.validateJSONResponse(result);
            srResultCount.setText(SRUtil.getResultCount(result));
            srResultKeyword.setText(keyword);
            populateSRListings(SRUtil.getIterableResult(result));
        } catch (JSONDataException e){
            Log.v(StrUtil.LOG_TAG+"|Error",e.getMessage());
            srProgressLL.setVisibility(View.GONE);
            srErrorLL.setVisibility(View.VISIBLE);
            srListingsLL.setVisibility(View.GONE);
            throw new JSONDataException("Ebay Find Call | initiateAndPopulateSRListings failed");
        }
    }

    private void populateSRListings(JSONArray result) {

        int srCount = result.length();

        for(int i=0; i<srCount; i++){
            final SRDetails srDetails = new SRDetails();

            try {
                final String itemId = SRUtil.fetchItemId(result, i);
                srDetails.setItemId(itemId);
                srDetails.setTitle(SRUtil.fetchTitle(result, i));
                srDetails.setCondition(SRUtil.fetchCondition(result, i));
                srDetails.setItemURL(SRUtil.fetchItemURL(result, i));
                srDetails.setImageURL(SRUtil.fetchImageURL(result, i));
                srDetails.setPrice(SRUtil.fetchPrice(result, i));
                srDetails.setShippingCost(SRUtil.fetchShippingCost(result, i));
                srDetails.setZipcode(SRUtil.fetchZipCode(result, i));

                final ShippingDetails shippingDetails = SRUtil.fetchShippingDetails(result, i);
                final SellerDetails sellerDetails = SRUtil.fetchSellerDetails(result, i);
                final ReturnsDetails returnsDetails = SRUtil.fetchReturnsDetails(result, i);

                srDetails.setShippingDetails(shippingDetails);
                srDetails.setSellerDetails(sellerDetails);
                srDetails.setReturnsDetails(returnsDetails);

            } catch (JSONDataException e) {
                Log.v(StrUtil.LOG_TAG+"|Error",e.getMessage());
                srProgressLL.setVisibility(View.GONE);
                srErrorLL.setVisibility(View.VISIBLE);
                srListingsLL.setVisibility(View.GONE);
            }

            srList.add(srDetails);
        }

        if(srCount == 0){
            srProgressLL.setVisibility(View.GONE);
            srErrorLL.setVisibility(View.VISIBLE);
            srListingsLL.setVisibility(View.GONE);
        }

        searchResultsAdapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }

}
