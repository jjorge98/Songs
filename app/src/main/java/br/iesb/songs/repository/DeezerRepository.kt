package br.iesb.songs.repository

import ArtistDTO
import MusicListDTO
import android.content.Context
import br.iesb.songs.data_class.Music
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface DeezerService {
    @GET("search")
    fun search(
        @Query("q") search: String,
        @Header("x-rapidapi-host") host: String = "deezerdevs-deezer.p.rapidapi.com",
        @Header("x-rapidapi-key") key: String = "ab2b40b599msh9fbe1da77c7e51ap1dbd17jsnb46ee7ad8fb6"
    ): Call<MusicListDTO>

    @GET("artist/{id}")
    fun artist(
        @Path("id") id: Int?,
        @Header("x-rapidapi-host") host: String = "deezerdevs-deezer.p.rapidapi.com",
        @Header("x-rapidapi-key") key: String = "ab2b40b599msh9fbe1da77c7e51ap1dbd17jsnb46ee7ad8fb6"
    ): Call<ArtistDTO>

    @GET("top?limit=50")
    fun tracklist(): Call<MusicListDTO>
}

class DeezerRepository(private val context: Context, url: String) : RetrofitInit(context, url) {
    private val service = retrofit.create(DeezerService::class.java)
    private val database = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val uid = auth.uid

    fun search(find: String, callback: (musicSet: MutableSet<Music>) -> Unit) {
        service.search(find).enqueue(object : Callback<MusicListDTO> {
            override fun onFailure(call: Call<MusicListDTO>, t: Throwable) {
                callback(mutableSetOf())
            }

            override fun onResponse(call: Call<MusicListDTO>, response: Response<MusicListDTO>) {
                val result = mutableSetOf<Music>()
                val musics = response.body()?.data

                musics?.forEach { m ->
                    val new = Music(
                        id = m.id,
                        title = m.title,
                        link = m.link,
                        duration = m.duration,
                        preview = m.preview,
                        coverImg = m.album?.cover,
                        albumID = m.album?.id,
                        artist = m.artist?.name,
                        artistID = m.artist?.id,
                        artistImage = m.artist?.picture
                    )

                    result.add(new)
                }

                callback(result)
            }
        })
    }

    fun artist(id: Int?, callback: (musicCall: MutableSet<Music>) -> Unit) {
        service.artist(id).enqueue(object : Callback<ArtistDTO> {
            override fun onFailure(call: Call<ArtistDTO>, t: Throwable) {
                callback(mutableSetOf())
            }

            override fun onResponse(call: Call<ArtistDTO>, response: Response<ArtistDTO>) {
                val artist = response.body()

                if (artist?.tracklist != null) {
                    val result = mutableSetOf<Music>()
                    tracklistHTTP(artist.tracklist) { song ->
                        song.artistImage = artist.picBig
                        result.add(song)
                        callback(result)
                    }
                }
            }
        })
    }

    fun favoritesList(callback: (musicSet: MutableSet<Music>) -> Unit) {
        val query = database.getReference("$uid/favorites")
        val result = mutableSetOf<Music>()

        query.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                //
            }

            override fun onDataChange(p0: DataSnapshot) {
                val list = p0.children

                list.forEach { child ->
                    val music = child.getValue(Music::class.java)

                    if (music != null) {
                        result.add(music)
                    }
                }

                callback(result)
            }
        })
    }

    fun verifyFav(musicId: Int, callback: (id: Int?) -> Unit) {
        val a = database.reference
        val query = a.child("$uid/favorites").orderByChild("id").equalTo(musicId.toDouble())

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                //
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { result ->
                    val id = result.child("id").getValue(Int::class.java)
                    callback(id)
                }
            }
        })
    }

    fun removeFavorite(musicId: Int?) {
        if (musicId != null) {
            val remove = database.getReference("$uid/favorites/$musicId")
            remove.removeValue()
        }
    }

    fun favorite(fav: Music) {
        val favorites = database.getReference("$uid/favorites/${fav.id}")

        favorites.setValue(fav)
    }

    private fun tracklistHTTP(track: String, callback: (result: Music) -> Unit) {
        val r = RetrofitInit(context, track.replace("top?limit=50", ""))
        val s = r.retrofit.create(DeezerService::class.java)
        s.tracklist().enqueue(object : Callback<MusicListDTO> {
            override fun onFailure(call: Call<MusicListDTO>, t: Throwable) {
                //
            }

            override fun onResponse(
                call: Call<MusicListDTO>,
                response: Response<MusicListDTO>
            ) {
                val musics = response.body()?.data

                musics?.forEach { m ->
                    val new = Music(
                        id = m.id,
                        title = m.title,
                        link = m.link,
                        duration = m.duration,
                        preview = m.preview,
                        coverImg = m.album?.cover,
                        albumID = m.album?.id,
                        artist = m.artist?.name,
                        artistID = m.artist?.id,
                        artistImage = m.artist?.picMedium
                    )
                    callback(new)
                }
            }
        })
    }
}