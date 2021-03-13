package com.example.testathome.models


import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "item_db")
data class Item(
    val address_name: String,
    val category_group_code: String,
    val category_group_name: String,
    val category_name: String,
    var distance: String,
    @PrimaryKey val id: String,
    val phone: String,
    val place_name: String,
    val place_url: String,
    val road_address_name: String,
    val x: String,
    val y: String,
    var liked:Boolean=false
):Serializable