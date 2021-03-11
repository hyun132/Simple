package com.example.testathome.ui.setting

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testathome.R
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

        var button = view.findViewById<TextView>(R.id.gotolicense)
        button.setOnClickListener {
            findNavController().navigate(R.id.action_setting_to_openLicenseFragment)
        }
    }


}