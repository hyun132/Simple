package com.example.testathome.ui.setting

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testathome.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream

class OpenLicenseFragment : Fragment() {

    val fileNames = arrayOf(R.raw.lottie,R.raw.okhttp,R.raw.okhttp)
    val contents = arrayListOf<String>()
    @SuppressLint("SdCardPath")
    val filePath="/data/data/com.example.testathome/files/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//
        for(filename in fileNames){
            readTxtfile(requireContext(),filename)
        }

        for (str in contents){
            println(str)
        }

    }
    fun readFileAsLinesUsingBufferedReader(resId: Int): List<String>
            = File(requireContext().getResources().openRawResource(resId).toString()).bufferedReader().readLines()

    fun readTxtfile(context: Context, resId: Int): String? {
        var result = ""
        val txtResource: InputStream = context.getResources().openRawResource(resId)
        val byteArrayOutputStream = ByteArrayOutputStream()
        var i: Int
        try { i = txtResource.read()
            while (i != -1) {
                byteArrayOutputStream.write(i)
                i = txtResource.read()
            }
            contents.add(String(byteArrayOutputStream.toByteArray()))
            txtResource.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return result.trim { it <= ' ' }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_open_license, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}