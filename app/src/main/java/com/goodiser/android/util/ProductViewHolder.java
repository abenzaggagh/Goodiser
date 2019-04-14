package com.goodiser.android.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodiser.android.R;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    ImageView productImage;
    TextView productTitle;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        productImage = (ImageView) itemView.findViewById(R.id.product_image);
    }


}
