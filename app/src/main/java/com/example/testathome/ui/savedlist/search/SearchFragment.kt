package com.example.testathome.ui.savedlist.search

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testathome.HomeRecyclerviewAdapter
import com.example.testathome.R
import com.example.testathome.databinding.FragmentSearchBinding
import com.example.testathome.db.ItemDatabase
import com.example.testathome.models.Item
import com.example.testathome.repository.SearchRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SearchFragment(currentLocation: Location) : BottomSheetDialogFragment() {

    lateinit var viewModel : SearchViewModel
    lateinit var binding:FragmentSearchBinding
    var currentLocation =LatLng(currentLocation.latitude,currentLocation.longitude)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //추가
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBottomSheet(view)

        val arg = arguments?.getDoubleArray("currentLocation")
        if (arg!=null){
            currentLocation=LatLng(arg[0],arg[1])
        }

        val db = ItemDatabase.getDatabase(requireContext())
        val repository=SearchRepository(db)
        viewModel= SearchViewModel(repository)

        val adapter = HomeRecyclerviewAdapter()
        adapter.setOnItemClickListener {
            showAddDialog(it)
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
                    viewModel.getSearchItem(query = newText,y=currentLocation.latitude,x=currentLocation.longitude)
                }
                return false
            }

        })

    }

    private fun initBottomSheet(view:View) {
        if (dialog != null) {
            val bottomSheet: View = dialog!!.findViewById(R.id.design_bottom_sheet)
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }

        val view = view
        view.post{
            val parent = view.parent as View
            val params = parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            val bottomSheetBehavior = behavior as BottomSheetBehavior<*>?
            bottomSheetBehavior!!.peekHeight = view!!.measuredHeight-binding.searchToolbar.height
            parent.setBackgroundColor(Color.TRANSPARENT)
        }

        //https://life-inus.tistory.com/m/33
    }

    fun showAddDialog(item:Item){
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(item.place_name +"을 추가하시겠습니까?")
            .setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, id ->
                    viewModel.saveItem(item)
                    dialog.dismiss()
                    this.dismiss()
                })
            .setNegativeButton("취소",
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                })
        // Create the AlertDialog object and return it
        builder.create().show()
    }

}

