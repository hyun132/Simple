package com.example.testathome.api

import com.example.testathome.Constants.Companion.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.ByteString.Companion.encode
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URLEncoder

abstract class RetrofitInstance {

    companion object {

        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder().addInterceptor(logging).build()
//            client.apply {
//                addInterceptor(
//                    Interceptor { chain ->
//                        val request = chain.request().newBuilder()
//                            .addHeader("X-Naver-Client-Id",URLEncoder.encode("0TaUOlc0yhPji0ZcSzvt","UTF-8") )
//                            .addHeader("X-Naver-Client-Secret", URLEncoder.encode("B21IhitoqT","UTF-8"))
//                        return@Interceptor chain.proceed(request.build())
//
//                    }
//                )
//            }



            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api by lazy {
            retrofit.create(SearchApi::class.java)
        }

    }

}