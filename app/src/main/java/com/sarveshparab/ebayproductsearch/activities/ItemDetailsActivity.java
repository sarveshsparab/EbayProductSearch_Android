package com.sarveshparab.ebayproductsearch.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sarveshparab.ebayproductsearch.R;
import com.sarveshparab.ebayproductsearch.fragments.ProductFragment;
import com.sarveshparab.ebayproductsearch.fragments.ShippingFragment;
import com.sarveshparab.ebayproductsearch.pojos.SRDetails;
import com.sarveshparab.ebayproductsearch.utility.IDUtil;
import com.sarveshparab.ebayproductsearch.utility.StrUtil;
import com.sarveshparab.ebayproductsearch.utility.ValUtil;
import com.sarveshparab.ebayproductsearch.utility.WishListUtil;

import java.util.Map;

public class ItemDetailsActivity extends AppCompatActivity implements
        ProductFragment.DataProgressListener{

    private SharedPreferences wishPref;
    private SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        wishPref = getApplicationContext().getSharedPreferences(StrUtil.WISHLIST_PREF, MODE_PRIVATE);
        prefEditor = wishPref.edit();

        Toolbar toolbar = findViewById(R.id.idToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle data = getIntent().getExtras();
        final SRDetails srDetails = data.getParcelable(StrUtil.SR_ITEM_PARCEL);
        Log.v(StrUtil.LOG_TAG+"|ForwardACK",srDetails.toString());

        TextView idTitle = findViewById(R.id.idTitle);
        idTitle.setText(srDetails.getTitle());

        handleFBShare(srDetails.getTitle(),srDetails.getItemURL(), srDetails.getPrice());

        handleAndProcessWishListToggle(srDetails);

        // Tab setup

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        final ViewPager itemDetailsPager = findViewById(R.id.itemDetailsPager);
        itemDetailsPager.setOffscreenPageLimit(ValUtil.ITEM_DETAIL_OFF_SCREEN_PAGE_LIMIT);

        IDUtil.initTabsPager(itemDetailsPager, getSupportFragmentManager(), data);

        tabLayout.setupWithViewPager(itemDetailsPager);
        itemDetailsPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                itemDetailsPager.setCurrentItem(tab.getPosition());
                IDUtil.highlightTheTab(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                IDUtil.dullTheTabs(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        IDUtil.initTabs(this, tabLayout);

        syncAndShowWishlistItems(srDetails);

        SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener = (prefs, key) -> {
            syncAndShowWishlistItems(srDetails);
        };

        wishPref.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);


    }

    private void syncAndShowWishlistItems(SRDetails srDetails) {
        FloatingActionButton idWishListToggleBtn = findViewById(R.id.idWishListToggleBtn);

        Map<String, ?> wishPrefAllAgain = wishPref.getAll();

        if(wishPrefAllAgain.containsKey(StrUtil.ITEM_KEY_PREFIX + srDetails.getItemId())){
            srDetails.setInWishList(true);
            idWishListToggleBtn.setImageResource(R.drawable.cart_remove_white);
            idWishListToggleBtn.setTag(StrUtil.IN_WISHLIST_TAG);
        } else {
            srDetails.setInWishList(false);
            idWishListToggleBtn.setImageResource(R.drawable.cart_plus_white);
            idWishListToggleBtn.setTag(StrUtil.NOT_IN_WISHLIST_TAG);
        }
    }

    private void handleFBShare(final String title, final String url, final String price) {
        ImageView idFBBtn = findViewById(R.id.idFBBtn);
        idFBBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fbShareURL = IDUtil.buildFBShareLink(title, url, price);
                Log.v(StrUtil.LOG_TAG+"|FBShare", fbShareURL);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fbShareURL));
                startActivity(browserIntent);
            }
        });
    }

    private void handleAndProcessWishListToggle(final SRDetails srDetails) {
        FloatingActionButton idWishListToggleBtn = findViewById(R.id.idWishListToggleBtn);

        if(srDetails.getInWishList()) {
            idWishListToggleBtn.setImageResource(R.drawable.cart_remove_white);
            idWishListToggleBtn.setTag(StrUtil.IN_WISHLIST_TAG);
        } else {
            idWishListToggleBtn.setImageResource(R.drawable.cart_plus_white);
            idWishListToggleBtn.setTag(StrUtil.NOT_IN_WISHLIST_TAG);
        }
        idWishListToggleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getTag() == StrUtil.IN_WISHLIST_TAG){
                    ((ImageView)v).setImageResource(R.drawable.cart_plus_white);
                    v.setTag(StrUtil.NOT_IN_WISHLIST_TAG);
                    Toast.makeText(v.getContext(),srDetails.getTitleCutoff()
                                    + " was removed from wish list",
                            Toast.LENGTH_SHORT).show();
                    srDetails.setInWishList(false);
                    prefEditor.remove(StrUtil.ITEM_KEY_PREFIX + srDetails.getItemId());
                } else {
                    ((ImageView)v).setImageResource(R.drawable.cart_remove_white);
                    v.setTag(StrUtil.IN_WISHLIST_TAG);
                    Toast.makeText(v.getContext(),srDetails.getTitleCutoff()
                                    + " was added to wish list",
                            Toast.LENGTH_SHORT).show();
                    srDetails.setInWishList(true);
                    prefEditor.putString(StrUtil.ITEM_KEY_PREFIX + srDetails.getItemId(), WishListUtil.gson.toJson(srDetails));
                }
                prefEditor.commit();
            }
        });
    }

    @Override
    public void onBackPressed() {

        Log.v("EPS","back pressed");

        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }

    @Override
    public void onDataFetchCompletion(SRDetails updatedPojo, String dest) {
        if(dest.equals("SHIPPING")){
            ViewPager pager = findViewById(R.id.itemDetailsPager);
            FragmentStatePagerAdapter adapter = (FragmentStatePagerAdapter) pager.getAdapter();
            Fragment shippingFragment = (ShippingFragment) adapter.instantiateItem(pager, 1);

            ((ShippingFragment) shippingFragment).onDataReady(updatedPojo);
        }
    }
}
