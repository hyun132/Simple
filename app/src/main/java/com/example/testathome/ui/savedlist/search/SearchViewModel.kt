package com.example.testathome.ui.savedlist.search

import androidx.core.content.ContentProviderCompat.requireContext
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

class SearchViewModel : ViewModel() {

    val db = ItemDatabase.getDatabase()
    private val searchRepository=SearchRepository(db)

    private val _searchResults: MutableLiveData<List<Item>> = MutableLiveData()
    val searchResult: LiveData<List<Item>>
        get() = _searchResults

    //메인 쓰레드에서 실행(viewModelScope 는 메인쓰레드에서 돌아감 )
    fun getSearchItem(query: String, x: Double, y: Double) = viewModelScope.launch {
        val response = searchRepository.getSearchResult(query = query, x = x, y = y)
        if (response.isSuccessful) {
            _searchResults.postValue(response.body()?.documents)
        }
    }

    fun saveItem(item: Item) =viewModelScope.launch {
        searchRepository.upsert(item)

    }

}
