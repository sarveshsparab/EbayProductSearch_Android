package com.sarveshparab.ebayproductsearch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sarveshparab.ebayproductsearch.R;
import com.sarveshparab.ebayproductsearch.pojos.SimilarItem;

import java.util.List;

public class SimFragAdapter extends RecyclerView.Adapter<SimFragAdapter.SimFragViewHolder> {

    private Context ctx;
    private List<SimilarItem> simList;

    public class SimFragViewHolder extends RecyclerView.ViewHolder {

//        public ImageView phofCardIV;

        public SimFragViewHolder(View itemView) {
            super(itemView);
//            phofCardIV = itemView.findViewById(R.id.phofCardIV);
        }
    }

    public SimFragAdapter(Context ctx, List<SimilarItem> simList){
        this.ctx = ctx;
        this.simList = simList;
    }

    @Override
    public SimFragViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View phofItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sim_card_layout, parent, false);

        return new SimFragViewHolder(phofItemView);
    }

    @Override
    public void onBindViewHolder(SimFragViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return simList.size();
    }

}
