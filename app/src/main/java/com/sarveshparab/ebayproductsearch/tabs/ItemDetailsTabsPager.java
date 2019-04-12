package com.sarveshparab.ebayproductsearch.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ItemDetailsTabsPager extends FragmentStatePagerAdapter {
    private final List<Fragment> tabFragList = new ArrayList<>();
    private final List<String> tabTitles = new ArrayList<>();

    public ItemDetailsTabsPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return tabFragList.get(position);
    }

    @Override
    public int getCount() {
        return tabFragList.size();
    }

    public void addNewFrag(Fragment fragment, String title, Bundle bundle) {
        tabFragList.add(fragment);
        fragment.setArguments(bundle);
        tabTitles.add(title);
    }

}
