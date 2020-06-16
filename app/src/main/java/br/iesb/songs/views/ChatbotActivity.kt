package br.iesb.songs.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import br.iesb.songs.R
import br.iesb.songs.view_model.ChatbotViewModel

import kotlinx.android.synthetic.main.activity_chatbot.*


class ChatbotActivity : AppCompatActivity() {
    private val viewModelC: ChatbotViewModel by lazy {
        ViewModelProvider(this).get(ChatbotViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)

//        id_backMenuInicialFloating.setOnClickListener { backListFavoritos() }
    }

    private fun backListFavoritos() {
        val intentMain = Intent(this, MainActivity::class.java)
        startActivity(intentMain)
    }

    fun sendText(){
        viewModelC.sendText("Olá!", "song@song.com", "random1234584random"){response ->

        }
    }
}
