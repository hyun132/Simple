package com.example.testathome.ui.setting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testathome.MyApplication
import com.example.testathome.db.ItemDatabase
import com.example.testathome.models.Item
import com.example.testathome.repository.SearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingViewModel : ViewModel() {

    val db = ItemDatabase.getDatabase()
    val searchRepository = SearchRepository(db)

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        searchRepository.deleteAll()
    }

}
