package br.iesb.songs.data_class

data class DialogflowRequest(
    val text: String,
    val email: String,
    val sessionId: String
)
