package com.sarveshparab.ebayproductsearch;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sarveshparab.ebayproductsearch.fragments.PSFormFragment;
import com.sarveshparab.ebayproductsearch.fragments.WishListFragment;
import com.sarveshparab.ebayproductsearch.tabs.LandingTabsPager;

public class LandingActivity extends AppCompatActivity implements PSFormFragment.OnFragmentInteractionListener, WishListFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        Toolbar toolbar = (Toolbar) findViewById(R.id.landingsTabsToolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("SEARCH"));
        tabLayout.addTab(tabLayout.newTab().setText("WISH LIST"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager landingPager = (ViewPager) findViewById(R.id.landingPager);
        final LandingTabsPager adapter = new LandingTabsPager(getSupportFragmentManager(), tabLayout.getTabCount());
        landingPager.setAdapter(adapter);

        landingPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                landingPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
