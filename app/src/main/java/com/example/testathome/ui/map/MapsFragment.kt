package com.example.testathome.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.testathome.R
import com.example.testathome.databinding.FragmentMapsBinding
import com.example.testathome.models.Item
import com.example.testathome.ui.BaseFragment
import com.example.testathome.ui.MainActivity
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
    lateinit var mapFragment: SupportMapFragment

    val PERMISSION_REQUEST_CODE = 1001
    var NEVER_ASK_AGAIN=false

    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            when {
                checkAllPermissionsGranted(permissions) -> {
                    val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
                    mapFragment?.getMapAsync(callback)
                }

                else -> {
//                    if (!NEVER_ASK_AGAIN){
//                        onRequestPermissionsResult(PERMISSION_REQUEST_CODE,permissions, intArrayOf(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                            ,checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)))
//                    }

                    // You can directly ask for the permission.
                    // The registered ActivityResultCallback gets the result of this request.
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            permissions,
                            PERMISSION_REQUEST_CODE
                        )
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_maps, container, false)
        mapFragment = (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if( checkAllPermissionsGranted(permissions)){
            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(callback)
        }

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
        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    }

    private fun getCurrentLocation() {
        fuseLocationProvicerClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fuseLocationProvicerClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    currentLocation = location
                    mapFragment?.getMapAsync {callback }
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

    fun checkAllPermissionsGranted(permissions: Array<String>): Boolean {

        var hasGranted = true
        for (permission in permissions) {
            hasGranted = (hasGranted && ContextCompat.checkSelfPermission(requireContext().applicationContext, permission
            ) == PackageManager.PERMISSION_GRANTED)
        }
        return hasGranted
    }

    @SuppressLint("ResourceType")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isEmpty() ||
                            PackageManager.PERMISSION_DENIED in grantResults)
                ) {
                    //권한 여러개일 때 하나라도 거절된 권한이 있으면
                    Toast.makeText(requireContext(), "서비스 사용을 위해 권한 동의가 필요합니다.", Toast.LENGTH_SHORT).show()
                    return
                } else {
//                   refreshFragment(this,mapFragment.childFragmentManager)
//                    mapFragment.getMapAsync(callback)

                }
            }
            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }
    fun refreshFragment(fragment: Fragment, fragmentManager: FragmentManager) {
        var ft: FragmentTransaction = fragmentManager.beginTransaction()
        ft.detach(fragment).attach(fragment).commit()
    }

}

