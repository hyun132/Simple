package com.example.testathome.ui.savedlist

import androidx.lifecycle.ViewModel
import com.example.testathome.models.Item
import com.example.testathome.repository.SearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavedViewModel(private val searchRepository: SearchRepository) : ViewModel() {

    fun deleteItem(item: Item){
        CoroutineScope(Dispatchers.IO).launch {
            searchRepository.delete(item)
        }
    }

    var savedItems = searchRepository.getSavedItemList

}
