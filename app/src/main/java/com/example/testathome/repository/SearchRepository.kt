package com.example.testathome.repository

import androidx.lifecycle.LiveData
import com.example.testathome.api.RetrofitInstance
import com.example.testathome.db.Dao
import com.example.testathome.db.ItemDatabase
import com.example.testathome.models.Item

class SearchRepository(db:ItemDatabase) {

    val dao = db.getDao()

    suspend fun getSearchResult(query:String) = RetrofitInstance.naverApi.searchRestaurant(query)

    suspend fun upsert(item:Item){
        dao.upsert(item)
    }

    suspend fun delete(item: Item){
        dao.deleteItem(item)
    }

    val getSavedItemList:LiveData<List<Item>> = dao.getSavedItem()

}