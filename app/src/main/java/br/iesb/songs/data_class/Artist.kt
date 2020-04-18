package br.iesb.songs.data_class

data class Artist(
    val id: Int? = null,
    val name: String? = null,
    val link: String? = null,
    val picture: String? = null,
    val tracklist: Array<Music>
)