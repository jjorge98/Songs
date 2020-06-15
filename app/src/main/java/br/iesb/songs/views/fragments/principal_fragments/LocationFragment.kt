package br.iesb.songs.views.fragments.principal_fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.iesb.songs.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class LocationFragment(context: Context) : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {
    private lateinit var mMap: GoogleMap
    var lastSelectedMark: Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val myLocation = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(myLocation).title("Minha localização"))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 14f))
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        lastSelectedMark = marker
        marker?.showInfoWindow()

        return true
    }

}