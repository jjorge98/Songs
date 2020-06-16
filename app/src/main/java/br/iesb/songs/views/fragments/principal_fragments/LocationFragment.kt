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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.iesb.songs.R
import br.iesb.songs.data_class.User
import br.iesb.songs.view_model.LoginViewModel
import br.iesb.songs.view_model.PlaylistViewModel
import br.iesb.songs.views.PrincipalActivity
import br.iesb.songs.views.fragments.dialog_fragment.SharePlaylistsConfirmation
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

class LocationFragment(context: Context, private val principalView: PrincipalActivity) : Fragment(),
    OnMapReadyCallback,
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

    //Overrides
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleLocationFragment.visibility = View.GONE
        denyShareLocationFragment.visibility = View.GONE
        allowShareLocationFragment.visibility = View.GONE

        allowShareLocationFragment.setOnClickListener { sharePermission() }
        denyShareLocationFragment.setOnClickListener { removeSharePermission() }
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

    override fun onPause() {
        super.onPause()

        viewModelP.removeUserMap()
    }

    override fun onResume() {
        super.onResume()

        try {
            mMap.clear()
        } catch (e: Exception) {
            //
        }


        allowShareLocationFragment.visibility = View.VISIBLE
        denyShareLocationFragment.visibility = View.INVISIBLE
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)

        if (!verifyPermission()) {
            allowShareLocationFragment.visibility = View.INVISIBLE
            denyShareLocationFragment.visibility = View.INVISIBLE

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

        if (marker != null) {
            val sharePlaylist = SharePlaylistsConfirmation(marker.tag as User, principalView)

            val manager = activity?.supportFragmentManager

            val transaction = manager?.beginTransaction()
            transaction?.add(sharePlaylist, "shareFragment")
            transaction?.commit()
        }

        return true
    }
    //End overrides

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
        titleLocationFragment.visibility = View.VISIBLE
        allowShareLocationFragment.visibility = View.VISIBLE
        denyShareLocationFragment.visibility = View.INVISIBLE

        mMap.isMyLocationEnabled = true

        getUserLocation { latLng ->
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    latLng,
                    14f
                )
            )
        }
    }
    //End map permission

    private fun sharePermission() {
        getUserLocation { latLng ->
            saveUserData(latLng)
            getAllUsers()

            allowShareLocationFragment.visibility = View.INVISIBLE
            denyShareLocationFragment.visibility = View.VISIBLE
        }
    }

    private fun removeSharePermission() {
        viewModelP.removeUserMap()

        Handler().postDelayed({
            mMap.clear()
            allowShareLocationFragment.visibility = View.VISIBLE
        }, 1500)

        denyShareLocationFragment.visibility = View.INVISIBLE
    }

    private fun placeMarkerOnMap(user: User) {
        if (user.latitude != null && user.longitude != null && user.name != null && user.uid != null) {
            val location = LatLng(user.latitude, user.longitude)

            val markerOptions = MarkerOptions().position(location).title(user.name)
            mMap.addMarker(markerOptions).tag = user
        }
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

    private fun getAllUsers() {
        viewModelP.allUsersMap.observe(viewLifecycleOwner, Observer { users ->
            mMap.clear()

            users.forEach { user ->
                if (user.latitude != null && user.longitude != null && user.name != null && user.uid != null) {
                    placeMarkerOnMap(user)
                }
            }
        })

        viewModelP.getAllUsersMap(lastLocation)
    }
}
