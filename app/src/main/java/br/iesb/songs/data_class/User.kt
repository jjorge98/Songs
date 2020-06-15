package br.iesb.songs.data_class

data class User(
    val name: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    var uid: String? = null
)