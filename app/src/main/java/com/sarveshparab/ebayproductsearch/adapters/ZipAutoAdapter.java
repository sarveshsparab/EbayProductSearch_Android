package com.sarveshparab.ebayproductsearch.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

public class ZipAutoAdapter extends ArrayAdapter<String> implements Filterable {

    private List<String> fetchedZipCodes;

    public ZipAutoAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        fetchedZipCodes = new ArrayList<>();
    }

    public void setData(List<String> zips) {
        fetchedZipCodes.clear();
        fetchedZipCodes.addAll(zips);
    }

    @Override
    public int getCount() {
        return fetchedZipCodes.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return fetchedZipCodes.get(position);
    }

    public String getObject(int position){
        return fetchedZipCodes.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter zipFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                if(constraint != null){
                    filterResults.values = fetchedZipCodes;
                    filterResults.count = fetchedZipCodes.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && (results.count > 0)) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return zipFilter;
    }
}
