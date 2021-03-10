package com.example.testathome.ui.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testathome.HomeRecyclerviewAdapter
import com.example.testathome.R
import com.example.testathome.databinding.FragmentSearchBinding
import com.example.testathome.db.ItemDatabase
import com.example.testathome.models.Item
import com.example.testathome.repository.SearchRepository
import com.example.testathome.ui.SearchViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SearchFragment : BottomSheetDialogFragment() {

    lateinit var viewModel : SearchViewModel
    lateinit var binding:FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = ItemDatabase.getDatabase(requireContext())
        val repository=SearchRepository(db)
        viewModel= SearchViewModel(repository)

        val adapter = HomeRecyclerviewAdapter()
        adapter.setOnItemClickListener {
            showDialog(it)
//            var bundle = Bundle().apply {
//                putSerializable("RestaurnatItem",it)
//            }
            findNavController().navigate(R.id.action_homeFragment_to_mapsFragment)
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
    fun showDialog(item:Item){
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(item.place_name +"을 추가하시겠습니까?")
            .setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, id ->
                    viewModel.saveItem(item)
                    viewModel.savedItems
                })
            .setNegativeButton("취소",
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                })
        // Create the AlertDialog object and return it
        builder.create().show()
    }

}

