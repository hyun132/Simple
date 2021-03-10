package com.example.testathome.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testathome.HomeRecyclerviewAdapter
import com.example.testathome.R
import com.example.testathome.databinding.FragmentHomeBinding
import com.example.testathome.db.ItemDatabase
import com.example.testathome.repository.SearchRepository
import com.example.testathome.ui.SearchViewModel

class SearchFragment : Fragment() {

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
        viewModel= SearchViewModel(repository)

        val adapter = HomeRecyclerviewAdapter()
        adapter.setOnItemClickListener {
            var bundle = Bundle().apply {
//                var item=it
//                item.title=item.title.replace("<b>","").replace("</b>","")
                putSerializable("RestaurnatItem",it)
            }
            findNavController().navigate(R.id.action_homeFragment_to_mapsFragment,bundle)
            Toast.makeText(context,"${it.place_name} clicked",Toast.LENGTH_SHORT).show()
        }

        binding.homeRecyclerview.adapter =adapter
        binding.viewModel=viewModel

        viewModel.searchResult.observe(viewLifecycleOwner){
            Log.d("main: ","data changed!")
            adapter.differ.submitList(it)
        }


        binding.homeRecyclerview.layoutManager = LinearLayoutManager(context)

        binding.searchView.setOnQueryTextListener(object :androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank()) {
                    viewModel.getSearchItem(newText)
                }
                return false
            }

        })

    }

}

