package com.thanht.presentation.home

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isGone
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import com.thanht.presentation.R

@BindingAdapter("app:gone")
fun View.gone(isHide: Boolean?) {
    isGone = isHide == true
}

@BindingAdapter(value = ["app:url"])
fun ImageView.loadImage(url: String?) {
    load(url) {
        transformations(CircleCropTransformation())
        placeholder(R.color.alto)
        error(R.color.dove_gray)
    }
}

@BindingAdapter(value = ["app:formatNumber"])
fun TextView.formatNumber(number: Int?) {
    text = number?.toString()
}