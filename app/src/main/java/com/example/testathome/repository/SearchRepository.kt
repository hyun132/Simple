package com.example.testathome.repository

import androidx.lifecycle.LiveData
import com.example.testathome.api.RetrofitInstance
import com.example.testathome.db.ItemDatabase
import com.example.testathome.models.Item

class SearchRepository(db:ItemDatabase) {

    val dao = db.getDao()

    suspend fun getSearchResult(query:String,x:Double,y:Double) = RetrofitInstance.api.searchRestaurant(query=query,x=x,y=y)

    suspend fun upsert(item:Item){
        dao.upsert(item)
    }

    suspend fun delete(item: Item){
        dao.deleteItem(item)
    }

//    val getSavedItemList:LiveData<List<Item>> = dao.getSavedItem()
    fun getAll():LiveData<List<Item>> = dao.getSavedItem()

}