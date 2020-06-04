import com.google.gson.annotations.SerializedName

data class MusicDTO(
    val id: Int? = null,
    val readable: String? = null,
    val title: String? = null,
    val link: String? = null,
    val duration: Int? = null,
    val rank: Long? = null,
    @SerializedName("explicit_lyrics")
    val explicit: String? = null,
    @SerializedName("explicit_content_lyrics")
    val explicit_content: String? = null,
    @SerializedName("explicit_content_cover")
    val explicitCover: String? = null,
    val preview: String? = null,
    val artist: ArtistDTO? = null,
    val album: AlbumDTO? = null,
    val type: String? = null
)

data class MusicListDTO(
    val data: Array<MusicDTO>? = null,
    val total: Int? = null,
    val next: String? = null
)

data class ArtistDTO(
    val id: Int? = null,
    val name: String? = null,
    val link: String? = null,
    val picture: String? = null,
    @SerializedName("picture_small")
    val picSmal: String? = null,
    @SerializedName("picture_medium")
    val picMedium: String? = null,
    @SerializedName("picture_big")
    val picBig: String? = null,
    @SerializedName("picture_xl")
    val picXL: String? = null,
    val tracklist: String? = null,
    val type: String? = null
)

data class AlbumDTO(
    val id: Int? = null,
    val title: String? = null,
    val cover: String? = null,
    @SerializedName("cover_small")
    val coverSmall: String? = null,
    @SerializedName("cover_medium")
    val coverMedium: String? = null,
    @SerializedName("cover_big")
    val coverBig: String? = null,
    @SerializedName("cover_xl")
    val coverXL: String? = null,
    val tracklist: String? = null,
    val type: String? = null
)