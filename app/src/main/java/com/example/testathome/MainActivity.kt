package com.example.testathome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.testathome.db.ItemDatabase
import com.example.testathome.repository.SearchRepository
import com.example.testathome.ui.HomeFragment
import kotlinx.coroutines.CoroutineScope

class MainActivity : AppCompatActivity() {

//    lateinit var viewModel : SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        val db = ItemDatabase.getDatabase(this.applicationContext)
//        val repository= SearchRepository(db)
//        viewModel= ViewModelProvider(this)[SearchViewModel(repository)::class.java]
    }
}