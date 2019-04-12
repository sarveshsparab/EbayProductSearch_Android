package com.sarveshparab.ebayproductsearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sarveshparab.ebayproductsearch.R;
import com.sarveshparab.ebayproductsearch.activities.ItemDetailsActivity;
import com.sarveshparab.ebayproductsearch.pojos.SRDetails;
import com.sarveshparab.ebayproductsearch.utility.StrUtil;

import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SRViewHolder> {

    private Context ctx;
    private List<SRDetails> srList;

    public class SRViewHolder extends RecyclerView.ViewHolder {

        public TextView srTitleTV, srZipTV, srShippingTV, srConditionTV, srPriceTV;
        public ImageView srWishListToggleIV, srImageIV;
        public LinearLayout srListingsCLLL;

        public SRViewHolder(View itemView) {
            super(itemView);
            srTitleTV = itemView.findViewById(R.id.srTitleTV);
            srZipTV = itemView.findViewById(R.id.srZipTV);
            srShippingTV = itemView.findViewById(R.id.srShippingTV);
            srConditionTV = itemView.findViewById(R.id.srConditionTV);
            srPriceTV = itemView.findViewById(R.id.srPriceTV);
            srWishListToggleIV = itemView.findViewById(R.id.srWishListToggleIV);
            srImageIV = itemView.findViewById(R.id.srImageIV);
            srListingsCLLL = itemView.findViewById(R.id.srListingsCLLL);
        }
    }

    public SearchResultsAdapter(Context ctx, List<SRDetails> srList){
        this.ctx = ctx;
        this.srList = srList;
    }

    @Override
    public SRViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View srItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sr_card_layout, parent, false);

        return new SRViewHolder(srItemView);
    }

    @Override
    public void onBindViewHolder(SRViewHolder holder, int position) {
        final SRDetails srDetails = srList.get(position);

        holder.srTitleTV.setText(srDetails.getTitleCutoff());
        holder.srZipTV.setText("Zip: " + srDetails.getZipcode());
        holder.srShippingTV.setText(srDetails.getShippingCost());
        holder.srConditionTV.setText(srDetails.getCondition());
        holder.srPriceTV.setText(srDetails.getPrice());

        if(srDetails.getInWishList()){
            holder.srWishListToggleIV.setTag(StrUtil.IN_WISHLIST_TAG);
            holder.srWishListToggleIV.setImageResource(R.drawable.cart_remove);
        } else {
            holder.srWishListToggleIV.setTag(StrUtil.NOT_IN_WISHLIST_TAG);
            holder.srWishListToggleIV.setImageResource(R.drawable.cart_plus);
        }

        holder.srWishListToggleIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getTag() == StrUtil.IN_WISHLIST_TAG){
                    ((ImageView)v).setImageResource(R.drawable.cart_plus);
                    v.setTag(StrUtil.NOT_IN_WISHLIST_TAG);
                    Toast.makeText(v.getContext(),srDetails.getTitleCutoff()
                                    + " was removed from wish list",
                            Toast.LENGTH_SHORT).show();
                } else {
                    ((ImageView)v).setImageResource(R.drawable.cart_remove);
                    v.setTag(StrUtil.IN_WISHLIST_TAG);
                    Toast.makeText(v.getContext(),srDetails.getTitleCutoff()
                                    + " was added to wish list",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        Glide.with(ctx).load(srDetails.getImageURL()).into(holder.srImageIV);

        holder.srListingsCLLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(StrUtil.LOG_TAG+"|Forward","To ItemDetails Activity");

                Intent itemDetailsActivity = new Intent( ctx
                        , ItemDetailsActivity.class);
                itemDetailsActivity.putExtra(StrUtil.SR_ITEM_PARCEL, srDetails);
                ctx.startActivity(itemDetailsActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return srList.size();
    }

}
