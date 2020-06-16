package br.iesb.songs.interactor

import android.content.Context
import android.location.Location
import br.iesb.songs.data_class.Music
import br.iesb.songs.data_class.User
import br.iesb.songs.repository.PlaylistRepository
import com.google.android.gms.maps.model.LatLng
import java.util.*

class PlaylistInteractor(private val context: Context) {
    private val repository = PlaylistRepository(context)

    fun newPlaylist(set: MutableSet<String>, name: String, callback: (String) -> Unit) {
        if (name.isEmpty()) {
            callback("EMPTY")
        } else {
            run stop@{
                set.forEach { playlist ->
                    if (playlist.toUpperCase(Locale.ROOT) == name.toUpperCase(Locale.ROOT)) return@stop callback(
                        "EQUAL"
                    )
                }

                repository.newPlaylist(name)
                callback("OK")
            }
        }
    }

    fun showPlaylist(callback: (String?) -> Unit) {
        repository.showPlaylists(callback)
    }

    fun verifyPlaylist(musicId: Int?, playlist: String, callback: (id: Int) -> Unit) {
        if (musicId != null) {
            repository.verifyPlaylist(musicId, playlist) {
                if (it == null) {
                    callback(0)
                } else {
                    callback(it)
                }
            }
        }
    }

    fun addPlaylist(music: Music, playlist: String) {
        repository.addPlaylist(music, playlist)
    }

    fun playlist(playlist: String, callback: (musicSet: MutableSet<Music>) -> Unit) {
        repository.playlist(playlist, callback)
    }

    fun removeFromPlaylist(playlist: String, id: Int?) {
        if (id != null) {
            repository.removeFromPlaylist(playlist, id)
        }
    }

    fun deletePlaylist(playlist: String) {
        repository.deletePlaylist(playlist)
    }

    fun saveUserMap(user: User) {
        repository.saveUserMap(user)
    }

    fun removeUserMap() {
        repository.removeUserMap()
    }

    fun getAllUsersMap(location: Location, callback: (User) -> Unit) {
        val currentLatLng = LatLng(location.latitude, location.longitude)

        repository.getAllUsersMap { user ->
            if (user.latitude != null && user.longitude != null) {
                val userLatLng = LatLng(user.latitude, user.longitude)
                val distance = distance(currentLatLng, userLatLng)

                if (distance <= 500) {
                    callback(user)
                }
            }
        }
    }

    private fun distance(start: LatLng, end: LatLng): Float {
        val sLat = start.latitude
        val sLng = start.longitude
        val eLat = end.latitude
        val eLng = end.longitude
        val response = FloatArray(1)

        Location.distanceBetween(sLat, sLng, eLat, eLng, response)

        return response[0]
    }

    fun sharedFavorites(user: User?, callback: (musicSet: MutableSet<Music>) -> Unit) {
        repository.sharedFavorites(user, callback)
    }

    fun addSharedPlaylist(music: Music, playlist: String, user: User) {
        repository.addSharedPlaylist(music, playlist, user)
    }

    fun userLocationVerify(latLng: LatLng, uid: String, callback: (String) -> Unit) {
        repository.userLocationVerify(uid) { user ->
            if (user == null) {
                callback("NOT FOUND")
            } else {
                if (user.latitude != null && user.longitude != null) {
                    val newLatLng = LatLng(user.latitude, user.longitude)

                    val distance = distance(latLng, newLatLng)

                    if (distance >= 500) {
                        callback("OUT OF RANGE")
                    } else {
                        callback("OK")
                    }
                } else {
                    callback("ERROR")
                }
            }
        }
    }
}