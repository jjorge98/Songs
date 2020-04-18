package br.iesb.songs.view_model

import android.app.Application
import android.app.SharedElementCallback
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.iesb.songs.data_class.Music
import br.iesb.songs.interactor.DeezerInteractor

class DeezerViewModel(val app: Application) : AndroidViewModel(app) {
    private val interactor = DeezerInteractor(app.applicationContext)

    val musicSet = MutableLiveData<Array<Music>>()

    fun search(find: String) {
        interactor.search(find) { result ->
            musicSet.value = result
        }
    }

    fun getId(callback: (id: Int) -> Unit){
        interactor.getId(callback)
    }

    fun favorite(fav: Music, id: Int){
        interactor.favorite(fav, id)
    }
}