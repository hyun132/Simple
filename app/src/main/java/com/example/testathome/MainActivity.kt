package com.example.testathome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.testathome.repository.SearchRepository
import com.example.testathome.ui.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragManager = supportFragmentManager
        var transaction = fragManager.beginTransaction()

        transaction.replace(R.id.fragment_container,HomeFragment())
        fragManager.beginTransaction()

        transaction.commit()

    }
}