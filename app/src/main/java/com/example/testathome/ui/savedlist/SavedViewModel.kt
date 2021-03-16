package com.example.testathome.ui.savedlist

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

class SavedViewModel : ViewModel() {

    val db = ItemDatabase.getDatabase()
    private val searchRepository=SearchRepository(db)

    fun deleteItem(item: Item) = viewModelScope.launch(Dispatchers.IO) {
        searchRepository.delete(item)
    }

    fun updateItem(item: Item) = viewModelScope.launch(Dispatchers.IO) {
        searchRepository.update(item)
    }

    var getSavedItems = searchRepository.getAll()

}
