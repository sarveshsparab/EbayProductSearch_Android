package com.sarveshparab.ebayproductsearch.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.sarveshparab.ebayproductsearch.R;
import com.sarveshparab.ebayproductsearch.fragments.AnyImageLoadedListener;
import com.sarveshparab.ebayproductsearch.utility.StrUtil;

import java.util.List;

public class PhoFragAdapter extends RecyclerView.Adapter<PhoFragAdapter.PhoFragViewHolder> {

    private Context ctx;
    private List<String> imagesURLList;
    private int imgCount;
    AnyImageLoadedListener anyImageLoadedListener;

    public class PhoFragViewHolder extends RecyclerView.ViewHolder {

        public ImageView phofCardIV;

        public PhoFragViewHolder(View itemView) {
            super(itemView);
            phofCardIV = itemView.findViewById(R.id.phofCardIV);
        }
    }

    public PhoFragAdapter(Context ctx, List<String> imagesURLList, AnyImageLoadedListener anyImageLoadedListener){
        this.ctx = ctx;
        this.imagesURLList = imagesURLList;
        this.anyImageLoadedListener = anyImageLoadedListener;
        this.imgCount = 0;
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
        Glide.with(ctx)
                .load(imageURL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if(imageURL.contains("x-raw-image"))
                            Log.v(StrUtil.LOG_TAG + "|GlideImageLoadFailed", "Google (x-raw-image) CSE API issue | [Ref: http://bit.ly/2Pu14eW ] " + model.toString());
                        else
                            Log.v(StrUtil.LOG_TAG + "|GlideImageLoadFailed", "Potentially Image not found[404] : " + model.toString());
                        anyImageLoadedListener.atLeastOneImageLoaded(imgCount);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        anyImageLoadedListener.atLeastOneImageLoaded(imgCount++);
                        return false;
                    }
                })
                .error(R.drawable.image_broken_icon)
                .into(holder.phofCardIV);
    }

    @Override
    public int getItemCount() {
        return imagesURLList.size();
    }

}
