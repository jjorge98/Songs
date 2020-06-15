package br.iesb.songs.interactor

import android.content.Context
import br.iesb.songs.data_class.Music
import br.iesb.songs.data_class.User
import br.iesb.songs.repository.PlaylistRepository
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

    fun getAllUsersMap(callback: (User?) -> Unit) {
        repository.getAllUsersMap(callback)
    }

    fun sharedFavorites(user: User?, callback: (musicSet: MutableSet<Music>) -> Unit) {
        repository.sharedFavorites(user, callback)
    }
}