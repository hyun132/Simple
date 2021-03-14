package com.example.testathome.ui.savedlist

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testathome.HomeRecyclerviewAdapter
import com.example.testathome.R
import com.example.testathome.databinding.FragmentSavedBinding
import com.example.testathome.db.ItemDatabase
import com.example.testathome.repository.SearchRepository
import com.example.testathome.ui.BaseFragment
import com.example.testathome.ui.savedlist.search.SearchViewModel
import kotlin.random.Random

class SavedFragment : BaseFragment() {

    lateinit var binding: FragmentSavedBinding
    lateinit var adapter: HomeRecyclerviewAdapter
    lateinit var dialog: Dialog
    lateinit var anim:Animation
    lateinit var viewModel : SavedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val savedViewModel: SavedViewModel by viewModels()
        viewModel=savedViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_saved, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val viewModel = SavedViewModel(repository)
        adapter = HomeRecyclerviewAdapter()
        binding.apply {
            viewModel = viewModel
            savedRecyclerview.layoutManager= LinearLayoutManager(context)
            savedRecyclerview.adapter=adapter
        }

        initRecyclerview()
        searchDialogSetting()

        viewModel.getSavedItems.observe(viewLifecycleOwner) {
            Log.d("main: ", "data changed!")
            adapter.differ.submitList(it)
        }

        binding.randomPickButton.setOnClickListener {
            viewModel.getSavedItems.value?.let { it1 ->
                if (it1.size < 1) {
                    Toast.makeText(requireContext(), "목록을 추가해주세요!", Toast.LENGTH_SHORT).show()
                } else {
                    val num = Random.nextInt(it1.size)

                    val title = dialog.findViewById<TextView>(R.id.edit)
                    title.apply {
                        text = it1.get(num).place_name
                        animation = anim
                    }
                    dialog.show()
                    anim.start()
                }
            }
        }
    }

    private fun initRecyclerview() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

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

    }

    private fun searchDialogSetting() {
        dialog = Dialog(requireContext())
        dialog.apply {
            setContentView(R.layout.fragment_dialog)
            window?.setLayout(700, 700)
        }

        anim = AnimationUtils.loadAnimation(context, R.anim.dialog_text_anim)
        val dialogButton = dialog.findViewById<TextView>(R.id.ok_button)
        dialogButton.setOnClickListener {
            dialog.dismiss()
            anim.cancel()
        }
    }

}



