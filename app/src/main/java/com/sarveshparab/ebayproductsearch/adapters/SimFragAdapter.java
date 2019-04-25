package com.sarveshparab.ebayproductsearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.sarveshparab.ebayproductsearch.R;
import com.sarveshparab.ebayproductsearch.pojos.SimilarItem;
import com.sarveshparab.ebayproductsearch.utility.StrUtil;

import java.util.List;

public class SimFragAdapter extends RecyclerView.Adapter<SimFragAdapter.SimFragViewHolder> {

    private Context ctx;
    private List<SimilarItem> simList;

    public class SimFragViewHolder extends RecyclerView.ViewHolder {

        public TextView simfTitleTV, simfShippingCostTV, simfDaysLeftTV, simfPriceTV;
        public ImageView simfImageIV;
        public LinearLayout simCardLL;

        public SimFragViewHolder(View itemView) {
            super(itemView);
            simfTitleTV = itemView.findViewById(R.id.simfTitleTV);
            simfShippingCostTV = itemView.findViewById(R.id.simfShippingCostTV);
            simfDaysLeftTV = itemView.findViewById(R.id.simfDaysLeftTV);
            simfPriceTV = itemView.findViewById(R.id.simfPriceTV);
            simfImageIV = itemView.findViewById(R.id.simfImageIV);
            simCardLL = itemView.findViewById(R.id.simCardLL);
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
        final SimilarItem simItem = simList.get(position);

        holder.simfTitleTV.setText(simItem.getTitle());
        holder.simfShippingCostTV.setText(simItem.getShippingCost());
        holder.simfPriceTV.setText(simItem.getPrice());
        holder.simfDaysLeftTV.setText(simItem.getDaysLeft());

        Glide.with(ctx)
                .load(simItem.getImageURL())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.v(StrUtil.LOG_TAG + "|GlideImageLoadFailed", "Potentially Image not found[404] : " + model.toString());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .error(R.drawable.image_broken_icon)
                .into(holder.simfImageIV);

        holder.simCardLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(StrUtil.LOG_TAG+"|Redirect", "To Link : " + simItem.getItemURL());
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(simItem.getItemURL()));
                ctx.startActivity(browserIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return simList.size();
    }

    public void setSimList(List<SimilarItem> simList) {
        this.simList = simList;
    }

    public List<SimilarItem> getSimList() {
        return simList;
    }
}
