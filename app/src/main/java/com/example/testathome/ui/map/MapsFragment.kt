package com.example.testathome.ui.map

import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.example.testathome.R
import com.example.testathome.databinding.FragmentMapsBinding
import com.example.testathome.models.Item
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


@SuppressLint("MissingPermission")
class MapsFragment : Fragment(), GoogleMap.OnMyLocationClickListener,
    GoogleMap.OnMyLocationButtonClickListener {

    var defaultLocation = LatLng(126.734086, 127.269311)
    lateinit var currentLocation: Location
    lateinit var currentPosition: LatLng
    lateinit var item :Item
    lateinit var binding: FragmentMapsBinding
    private lateinit var fuseLocationProvicerClient: FusedLocationProviderClient
    lateinit var mLocationManager:LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentPosition=defaultLocation

        fuseLocationProvicerClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        Log.d("onCreate : ", "$fuseLocationProvicerClient")
        fuseLocationProvicerClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
//                    currentLocation=location
                    currentPosition= LatLng(location.altitude,location.longitude)
                }

            }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_maps, container, false)
        return binding.root
    }

    private val callback = OnMapReadyCallback { googleMap ->

        mLocationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager
        var mLocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location?) {
                var lat = 0.0
                var lng = 0.0
                if (location != null) {
                    lat = location.latitude
                    lng = location.longitude
                    Log.d("GmapViewFragment", "Lat: ${lat}, lon: ${lng}")
                }
                currentPosition = LatLng(lat, lng)
                googleMap!!.addMarker(MarkerOptions().position(currentPosition).title("현재위치"))
                googleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15f))
            }
        }

//        mLocationManager.requestLocationUpdates(
//            LocationManager.GPS_PROVIDER,
//            3000L,
//            30f,
//            mLocationListener
//        )
        googleMap.isMyLocationEnabled=true
        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.setOnMyLocationClickListener(this)
//        googleMap.addMarker(MarkerOptions().position(fuseLocationProvicerClient.lastLocation.result.la))
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                (currentPosition), 18F
            )
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        binding.saveItemButton.setOnClickListener {
            findNavController().navigate(R.id.action_mapsFragment_to_homeFragment)

        }
    }

   override fun onMyLocationClick(location: Location) {
        Toast.makeText(activity, "Current location:\n$location", Toast.LENGTH_LONG).show()
        currentLocation=location
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(activity, "MyLocation button clicked", Toast.LENGTH_SHORT)
            .show()
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onResume() {
        super.onResume()

    }



}

