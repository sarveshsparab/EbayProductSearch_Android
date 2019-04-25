package com.sarveshparab.ebayproductsearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
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
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.sarveshparab.ebayproductsearch.R;
import com.sarveshparab.ebayproductsearch.activities.ItemDetailsActivity;
import com.sarveshparab.ebayproductsearch.pojos.SRDetails;
import com.sarveshparab.ebayproductsearch.utility.StrUtil;
import com.sarveshparab.ebayproductsearch.utility.WishListUtil;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.SRViewHolder> {

    private Context ctx;
    private List<SRDetails> srList;
    private SharedPreferences wishPref;
    private SharedPreferences.Editor prefEditor;

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

    public WishListAdapter(Context ctx, List<SRDetails> srList, Context appCtx){
        this.ctx = ctx;
        this.srList = srList;
        wishPref = appCtx.getSharedPreferences(StrUtil.WISHLIST_PREF, MODE_PRIVATE);
        prefEditor = wishPref.edit();
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
                    srDetails.setInWishList(false);
                    prefEditor.remove(StrUtil.ITEM_KEY_PREFIX + srDetails.getItemId());
                } else {
                    ((ImageView)v).setImageResource(R.drawable.cart_remove);
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

        Glide.with(ctx)
                .load(srDetails.getImageURL())
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
                .into(holder.srImageIV);

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
