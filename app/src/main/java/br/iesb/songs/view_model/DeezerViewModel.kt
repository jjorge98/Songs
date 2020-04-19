package br.iesb.songs.view_model

import android.app.Application
import android.app.SharedElementCallback
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.iesb.songs.data_class.Artist
import br.iesb.songs.data_class.Music
import br.iesb.songs.interactor.DeezerInteractor

class DeezerViewModel(val app: Application) : AndroidViewModel(app) {
    private val interactor = DeezerInteractor(app.applicationContext)

    val musicSet = MutableLiveData<Array<Music>>()
    val allFavorites = MutableLiveData<Array<Music>>()
    val artists = MutableLiveData<Artist>()

    fun search(find: String) {
        interactor.search(find) { result ->
            musicSet.value = result
        }
    }

    fun artist(id: Int){
        interactor.artist(id){result ->
            artists.value = result
        }
    }

    fun favoritesList(){
        interactor.favoritesList(){result ->
            allFavorites.value = result
        }
    }

    fun getId(callback: (id: Int) -> Unit){
        interactor.getId(callback)
    }

    fun favorite(fav: Music, id: Int){
        interactor.favorite(fav, id)
    }

    fun verifyFav(musicId: Int, callback: (verified: String) -> Unit){
        interactor.verifyFav(musicId){
            if(it != 0){
                callback("exists")
            } else{
                callback("doesntExists")
            }
        }
    }
}