package com.example.testathome.ui.map

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testathome.R
import com.example.testathome.databinding.FragmentMapsBinding
import com.example.testathome.db.ItemDatabase
import com.example.testathome.models.Item
import com.example.testathome.repository.SearchRepository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MapsFragment : Fragment() {

//    lateinit var locationManager:LocationManager
    var location = LatLng(126.734086, 127.269311)
    lateinit var item :Item
    lateinit var binding: FragmentMapsBinding

    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.addMarker(MarkerOptions().position(location).title(item.title))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18F))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_maps, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        item = arguments?.getSerializable("RestaurnatItem") as Item
        binding.item=item

        var geocoder = Geocoder(context).getFromLocationName(item.address,1)
        location = LatLng(geocoder[0].latitude,geocoder[0].longitude)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        binding.saveItemButton.setOnClickListener {
            val db = ItemDatabase.getDatabase(requireContext())
            val repository=SearchRepository(db)
            CoroutineScope(Dispatchers.IO).launch {
                repository.upsert(item)
            }
            findNavController().navigate(R.id.action_mapsFragment_to_savedFragment)
        }
    }
}