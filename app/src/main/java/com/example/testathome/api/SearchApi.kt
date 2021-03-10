package com.example.testathome.api

import com.example.testathome.models.KakaoSearchResponse
import com.example.testathome.models.SearchResponse
import com.example.testathome.utils.Constants.Companion.KAKAO_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchApi {

    @GET("/v2/local/search/keyword.json")
    suspend fun searchRestaurant(
        @Query("query") query:String,
        @Query("page") display:Int =1,
        @Query("size") size:Int =15,
        @Query("y") y:Double,
        @Query("x") x:Double,
        @Query("radius") radius:Int =1000,
    ): Response<KakaoSearchResponse>


}