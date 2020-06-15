package br.iesb.songs.interactor

import android.content.Context
import br.iesb.songs.R
import br.iesb.songs.data_class.Music
import br.iesb.songs.repository.DeezerRepository

class DeezerInteractor(context: Context) {
    private val repository = DeezerRepository(context, context.getString(R.string.url))

    fun search(find: String, callback: (musicSet: MutableSet<Music>) -> Unit) {
        repository.search(find, callback)
    }

    fun artist(id: Int?, callback: (music: MutableSet<Music>) -> Unit) {
        repository.artist(id, callback)
    }
}