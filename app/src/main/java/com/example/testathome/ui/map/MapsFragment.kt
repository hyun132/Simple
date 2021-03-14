package com.example.testathome.ui.map

import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.testathome.R
import com.example.testathome.databinding.FragmentMapsBinding
import com.example.testathome.models.Item
import com.example.testathome.ui.BaseFragment
import com.example.testathome.ui.savedlist.search.SearchFragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng


@SuppressLint("MissingPermission")
class MapsFragment : BaseFragment(), GoogleMap.OnMyLocationClickListener,
    GoogleMap.OnMyLocationButtonClickListener {

    lateinit var currentLocation: Location
    lateinit var item: Item
    lateinit var binding: FragmentMapsBinding
    private lateinit var fuseLocationProvicerClient: FusedLocationProviderClient
    lateinit var mLocationManager: LocationManager
    private var mMap: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_maps, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        binding.saveItemButton.setOnClickListener {
            val dialogFragment = SearchFragment(currentLocation)
            dialogFragment.show(childFragmentManager,"fragment_search")
        }
    }

    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        mLocationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager
        googleMap.isMyLocationEnabled = true
        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.setOnMyLocationClickListener(this)
        getCurrentLocation()
    }

    private fun moveCamera(latLng: LatLng, zoom: Float) {
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    }

    private fun getCurrentLocation() {
        fuseLocationProvicerClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fuseLocationProvicerClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    currentLocation = location
                    moveCamera(LatLng(currentLocation.latitude, currentLocation.longitude), 18F)
                }
            }
    }

    override fun onMyLocationClick(location: Location) {
        Toast.makeText(activity, "Current location:\n$location", Toast.LENGTH_LONG).show()
        currentLocation = location
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
        getCurrentLocation()
    }


}

