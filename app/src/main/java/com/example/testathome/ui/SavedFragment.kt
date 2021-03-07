package com.example.testathome.ui

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testathome.HomeRecyclerviewAdapter
import com.example.testathome.R
import com.example.testathome.SearchViewModel
import com.example.testathome.databinding.FragmentSavedBinding
import com.example.testathome.db.ItemDatabase
import com.example.testathome.repository.SearchRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class SavedFragment : Fragment() {

    lateinit var viewModel: SearchViewModel
    lateinit var binding: FragmentSavedBinding
    lateinit var adapter: HomeRecyclerviewAdapter
    lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_saved, container, false)

        val db = ItemDatabase.getDatabase(requireContext())
        val repository = SearchRepository(db)
        viewModel = SearchViewModel(repository)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HomeRecyclerviewAdapter()

        ItemTouchHelper(object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT){

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteItem(adapter.differ.currentList[viewHolder.adapterPosition])
                Log.d("onSwiped : ", "item swiped!")
            }

        }).attachToRecyclerView(binding.savedRecyclerview)

        binding.viewModel = viewModel

        viewModel.savedItems.observe(viewLifecycleOwner) {
            Log.d("main: ", "data changed!")
            adapter.differ.submitList(it)
        }

        binding.savedRecyclerview.adapter = adapter
        binding.savedRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        binding.addItemFloationbutton.setOnClickListener {
            findNavController().navigate(R.id.action_savedFragment_to_homeFragment)
        }

        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.fragment_dialog)
        dialog.window?.setLayout(700, 700)

        val anim = AnimationUtils.loadAnimation(context,R.anim.dialog_text_anim)
        val dialogButton = dialog.findViewById<TextView>(R.id.ok_button)
        dialogButton.setOnClickListener {
            dialog.dismiss()
            anim.cancel()
        }

        binding.randomPickButton.setOnClickListener {
            viewModel.savedItems.value?.let { it1 ->
                val num = Random.nextInt(it1.size)

                val title =dialog.findViewById<TextView>(R.id.edit)
                title.apply {
                    text = viewModel.savedItems.value?.get(num)?.title.toString()
                    animation = anim
                }
                dialog.show()
                anim.start()
            }
        }



    }
}



