package com.example.testathome.ui.setting

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.testathome.R
import com.example.testathome.db.ItemDatabase
import com.example.testathome.models.Item
import com.example.testathome.repository.SearchRepository
import com.example.testathome.ui.BaseFragment
import com.example.testathome.ui.savedlist.SavedViewModel
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream


class SettingFragment : BaseFragment() {

    val viewModel: SettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = view.findViewById<RelativeLayout>(R.id.btn_goto_license)
        button.setOnClickListener {
            findNavController().navigate(R.id.action_setting_to_openLicenseFragment)
        }

        view.findViewById<RelativeLayout>(R.id.btn_delete_db).setOnClickListener {
            viewModel.deleteAll()
            showAddDialog(viewModel)
        }
    }


    fun showAddDialog(viewModel:SettingViewModel){
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("모든 저장목록을 삭제하시겠습니까?")
            .setPositiveButton("확인") { dialog, id ->
                viewModel.deleteAll()
                dialog.dismiss()
                showSnackBar(this.requireView())
            }
            .setNegativeButton("취소") { dialog, id ->
                dialog.dismiss()
            }
        // Create the AlertDialog object and return it
        builder.create().show()
    }

    fun showSnackBar(view: View){
        Snackbar.make(view,
            "저장목록을 삭제하였습니다.", Snackbar.LENGTH_SHORT).show()
    }

}