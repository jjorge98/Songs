package br.iesb.songs.view_model

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.iesb.songs.data_class.Music
import br.iesb.songs.data_class.User
import br.iesb.songs.interactor.PlaylistInteractor
import com.google.android.gms.maps.model.LatLng

class PlaylistViewModel(val app: Application) : AndroidViewModel(app) {
    private val interactor = PlaylistInteractor(app.applicationContext)

    val allSongs = MutableLiveData<MutableSet<Music>>()
    val playlists = MutableLiveData<MutableSet<String>>()
    val allUsersMap = MutableLiveData<MutableSet<User>>()
    val favorites = MutableLiveData<MutableSet<Music>>()

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

    fun showPlaylists() {
        val names = mutableSetOf<String>()
        interactor.showPlaylist() { playlistName ->
            if (playlistName != null) {
                names.add(playlistName)
            }

            playlists.value = names
        }
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

    fun addPlaylist(music: Music, playlist: String, callback: (String) -> Unit) {
        interactor.addPlaylist(music, playlist)

        val text = "Música \"${music.title}\" adicionada com sucesso."
        callback(text)
    }

    fun playlist(playlist: String) {
        allSongs.value?.clear()
        interactor.playlist(playlist) { result ->
            allSongs.value = result
        }
    }

    fun removeFromPlaylist(playlist: String, id: Int?) {
        interactor.removeFromPlaylist(playlist, id)
    }

    fun deletePlaylist(playlist: String) {
        interactor.deletePlaylist(playlist)
    }

    fun saveUserMap(name: String, latLng: LatLng) {
        val user = User(name = name, latitude = latLng.latitude, longitude = latLng.longitude)

        interactor.saveUserMap(user)
    }

    fun removeUserMap() {
        interactor.removeUserMap()
    }

    fun getAllUsersMap(location: Location) {
        val users = mutableSetOf<User>()
        interactor.getAllUsersMap(location) { user ->
            users.add(user)

            allUsersMap.value = users
        }
    }

    fun sharedFavorites(user: User?) {
        interactor.sharedFavorites(user) { set ->
            favorites.value = set
        }
    }

    fun addSharedPlaylist(music: Music, playlist: String, user: User, callback: (String) -> Unit) {
        interactor.addSharedPlaylist(music, playlist, user) { response ->
            if (response == "OK") {
                val text = "Música \"${music.title}\" adicionada com sucesso."
                callback(text)
            } else if (response == "NOT FOUND") {
                val text =
                    "Usuário está offline para funcionalidade, portanto não é possível adicionar na playlist compartilhada!"
                callback(text)
            }
        }
    }
}

