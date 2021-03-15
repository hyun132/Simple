package com.example.testathome.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.example.testathome.models.Item

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item:Item):Long

    @Delete
    suspend fun deleteItem(item: Item)

    @Query("select * from item_db")
    fun getSavedItem():LiveData<List<Item>>

    @Query("Delete from item_db")
    suspend fun deleteAll()

    @Update
    suspend fun update(item: Item)

}