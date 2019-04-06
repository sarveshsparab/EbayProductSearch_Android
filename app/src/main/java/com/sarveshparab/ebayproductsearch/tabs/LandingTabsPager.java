package com.sarveshparab.ebayproductsearch.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sarveshparab.ebayproductsearch.fragments.PSFormFragment;
import com.sarveshparab.ebayproductsearch.fragments.WishListFragment;

public class LandingTabsPager extends FragmentStatePagerAdapter {
    private int tabsCount;

    public LandingTabsPager(FragmentManager fm, int tabsCount) {
        super(fm);
        this.tabsCount = tabsCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment psFormFrag = new PSFormFragment();
                return psFormFrag;
            case 1:
                Fragment wlFrag = new WishListFragment();
                return wlFrag;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabsCount;
    }
}
