package com.example.testathome.models


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    val display: Int,
    val items: List<Item>,
    val lastBuildDate: String,
    val start: Int,
    val total: Int
)