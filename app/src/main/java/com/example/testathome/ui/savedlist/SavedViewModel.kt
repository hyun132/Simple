package com.example.testathome.ui.savedlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testathome.models.Item
import com.example.testathome.repository.SearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavedViewModel(var searchRepository: SearchRepository) : ViewModel() {

    fun deleteItem(item: Item) = viewModelScope.launch {
        searchRepository.delete(item)
    }

    fun getSavedItems() = searchRepository.getAll()

}
