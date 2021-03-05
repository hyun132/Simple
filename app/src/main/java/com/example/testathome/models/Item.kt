package com.example.testathome.models


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Item(
    val address: String,
    val category: String,
    val description: String,
    val link: String,
    val mapx: String,
    val mapy: String,
    val roadAddress: String,
    val telephone: String,
    val title: String
):Serializable