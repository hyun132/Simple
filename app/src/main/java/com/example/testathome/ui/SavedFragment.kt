package com.example.testathome.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testathome.HomeRecyclerviewAdapter
import com.example.testathome.R
import com.example.testathome.SearchViewModel
import com.example.testathome.databinding.FragmentSavedBinding
import com.example.testathome.db.ItemDatabase
import com.example.testathome.repository.SearchRepository

class SavedFragment : Fragment() {

    lateinit var viewModel : SearchViewModel
    lateinit var binding :FragmentSavedBinding
    lateinit var adapter:HomeRecyclerviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_saved, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.savedItems.observe(viewLifecycleOwner){
            Log.d("main: ","data changed!")
            adapter.differ.submitList(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = ItemDatabase.getDatabase(requireContext())
        val repository= SearchRepository(db)
        viewModel=SearchViewModel(repository)

        adapter = HomeRecyclerviewAdapter()

        binding.viewModel = viewModel

        viewModel.savedItems.observe(viewLifecycleOwner){
            Log.d("main: ","data changed!")
            adapter.differ.submitList(it)
        }

        binding.savedRecyclerview.adapter=adapter
        binding.savedRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        binding.addItemFloationbutton.setOnClickListener {
            findNavController().navigate(R.id.action_savedFragment_to_homeFragment)
        }
    }


}