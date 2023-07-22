package surajgiri.swipe.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import surajgiri.swipe.R

fun ImageView.loadUrl(url: String) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.light_background)
        .error(R.drawable.no_photo)
        .into(this)
}