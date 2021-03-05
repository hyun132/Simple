package com.example.testathome.repository

import com.example.testathome.api.RetrofitInstance

class SearchRepository {

    suspend fun getSearchResult(query:String) = RetrofitInstance.api.searchRestaurant(query)

}