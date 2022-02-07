package com.olutoba.marsrealestate


import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


val sharedOptions = RequestOptions()
    .placeholder(R.drawable.loading_animation)
    .error(R.drawable.ic_broken_image)

/** Use the Glide library to load an image by URL into an ImageView */
@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        val imageUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(imageUri)
            .apply(sharedOptions)
            .into(imageView)
    }
}