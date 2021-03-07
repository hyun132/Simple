package com.example.testathome.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "item_db")
data class Item(
    val address: String,
    val category: String,
    val description: String,
    val link: String,
    val mapx: String,
    val mapy: String,
    val roadAddress: String,
    val telephone: String,
    @PrimaryKey
    var title: String
):Serializable