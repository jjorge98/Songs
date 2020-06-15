package br.iesb.songs.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import br.iesb.songs.data_class.Music
import br.iesb.songs.interactor.DeezerInteractor

class DeezerViewModel(val app: Application) : AndroidViewModel(app) {
    private val interactor = DeezerInteractor(app.applicationContext)

    val musicSet = MutableLiveData<MutableSet<Music>>()

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
}
