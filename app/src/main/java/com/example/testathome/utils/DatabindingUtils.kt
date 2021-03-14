package com.example.testathome.utils

import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testathome.HomeRecyclerviewAdapter
import com.example.testathome.R
import com.example.testathome.models.Item

object DatabindingUtils {
    @JvmStatic
    @BindingAdapter("listItem")
    fun bindSearchList(recyclerView: RecyclerView, items : List<Item>?){
        var adapter :HomeRecyclerviewAdapter
        if (recyclerView.adapter==null){
            adapter = HomeRecyclerviewAdapter()
            recyclerView.adapter=adapter
        }else{
            adapter= recyclerView.adapter as HomeRecyclerviewAdapter
        }
        adapter.differ.submitList(items)
    }

    @JvmStatic
    @BindingAdapter("liked_src")
    fun bindItemLiked(imageView: ImageView, isLiked:Boolean){
        if (isLiked){
            imageView.setImageResource(R.drawable.ic_yellow_star)
        }else{
            imageView.setImageResource(R.drawable.ic_gray_star)
        }
    }
}