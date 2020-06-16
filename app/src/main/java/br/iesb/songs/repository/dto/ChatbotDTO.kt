package br.iesb.songs.repository.dto

data class ChatbotDTO(
    val queryResult: QueryResult? = null
)

data class QueryResult(
    val fulfillmentText: String? = null
)
