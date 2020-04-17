package br.iesb.songs.interactor

import android.content.Context
import br.iesb.songs.R
import br.iesb.songs.data_class.Music
import br.iesb.songs.repository.DeezerRepository

class DeezerInteractor(private val context: Context) {
    private val repository = DeezerRepository(context, context.getString(R.string.url))

    fun search(find: String, callback: (musicSet: Array<Music>) -> Unit) {
        repository.search(find, callback)
    }
}