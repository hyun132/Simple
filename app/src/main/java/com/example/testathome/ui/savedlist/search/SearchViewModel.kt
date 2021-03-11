package com.example.testathome.ui.savedlist.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testathome.models.Item
import com.example.testathome.repository.SearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {

    private val _searchResults: MutableLiveData<List<Item>> = MutableLiveData()
    val searchResult: LiveData<List<Item>>
        get() = _searchResults

    fun getSearchItem(query: String,x:Double,y:Double){
        CoroutineScope(Dispatchers.IO).launch {
            val response = searchRepository.getSearchResult(query = query,x = x,y=y)
            if(response.isSuccessful) {
                _searchResults.postValue(response.body()?.documents)
            }
        }
    }

    fun saveItem(item: Item){
        CoroutineScope(Dispatchers.IO).launch {
            searchRepository.upsert(item)
        }
    }

}
