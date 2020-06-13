package br.iesb.songs.interactor

import android.content.Context
import br.iesb.songs.R
import br.iesb.songs.data_class.Music
import br.iesb.songs.repository.DeezerRepository
import java.util.*

class DeezerInteractor(context: Context) {
    private val repository = DeezerRepository(context, context.getString(R.string.url))

    fun search(find: String, callback: (musicSet: MutableSet<Music>) -> Unit) {
        repository.search(find, callback)
    }

    fun artist(id: Int?, callback: (music: MutableSet<Music>) -> Unit) {
        repository.artist(id, callback)
    }

    fun favoritesList(callback: (musicSet: MutableSet<Music>) -> Unit) {
        repository.favoritesList(callback)
    }

    fun addPlaylist(music: Music, playlist: String) {
        repository.addPlaylist(music, playlist)
    }

    fun removeFavorite(id: Int?) {
        repository.removeFavorite(id)
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

    fun showPlaylist(callback: (String?) -> Unit) {
        repository.showPlaylists(callback)
    }

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
}