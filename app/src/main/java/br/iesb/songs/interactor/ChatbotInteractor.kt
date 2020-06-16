package br.iesb.songs.interactor

import android.content.Context
import br.iesb.songs.data_class.DialogflowRequest
import br.iesb.songs.repository.ChatbotRepository

class ChatbotInteractor(context: Context) {
    private val repository = ChatbotRepository(context, "https://app-songs.herokuapp.com/")

    fun sendText(request: DialogflowRequest, callback: (String) -> Unit) {
        repository.sendText(request, callback)
    }
}