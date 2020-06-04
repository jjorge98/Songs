package br.iesb.songs.interactor

import android.content.Context
import br.iesb.songs.R
import br.iesb.songs.data_class.Artist
import br.iesb.songs.data_class.Music
import br.iesb.songs.repository.DeezerRepository

class DeezerInteractor(context: Context) {
    private val repository = DeezerRepository(context, context.getString(R.string.url))

    fun search(find: String, callback: (musicSet: Array<Music>) -> Unit) {
        repository.search(find, callback)
    }

    fun artist(id: Int?, callback: (music: Array<Music>) -> Unit){
        repository.artist(id, callback)
    }

    fun favoritesList(callback: (musicSet: Array<Music>) -> Unit) {
        repository.favoritesList(callback)
    }

    fun favorite(fav: Music) {
        repository.favorite(fav)
    }

    fun removeFavorite(id: Int?){
        repository.removeFavorite(id)
    }

    fun verifyFav(musicId: Int?, callback: (id: Int) -> Unit) {
        if (musicId != null) {
            repository.verifyFav(musicId){
                if(it == null){
                    callback(0)
                } else{
                    callback(it)
                }
            }
        }
    }
}