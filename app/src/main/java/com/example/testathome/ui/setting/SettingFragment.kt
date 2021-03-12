package com.example.testathome.ui.setting

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.testathome.R
import com.example.testathome.db.ItemDatabase
import com.example.testathome.repository.SearchRepository
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream


class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = ItemDatabase.getDatabase(requireContext())
        val repository = SearchRepository(db)
        val viewModel: SettingViewModel by viewModels {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                    SettingViewModel(repository) as T
            }
        }

        val button = view.findViewById<RelativeLayout>(R.id.btn_goto_license)
        button.setOnClickListener {
            findNavController().navigate(R.id.action_setting_to_openLicenseFragment)
        }

        view.findViewById<RelativeLayout>(R.id.btn_delete_db).setOnClickListener {
            viewModel.deleteAll()
        }
    }


}