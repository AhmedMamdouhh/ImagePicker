package com.payback.imagepicker.manager.utilities

import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.payback.imagepicker.R

@BindingAdapter("imageUrl")
fun ImageView.bindImage( imgUrl: String?) {
        imgUrl?.let {
            val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            Glide.with(this.context)
                .load(imgUri)
                .apply(
                    RequestOptions()
                        .error(R.drawable.ic_broken_image)
                )
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(this)

    }
}

