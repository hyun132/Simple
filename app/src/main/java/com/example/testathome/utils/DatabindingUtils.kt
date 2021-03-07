package com.example.testathome.utils

import android.text.Html
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testathome.HomeRecyclerviewAdapter
import com.example.testathome.models.Item


// null값 처리 어떻게할건지 ?
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
    @BindingAdapter("makeKeywordBold")
    fun boldText(textView: TextView,text:String){
        textView.setText(Html.fromHtml(text))
    }
}