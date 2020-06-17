package br.iesb.songs.views

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.iesb.songs.R
import br.iesb.songs.view_model.ChatbotViewModel
import br.iesb.songs.views.adapter.MessageAdapter
import kotlinx.android.synthetic.main.activity_chatbot.*
import java.util.*
import kotlin.random.Random

class ChatbotActivity : AppCompatActivity() {
    private val viewModelC: ChatbotViewModel by lazy {
        ViewModelProvider(this).get(ChatbotViewModel::class.java)
    }

    private val adapter = MessageAdapter(this)
    private lateinit var inputText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)

        inputText = textChatbot

        initRecyclerView()
        send.setOnClickListener { sendText() }

        id_backMenuInicialFloating.setOnClickListener { back() }
    }

    private fun initRecyclerView() {
        messageRecyclerView.layoutManager = LinearLayoutManager(this)
        messageRecyclerView.adapter = adapter
    }

    private fun back() {
        val intentMain = Intent(this, MainActivity::class.java)
        startActivity(intentMain)
    }

    private fun sendText() {
        val message = textChatbot.text.toString()

        viewModelC.verifyEmpty(message) { response ->
            if (response == "OK") {
                adapter.addMessage(message, "USER")
                inputText.text = ""

                val data = Date().toString().substring(0, 10).replace(" ", "")
                val random = Random.nextInt(10000000, 1000000000)
                val sessionId = data + random

                viewModelC.sendText(message, "song@song.com", sessionId) { chatMessage ->
                    adapter.addMessage(chatMessage, "GEO")
                }
            }
        }
    }
}
