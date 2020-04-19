package br.iesb.songs.data_class

data class Music(
    val id: Int? = null,
    val title: String? = null,
    val link: String? = null,
    val duration: Int? = null,
    val preview: String? = null,
    val coverImg: String? = null,
    val artist: String? = null,
    val artistID: Int? = null,
    val albumID: Int? = null,
    var artistImage: String? = null
)