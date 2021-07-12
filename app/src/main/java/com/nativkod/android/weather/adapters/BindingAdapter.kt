package com.nativkod.android.weather.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("bind:imageUrl")
fun loadImage(imageView: ImageView, url: String){
    if (!url.isNullOrEmpty()){
        Picasso.get()
            .load(url)

            .into(imageView)
    }
}