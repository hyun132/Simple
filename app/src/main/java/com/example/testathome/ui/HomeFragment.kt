package com.example.testathome.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testathome.HomeRecyclerviewAdapter
import com.example.testathome.R
import com.example.testathome.SearchViewModel
import com.example.testathome.databinding.FragmentHomeBinding
import com.example.testathome.db.ItemDatabase
import com.example.testathome.repository.SearchRepository

class HomeFragment : Fragment() {

    lateinit var viewModel : SearchViewModel
    lateinit var binding:FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = ItemDatabase.getDatabase(requireContext())
        val repository=SearchRepository(db)
        viewModel=SearchViewModel(repository)

        val adapter = HomeRecyclerviewAdapter()
        adapter.setOnItemClickListener {
            var bundle = Bundle().apply {
                putSerializable("RestaurnatItem",it)
            }
            findNavController().navigate(R.id.action_homeFragment_to_mapsFragment,bundle)
            Toast.makeText(context,"${it.title} clicked",Toast.LENGTH_SHORT).show()
        }

//        adapter.setOnItemButtonClickListener {
//            viewModel.saveItem(it)
//            findNavController().popBackStack()
//        }



        binding.homeRecyclerview.adapter =adapter
        binding.viewModel=viewModel

        viewModel.searchResult.observe(viewLifecycleOwner){
            Log.d("main: ","data changed!")
            adapter.differ.submitList(it)
        }


        binding.homeRecyclerview.layoutManager = LinearLayoutManager(context)

        binding.searchView.setOnQueryTextListener(object :androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    viewModel.getSearchItem(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

    }

}

