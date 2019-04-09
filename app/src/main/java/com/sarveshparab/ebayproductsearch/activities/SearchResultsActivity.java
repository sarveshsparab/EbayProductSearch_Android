package com.sarveshparab.ebayproductsearch.activities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sarveshparab.ebayproductsearch.R;
import com.sarveshparab.ebayproductsearch.adapters.SearchResultsAdapter;
import com.sarveshparab.ebayproductsearch.expections.JSONDataException;
import com.sarveshparab.ebayproductsearch.network.CallArbitrator;
import com.sarveshparab.ebayproductsearch.network.NetworkCallBack;
import com.sarveshparab.ebayproductsearch.pojos.PSForm;
import com.sarveshparab.ebayproductsearch.pojos.SRDetails;
import com.sarveshparab.ebayproductsearch.utility.SRUtil;
import com.sarveshparab.ebayproductsearch.utility.StrUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {

    private LinearLayout srProgressLL, srListingsLL;
    private RecyclerView srListingsRV;
    private List<SRDetails> srList;
    private SearchResultsAdapter searchResultsAdapter;
    
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
        srListingsRV = findViewById(R.id.srListingsRV);
        
        srProgressLL.setVisibility(View.VISIBLE);
        srListingsLL.setVisibility(View.GONE);

        Bundle data = getIntent().getExtras();
        final PSForm psForm = (PSForm) data.getParcelable(StrUtil.PSFORM_PARCEL);
        Log.v(StrUtil.LOG_TAG+"|ForwardACK",psForm.toString());

        CallArbitrator.makeRequest(StrUtil.NODE_BASE + StrUtil.NODE_EBAY_FIND,
            new NetworkCallBack() {
                @Override
                public void onSuccess(JSONObject result) throws JSONException {
                    Log.v(StrUtil.LOG_TAG+"|EbayFindSuccess", result.toString());

                    initiateAndPopulateSRListings(getApplicationContext(), result,
                            psForm.getKeyword());

                    srProgressLL.setVisibility(View.GONE);
                    srListingsLL.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError(String result) throws Exception {
                    Log.v(StrUtil.LOG_TAG + "|EbayFindError", result.toString());
                }
            }, getApplicationContext(), psForm.buildQueryParamsMap());


    }

    private void initiateAndPopulateSRListings(Context ctx, JSONObject result, String keyword) {

        TextView srResultKeyword = findViewById(R.id.srResultKeyword);
        TextView srResultCount = findViewById(R.id.srResultCount);

        srList = new ArrayList<>();
        searchResultsAdapter = new SearchResultsAdapter(ctx, srList);
        
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ctx, 2);
        srListingsRV.setLayoutManager(layoutManager);
        srListingsRV.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(2),
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
        }
    }

    private void populateSRListings(JSONArray result) {

        int srCount = result.length();

        for(int i=0; i<srCount; i++){
            SRDetails srDetails = new SRDetails();

            try {
                srDetails.setItemId(SRUtil.fetchItemId(result, i));
                srDetails.setTitle(SRUtil.fetchTitle(result, i));
                srDetails.setCondition(SRUtil.fetchCondition(result, i));
                srDetails.setImageURL(SRUtil.fetchImageURL(result, i));
                srDetails.setPrice(SRUtil.fetchPrice(result, i));
                srDetails.setShippingCost(SRUtil.fetchShippingCost(result, i));
                srDetails.setZipcode(SRUtil.fetchZipCode(result, i));
//                srDetails.setShippingDetails(SRUtil.fetchShippingDetails(result, i));
//                srDetails.setSellerDetails(SRUtil.fetchSellerDetails(result, i));
//                srDetails.setReturnsDetails(SRUtil.fetchReturnsDetails(result, i));
            } catch (JSONDataException e) {
                Log.v(StrUtil.LOG_TAG+"|Error",e.getMessage());
            }
            srList.add(srDetails);
        }

        searchResultsAdapter.notifyDataSetChanged();

    }

    private int dpToPx(int dp) {
        Resources resources = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                resources.getDisplayMetrics()));
    }

    private class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}
