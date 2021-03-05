package com.example.testathome

import android.util.Log
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

    fun getSearchItem(query: String){
        CoroutineScope(Dispatchers.IO).launch {
            val response = searchRepository.getSearchResult(query)
            if(response.isSuccessful) {
                _searchResults.postValue(response.body()?.items)
                Log.d("inViewModel : ", response.body()?.items?.get(0).toString())
            }
//                ?.let { response->
//                Log.d("response : ",response.isSuccessful.toString())
//                if(response.isSuccessful){
//                    response.body()?.let {
//                        _searchResults.postValue(it.items)
//                    }
//                }
//            }
        }
    }

}