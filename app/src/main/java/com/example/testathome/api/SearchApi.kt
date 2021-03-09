package com.example.testathome.api

import com.example.testathome.models.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchApi {

    @GET("search/local.json")
    suspend fun searchRestaurant(
        @Query("query") query:String,
        @Query("display") display:Int =5,
        @Header("X-Naver-Client-Id") clientId:String ="0TaUOlc0yhPji0ZcSzvt",
        @Header("X-Naver-Client-Secret") clientSecret: String ="yH4tQyQWmD"
    ): Response<SearchResponse>


}