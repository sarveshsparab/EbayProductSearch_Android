package com.sarveshparab.ebayproductsearch.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sarveshparab.ebayproductsearch.R;

import java.util.List;

public class PhoFragAdapter extends RecyclerView.Adapter<PhoFragAdapter.PhoFragViewHolder> {

    private Context ctx;
    private List<String> imagesURLList;

    public class PhoFragViewHolder extends RecyclerView.ViewHolder {

        public ImageView phofCardIV;

        public PhoFragViewHolder(View itemView) {
            super(itemView);
            phofCardIV = itemView.findViewById(R.id.phofCardIV);
        }
    }

    public PhoFragAdapter(Context ctx, List<String> imagesURLList){
        this.ctx = ctx;
        this.imagesURLList = imagesURLList;
    }

    @Override
    public PhoFragViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View phofItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.phof_card_layout, parent, false);

        return new PhoFragViewHolder(phofItemView);
    }

    @Override
    public void onBindViewHolder(PhoFragViewHolder holder, int position) {
        String imageURL = imagesURLList.get(position);
        Glide.with(ctx).load(imageURL).into(holder.phofCardIV);
    }

    @Override
    public int getItemCount() {
        return imagesURLList.size();
    }

}
