package br.iesb.songs.repository

import android.content.Context
import br.iesb.songs.data_class.Artist
import br.iesb.songs.data_class.Music
import br.iesb.songs.repository.dto.ArtistDTO
import br.iesb.songs.repository.dto.MusicListDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*
import java.net.MulticastSocket

interface DeezerService {
    @GET("search")
    fun search(
        @Query("q") search: String,
        @Header("x-rapidapi-host") host: String = "deezerdevs-deezer.p.rapidapi.com",
        @Header("x-rapidapi-key") key: String = "ab2b40b599msh9fbe1da77c7e51ap1dbd17jsnb46ee7ad8fb6"
    ): Call<MusicListDTO>

    @GET("artist")
    fun artist(
        @Query("id") id: Int,
        @Header("x-rapidapi-host") host: String = "deezerdevs-deezer.p.rapidapi.com",
        @Header("x-rapidapi-key") key: String = "ab2b40b599msh9fbe1da77c7e51ap1dbd17jsnb46ee7ad8fb6"
    ): Call<ArtistDTO>
}

class DeezerRepository(context: Context, url: String) : RetrofitInit(context, url) {
    private val service = retrofit.create(DeezerService::class.java)
    private val database = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val uid = auth.uid

    fun search(find: String, callback: (musicSet: Array<Music>) -> Unit) {
        service.search(find).enqueue(object : Callback<MusicListDTO> {
            override fun onFailure(call: Call<MusicListDTO>, t: Throwable) {
                callback(arrayOf())
            }

            override fun onResponse(call: Call<MusicListDTO>, response: Response<MusicListDTO>) {
                val result = mutableListOf<Music>()
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
                        artistID = m.artist?.id
                    )

                    result.add(new)
                }

                callback(result.toTypedArray())
            }
        })
    }

    fun artist(id: Int, callback: (backArtist: Artist) -> Unit) {
        service.artist(id).enqueue(object : Callback<ArtistDTO> {
            override fun onFailure(call: Call<ArtistDTO>, t: Throwable) {
                callback(Artist(tracklist = arrayOf()))
            }

            override fun onResponse(call: Call<ArtistDTO>, response: Response<ArtistDTO>) {
                val result = mutableListOf<Music>()
                val artist = response.body()

                artist?.tracklist?.forEach { m ->
                    val new = Music(
                        id = m.id,
                        title = m.title,
                        link = m.link,
                        duration = m.duration,
                        preview = m.preview,
                        coverImg = m.album?.cover,
                        albumID = m.album?.id,
                        artist = m.artist?.name,
                        artistID = m.artist?.id
                    )
                    result.add(new)
                }

                val newArtist = Artist(
                    id = artist?.id,
                    name = artist?.name,
                    link = artist?.link,
                    picture = artist?.picture,
                    tracklist = result.toTypedArray()
                )
                callback(newArtist)
            }
        })
    }

    fun favoritesList(callback: (musicSet: Array<Music>) -> Unit) {
        val query = database.getReference("$uid/favorites")
        val result = mutableListOf<Music>()

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

                callback(result.toTypedArray())
            }

        })
    }

    fun getId(callback: (id: Int?) -> Unit) {
        val ids = database.getReference("$uid/ids/favorites")

        ids.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                //
            }

            override fun onDataChange(p0: DataSnapshot) {
                val result = p0.getValue(Int::class.java)
                callback(result)
            }

        })

    }

    fun verifyFav(musicId: Int, callback: (id: Int?) -> Unit) {
        val favs = database.getReference("$uid/favorites")

        val query = favs.orderByChild("id").equalTo("$musicId")

        query.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                //
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val music = snapshot.getValue(Music::class.java)

                if (music != null) {
                    callback(music.id)
                }
            }
        })
    }

    fun favorite(fav: Music, id: Int) {
        val favorites = database.getReference("$uid/favorites/$id")
        val ids = database.getReference("$uid/ids/favorites")

        favorites.setValue(fav)
        ids.setValue(id)
    }
}