package com.example.testathome.ui.setting

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testathome.R
import com.example.testathome.databinding.FragmentOpenLicenseBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream

class OpenLicenseFragment : Fragment() {

    val fileNames = arrayOf(R.raw.lottie,R.raw.okhttp,R.raw.retrofit)
    val contents = arrayListOf<LicenseData>()
    lateinit var binding:FragmentOpenLicenseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        for(filename in fileNames){
            readTxtfile(requireContext(),filename)?.let { contents.add(it) }
        }
    }

    fun readTxtfile(context: Context, resId: Int): LicenseData? {
        var result = ""
        var filename =""
        val txtResource: InputStream = context.getResources().openRawResource(resId)
        val byteArrayOutputStream = ByteArrayOutputStream()
        var i: Int
        try { i = txtResource.read()  //파일 끝나면 -1을 리턴함.
            while (i != -1) {
                byteArrayOutputStream.write(i)
                i = txtResource.read()
            }
            result = String(byteArrayOutputStream.toByteArray()).trim(' ')
            filename= requireContext().resources.getResourceName(resId).split('/')[1]
            txtResource.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return LicenseData(filename,result)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_open_license, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            title="오픈소스 라이선스"
        }

        binding.lisenceRecyclerview.apply {
            adapter = LicenseAdapter(contents)
            layoutManager=LinearLayoutManager(requireContext())
        }
    }

    data class LicenseData(
        val title:String,
        val contents:String
    )
}