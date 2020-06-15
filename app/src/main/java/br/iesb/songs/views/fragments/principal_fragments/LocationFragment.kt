package br.iesb.songs.views.fragments.principal_fragments

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.iesb.songs.R
import br.iesb.songs.view_model.LoginViewModel
import br.iesb.songs.view_model.PlaylistViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_location.*

class LocationFragment(context: Context) : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {
    //Attributes
    var lastSelectedMark: Marker? = null

    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val viewModelL: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }
    private val viewModelP: PlaylistViewModel by lazy {
        ViewModelProvider(this).get(PlaylistViewModel::class.java)
    }
    //End attributes

    //Map permission
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private fun verifyPermission(): Boolean {
        return context?.let {
            ActivityCompat.checkSelfPermission(
                it,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        } == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        activity?.let {
            ActivityCompat.requestPermissions(
                it,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
        return
    }

    private fun permissionGranted() {
        permissionButton.visibility = View.GONE
        permissionText.visibility = View.GONE

        mMap.isMyLocationEnabled = true

        getUserLocation { latLng ->
            saveUserData(latLng)
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    latLng,
                    14f
                )
            )
        }
    }
    //End map permission

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        permissionButton.setOnClickListener {
            requestPermission()
            Handler().postDelayed({
                if (verifyPermission()) {
                    permissionGranted()
                }
            }, 3000)
        }

        fusedLocationClient =
            activity?.let { LocationServices.getFusedLocationProviderClient(it) }!!

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)

        if (!verifyPermission()) {
            requestPermission()

            Handler().postDelayed({
                if (verifyPermission()) {
                    permissionGranted()
                }
            }, 4000)
        } else {
            permissionGranted()
        }
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        lastSelectedMark = marker
        marker?.showInfoWindow()

        return true
    }

    private fun placeMarkerOnMap(location: LatLng, title: String) {
        val markerOptions = MarkerOptions().position(location).title(title)
        mMap.addMarker(markerOptions)
    }

    private fun getUserLocation(callback: (LatLng) -> Unit) {
        activity?.let {
            fusedLocationClient.lastLocation.addOnSuccessListener(it) { location ->
                if (location != null) {
                    lastLocation = location
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    callback(currentLatLng)
                }
            }
        }
    }

    private fun saveUserData(latLng: LatLng) {
        viewModelL.verifyName { name ->
            viewModelP.saveUserMap(name, latLng)
        }
    }

    override fun onResume() {
        super.onResume()

        if (verifyPermission()) {
            getUserLocation { latLng ->
                saveUserData(latLng)
            }
        }
    }

    override fun onPause() {
        super.onPause()

        viewModelP.removeUserMap()
    }
}