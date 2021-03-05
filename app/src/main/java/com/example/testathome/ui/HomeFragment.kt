package com.example.testathome.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testathome.HomeRecyclerviewAdapter
import com.example.testathome.R
import com.example.testathome.SearchViewModel
import com.example.testathome.repository.SearchRepository

class HomeFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerview = view.findViewById<RecyclerView>(R.id.homeRecyclerview)
        val adapter = HomeRecyclerviewAdapter()
        adapter.setOnItemClickListener {
//            var bundle = Bundle().apply {
//                putSerializable("restaurantItem",it)
//            }
            Toast.makeText(context,"${it.title} clicked",Toast.LENGTH_SHORT).show()
        }
        recyclerview.adapter =adapter

        val repository= SearchRepository()
        viewModel= SearchViewModel(repository)

        viewModel.searchResult.observe(this){
            Log.d("main: ","data changed!")
            adapter.differ.submitList(it)
        }


        recyclerview.layoutManager = LinearLayoutManager(context)
        viewModel.getSearchItem("카레")
        Log.d("isLoadad?",viewModel.searchResult.value?.get(0)?.title.toString())


    }

}