package br.iesb.songs.view_model

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.iesb.songs.data_class.Music
import br.iesb.songs.interactor.DeezerInteractor

class DeezerViewModel(val app: Application) : AndroidViewModel(app) {
    private val interactor = DeezerInteractor(app.applicationContext)
    val playlists = MutableLiveData<MutableSet<String>>()

    val musicSet = MutableLiveData<MutableSet<Music>>()
    val allSongs = MutableLiveData<MutableSet<Music>>()

    fun search(find: String) {
        interactor.search(find) { result ->
            musicSet.value?.clear()
            musicSet.value = result
        }
    }

    fun artist(id: Int?) {
        interactor.artist(id) { result ->
            musicSet.value = result
        }
    }

    fun playlist(playlist: String) {
        allSongs.value?.clear()
        interactor.playlist(playlist) { result ->
            allSongs.value = result
        }
    }

    fun addPlaylist(music: Music, playlist: String, callback: (String) -> Unit) {
        interactor.addPlaylist(music, playlist)

        val text = "Música \"${music.title}\" adicionada com sucesso."
        callback(text)
    }

    fun removeFromPlaylist(playlist: String, id: Int?) {
        interactor.removeFromPlaylist(playlist, id)
    }

    fun verifyPlaylist(musicId: Int, playlist: String, callback: (verified: String) -> Unit) {
        interactor.verifyPlaylist(musicId, playlist) {
            if (it != 0) {
                callback("exists")
            } else {
                callback("doesntExists")
            }
        }
    }

    fun showPlaylists() {
        val names = mutableSetOf<String>()
        playlists.value = names
        interactor.showPlaylist() { playlistName ->
            if (playlistName != null) {
                names.add(playlistName)
            }

            playlists.value = names
        }
    }

    fun newPlaylist(set: MutableSet<String>, name: String, callback: (Array<String>) -> Unit) {
        interactor.newPlaylist(set, name) { response ->
            if (response == "EMPTY") {
                val result = arrayOf("ERROR", "Por favor, preencha o nome da playlist!")
                callback(result)
            } else if (response == "EQUAL") {
                val result =
                    arrayOf("ERROR", "O nome de playlist já existe. Por favor informe um novo!")
                callback(result)
            } else {
                val result = arrayOf("OK", "Playlist criada com sucesso!")
                callback(result)
            }
        }
    }

    fun deletePlaylist(playlist: String) {
        interactor.deletePlaylist(playlist)
    }
}
