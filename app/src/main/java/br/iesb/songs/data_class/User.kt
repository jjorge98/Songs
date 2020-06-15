package br.iesb.songs.data_class

import com.google.android.gms.maps.model.LatLng

data class User(
    val name: String? = null,
    val latLng: LatLng? = null,
    var uid: String? = null
)