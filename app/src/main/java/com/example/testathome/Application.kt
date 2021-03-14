package com.example.testathome

import android.app.Application
import android.content.Context
import com.google.android.gms.maps.model.LatLng

class MyApplication:Application(){

    init{
        instance = this
    }

    companion object {
        private var instance: MyApplication? = null
        fun ApplicationContext() : Context {
            return instance!!.applicationContext
        }
    }
}