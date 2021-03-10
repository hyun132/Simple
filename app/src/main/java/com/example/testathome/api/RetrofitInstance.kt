package com.example.testathome.api

import com.example.testathome.utils.Constants.Companion.BASE_URL_FOR_NAVER
import com.example.testathome.utils.Constants.Companion.CLIENT_ID
import com.example.testathome.utils.Constants.Companion.CLIENT_SECRET
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URLEncoder

abstract class RetrofitInstance {

    companion object {

        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val interceptor =Interceptor { chain ->
                        val request = chain.request().newBuilder()
                            .addHeader("X-Naver-Client-Id", CLIENT_ID)
                            .addHeader("X-Naver-Client-Secret", CLIENT_SECRET).build()
                        return@Interceptor chain.proceed(request)
                    }

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(interceptor)
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL_FOR_NAVER)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val naverApi by lazy {
            retrofit.create(SearchApi::class.java)
        }

    }

}