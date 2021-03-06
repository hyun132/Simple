package com.example.testathome.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testathome.models.Item

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item:Item):Long

    @Delete
    suspend fun deleteItem(item: Item)

    @Query("select * from item_db")
    fun getSavedItem():LiveData<List<Item>>

}