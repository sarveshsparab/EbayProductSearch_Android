package com.sarveshparab.ebayproductsearch.utility;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.sarveshparab.ebayproductsearch.R;
import com.sarveshparab.ebayproductsearch.activities.ItemDetailsActivity;
import com.sarveshparab.ebayproductsearch.fragments.PhotosFragment;
import com.sarveshparab.ebayproductsearch.fragments.ProductFragment;
import com.sarveshparab.ebayproductsearch.fragments.ShippingFragment;
import com.sarveshparab.ebayproductsearch.fragments.SimilarFragment;
import com.sarveshparab.ebayproductsearch.tabs.ItemDetailsTabsPager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class IDUtil {

    public static String buildFBShareLink(String title, String url, String price){

        if(url == null || url.equals(StrUtil.DEFAULT_NA_VALUE)){
            url = "https://www.ebay.com/";
        }

        StringBuilder fb = new StringBuilder();

        try {
            fb.append("https://www.facebook.com/dialog/share?");
            fb.append("app_id=");
            fb.append("437955393676463");
            fb.append("&display=popup");
            fb.append("&href=");
            fb.append(URLEncoder.encode(url, "UTF-8"));
            fb.append("&hashtag=");
            fb.append("%23CSCI571Spring2019Ebay");
            fb.append("&quote='Buy ");
            fb.append(URLEncoder.encode(title, "UTF-8"));
            fb.append(" at ");
            fb.append(price);
            fb.append(" from the link below'");
        } catch (UnsupportedEncodingException e) {
            Log.v(StrUtil.LOG_TAG+"|TitleEncodingError", e.getMessage());
            e.printStackTrace();
        }

        return fb.toString();
    }

    public static void dullTheTabs(TabLayout.Tab tab) {
        ((AppCompatTextView) tab.getCustomView()).setTextColor(Color.parseColor("#c675ff"));

        switch (tab.getPosition()){
            case 0:
                ((TextView)(tab.getCustomView().findViewById(R.id.idTab))).setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.information_variant_dull_24dp,0,0);
                break;
            case 1:
                ((TextView)(tab.getCustomView().findViewById(R.id.idTab))).setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.truck_delivery_dull_24dp,0,0);
                break;
            case 2:
                ((TextView)(tab.getCustomView().findViewById(R.id.idTab))).setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.google_dull_24dp,0,0);
                break;
            case 3:
                ((TextView)(tab.getCustomView().findViewById(R.id.idTab))).setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.equal_dull_24dp,0,0);
                break;
        }
    }

    public static void highlightTheTab(TabLayout.Tab tab) {
        ((AppCompatTextView) tab.getCustomView()).setTextColor(Color.parseColor("#ffffff"));

        switch (tab.getPosition()){
            case 0:
                ((TextView)(tab.getCustomView().findViewById(R.id.idTab))).setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.information_variant_24dp,0,0);
                break;
            case 1:
                ((TextView)(tab.getCustomView().findViewById(R.id.idTab))).setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.truck_delivery_24dp,0,0);
                break;
            case 2:
                ((TextView)(tab.getCustomView().findViewById(R.id.idTab))).setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.google_24dp,0,0);
                break;
            case 3:
                ((TextView)(tab.getCustomView().findViewById(R.id.idTab))).setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.equal_24dp,0,0);
                break;
        }
    }

    public static void initTabs(ItemDetailsActivity itemDetailsActivity, TabLayout tabLayout) {
        TextView productTab = (TextView) LayoutInflater.from(itemDetailsActivity).inflate(R.layout.item_details_tab, null);
        productTab.setText("PRODUCT");
        productTab.setTextColor(Color.parseColor("#ffffff"));
        productTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.information_variant_24dp, 0, 0);
        tabLayout.getTabAt(0).setCustomView(productTab);

        TextView shippingTab = (TextView) LayoutInflater.from(itemDetailsActivity).inflate(R.layout.item_details_tab, null);
        shippingTab.setText("SHIPPING");
        shippingTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.truck_delivery_dull_24dp, 0, 0);
        tabLayout.getTabAt(1).setCustomView(shippingTab);

        TextView photosTab = (TextView) LayoutInflater.from(itemDetailsActivity).inflate(R.layout.item_details_tab, null);
        photosTab.setText("PHOTOS");
        photosTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.google_dull_24dp, 0, 0);
        tabLayout.getTabAt(2).setCustomView(photosTab);

        TextView similarTab = (TextView) LayoutInflater.from(itemDetailsActivity).inflate(R.layout.item_details_tab, null);
        similarTab.setText("SIMILAR");
        similarTab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.equal_dull_24dp, 0, 0);
        tabLayout.getTabAt(3).setCustomView(similarTab);
    }

    public static void initTabsPager(ViewPager itemDetailsPager, FragmentManager supportFragmentManager, Bundle bundle) {
        final ItemDetailsTabsPager adapter = new ItemDetailsTabsPager(supportFragmentManager);

        adapter.addNewFrag(new ProductFragment(), "PRODUCT", bundle);
        adapter.addNewFrag(new ShippingFragment(), "SHIPPING", bundle);
        adapter.addNewFrag(new PhotosFragment(), "PHOTOS", bundle);
        adapter.addNewFrag(new SimilarFragment(), "SIMILAR", bundle);

        itemDetailsPager.setAdapter(adapter);
    }
}

